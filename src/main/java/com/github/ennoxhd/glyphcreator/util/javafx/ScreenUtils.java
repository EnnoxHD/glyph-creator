package com.github.ennoxhd.glyphcreator.util.javafx;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

/**
 * Contains some screen related utility functions.
 */
public class ScreenUtils {
	
	/**
	 * Gets the currently active screen by mouse position.
	 * @param mousePosition the current mouse position
	 * @return the active screen
	 * @see Screen#getScreensForRectangle(double, double, double, double)
	 */
	private static Screen getActiveScreen(Point2D mousePosition) {
		return Screen.getScreensForRectangle(mousePosition.getX(), mousePosition.getY(), 0, 0).get(0);
	}
	
	/**
	 * Gets the visual bounds of the currently active screen.
	 * @return the visual bounds
	 * @see #getActiveScreen(Point2D)
	 * @see MouseUtils#getMousePosition()
	 * @see Screen#getVisualBounds()
	 */
	static Rectangle2D getActiveVisualBounds() {
		return getActiveScreen(MouseUtils.getMousePosition()).getVisualBounds();
	}
}
