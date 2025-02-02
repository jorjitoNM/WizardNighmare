package org.wizard_nightmare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.ui.controllers.DemiurgeConsumer;
import org.wizard_nightmare.ui.controllers.HomeController;
import org.wizard_nightmare.ui.controllers.StartController;

import java.io.IOException;

public class App extends Application {
    private static Stage stage;


    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setTitle("Dungeon");
        cambiarPantalla(null, "/screens/start.fxml");
        primaryStage.setMinHeight(900);
        primaryStage.setMinWidth(900);
        primaryStage.show();
    }

    public static void cambiarPantalla(Demiurge demiurge, String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFile));
            Scene scene = new Scene(fxmlLoader.load());
            Object controller = fxmlLoader.getController();

            ((DemiurgeConsumer) controller).setDemiurge(demiurge);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}