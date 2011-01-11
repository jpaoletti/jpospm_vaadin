package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.Operation;
import org.jpos.ee.pm.core.Operations;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.core.operations.OperationCommandSupport;
import org.jpos.ee.pm.vaadin.components.PMMainWindow;

/**
 *
 * @author jpaoletti
 */
public abstract class GenericCommand {

    public static final String WINDOW = "window";
    private String operationId;
    private PMContext ctx;

    public GenericCommand(PMContext ctx) {
        this.operationId = ctx.getString("operation");
        this.ctx = ctx;
    }

    protected void createOperationBar(VerticalLayout vl, String... scopes) throws PMException {
        HorizontalLayout bar = new HorizontalLayout();
        Operations opers = getCtx().getEntity().getOperations().getOperationsFor(getCtx().getOperation());
        if (scopes != null && scopes.length > 0) {
            opers = opers.getOperationsForScope(scopes);
        }
        for (final Operation operation : opers.getOperations()) {
            final Button button = new Button(operation.getId());
            button.setIcon(new ThemeResource("./operations/" + operation.getId() + ".gif"));
            bar.addComponent(button);
            button.addListener(new Button.ClickListener() {

                public void buttonClick(ClickEvent event) {
                    final PMContext c = new PMContext(getCtx().getSessionId());
                    c.put(OperationCommandSupport.PM_ID, getCtx().get("entity"));
                    c.put(WINDOW, getCtx().get(WINDOW));
                    c.put("new", false);
                    final PMMainWindow window = (PMMainWindow) ctx.get(WINDOW);
                    final GenericCommand cmd = CommandFactory.newCommand(operation.getId(), c);
                    window.setMainScreen(cmd.execute());
                }
            });
        }
        vl.addComponent(bar);
    }

    public abstract HorizontalLayout execute();

    public String getOperationId() {
        return operationId;
    }

    public PMContext getCtx() {
        return ctx;
    }

    protected void createTitle(final Layout vl) throws PMException {
        final Label title = new Label("<h2>" + getOperationTitle() + "</h2>");
        title.setContentMode(Label.CONTENT_XHTML);
        vl.addComponent(title);
    }

    protected HorizontalLayout getErrorLayout(PMException ex) {
        ex.printStackTrace();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    protected void finalPanel(HorizontalLayout l, VerticalLayout vl) {
        Panel p = new Panel();
        p.setStyle(Panel.STYLE_LIGHT);
        p.setScrollable(true);
        p.setSizeFull();
        p.setContent(vl);
        l.addComponent(p);
    }

    protected Panel newOperationPanel() throws PMException {
        final Panel panel = new Panel(getOperationTitle());
        panel.setWidth("100%");
        return panel;
    }

    protected String getOperationTitle() throws PMException {
        return getOperationTitle(getCtx().getEntity(), getCtx().getOperation());
    }

    protected String getOperationTitle(final Entity entity, final Operation operation) throws PMException {
        return PresentationManager.getMessage("pm.entity." + entity.getId()) + "(" + PresentationManager.getMessage("operation." + operation.getId()) + ")";
    }
}
