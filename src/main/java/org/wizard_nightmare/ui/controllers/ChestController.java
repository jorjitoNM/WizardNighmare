package org.wizard_nightmare.ui.controllers;

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

    private boolean isSelectingForExchange = false;
    private Item exchangeItem;
    private HBox exchangeContainer;

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
        objetos.addAll(demiurge.getHome().getContainer().getItems());
        inventario.addAll(demiurge.getWizard().getWearables().getItems());

        cofreContainer.getChildren().clear();
        inventarioContainer.getChildren().clear();

        if (objetos.isEmpty()) {
            mensajeCofreVacio.setText("The chest is empty");
            mensajeCofreVacio.setVisible(true);
        } else {
            for (Item objeto : objetos) {
                Button objetoButton = createItemButton(objeto, cofreContainer);
                cofreContainer.getChildren().add(objetoButton);
            }
        }

        for (Item objeto : inventario) {
            Button inventarioButton = createItemButton(objeto, inventarioContainer);
            inventarioContainer.getChildren().add(inventarioButton);
        }
    }

    private Button createItemButton(Item item, HBox container) {
        Button button = new Button(item.toString());
        button.setUserData(item);
        addContextMenuToButton(button, item, container);
        return button;
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
                if (!objetos.isEmpty())
                    mensajeCofreVacio.setVisible(false);
            }
        });

        eliminar.setOnAction(e -> {
            container.getChildren().remove(button);
            objetos.remove(objeto);
            inventario.remove(objeto);
            if (objetos.isEmpty()) {
                mensajeCofreVacio.setText("The chest is empty");
                mensajeCofreVacio.setVisible(true);
            }
        });

        intercambiar.setOnAction(e -> handleIntercambiar(objeto, container));

        coger.setOnAction(e -> {
            if (container == cofreContainer) {
                inventario.add(objeto);
                objetos.remove(objeto);
                cofreContainer.getChildren().remove(button);

                Button inventarioButton = new Button(objeto.toString());
                addContextMenuToButton(inventarioButton, objeto, inventarioContainer);
                inventarioContainer.getChildren().add(inventarioButton);

                if (objetos.isEmpty()) {
                    mensajeCofreVacio.setText("The chest is empty");
                    mensajeCofreVacio.setVisible(true);
                }
            }
        });

        menu.getItems().addAll(dejar, eliminar, intercambiar, coger);

        button.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                menu.show(button, event.getScreenX(), event.getScreenY());
            }
        });
    }

    private void handleIntercambiar(Item item, HBox container) {
        if (!isSelectingForExchange) {
            exchangeItem = item;
            exchangeContainer = container;
            isSelectingForExchange = true;

        } else {
            if (exchangeContainer != container) {
                swapItems(exchangeItem, exchangeContainer, item, container);
            }
            isSelectingForExchange = false;
            exchangeItem = null;
            exchangeContainer = null;
        }
    }

    private void swapItems(Item item1, HBox container1, Item item2, HBox container2) {
        if (container1 == cofreContainer) {
            objetos.remove(item1);
            inventario.add(item1);
        } else {
            inventario.remove(item1);
            objetos.add(item1);
        }

        if (container2 == cofreContainer) {
            objetos.remove(item2);
            inventario.add(item2);
        } else {
            inventario.remove(item2);
            objetos.add(item2);
        }

        updateButtonContainer(item1, container1, container2);
        updateButtonContainer(item2, container2, container1);

        mensajeCofreVacio.setVisible(objetos.isEmpty());
    }

    private void updateButtonContainer(Item item, HBox oldContainer, HBox newContainer) {
        oldContainer.getChildren().removeIf(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                return item.equals(button.getUserData());
            }
            return false;
        });

        Button newButton = createItemButton(item, newContainer);
        newContainer.getChildren().add(newButton);
    }

    public void returnBack() {
        demiurge.getHome().getContainer().setItems(objetos);
        demiurge.getWizard().getWearables().setItems(inventario);
        App.cambiarPantalla(demiurge, "/screens/home.fxml");
    }

    @Override
    public void loadScreenData(Demiurge demiurge) {
        this.demiurge = demiurge;
        loadChest();
    }
}