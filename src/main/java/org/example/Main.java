package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("Customer Manager");

        Label idLabel = new Label("Customer ID:");
        TextField idField = new TextField();

        Label nameLabel = new Label("Customer Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label phoneLabel = new Label("Phone Number:");
        TextField phoneField = new TextField();

        // Province
        Label provinceLabel = new Label("Province:");

        ComboBox<String> provinceBox = new ComboBox<>();

        provinceBox.getItems().addAll(
                "Central",
                "Copperbelt",
                "Eastern",
                "Luapula",
                "Lusaka",
                "Muchinga",
                "Northern",
                "North-Western",
                "Southern",
                "Western"
        );

        provinceBox.setPromptText("Select Province");

        Button addButton = new Button("Add Customer");
        Button updateButton = new Button("Update Customer");
        Button deleteButton = new Button("Delete Customer");
        Button clearButton = new Button("Clear");

        // ObservableList of customers
        ObservableList<Customer> customers =
                FXCollections.observableArrayList();

        // Table
        TableView<Customer> table = new TableView<>();

        table.setItems(customers);

        // Customer ID column
        TableColumn<Customer, String> idColumn =
                new TableColumn<>("Customer ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        // Name column
        TableColumn<Customer, String> nameColumn =
                new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        // Email column
        TableColumn<Customer, String> emailColumn =
                new TableColumn<>("Email");

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        // Phone column
        TableColumn<Customer, String> phoneColumn =
                new TableColumn<>("Phone");

        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<>("phone")
        );

        // Province column
        TableColumn<Customer, String> provinceColumn =
                new TableColumn<>("Province");

        provinceColumn.setCellValueFactory(
                new PropertyValueFactory<>("province")
        );

        table.getColumns().addAll(
                idColumn,
                nameColumn,
                emailColumn,
                phoneColumn,
                provinceColumn
        );

        // Add Customer
        addButton.setOnAction(e -> {

            if (idField.getText().isEmpty()
                    || nameField.getText().isEmpty()
                    || emailField.getText().isEmpty()
                    || phoneField.getText().isEmpty()
                    || provinceBox.getValue() == null) {

                showWarning(
                        "Missing Information",
                        "Please fill in all customer details and select a province."
                );

                return;
            }

            // Email validation
            if (!emailField.getText().contains("@")
                    || !emailField.getText().contains(".")) {

                showWarning(
                        "Invalid Email",
                        "Please enter a valid email address."
                );

                return;
            }

            // Phone validation
            if (!phoneField.getText().matches("\\d+")) {

                showWarning(
                        "Invalid Phone Number",
                        "Please enter numbers only for the phone number."
                );

                return;
            }

            // Duplicate ID validation
            for (Customer existingCustomer : customers) {

                if (existingCustomer.getId()
                        .equals(idField.getText())) {

                    showWarning(
                            "Duplicate Customer ID",
                            "A customer with this ID already exists."
                    );

                    return;
                }
            }

            Customer customer = new Customer(
                    idField.getText(),
                    nameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    provinceBox.getValue()
            );

            customers.add(customer);

            clearForm(
                    idField,
                    nameField,
                    emailField,
                    phoneField,
                    provinceBox
            );
        });

        // Select customer
        table.setOnMouseClicked(event -> {

            Customer selectedCustomer =
                    table.getSelectionModel().getSelectedItem();

            if (selectedCustomer != null) {

                idField.setText(selectedCustomer.getId());
                nameField.setText(selectedCustomer.getName());
                emailField.setText(selectedCustomer.getEmail());
                phoneField.setText(selectedCustomer.getPhone());

                provinceBox.setValue(
                        selectedCustomer.getProvince()
                );
            }
        });

        // Update Customer
        updateButton.setOnAction(e -> {

            Customer selectedCustomer =
                    table.getSelectionModel().getSelectedItem();

            if (selectedCustomer == null) {

                showWarning(
                        "No Customer Selected",
                        "Please select a customer to update."
                );

                return;
            }

            // Empty field validation
            if (idField.getText().isEmpty()
                    || nameField.getText().isEmpty()
                    || emailField.getText().isEmpty()
                    || phoneField.getText().isEmpty()
                    || provinceBox.getValue() == null) {

                showWarning(
                        "Missing Information",
                        "Please fill in all customer details and select a province."
                );

                return;
            }

            // Email validation
            if (!emailField.getText().contains("@")
                    || !emailField.getText().contains(".")) {

                showWarning(
                        "Invalid Email",
                        "Please enter a valid email address."
                );

                return;
            }

            // Phone validation
            if (!phoneField.getText().matches("\\d+")) {

                showWarning(
                        "Invalid Phone Number",
                        "Please enter numbers only for the phone number."
                );

                return;
            }

            // Duplicate ID validation
            for (Customer existingCustomer : customers) {

                if (existingCustomer != selectedCustomer
                        && existingCustomer.getId()
                        .equals(idField.getText())) {

                    showWarning(
                            "Duplicate Customer ID",
                            "A customer with this ID already exists."
                    );

                    return;
                }
            }

            selectedCustomer.setId(idField.getText());
            selectedCustomer.setName(nameField.getText());
            selectedCustomer.setEmail(emailField.getText());
            selectedCustomer.setPhone(phoneField.getText());
            selectedCustomer.setProvince(
                    provinceBox.getValue()
            );

            table.refresh();

            clearForm(
                    idField,
                    nameField,
                    emailField,
                    phoneField,
                    provinceBox
            );

            table.getSelectionModel().clearSelection();
        });

        // Delete Customer
        deleteButton.setOnAction(e -> {

            Customer selectedCustomer =
                    table.getSelectionModel().getSelectedItem();

            if (selectedCustomer == null) {

                showWarning(
                        "No Customer Selected",
                        "Please select a customer to delete."
                );

                return;
            }

            Alert alert =
                    new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Delete Customer");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Are you sure you want to delete this customer?"
            );

            ButtonType result =
                    alert.showAndWait().orElse(ButtonType.NO);

            if (result == ButtonType.OK) {

                customers.remove(selectedCustomer);

                clearForm(
                        idField,
                        nameField,
                        emailField,
                        phoneField,
                        provinceBox
                );
            }
        });

        // Clear Form
        clearButton.setOnAction(e -> {

            clearForm(
                    idField,
                    nameField,
                    emailField,
                    phoneField,
                    provinceBox
            );

            table.getSelectionModel().clearSelection();
        });

        // Buttons
        HBox buttonBox = new HBox(10);

        buttonBox.getChildren().addAll(
                addButton,
                updateButton,
                deleteButton,
                clearButton
        );

        // Main layout
        VBox layout = new VBox(10);

        layout.getChildren().addAll(
                title,
                idLabel,
                idField,
                nameLabel,
                nameField,
                emailLabel,
                emailField,
                phoneLabel,
                phoneField,
                provinceLabel,
                provinceBox,
                buttonBox,
                table
        );

        Scene scene = new Scene(layout, 750, 650);

        stage.setTitle("Customer Manager");
        stage.setScene(scene);
        stage.show();
    }

    // Warning helper method
    private void showWarning(String title, String message) {

        Alert alert =
                new Alert(Alert.AlertType.WARNING);

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Clear form helper method
    private void clearForm(
            TextField idField,
            TextField nameField,
            TextField emailField,
            TextField phoneField,
            ComboBox<String> provinceBox) {

        idField.clear();
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        provinceBox.setValue(null);
    }

    public static void main(String[] args) {
        launch(args);
    }
}