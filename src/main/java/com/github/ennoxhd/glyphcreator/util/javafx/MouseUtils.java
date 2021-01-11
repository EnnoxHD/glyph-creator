package com.github.ennoxhd.glyphcreator.util.javafx;

import java.awt.MouseInfo;
import java.awt.Point;

import javafx.geometry.Point2D;

/**
 * Contains some mouse input related utility functions.
 */
class MouseUtils {

	/**
	 * Gets the current position of the mouse cursor on the screen.
	 * @return the position of the mouse cursor
	 */
	static Point2D getMousePosition() {
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		return new Point2D(mouseLocation.getX(), mouseLocation.getY());
	}	
}
