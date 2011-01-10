package org.jpos.ee.pm.vaadin;

import com.vaadin.Application;
import com.vaadin.ui.*;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PMSession;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.vaadin.commands.LoginCommand;
import org.jpos.ee.pm.vaadin.components.PMLoginWindow;
import org.jpos.ee.pm.vaadin.components.PMMainWindow;

/**
 *
 * @author jpaoletti
 */
public class PMVaadinApp extends Application {

    public String sessionId = null;

    @Override
    public void init() {
        if (PresentationManager.getPm() == null) {
            setMainWindow(new Window("Error"));
            getMainWindow().showNotification(
                    "Error",
                    PresentationManager.getMessage("pm_core.unespected.error"),
                    Window.Notification.TYPE_ERROR_MESSAGE);

        } else {
            setTheme(PresentationManager.getPm().getTemplate());
            if (PresentationManager.getPm().isLoginRequired()) {
                setMainWindow(new PMLoginWindow());
            } else {
                LoginCommand lc = new LoginCommand();
                try {
                    final String sid = lc.login("", "");
                    setSessionId(sid);
                    final PMMainWindow mw = new PMMainWindow();
                    setMainWindow(mw);
                    mw.init();
                } catch (PMException ex) {
                }
            }
        }

    }

    public PMSession getPMSession(){
        return PresentationManager.getPm().getSession(getSessionId());
    }

    public PMMainWindow getWindow() {
        return (PMMainWindow) getMainWindow();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
