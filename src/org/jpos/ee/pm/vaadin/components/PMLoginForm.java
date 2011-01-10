package org.jpos.ee.pm.vaadin.components;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.*;
import org.jpos.ee.pm.core.*;
import org.jpos.ee.pm.vaadin.PMVaadinApp;
import org.jpos.ee.pm.vaadin.commands.LoginCommand;

/**
 *
 * @author jpaoletti
 */
public class PMLoginForm extends Form {

    private TextField username;
    private TextField password;

    public PMLoginForm(String error) {
        super();
        username = new TextField(PresentationManager.getMessage("pm.vaadin.username"), "super");
        addField("username", username);
        password = new TextField(PresentationManager.getMessage("pm.vaadin.password"), "test");
        password.setSecret(true);
        addField("password", password);
        if (error != null) {
            setComponentError(new UserError(PresentationManager.getMessage(error)));
        }
        // Have a button bar in the footer.
        HorizontalLayout okbar = new HorizontalLayout();
        okbar.setHeight("25px");
        getFooter().addComponent(okbar);
        final Button okbutton = new Button("OK", this, "commit");
        okbar.addComponent(okbutton);
        okbar.setComponentAlignment(okbutton, Alignment.TOP_RIGHT);
        okbar.addComponent(new Button("Reset", this, "discard"));
    }

    @Override
    public void commit() throws SourceException {
        super.commit();
        LoginCommand lc = new LoginCommand();
        try {
            final String sid = lc.login(username.getInputPrompt(), password.getInputPrompt());
            getPMApplication().setSessionId(sid);
            final PMMainWindow mw = new PMMainWindow();
            getPMApplication().setMainWindow(mw);
            mw.init();
            getPMApplication().removeWindow(this.getWindow());
        } catch (PMException ex) {
            setComponentError(new UserError(PresentationManager.getMessage(ex.getKey())));
        }

    }

    protected PMMainWindow getPMWindow() {
        return getPMApplication().getWindow();
    }

    protected PMVaadinApp getPMApplication() {
        return (PMVaadinApp) getApplication();
    }
}
