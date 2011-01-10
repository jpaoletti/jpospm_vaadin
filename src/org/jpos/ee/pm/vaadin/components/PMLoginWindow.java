package org.jpos.ee.pm.vaadin.components;

import com.vaadin.ui.*;

/**
 *
 * @author jpaoletti
 */
public class PMLoginWindow extends Window {
    private PMLoginForm form;

    public PMLoginWindow() {
        HorizontalLayout hPanel = new HorizontalLayout();
        hPanel.setSpacing(true);
        hPanel.setHeight("100%");
        hPanel.setWidth("100%");

        Panel panel = new Panel();
        panel.addStyleName("login");
        panel.setWidth("400px");
        hPanel.addComponent(panel);
        hPanel.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

        form = new PMLoginForm(null);
        panel.addComponent(form);
        setContent(hPanel);
        center();
    }
}
