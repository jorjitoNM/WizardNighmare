module org.wizard_nightmare.wizardnightmare {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires static lombok;
    requires java.xml.bind;


    opens org.wizard_nightmare to javafx.fxml;
    exports org.wizard_nightmare;
}