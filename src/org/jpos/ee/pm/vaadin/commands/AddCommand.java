package org.jpos.ee.pm.vaadin.commands;

import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.operations.AddOperation;

/**
 *
 * @author jpaoletti
 */
public class AddCommand extends EditCommand{

    public AddCommand(PMContext ctx) {
        super(ctx);
    }

    @Override
    protected void doExcecute() throws PMException {
        AddOperation op = new AddOperation("add");
        op.excecute(getCtx());
    }


}
