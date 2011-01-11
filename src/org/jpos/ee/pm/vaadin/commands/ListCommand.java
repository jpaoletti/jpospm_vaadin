package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.event.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.jpos.ee.pm.core.Operation;
import org.jpos.ee.pm.core.Operations;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.core.operations.ListOperation;
import org.jpos.ee.pm.core.operations.OperationCommandSupport;
import org.jpos.ee.pm.vaadin.components.ListContainer;
import org.jpos.ee.pm.vaadin.components.PMMainWindow;
import org.jpos.ee.pm.vaadin.components.TableNavForm;

/**
 *
 * @author jpaoletti
 */
public class ListCommand extends GenericCommand {

    public ListCommand(PMContext ctx) {
        super(ctx);
    }

    @Override
    public HorizontalLayout execute() {
        try {
            ListOperation op = new ListOperation("list");
            getCtx().put(OperationCommandSupport.PM_ID, getCtx().get("entity"));

            if (getCtx().getBoolean("new", true)) {
                getCtx().put("order", null);
                getCtx().put("desc", false);
                getCtx().put("page", 1);
                getCtx().put("rows_per_page", 10);
            }
            op.excecute(getCtx());

            HorizontalLayout l = new HorizontalLayout();
            l.setSizeFull();
            VerticalLayout vl = new VerticalLayout();
            //vl.setSpacing(true);
            vl.setMargin(false, true, true, true);
            createTitle(vl);
            createOperationBar(vl, Operation.SCOPE_GRAL);

            final Table table = new Table();
            table.setContainerDataSource(new ListContainer(getCtx().getList()));
            table.setHeight("150px");
            table.setPageLength(getCtx().getList().getRowsPerPage());
            table.setSizeFull();

            table.addActionHandler(new Action.Handler() {

                public Action[] getActions(Object target, Object sender) {
                    try {
                        final Operations itemOperations = getMyOperations().getItemOperations();
                        final Action[] actions = new Action[itemOperations.getOperations().size()];
                        int i = 0;
                        for (Operation operation : itemOperations.getOperations()) {
                            PMContext c = new PMContext(getCtx().getSessionId());
                            c.put(OperationCommandSupport.PM_ID, getCtx().get("entity"));
                            c.put(OperationCommandSupport.PM_ITEM, target.toString());
                            c.put(WINDOW, getCtx().get(WINDOW));
                            actions[i] = new LocalListAction(c, operation);
                            actions[i].setIcon(getOperationIcon(operation));
                            i++;
                        }
                        return actions;
                    } catch (PMException ex) {
                        PresentationManager.getPm().error(ex);
                    }
                    return null;
                }

                protected Operations getMyOperations() throws PMException {
                    return getCtx().getEntity().getOperations().getOperationsFor(getCtx().getOperation());
                }

                public void handleAction(Action action, Object sender, Object target) {
                    LocalListAction lla = (LocalListAction)action;
                    lla.doIt();
                }
            });


            vl.addComponent(table);


            final TableNavForm navform = new TableNavForm(getCtx());
            vl.addComponent(navform);
            vl.setComponentAlignment(navform, Alignment.BOTTOM_CENTER);

            finalPanel(l, vl);

            return l;
        } catch (PMException ex) {
            PresentationManager.getPm().error(ex);
            return getErrorLayout(ex);
        }
    }

    private class LocalListAction extends Action {
        private PMMainWindow window;
        private GenericCommand command;

        public LocalListAction(String caption) {
            super(caption);
        }

        public LocalListAction(PMContext ctx, Operation operation) throws PMException {
            super(PresentationManager.getMessage("operation."+operation.getId()));
            window = (PMMainWindow) ctx.get(WINDOW);
            command = CommandFactory.newCommand(operation.getId(), ctx);
        }

        public void doIt() {
            final HorizontalLayout l = command.execute();
            window.setMainScreen(l);
        }
    }
}
