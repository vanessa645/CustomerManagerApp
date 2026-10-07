package org.example;

import javafx.application.Application;
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

        Button addButton = new Button("Add Customer");
        Button updateButton = new Button("Update Customer");
        Button deleteButton = new Button("Delete Customer");
        Button clearButton = new Button("Clear");

        TableView<Customer> table = new TableView<>();

        // Table columns
        TableColumn<Customer, String> idColumn =
                new TableColumn<>("Customer ID");
        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        TableColumn<Customer, String> nameColumn =
                new TableColumn<>("Name");
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        TableColumn<Customer, String> emailColumn =
                new TableColumn<>("Email");
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        TableColumn<Customer, String> phoneColumn =
                new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<>("phone")
        );

        table.getColumns().addAll(
                idColumn,
                nameColumn,
                emailColumn,
                phoneColumn
        );

        // Add Customer
        addButton.setOnAction(e -> {

            if (idField.getText().isEmpty()
                    || nameField.getText().isEmpty()
                    || emailField.getText().isEmpty()
                    || phoneField.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Please fill in all customer details."
                );
                alert.showAndWait();

                return;
            }

            // Email validation
            if (!emailField.getText().contains("@")
                    || !emailField.getText().contains(".")) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Email");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Please enter a valid email address."
                );
                alert.showAndWait();

                return;
            }
            // Phone number validation
            if (!phoneField.getText().matches("\\d+")) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Phone Number");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Please enter numbers only for the phone number."
                );
                alert.showAndWait();

                return;
            }

            // Duplicate Customer ID validation
            for (Customer existingCustomer : table.getItems()) {

                if (existingCustomer.getId()
                        .equals(idField.getText())) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Duplicate Customer ID");
                    alert.setHeaderText(null);
                    alert.setContentText(
                            "A customer with this ID already exists."
                    );
                    alert.showAndWait();

                    return;
                }
            }

            Customer customer = new Customer(
                    idField.getText(),
                    nameField.getText(),
                    emailField.getText(),
                    phoneField.getText()
            );

            table.getItems().add(customer);

            idField.clear();
            nameField.clear();
            emailField.clear();
            phoneField.clear();
        });

        // Select a customer from the table
        table.setOnMouseClicked(event -> {

            Customer selectedCustomer =
                    table.getSelectionModel().getSelectedItem();

            if (selectedCustomer != null) {

                idField.setText(selectedCustomer.getId());
                nameField.setText(selectedCustomer.getName());
                emailField.setText(selectedCustomer.getEmail());
                phoneField.setText(selectedCustomer.getPhone());
            }
        });

        // Update Customer
        updateButton.setOnAction(e -> {

            Customer selectedCustomer =
                    table.getSelectionModel().getSelectedItem();

            if (selectedCustomer != null) {

                if (idField.getText().isEmpty()
                        || nameField.getText().isEmpty()
                        || emailField.getText().isEmpty()
                        || phoneField.getText().isEmpty()) {
                    // Email validation
                    if (!emailField.getText().contains("@")
                            || !emailField.getText().contains(".")) {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Email");
                        alert.setHeaderText(null);
                        alert.setContentText(
                                "Please enter a valid email address."
                        );
                        alert.showAndWait();

                        return;
                    }
                    // Phone number validation
                    if (!phoneField.getText().matches("\\d+")) {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Phone Number");
                        alert.setHeaderText(null);
                        alert.setContentText(
                                "Please enter numbers only for the phone number."
                        );
                        alert.showAndWait();

                        return;
                    }

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Missing Information");
                    alert.setHeaderText(null);
                    alert.setContentText(
                            "Please fill in all customer details."
                    );
                    alert.showAndWait();

                    return;
                }

                selectedCustomer.setId(idField.getText());
                selectedCustomer.setName(nameField.getText());
                selectedCustomer.setEmail(emailField.getText());
                selectedCustomer.setPhone(phoneField.getText());

                table.refresh();

                idField.clear();
                nameField.clear();
                emailField.clear();
                phoneField.clear();
            }
        });

        // Delete Customer
        deleteButton.setOnAction(e -> {

            Customer selectedCustomer =
                    table.getSelectionModel().getSelectedItem();

            if (selectedCustomer != null) {

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

                    table.getItems().remove(selectedCustomer);

                    idField.clear();
                    nameField.clear();
                    emailField.clear();
                    phoneField.clear();
                }
            }
        });

        // Clear Form
        clearButton.setOnAction(e -> {

            idField.clear();
            nameField.clear();
            emailField.clear();
            phoneField.clear();

            table.getSelectionModel().clearSelection();
        });

        // Button layout
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
                buttonBox,
                table
        );

        Scene scene = new Scene(layout, 600, 500);

        stage.setTitle("Customer Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}