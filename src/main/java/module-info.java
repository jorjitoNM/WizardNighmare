module org.wizard_nightmare.wizardnightmare {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens org.wizard_nightmare.controllers to javafx.fxml;
    exports org.wizard_nightmare.controllers;
    exports org.wizard_nightmare;
}