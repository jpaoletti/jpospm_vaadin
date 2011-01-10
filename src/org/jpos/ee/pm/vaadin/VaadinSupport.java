package org.jpos.ee.pm.vaadin;

import com.vaadin.ui.*;
import org.jpos.ee.pm.core.PresentationManager;

/**
 * Helper methods
 * @author jpaoletti
 */
public class VaadinSupport {

    public static Panel newPanel(String titleKey, String width) {
        Panel panel = new Panel(PresentationManager.getMessage(titleKey));
        panel.setWidth(width);
        return panel;
    }

}
