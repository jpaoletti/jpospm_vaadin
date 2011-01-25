package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.event.Action;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.jpos.ee.pm.core.*;
import org.jpos.ee.pm.core.operations.ListOperation;
import org.jpos.ee.pm.core.operations.OperationCommandSupport;
import org.jpos.ee.pm.vaadin.components.*;

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

            op.excecute(getCtx());

            HorizontalLayout l = new HorizontalLayout();
            l.setSizeFull();
            VerticalLayout vl = new VerticalLayout();
            //vl.setSpacing(true);
            vl.setMargin(false, true, true, true);
            createTitle(vl);
            createOperationBar(vl, Operation.SCOPE_GRAL);

            final Table table = new Table();
            table.setContainerDataSource(new ListContainer(getCtx(), getCtx().getList()));
            table.setHeight("150px");
            table.setPageLength(getCtx().getList().getRowsPerPage());
            table.setSizeFull();

            for (Field field : getCtx().getEntity().getOrderedFields()) {
                table.setColumnHeader(field.getId(), PresentationManager.getMessage("pm.field." + getCtx().getEntity().getId() + "." + field.getId()));
            }

            table.addActionHandler(new Action.Handler() {

                public Action[] getActions(Object target, Object sender) {
                    final PMMainWindow window = (PMMainWindow) getCtx().get(WINDOW);
                    try {
                        final Operations itemOperations = getMyOperations().getItemOperations();
                        final Action[] actions = new Action[itemOperations.getOperations().size()];
                        int i = 0;
                        for (Operation operation : itemOperations.getOperations()) {
                            PMContext c = new PMContext(getCtx().getSessionId());
                            c.put(OperationCommandSupport.PM_ID, getCtx().get("entity"));
                            c.put(OperationCommandSupport.PM_ITEM, target.toString());
                            c.put(WINDOW, window);
                            c.setEntityContainer(getCtx().getEntityContainer());
                            actions[i] = new LocalListAction(c, operation);
                            actions[i].setIcon(getOperationIcon(operation));
                            i++;
                        }
                        return actions;
                    } catch (PMException ex) {
                        window.sendError(ex);
                    }
                    return null;
                }

                protected Operations getMyOperations() throws PMException {
                    return getCtx().getEntity().getOperations().getOperationsFor(getCtx().getOperation());
                }

                public void handleAction(Action action, Object sender, Object target) {
                    try {
                        LocalListAction lla = (LocalListAction) action;
                        lla.doIt();
                    } catch (PMException ex) {
                        PresentationManager.getPm().error(ex);
                    }
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
            super(PresentationManager.getMessage("operation." + operation.getId()));
            window = (PMMainWindow) ctx.get(WINDOW);
            command = CommandFactory.newCommand(operation.getId(), ctx);
        }

        public void doIt() throws PMException {
            if (command.getCtx().getEntity().getOperations().getOperation(command.getOperationId()).getConfirm()) {
                window.addWindow(
                        new ConfirmationWindow(
                        "pm.confirmation.question",
                        PresentationManager.getMessage("pm.confirmation.label"),
                        new ClickListener() {

                            public void buttonClick(ClickEvent event) {
                                window.setMainScreen(command.execute());
                            }
                        }));
            } else {
                window.setMainScreen(command.execute());
            }
        }
    }
}
