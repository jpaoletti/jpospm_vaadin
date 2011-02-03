package org.jpos.ee.pm.vaadin.components;

import com.vaadin.ui.Table.CellStyleGenerator;
import org.jpos.ee.pm.core.*;

/**
 * 
 * @author jpaoletti
 */
public class HighlighterGeneration implements CellStyleGenerator {

    private EntityContainer container;

    public HighlighterGeneration(EntityContainer container) {
        this.container = container;
    }

    public String getStyle(Object itemId, Object propertyId) {
        //Here itemId is the index of the item in the pmlist
        final Object instance = container.getList().getContents().get((Integer) itemId);
        //And propertyId is the field id
        final Field field = container.getEntity().getFieldById((String) propertyId);
        final Highlights highlights = container.getEntity().getHighlights();
        if (highlights != null) {
            //We need to get a hightliht if there is one. First, item scoped
            final Highlight highlight = highlights.getHighlight(container.getEntity(), field, instance);
            if (highlight != null) {
                int id = highlights.indexOf(highlight);
                return "highlight" + id;
            } else {
                //Next, instance scope
                final Highlight highlight2 = highlights.getHighlight(container.getEntity(), instance);
                if (highlight2 != null) {
                    int id = highlights.indexOf(highlight2);
                    return "highlight" + id;
                } else {
                    //no luck
                    return null;
                }
            }
        }
        return null;
    }
}
