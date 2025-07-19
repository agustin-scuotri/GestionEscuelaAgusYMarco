package com.example.app.base.ui.view;

import com.example.app.base.domain.Role;
import com.example.app.base.domain.User;
import com.example.app.base.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;


@Route(value = "admin/users", layout = MainLayout.class)
@PageTitle("Usuarios – Gestión Escuela")
@RolesAllowed("ADMIN")
public class AdminUserView extends VerticalLayout {

    private final UserService userService;
    private final Grid<User> grid;

    public AdminUserView(UserService userService) {
        this.userService = userService;
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        // Título y botón Nuevo
        H2 title = new H2("Gestión de Usuarios");
        Button btnNew = new Button("Nuevo Usuario", e -> openUserDialog(new User()));
        HorizontalLayout header = new HorizontalLayout(title, btnNew);
        header.setAlignItems(Alignment.BASELINE);
        add(header);

        // Grid
        grid = new Grid<>(User.class, false);
        grid.addColumn(User::getUsername).setHeader("Usuario").setSortable(true);
        grid.addColumn(u -> u.getRole().name()).setHeader("Rol").setSortable(true);
        grid.addComponentColumn(this::createActions).setHeader("Acciones");
        grid.setItems(userService.findAll());
        grid.setSizeFull();

        add(grid);
    }

    private HorizontalLayout createActions(User user) {
        Button edit = new Button("Editar", e -> openUserDialog(user));
        Button delete = new Button("Eliminar", e -> {
            userService.deleteById(user.getId());
            refreshGrid();
        });
        return new HorizontalLayout(edit, delete);
    }

    private void openUserDialog(User user) {
        Dialog dialog = new Dialog();
        dialog.setWidth("400px");

        FormLayout form = new FormLayout();
        TextField tfUsername = new TextField("Usuario");
        tfUsername.setValue(user.getUsername() != null ? user.getUsername() : "");

        PasswordField pfPassword = new PasswordField("Contraseña");
        pfPassword.setPlaceholder(user.getId() == null ? "" : "Dejar en blanco para no cambiar");

        com.vaadin.flow.component.combobox.ComboBox<Role> cbRole =
            new com.vaadin.flow.component.combobox.ComboBox<>("Rol");
        cbRole.setItems(Role.values());
        cbRole.setItemLabelGenerator(Role::name);
        cbRole.setValue(user.getRole() != null ? user.getRole() : Role.STUDENT);

        form.add(tfUsername, pfPassword, cbRole);

        Button btnSave = new Button("Guardar", e -> {
            user.setUsername(tfUsername.getValue());
            String pass = pfPassword.getValue();
            if (pass != null && !pass.isBlank()) {
                user.setPassword(pass); 
            }
            user.setRole(cbRole.getValue());
            userService.save(user);
            dialog.close();
            refreshGrid();
        });
        Button btnCancel = new Button("Cancelar", e -> dialog.close());

        HorizontalLayout actions = new HorizontalLayout(btnSave, btnCancel);
        VerticalLayout content = new VerticalLayout(form, actions);
        dialog.add(content);
        dialog.open();
    }

    private void refreshGrid() {
        grid.setItems(userService.findAll());
    }
}
