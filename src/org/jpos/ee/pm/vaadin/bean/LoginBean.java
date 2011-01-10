package org.jpos.ee.pm.vaadin.bean;

/**
 *
 * @author jpaoletti
 */
public class LoginBean {
    private String username;
    private String password;

    public LoginBean() {
        username = "super";
        password = "test";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
