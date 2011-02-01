package org.jpos.ee.pm.vaadin.components;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMCoreObject;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PaginatedList;
import org.jpos.ee.pm.vaadin.VaadinSupport;
import org.jpos.iso.ISOUtil;

/**
 *
 * @author jpaoletti
 */
public class ListContainer extends IndexedContainer implements Container {

    public static final String ROW_NUM_ID = "pmvaadin_row_number";

    public ListContainer(final PMContext ctx, PaginatedList list) throws PMException {
        if (list.isShowRowNumber()) {
            addContainerProperty(ROW_NUM_ID, String.class, null);
        }
        for (Field field : list.getEntity().getOrderedFields()) {
            addContainerProperty(field.getId(), String.class, null);
        }
        for (Object item : list.getContents()) {
            ctx.put(PMCoreObject.PM_ENTITY_INSTANCE, item);
            Integer id = list.getContents().indexOf(item);
            Item listitem = addItem(id);
            if (list.isShowRowNumber()) {
                if (list.getTotal() != null) {
                    listitem.getItemProperty(ROW_NUM_ID).setValue("[" + ISOUtil.zeropad(id, list.getTotal().toString().length()) + "]");
                } else {
                    listitem.getItemProperty(ROW_NUM_ID).setValue("[" + id + "]");
                }
            }
            for (Field field : list.getEntity().getOrderedFields()) {
                com.vaadin.ui.Field f = VaadinSupport.createField(ctx, field.getId());
                listitem.getItemProperty(field.getId()).setValue(f.getValue());
            }
        }
    }
}
