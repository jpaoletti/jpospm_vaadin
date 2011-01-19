package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.ui.HorizontalLayout;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.core.operations.DeleteOperation;
import org.jpos.ee.pm.core.operations.OperationCommandSupport;
import org.jpos.ee.pm.vaadin.components.PMMainWindow;

/**
 * After deleting, this operation goes to list
 * 
 * @author jpaoletti
 */
class DeleteCommand extends GenericCommand {

    public DeleteCommand(PMContext ctx) {
        super(ctx);
    }

    @Override
    public HorizontalLayout execute() {
        try {
            DeleteOperation op = new DeleteOperation("delete");
            op.excecute(getCtx());
            return (HorizontalLayout) redirect(getCtx(), "list");
        } catch (PMException ex) {
            PresentationManager.getPm().error(ex);
            return getErrorLayout(ex);
        }
    }
}
