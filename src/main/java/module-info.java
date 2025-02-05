module org.wizard_nightmare {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires static lombok;
    requires java.xml;


    opens org.wizard_nightmare to javafx.fxml;
    opens org.wizard_nightmare.ui.controllers to javafx.fxml;
    opens org.wizard_nightmare.game to javafx.fxml;
    opens org.wizard_nightmare.game.dungeon to javafx.fxml;
    opens org.wizard_nightmare.game.objectContainer to javafx.fxml;
    opens org.wizard_nightmare.game.demiurge to javafx.fxml;
    opens org.wizard_nightmare.game.object to javafx.fxml;
    opens org.wizard_nightmare.game.actions to javafx.fxml;
    opens org.wizard_nightmare.game.conditions to javafx.fxml;
    opens org.wizard_nightmare.game.spell to javafx.fxml;
    opens org.wizard_nightmare.game.spellContainer to javafx.fxml;
    opens org.wizard_nightmare.game.util to javafx.fxml;
    opens org.wizard_nightmare.data to javafx.fxml;
    opens org.wizard_nightmare.console to javafx.fxml;
    opens org.wizard_nightmare.service to javafx.fxml;
    exports org.wizard_nightmare;
    exports org.wizard_nightmare.game.demiurge;
    exports org.wizard_nightmare.ui.controllers to javafx.fxml;
}