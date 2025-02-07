package org.wizard_nightmare.ui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.wizard_nightmare.App;
import org.wizard_nightmare.game.demiurge.Demiurge;
import org.wizard_nightmare.ui.common.Constants;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FinishController implements DemiurgeConsumer {

    @FXML
    private AnchorPane screen;

    private Demiurge demiurge;

    public void initialize() {
        try {
            String imagePath = getClass().getResource(Constants.FINISH_IMAGE).toExternalForm();
            screen.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: cover;" +
                    "-fx-background-repeat: no-repeat;");
        } catch (NullPointerException e) {
            System.out.println("Image not found!");
        }
    }

    @Override
    public void setDemiurge(Demiurge demiurge) {
        this.demiurge = demiurge;
    }

    public void goToMenu(ActionEvent actionEvent) {
        App.cambiarPantalla(demiurge, Constants.START);
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void getInforme(ActionEvent actionEvent) {
        try {
            InputStream is = Objects.requireNonNull(
                    getClass().getResourceAsStream("/informes/informe_wizard.jrxml"),
                    "No se encontró el archivo del informe."
            );

            JasperReport jasperReport = JasperCompileManager.compileReport(is);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", demiurge.getWizard().getName());
            parameters.put("energy", demiurge.getWizard().getEnergy());
            parameters.put("energyMax", demiurge.getWizard().getEnergyMax());
            parameters.put("life", demiurge.getWizard().getLife());
            parameters.put("crystalCarrier", demiurge.getWizard().getCrystalCarrier());
            parameters.put("wearables", demiurge.getWizard().getWearables());
            parameters.put("jewelryBag", demiurge.getWizard().getJewelryBag());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);

        } catch (JRException e) {
            System.err.println("Error al generar el informe: " + e.getMessage());
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("Error al cargar el recurso: " + e.getMessage());
        }
    }
}
