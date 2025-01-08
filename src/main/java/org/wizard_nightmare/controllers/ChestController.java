package org.wizard_nightmare.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.wizard_nightmare.game.object.Item;
import org.wizard_nightmare.game.object.Weapon;

import java.util.ArrayList;
import java.util.List;

public class ChestController {

    @FXML
    private HBox inventarioContainer;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private HBox cofreContainer;
    @FXML
    private Label mensajeCofreVacio;

    private List<Item> objetos = new ArrayList<>();
    private List<Item> inventario = new ArrayList<>();

    public void initialize() {
        try {
            String imagePath = getClass().getResource("/images/gofre.jpeg").toExternalForm();
            anchorPane.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }

        objetos.add(new Weapon(1));

        if (objetos.isEmpty()) {
            mensajeCofreVacio.setText("El cofre está vacío.");
            mensajeCofreVacio.setVisible(true);
        } else {
            for (Item objeto : objetos) {
                Button objetoButton = new Button(objeto.toString());
                addContextMenuToButton(objetoButton, objeto, cofreContainer);
                cofreContainer.getChildren().add(objetoButton);
            }
        }

        for (Item objeto : inventario) {
            Button inventarioButton = new Button(objeto.toString());
            addContextMenuToButton(inventarioButton, objeto, inventarioContainer);
            inventarioContainer.getChildren().add(inventarioButton);
        }
    }

    private void addContextMenuToButton(Button button, Item objeto, HBox container) {
        ContextMenu menu = new ContextMenu();
        MenuItem dejar = new MenuItem("Dejar");
        MenuItem eliminar = new MenuItem("Eliminar");
        MenuItem intercambiar = new MenuItem("Intercambiar");
        MenuItem coger = new MenuItem("Coger");

        dejar.setOnAction(e -> {
            if (container == inventarioContainer) {
                inventario.remove(objeto);
                objetos.add(objeto);
                Button cofreButton = new Button(objeto.toString());
                addContextMenuToButton(cofreButton, objeto, cofreContainer);
                cofreContainer.getChildren().add(cofreButton);
                inventarioContainer.getChildren().remove(button);
            }
        });

        eliminar.setOnAction(e -> {
            container.getChildren().remove(button);
            objetos.remove(objeto);
            inventario.remove(objeto);
        });

        intercambiar.setOnAction(e -> System.out.println("Intercambiaste " + objeto));
        coger.setOnAction(e -> {
            inventario.add(objeto);
            if (container == cofreContainer) {
                cofreContainer.getChildren().remove(button);
                objetos.remove(objeto);
            }
            Button inventarioButton = new Button(objeto.toString());
            addContextMenuToButton(inventarioButton, objeto, inventarioContainer);
            inventarioContainer.getChildren().add(inventarioButton);
        });

        menu.getItems().addAll(dejar, eliminar, intercambiar, coger);

        button.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                menu.show(button, event.getScreenX(), event.getScreenY());
            }
        });
    }
}
