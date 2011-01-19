package org.jpos.ee.pm.vaadin.commands;

import org.jpos.ee.pm.core.PMContext;

/**
 * We need to change this for an xml definition
 * 
 * @author jpaoletti
 */
public class CommandFactory {

    public static GenericCommand newCommand(String id, PMContext ctx) {
        if ("list".equals(id)) {
            return new ListCommand(ctx);
        } else if ("show".equals(id)) {
            return new ShowCommand(ctx);
        } else if ("add".equals(id)) {
            return new AddCommand(ctx);
        } else if ("edit".equals(id)) {
            return new EditCommand(ctx);
        } else if ("delete".equals(id)) {
            return new DeleteCommand(ctx);
        }else if ("filter".equals(id)) {
            return new FilterCommand(ctx);
        }
        return null;
    }
}
