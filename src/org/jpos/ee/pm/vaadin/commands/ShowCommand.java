package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.jpos.ee.pm.core.*;
import org.jpos.ee.pm.core.operations.ShowOperation;
import org.jpos.ee.pm.vaadin.components.GenericForm;

/**
 *
 * @author jpaoletti
 */
public class ShowCommand extends GenericCommand {

    public ShowCommand(PMContext ctx) {
        super(ctx);
    }

    @Override
    public HorizontalLayout execute() {
        try {
            ShowOperation op = new ShowOperation("show");
            op.excecute(getCtx());

            HorizontalLayout l = new HorizontalLayout();
            l.setSizeFull();
            VerticalLayout vl = new VerticalLayout();
            vl.setMargin(false, true, true, true);
            createTitle(vl);
            createOperationBar(vl);

            final GenericForm f  = new GenericForm(getCtx(),getCtx().getSelected().getInstance());

            vl.addComponent(f);

            finalPanel(l, vl);
            return l;
        } catch (PMException ex) {
            PresentationManager.getPm().error(ex);
            return getErrorLayout(ex);
        }
    }
}
