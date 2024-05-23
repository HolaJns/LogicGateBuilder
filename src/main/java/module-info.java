module com.example.logicgatebuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.logicgatebuilder to javafx.fxml;
    //exports com.logicgatebuilder;
    exports com.logicgatebuilder.app;
    opens com.logicgatebuilder.app to javafx.fxml;
}