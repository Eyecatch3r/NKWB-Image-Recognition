package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ImageComponent extends VerticalLayout {

    public ImageComponent(String imageUrl, String fileName, VerticalLayout parentLayout) {
        Image image = new Image(imageUrl, fileName);
        image.setWidth("100%"); // Fixed width
        image.setHeight("100%"); // Fixed height
        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE));
        closeButton.getStyle().set("position", "absolute");
        closeButton.getStyle().set("top", "10px");
        closeButton.getStyle().set("right", "10px");
        closeButton.getStyle().set("background-color", "transparent");
        closeButton.getStyle().set("border", "none");

        closeButton.addClickListener(e -> parentLayout.remove(this));

        setWidth("100%");
        getStyle().set("position", "relative");
        getStyle().set("border", "1px solid var(--lumo-contrast-20pct)");
        getStyle().set("border-radius", "10px");
        getStyle().set("padding", "10px");
        getStyle().set("box-shadow", "2px 2px 10px var(--lumo-shade-20pct)");
        getStyle().set("margin-bottom", "10px");

        add(closeButton, image);
    }
}
