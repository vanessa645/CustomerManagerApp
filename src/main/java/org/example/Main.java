package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
        deleteButton.setOnAction(e -> {

            Customer selectedCustomer =
                    table.getSelectionModel().getSelectedItem();

            if (selectedCustomer != null) {
                table.getItems().remove(selectedCustomer);

                idField.clear();
                nameField.clear();
                emailField.clear();
                phoneField.clear();
            }
        });

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
                addButton,
                updateButton,
                deleteButton,
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