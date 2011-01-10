package org.jpos.ee.pm.vaadin;

import com.vaadin.event.Action.*;
import org.jpos.ee.pm.core.*;

/**
 *
 * @author jpaoletti
 */
public class PMVaadinService extends PMService {

    @Override
    public String visualizationWrapper(String s) {
        if (s == null) {
            return "void.jsp?text=";
        }
        if (s.contains(".jsp?") || s.contains(".do?")) {
            return s;
        } else {
            return "void.jsp?text=" + s;
        }
    }
}
