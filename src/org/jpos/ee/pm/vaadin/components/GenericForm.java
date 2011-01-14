package org.jpos.ee.pm.vaadin.components;

import com.vaadin.ui.*;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMCoreObject;
import org.jpos.ee.pm.core.PMException;
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
