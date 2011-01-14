package org.jpos.ee.pm.vaadin.converters;

import com.vaadin.data.Validator;
import com.vaadin.ui.TextField;
import java.math.BigDecimal;
import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PresentationManager;

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

    @Override
    public Object visualize(PMContext ctx) throws ConverterException {
        final TextField f = (TextField)super.visualize(ctx);
        f.addValidator(new Validator() {

            public void validate(Object value) throws InvalidValueException {
                try {
                    new BigDecimal(value.toString());
                } catch (Exception e) {
                    throw new InvalidValueException(PresentationManager.getMessage("invalid.decimal"));
                }
            }

            public boolean isValid(Object value) {
                try {
                    validate(value);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        });
        return f;
    }
}
