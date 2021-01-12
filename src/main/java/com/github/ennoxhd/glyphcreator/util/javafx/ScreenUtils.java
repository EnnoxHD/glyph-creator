package com.github.ennoxhd.glyphcreator.util.javafx;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class ScreenUtils {
	
	private static Screen getActiveScreen(Point2D mousePosition) {
		return Screen.getScreensForRectangle(mousePosition.getX(), mousePosition.getY(), 0, 0).get(0);
	}
	
	static Rectangle2D getActiveVisualBounds() {
		return getActiveScreen(MouseUtils.getMousePosition()).getVisualBounds();
	}
}
