/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2011 Alejandro P. Revilla
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpos.ee.pm.vaadin.converters;

import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;
import org.jpos.ee.pm.converter.Converter;
import org.jpos.ee.pm.converter.ConverterException;
import org.jpos.ee.pm.core.PMContext;

/**
 *
 * @author jpaoletti
 */
public class VaadinConverter extends Converter {

    protected org.jpos.ee.pm.core.Field getPMField(PMContext ctx) {
        return (org.jpos.ee.pm.core.Field) ctx.get(PM_FIELD);
    }

    protected Field createBasicField(PMContext ctx) throws ConverterException {
        Object value = ctx.get(PM_FIELD_VALUE);
        Field f = new TextField();
        f.setValue(super.visualize(value, ""));
        return f;
    }
}
