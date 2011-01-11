package org.jpos.ee.pm.vaadin.components;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import java.util.ArrayList;
import java.util.List;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;

/**
 *
 * @author jpaoletti
 */
public class GenericForm extends Form {

    private PMContext ctx;

    public GenericForm(PMContext ctx, Object instance) throws PMException {
        this.ctx = ctx;
        setWriteThrough(false);
        setFormFieldFactory(new PMFieldFactory());
        List<String> vp = new ArrayList<String>();
        for (org.jpos.ee.pm.core.Field field : ctx.getEntity().getOrderedFields()) {
            vp.add(field.getProperty());
        }
        setItemDataSource(new BeanItem(instance));
        setVisibleItemProperties(vp);
    }

    public PMContext getCtx() {
        return ctx;
    }

    private class PMFieldFactory extends DefaultFieldFactory {

        @Override
        public Field createField(Item item, Object propertyId, Component uiContext) {
            /*final TextField textField = new TextField("id");
            textField.setEnabled(false);
            return textField;*/
            Field f = super.createField(item, propertyId, uiContext);
            return f;
        }
    }
}
