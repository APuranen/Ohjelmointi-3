/**
 * Provides tools for representing Sisu add-on.
 */
module fi.tuni.prog3.projekti {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires org.json;

    opens fi.tuni.prog3.projekti to javafx.fxml;
    exports fi.tuni.prog3.projekti;
}
