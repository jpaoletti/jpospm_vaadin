package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PresentationManager;

/**
 *
 * @author jpaoletti
 */
public abstract class GenericCommand {

    private String operationId;
    private PMContext ctx;

    public GenericCommand(PMContext ctx) {
        this.operationId = ctx.getString("operation");
        this.ctx = ctx;
    }

    public abstract HorizontalLayout execute();

    public String getOperationId() {
        return operationId;
    }

    public PMContext getCtx() {
        return ctx;
    }

    protected HorizontalLayout getErrorLayout(PMException ex) {
        ex.printStackTrace();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    protected Panel newOperationPanel() throws PMException {
        final Panel panel = new Panel(getOperationTitle());
        panel.setWidth("100%");
        return panel;
    }

    protected String getOperationTitle() throws PMException {
        return PresentationManager.getMessage("pm.entity." + getCtx().getEntity().getId()) + "(" + PresentationManager.getMessage("operation." + getCtx().getOperation().getId()) + ")";
    }
}
