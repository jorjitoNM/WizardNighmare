package org.wizard_nightmare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Dungeon");
        cambiarPantalla("/screens/singa_storage.fxml");
        primaryStage.setMinHeight(900);
        primaryStage.setMinWidth(900);
        primaryStage.show();
    }

    public static void cambiarPantalla(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}