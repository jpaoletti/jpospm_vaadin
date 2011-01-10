package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.core.operations.ListOperation;
import org.jpos.ee.pm.core.operations.OperationCommandSupport;
import org.jpos.ee.pm.vaadin.components.ListContainer;
import org.jpos.ee.pm.vaadin.components.TableNavForm;

/**
 *
 * @author jpaoletti
 */
public class ListCommand extends GenericCommand {

    public ListCommand(PMContext ctx) {
        super(ctx);
    }

    @Override
    public HorizontalLayout execute() {
        try {
            ListOperation op = new ListOperation("list");
            getCtx().put(OperationCommandSupport.PM_ID, getCtx().get("entity"));

            if (getCtx().getBoolean("new", true)) {
                getCtx().put("order", null);
                getCtx().put("desc", false);
                getCtx().put("page", 1);
                getCtx().put("rows_per_page", 10);
            }
            op.excecute(getCtx());

            HorizontalLayout l = new HorizontalLayout();
            l.setSizeFull();
            VerticalLayout vl = new VerticalLayout();
            vl.setSpacing(true);
            vl.setMargin(true, true, true, true);

            final Label title = new Label("<h2>" + getOperationTitle() + "</h2>");
            title.setContentMode(Label.CONTENT_XHTML);
            vl.addComponent(title);

            final Table table = new Table();
            table.setContainerDataSource(new ListContainer(getCtx().getList()));
            table.setHeight("150px");
            table.setPageLength((Integer)getCtx().get("rows_per_page"));
            table.setSizeFull();
            vl.addComponent(table);


            final TableNavForm navform = new TableNavForm(getCtx());
            vl.addComponent(navform);
            vl.setComponentAlignment(navform, Alignment.BOTTOM_CENTER);

            Panel p = new Panel();
            p.setStyle(Panel.STYLE_LIGHT);
            p.setScrollable(true);
            p.setSizeFull();
            p.setContent(vl);
            l.addComponent(p);

            return l;
        } catch (PMException ex) {
            PresentationManager.getPm().error(ex);
            return getErrorLayout(ex);
        }
    }
}
