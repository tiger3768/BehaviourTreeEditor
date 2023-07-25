module com.bteditor {
	requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.bteditor to javafx.fxml;
    exports com.bteditor;
}
