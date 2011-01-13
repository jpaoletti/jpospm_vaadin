package org.jpos.ee.pm.vaadin;

import com.vaadin.ui.*;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMCoreObject;
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

    public static Field createField(final PMContext ctx, final String propertyId) {
        Field result = null;
        try {
            final org.jpos.ee.pm.core.Field pmfield = ctx.getEntity().getFieldById(propertyId);
            if (pmfield == null) {
                return null;
            }
            Object r = pmfield.visualize(ctx);
            if (r == null) {
                r = "";
            }
            //Ugly but keeps compatibility with generic converters.
            if (r instanceof Field) {
                result = (Field) r;
            } else {
                result = new TextField();
                result.setValue(r);
                result.setReadOnly(true);
            }
            result.setCaption(PresentationManager.getMessage("pm.field." + ctx.getEntity().getId() + "." + propertyId));
        } catch (Exception ex) {
            PresentationManager.getPm().error("Error getting " + propertyId + " converted value");
            PresentationManager.getPm().error(ex);
        }
        return result;
    }
}
