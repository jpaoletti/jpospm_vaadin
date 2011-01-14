package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.jpos.ee.pm.core.*;
import org.jpos.ee.pm.core.operations.EditOperation;
import org.jpos.ee.pm.core.operations.OperationCommandSupport;
import org.jpos.ee.pm.vaadin.components.GenericForm;
import org.jpos.ee.pm.vaadin.components.PMMainWindow;

/**
 *
 * @author jpaoletti
 */
public class EditCommand extends GenericCommand {

    public EditCommand(PMContext ctx) {
        super(ctx);
    }

    @Override
    public HorizontalLayout execute() {
        try {
            doExcecute();

            HorizontalLayout l = new HorizontalLayout();
            l.setSizeFull();
            VerticalLayout vl = new VerticalLayout();
            vl.setMargin(false, true, true, true);
            createTitle(vl);
            createOperationBar(vl);

            final GenericForm f = new GenericForm(getCtx(), getCtx().getSelected().getInstance());

            final HorizontalLayout footer = new HorizontalLayout();
            final Button discardChanges = new Button("Discard changes",
                    new Button.ClickListener() {

                        public void buttonClick(ClickEvent event) {
                            f.discard();
                        }
                    });
            //footer.addComponent(discardChanges);
            Button apply = new Button("Ok", new Button.ClickListener() {

                public void buttonClick(ClickEvent event) {
                    try {
                        getCtx().put("param_finish", "1");
                        for (Field field : getCtx().getEntity().getAllFields()) {
                            final com.vaadin.ui.Field vf = f.getField(field.getId());
                            if (vf != null && !vf.isReadOnly()) {
                                getCtx().put("param_f_" + field.getId(), vf.getValue());
                                vf.validate();
                            }
                        }
                        doExcecute();

                        final PMContext c = new PMContext(getCtx().getSessionId());
                        final PMMainWindow window = (PMMainWindow) getCtx().get(WINDOW);
                        c.put(OperationCommandSupport.PM_ID, getCtx().get("entity"));
                        c.put(WINDOW, getCtx().get(WINDOW));
                        final GenericCommand cmd = CommandFactory.newCommand("show", c);
                        window.setMainScreen(cmd.execute());
                    } catch (Exception e) {
                        PresentationManager.getPm().error(e);
                    }
                }
            });

            footer.addComponent(apply);
            f.setFooter(footer);

            vl.addComponent(f);

            finalPanel(l, vl);
            return l;
        } catch (PMException ex) {
            PresentationManager.getPm().error(ex);
            return getErrorLayout(ex);
        }
    }

    protected void doExcecute() throws PMException {
        EditOperation op = new EditOperation("edit");
        op.excecute(getCtx());
    }
}
