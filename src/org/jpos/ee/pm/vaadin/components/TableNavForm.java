package org.jpos.ee.pm.vaadin.components;

import com.vaadin.data.Property;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import java.util.ArrayList;
import java.util.List;
import org.jpos.ee.pm.core.PMContext;
import org.jpos.ee.pm.core.PMException;
import org.jpos.ee.pm.core.PaginatedList;
import org.jpos.ee.pm.core.PresentationManager;
import org.jpos.ee.pm.core.operations.OperationCommandSupport;
import org.jpos.ee.pm.vaadin.commands.GenericCommand;
import org.jpos.ee.pm.vaadin.commands.ListCommand;

/**
 *
 * @author jpaoletti
 */
public class TableNavForm extends Form {
    // rowPerPage combo

    private ComboBox rowPerPage;
    // from to
    private Label total;
    // Previous button
    private Button prevButton;
    // list of pages
    private List<Button> listOfPages;
    // Next button
    private Button nextButton;

    public TableNavForm(final PMContext ctx) throws PMException {
        final PaginatedList list = ctx.getList();
        final PMMainWindow window = (PMMainWindow) ctx.get("window");
        HorizontalLayout l = new HorizontalLayout();

        l.addComponent(new Label(PresentationManager.getMessage("list.rpp")));

        setupRowPerPageCombo(ctx, list, l, window);

        total = new Label(PresentationManager.getMessage("list.pagebar", list.getPage(), list.getPages(), ((list.getTotal() != null) ? list.getTotal() : "?")));
        l.addComponent(total);

        listOfPages = new ArrayList<Button>();

        if (list.getPage() > 1) {
            prevButton = new Button("<");
            prevButton.setData(list.getPage() - 1);
            listOfPages.add(prevButton);
        }

        if (list.getTotal() != null) {
            if (list.getPages() <= 20) {
                for (Integer i = 1; i <= list.getPages(); i++) {
                    Button b = new Button(Integer.toString(i));
                    b.setEnabled(i != list.getPage());
                    b.setData(i);
                    listOfPages.add(b);
                }
            } else {
                if (list.getPage() != 1) {
                    final Button button = new Button("1");
                    button.setData(1);
                    listOfPages.add(button);
                }
                Button b = new Button(list.getPage().toString());
                b.setEnabled(false);
                listOfPages.add(b);
                if (list.getPage() != list.getPages()) {
                    final Button button = new Button(list.getPages().toString());
                    button.setData(list.getPages());
                    listOfPages.add(button);
                }
            }
        } else {
            if (list.getPage() != 1) {
                final Button button = new Button("1");
                button.setData(1);
                listOfPages.add(button);
            }
            Button b = new Button(list.getPage().toString());
            b.setEnabled(false);
            listOfPages.add(b);
        }
        if (list.getTotal() != null && list.getPage() < list.getPages()) {
            nextButton = new Button(">");
            nextButton.setData(list.getPage() + 1);
            listOfPages.add(nextButton);
        }
        for (Button button : listOfPages) {
            if (button.getData() != null) {
                button.addListener(new Button.ClickListener() {

                    public void buttonClick(ClickEvent event) {
                        list.setPage((Integer) event.getButton().getData());
                        PMContext c = cloneContext(ctx);
                        c.put("page", list.getPage());
                        final ListCommand cmd = new ListCommand(c);
                        window.setMainScreen(cmd.execute());
                    }
                });
            }
            l.addComponent(button);
        }
        getLayout().addComponent(l);
    }

    protected final void setupRowPerPageCombo(final PMContext ctx, final PaginatedList list, HorizontalLayout l, final PMMainWindow window) throws UnsupportedOperationException {
        rowPerPage = new ComboBox();
        rowPerPage.addItem("5");
        rowPerPage.addItem("10");
        rowPerPage.addItem("20");
        rowPerPage.addItem("50");
        if (list.getRowsPerPage() == null) {
            rowPerPage.select("10");
        } else {
            rowPerPage.select(list.getRowsPerPage().toString());
        }
        rowPerPage.setNullSelectionAllowed(false);
        rowPerPage.setNewItemsAllowed(false);
        rowPerPage.setWidth("50px");
        rowPerPage.setFilteringMode(Filtering.FILTERINGMODE_OFF);
        rowPerPage.setImmediate(true);
        rowPerPage.addListener(new ValueChangeListener() {

            public void valueChange(Property.ValueChangeEvent event) {
                list.setRowsPerPage(Integer.parseInt((String) event.getProperty().getValue()));
                PMContext c = cloneContext(ctx);
                c.put("rows_per_page", list.getRowsPerPage());
                final ListCommand cmd = new ListCommand(c);
                window.setMainScreen(cmd.execute());
            }
        });
        l.addComponent(rowPerPage);
    }

    protected PMContext cloneContext(PMContext ctx) {
        final PMContext c = new PMContext(ctx.getSessionId());
        c.put(OperationCommandSupport.PM_ID, ctx.get("entity"));
        c.put(GenericCommand.WINDOW, ctx.get(GenericCommand.WINDOW));
        c.put("new", false);
        c.put("order", ctx.get("order"));
        c.put("desc", ctx.get("desc"));
        c.put("page", ctx.get("page"));
        c.put("rows_per_page", ctx.get("rows_per_page"));
        c.put("new", false);
        return c;
    }
}
