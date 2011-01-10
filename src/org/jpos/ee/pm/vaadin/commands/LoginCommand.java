package org.jpos.ee.pm.vaadin.commands;

import org.jpos.ee.pm.core.*;
import org.jpos.ee.pm.security.core.*;

/**
 *
 * @author jpaoletti
 */
public class LoginCommand {

    public String login(String username, String password) throws PMException {
        if (PresentationManager.getPm().isLoginRequired()) {
            try {
                PMSecurityUser u = null;
                u = getConnector(null).authenticate(username, decrypt(password, "abc123"));
                return createSession(u).getId();
            } catch (UserNotFoundException e) {
                throw new PMException("pm_security.user.not.found");
            } catch (InvalidPasswordException e) {
                throw new PMException("pm_security.password.invalid");
            } catch (Exception e) {
                PresentationManager.getPm().error(e);
                throw new PMException("pm_core.unespected.error");
            }
        } else {
            PMSecurityUser u = new PMSecurityUser();
            u.setName(" ");
            return createSession(u).getId();
        }
    }

    private PMSession createSession(PMSecurityUser u) throws PMException {
        final PMSession s = PresentationManager.getPm().registerSession(PresentationManager.newSessionId());
        s.setUser(u);
        return s;
    }

    private PMSecurityConnector getConnector(PMContext ctx) {
        return PMSecurityService.getService().getConnector(ctx);
    }

    private String decrypt(String password, String seed) {
        return password;
    }

}
