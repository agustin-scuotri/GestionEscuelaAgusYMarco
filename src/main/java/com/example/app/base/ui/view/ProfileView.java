package com.example.app.base.ui.view;

import com.example.app.base.domain.Person;
import com.example.app.base.domain.User;
import com.example.app.base.service.PersonService;
import com.example.app.base.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Route(value = "profile", layout = MainLayout.class)
@PageTitle("Mi Perfil – Gestión Escuela")
@PermitAll
public class ProfileView extends VerticalLayout {

    private final UserService userService;
    private final PersonService personService;
    private User currentUser;
    private Person currentPerson;
    private Button btnSave;

    public ProfileView(UserService userService, PersonService personService) {
        this.userService = userService;
        this.personService = personService;

        // Cargar usuario y persona autenticados
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        this.currentUser = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado: " + username));
        this.currentPerson = currentUser.getPerson();

        // Configuración de layout
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        setPadding(true);

        // Título
        add(new H2("Mi Perfil"));

        // Formulario
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        TextField tfUsername = new TextField("Usuario");
        tfUsername.setValue(currentUser.getUsername());
        tfUsername.setReadOnly(true);

        PasswordField pfPassword = new PasswordField("Contraseña");
        pfPassword.setPlaceholder("Dejar en blanco para no cambiar");

        TextField tfName = new TextField("Nombre");
        tfName.setValue(currentPerson.getName() != null ? currentPerson.getName() : "");

        EmailField efEmail = new EmailField("Email");
        efEmail.setValue(currentPerson.getEmail() != null ? currentPerson.getEmail() : "");

        form.add(tfUsername, pfPassword, tfName, efEmail);
        add(form);

        // Botón Guardar
        btnSave = new Button("Guardar cambios", e -> {
            String newPass = pfPassword.getValue();
            if (newPass != null && !newPass.isBlank()) {
                currentUser.setPassword(userService.encodePassword(newPass));
            }
            currentPerson.setName(tfName.getValue());
            currentPerson.setEmail(efEmail.getValue());

            personService.save(currentPerson);
            userService.save(currentUser);

            btnSave.setText("Guardado");
        });
        add(btnSave);
    }
}
