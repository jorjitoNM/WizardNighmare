module org.wizard_nightmare {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires static lombok;
    requires java.xml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires jasperreports;


    opens org.wizard_nightmare to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.ui.controllers to javafx.fxml;
    opens org.wizard_nightmare.game to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.game.dungeon to javafx.fxml, com.google.gson;
    opens org.wizard_nightmare.game.objectContainer to javafx.fxml, com.google.gson;
    opens org.wizard_nightmare.game.demiurge to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.game.object to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.game.actions to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.game.conditions to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.game.spell to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.game.spellContainer to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.game.util to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.data to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.console to javafx.fxml;
    opens org.wizard_nightmare.service to javafx.fxml,com.google.gson;
    opens org.wizard_nightmare.game.character to javafx.fxml,com.google.gson;
    exports org.wizard_nightmare;
    exports org.wizard_nightmare.game.demiurge;
    exports org.wizard_nightmare.ui.controllers to javafx.fxml;
}