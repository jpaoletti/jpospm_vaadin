package org.jpos.ee.pm.vaadin.components;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import java.util.ArrayList;
import java.util.List;
import org.jpos.ee.pm.core.Entity;
import org.jpos.ee.pm.core.Operation;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMCoreObject;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.vaadin.VaadinSupport;

/**
 *
 * @author jpaoletti
 */
public class GenericForm extends Form {

    private PMContext ctx;

    public GenericForm(PMContext ctx, Object instance) throws PMException {
        this.ctx = ctx;
        setWriteThrough(false);
        ctx.put(PMCoreObject.PM_ENTITY_INSTANCE, instance);
        for (org.jpos.ee.pm.core.Field field : ctx.getEntity().getOrderedFields()) {
            addField(field.getId(), VaadinSupport.createField(getCtx(), field.getId()));
        }
    }

    public final PMContext getCtx() {
        return ctx;
    }

   
}
