package com.example.application.views.main;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

@PageTitle("Main")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class MainView extends Composite<VerticalLayout> {

    public MainView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout imageLayout = new VerticalLayout();
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);

        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            byte[] imageBytes;
            try {
                imageBytes = buffer.getInputStream().readAllBytes();
            } catch (IOException e) {
                Notification.show("Failed to upload image: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
                return;
            }

            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            String imageUrl = "data:image/" + getFileExtension(fileName) + ";base64," + base64Image;

            ImageComponent imageComponent = new ImageComponent(imageUrl, fileName, imageLayout);
            //<theme-editor-local-classname>
            imageComponent.addClassName("main-view-vertical-layout-1");
            imageLayout.add(imageComponent);
            Notification.show("Image uploaded successfully!", 3000, Notification.Position.MIDDLE);
        });

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutColumn2.addClassName(Gap.XLARGE);
        layoutColumn2.setPadding(false);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.END);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        imageLayout.setWidth("100%");
        imageLayout.getStyle().set("flex-grow", "1");
        imageLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        upload.setWidth("100%");
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(imageLayout);
        layoutColumn2.add(upload);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
