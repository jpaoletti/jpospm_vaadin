package org.jpos.ee.pm.vaadin.converters;

import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.PMContext;

/**
 *
 * @author jpaoletti
 */
public class EditLongConverter extends VaadinEditStringConverter {

    @Override
    public Object build(PMContext ctx) throws ConverterException {
        String res = ctx.getString(PM_FIELD_VALUE);
        try {
            return Long.parseLong(res);
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }
}
