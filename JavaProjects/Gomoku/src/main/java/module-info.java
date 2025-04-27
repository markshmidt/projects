module org.example.gomoku {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.gomoku to javafx.fxml;
    exports org.example.gomoku;
}