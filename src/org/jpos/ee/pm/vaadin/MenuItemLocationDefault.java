package org.jpos.ee.pm.vaadin;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.Panel;
import java.util.HashMap;
import java.util.Map;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.menu.MenuItem;
import org.jpos.ee.pm.menu.MenuItemLocation;
import org.jpos.ee.pm.vaadin.commands.CommandFactory;
import org.jpos.ee.pm.vaadin.commands.GenericCommand;
import org.jpos.ee.pm.vaadin.components.PMMainWindow;

/**
 *
 * @author jpaoletti
 */
public class MenuItemLocationDefault implements MenuItemLocation {

    public Object build(MenuItem item, Object... params) {
        try {
            final PMMainWindow window = (PMMainWindow)params[0];
            final PMContext ctx= new PMContext(window.getPMApplication().getSessionId());
            ctx.put("window", window);
            String[] ps = ((String) params[1]).split("[;]");
            for (int i = 0; i < ps.length; i++) {
                String p1 = ps[i];
                String[] p1s = p1.split("[=]");
                if (p1s.length == 2) {
                    ctx.put(p1s[0], p1s[1]);
                }
            }

            return new Command() {

                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    System.out.println("Operation: " + ctx.get("operation"));
                    System.out.println("Entity: " + ctx.get("entity"));
                    GenericCommand c = CommandFactory.newCommand(ctx.getString("operation"), ctx);
                    final HorizontalLayout l = c.execute();
                    window.setMainScreen(l);
                }
            };
        } catch (Exception e) {
            PresentationManager.getPm().error(e);
            return null;
        }
    }
}
