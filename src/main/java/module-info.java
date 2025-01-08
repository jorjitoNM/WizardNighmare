module org.wizard_nightmare.wizardnightmare {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.wizard_nightmare to javafx.fxml;
    exports org.wizard_nightmare;
}