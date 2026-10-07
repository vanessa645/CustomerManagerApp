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

        TableView<Customer> table = new TableView<>();

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