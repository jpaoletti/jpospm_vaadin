package org.jpos.ee.pm.vaadin.converters;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.PMContext;

/**
 *
 * @author jpaoletti
 */
public class EditDecimalConverter extends VaadinEditStringConverter {

    @Override
    public Object build(PMContext ctx) throws ConverterException {
        String res = ctx.getString(PM_FIELD_VALUE);
        try {
            return new BigDecimal(res);
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }
}
