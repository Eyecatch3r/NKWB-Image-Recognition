package com.example.application.views.signup;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.application.services.UserService;

@AnonymousAllowed
@PageTitle("Sign Up")
@Route("signup")
public class SignupView extends VerticalLayout {

    @Autowired
    public SignupView(UserService userService) {

        // Create the form layout
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        TextField usernameField = new TextField("Username");
        TextField nameField = new TextField("Name");
        PasswordField passwordField = new PasswordField("Password");
        Button signupButton = new Button("Sign Up");

        signupButton.addClickListener(e -> {
            String username = usernameField.getValue();
            String name = nameField.getValue();
            String password = passwordField.getValue();

            try {
                userService.saveUser(username, name, password);
                Notification.show("User signed up successfully");
                usernameField.clear();
                nameField.clear();
                passwordField.clear();
            } catch (IllegalArgumentException ex) {
                Notification.show(ex.getMessage());
            }
        });

        formLayout.add(usernameField, nameField, passwordField, signupButton);

        // Create a wrapper layout with some styling to look like a box
        VerticalLayout formWrapper = new VerticalLayout();
        formWrapper.add(new H3("Sign Up"));
        formWrapper.add(formLayout);
        formWrapper.setPadding(true);
        formWrapper.setSpacing(true);
        formWrapper.getStyle().set("border", "1px solid var(--lumo-contrast-20pct)");
        formWrapper.getStyle().set("border-radius", "10px");
        formWrapper.getStyle().set("box-shadow", "2px 2px 10px var(--lumo-shade-20pct)");
        formWrapper.getStyle().set("padding", "20px");
        formWrapper.getStyle().set("background-color", "var(--lumo-base-color)");
        formWrapper.setWidth("300px");

        // Center the formWrapper in the SignupView
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(formWrapper);
    }
}
