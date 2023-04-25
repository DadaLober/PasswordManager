module PasswordManager {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.base;
    requires com.jfoenix;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.coreui;

    opens PM;
    exports PM;
}