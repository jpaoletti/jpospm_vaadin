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
        } else if ("edit".equals(id)) {
            return new EditCommand(ctx);
        }
        return null;
    }
}
