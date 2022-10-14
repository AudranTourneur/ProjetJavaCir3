module com.example.projetcir3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.projetcir3 to javafx.fxml;
    exports com.example.projetcir3;
}