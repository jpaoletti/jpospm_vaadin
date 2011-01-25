package org.jpos.ee.pm.vaadin.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.jpos.ee.pm.core.PresentationManager;

/**
 *
 * @author jpaoletti
 */
public class ConfirmationWindow extends Window {

    public ConfirmationWindow(final String captionkey, final String label, final Button.ClickListener yeslistener) {
        if (captionkey != null) {
            setCaption(PresentationManager.getMessage(captionkey));
        }
        setModal(true);
        setWidth("300px");
        VerticalLayout layout = (VerticalLayout) getContent();
        layout.setMargin(true);
        layout.setSpacing(true);
        Label message = new Label(label);
        addComponent(message);

        Button yes = new Button(PresentationManager.getMessage("pm.confirmation.yes"), new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                yeslistener.buttonClick(event);
                getParent().removeWindow(ConfirmationWindow.this);
            }
        });

        Button no = new Button(PresentationManager.getMessage("pm.confirmation.no"), new Button.ClickListener() {

            public void buttonClick(ClickEvent event) {
                getParent().removeWindow(ConfirmationWindow.this);
            }
        });

        HorizontalLayout l = new HorizontalLayout();
        l.addComponent(no);
        l.addComponent(yes);
        l.setComponentAlignment(yes, Alignment.MIDDLE_RIGHT);
        l.setComponentAlignment(no, Alignment.MIDDLE_LEFT);
        layout.addComponent(l);
    }
}
