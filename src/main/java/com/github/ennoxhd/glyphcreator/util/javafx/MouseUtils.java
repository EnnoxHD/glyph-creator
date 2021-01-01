package com.github.ennoxhd.glyphcreator.util.javafx;

import java.awt.MouseInfo;
import java.awt.Point;

import javafx.geometry.Point2D;

class MouseUtils {

	static Point2D getMousePosition() {
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		return new Point2D(mouseLocation.getX(), mouseLocation.getY());
	}	
}
