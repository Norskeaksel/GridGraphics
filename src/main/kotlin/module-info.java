module org.gridgraphics {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.desktop;


    opens org.gridgraphics to javafx.fxml;
    exports org.gridgraphics;
}