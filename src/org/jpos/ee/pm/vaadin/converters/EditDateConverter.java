package org.jpos.ee.pm.vaadin.converters;

import com.vaadin.ui.PopupDateField;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.PMContext;

/**
 *
 * @author jpaoletti
 */
public class EditDateConverter extends VaadinConverter {

    @Override
    public Object build(PMContext ctx) throws ConverterException {
        return ctx.get(PM_FIELD_VALUE);
    }

    @Override
    public Object visualize(PMContext ctx) throws ConverterException {
        PopupDateField f = new PopupDateField();
        f.setDateFormat(getFormatString());
        f.setValue(ctx.get(PM_FIELD_VALUE));
        f.setResolution(PopupDateField.RESOLUTION_DAY);
        return f;
    }

    /**
     * Return the format object of the date
     * @return The format
     */
    public DateFormat getDateFormat() {
        DateFormat df = new SimpleDateFormat(getFormatString());
        return df;
    }

    private String getFormatString() {
        return getConfig("format", "MM/dd/yyyy");
    }
}
