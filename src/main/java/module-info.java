module com.github.ennoxhd.glyphcreator {
	exports com.github.ennoxhd.glyphcreator.app;
	exports com.github.ennoxhd.glyphcreator.ui to javafx.fxml, javafx.graphics;
	opens com.github.ennoxhd.glyphcreator.ui to javafx.fxml;
	requires java.base;
	requires javafx.base;
	requires transitive javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;
}
