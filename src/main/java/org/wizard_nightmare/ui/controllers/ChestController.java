package org.wizard_nightmare.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.game.object.Item;
import org.wizard_nightmare.game.object.Weapon;

import java.util.ArrayList;
import java.util.List;

public class ChestController implements DemiurgeConsumer {
    @FXML
    private HBox inventarioContainer;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private HBox cofreContainer;
    @FXML
    private Label mensajeCofreVacio;

    private Demiurge demiurge;
    private List<Item> objetos = new ArrayList<>();
    private List<Item> inventario = new ArrayList<>();

    public void initialize() {
        try {
            String imagePath = getClass().getResource("/images/cofre.jpeg").toExternalForm();
            anchorPane.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }

    }

    private void loadChest() {
        objetos.addAll(demiurge.getContainerManager().getWearables().getItems());

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

    public void returnBack(ActionEvent actionEvent) {
        App.cambiarPantalla(demiurge, "/screens/home.fxml");
    }

    @Override
    public void setDemiurge(Demiurge demiurge) {
        this.demiurge = demiurge;
        loadChest();
    }
}