package org.jpos.ee.pm.vaadin.components;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import java.util.Collection;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PaginatedList;
import org.jpos.ee.pm.core.PresentationManager;

/**
 *
 * @author jpaoletti
 */
public class ListContainer extends IndexedContainer implements Container {

    private PaginatedList list;

    public ListContainer(PaginatedList list) {
        this.list = list;
        for (Field field : list.getEntity().getOrderedFields()) {
            addContainerProperty(field.getId(), String.class, null);
        }
        for (Object item : list.getContents()) {
            Integer id = list.getContents().indexOf(item);
            Item listitem = addItem(id);
            for (Field field : list.getEntity().getOrderedFields()) {
                listitem.getItemProperty(field.getId()).setValue(PresentationManager.getPm().get(item, field.getId()));
            }
        }
    }
}
