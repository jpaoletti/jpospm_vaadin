package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.ui.HorizontalLayout;
import org.jpos.ee.pm.core.PMContext;

/**
 *
 * @author jpaoletti
 */
public class FilterCommand extends GenericCommand{

    public FilterCommand(PMContext ctx) {
          super(ctx);
    }

    @Override
    public HorizontalLayout execute() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
