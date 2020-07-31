package com.github.ennoxhd.glyphcreator.util.javafx;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Window;

public class WindowUtils {

	public static Window from(ActionEvent event) {
		if(event == null) return null;
		return ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static void hide(ActionEvent event) {
		from(event).hide();
	}
}
