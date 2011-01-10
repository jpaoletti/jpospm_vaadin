package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.jpos.ee.pm.core.PMContext;

/**
 *
 * @author jpaoletti
 */
public class ShowCommand extends GenericCommand{

    public ShowCommand(PMContext ctx) {
        super(ctx);
    }

    @Override
    public HorizontalLayout execute() {
        HorizontalLayout l = new HorizontalLayout();
        l.addComponent(new Label("Show!!"));
        return l;
    }

}
