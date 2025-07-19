package com.example.app.base.ui.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("login")
@PageTitle("Login – Gestión Escuela")
@PermitAll
public class LoginView extends VerticalLayout {

    public LoginView(AuthenticationManager authManager) {
        // Centrar el LoginForm
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(e -> {
            try {
                Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword())
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
                UI.getCurrent().navigate(""); // redirige a la ruta raíz tras login
            } catch (AuthenticationException ex) {
                loginForm.setError(true);
            }
        });

        add(loginForm);
    }
}

