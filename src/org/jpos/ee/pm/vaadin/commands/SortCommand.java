package org.jpos.ee.pm.vaadin.commands;

import com.vaadin.data.Item;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.jpos.ee.pm.core.Field;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMContext.ContextPair;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.core.operations.SortOperation;
import org.jpos.ee.pm.vaadin.components.PMMainWindow;

/**
 *
 * @author jpaoletti
 */
public class SortCommand extends GenericCommand {

    public SortCommand(PMContext ctx) {
        super(ctx);
    }

    @Override
    public HorizontalLayout execute() {
        try {
            SortOperation op = new SortOperation("sort");
            op.excecute(getCtx());
            if(getCtx().getParameter("finish")!=null){
                return redirect(
                        getCtx(),
                        "list",
                        getCtx().getPair("desc"),
                        getCtx().getPair("order"),
                        getCtx().newPair("new", false));
            }
            HorizontalLayout l = new HorizontalLayout();
            l.setSizeFull();
            VerticalLayout vl = new VerticalLayout();
            vl.setMargin(false, true, true, true);
            createTitle(vl);
            createOperationBar(vl);
            final HorizontalLayout footer = new HorizontalLayout();
            final Form f = new Form();
            final ComboBox combo = new ComboBox(PresentationManager.getMessage("pm.list.order"));
            for (Field field : getCtx().getEntity().getOrderedFields()) {
                if(field.shouldDisplay("sort")){
                    combo.addItem(field.getId());
                }
            }
            combo.setNullSelectionAllowed(false);
            combo.setNewItemsAllowed(false);
            combo.setValue(getCtx().getList().getOrder());
            f.addField("order", combo);//Combo with sortable fields
            final CheckBox checkBox = new CheckBox(PresentationManager.getMessage("pm.list.desc"));
            checkBox.setValue(getCtx().getList().isDesc());
            f.addField("desc", checkBox);
            final Button apply = new Button(PresentationManager.getMessage("pm.vaadin.submit"), new Button.ClickListener() {

                public void buttonClick(Button.ClickEvent event) {
                    try {
                        final ContextPair p1 = getCtx().newPair("param_finish", "1");
                        final ContextPair p2 = getCtx().newPair("param_desc", checkBox.getValue());
                        final ContextPair p3 = getCtx().newPair("param_order", combo.getValue());
                        final PMMainWindow window = (PMMainWindow) getCtx().get(WINDOW);
                        window.setMainScreen(redirect(getCtx(), "sort", p1, p2, p3));
                    } catch (Exception e) {
                        PresentationManager.getPm().error(e);
                    }
                }
            });

            footer.addComponent(apply);
            f.setFooter(footer);
            vl.addComponent(f);
            finalPanel(l, vl);
            return l;
        } catch (PMException ex) {
            PresentationManager.getPm().error(ex);
            return getErrorLayout(ex);
        }
    }
}
