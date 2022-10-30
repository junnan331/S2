module com.example.cda {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires json.simple;

    //requires org.controlsfx.controls;
    //requires com.dlsc.formsfx;

    opens com.example.cda to javafx.fxml;
    exports com.example.cda;
}