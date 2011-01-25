package org.jpos.ee.pm.vaadin.commands;

import org.jpos.ee.pm.core.PMContext;

/**
 * We need to change this for an xml definition
 * 
 * @author jpaoletti
 */
public class CommandFactory {

    public static GenericCommand newCommand(String id, PMContext ctx) {
        ctx.put("operation",id);
        GenericCommand r = null;
        if ("list".equals(id)) {
            r = new ListCommand(ctx);
        } else if ("show".equals(id)) {
            r = new ShowCommand(ctx);
        } else if ("add".equals(id)) {
            r = new AddCommand(ctx);
        } else if ("edit".equals(id)) {
            r = new EditCommand(ctx);
        } else if ("delete".equals(id)) {
            r = new DeleteCommand(ctx);
        } else if ("filter".equals(id)) {
            r = new FilterCommand(ctx);
        } else if ("sort".equals(id)) {
            r = new SortCommand(ctx);
        }
        return r;
    }
}
