package com.github.ennoxhd.glyphcreator.util.javafx;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.function.DoubleConsumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

class FxUtils {
	
	private static Point2D getMousePosition() {
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
		return new Point2D(mouseLocation.getX(), mouseLocation.getY());
	}
	
	private static Screen getActiveScreen(Point2D mousePosition) {
		return Screen.getScreensForRectangle(mousePosition.getX(), mousePosition.getY(), 0, 0).get(0);
	}
	
	private static Rectangle2D getActiveVisualBounds() {
		return getActiveScreen(getMousePosition()).getVisualBounds();
	}
	
	private static void setWindowOrigin(Window window, Point2D origin) {
		window.setX(origin.getX());
		window.setY(origin.getY());
	}
	
	private static void originToActiveScreenCenter(Window window) {
		Rectangle2D visualBounds = getActiveVisualBounds();
		Point2D origin = new Point2D(visualBounds.getMinX(), visualBounds.getMinY());
		Point2D area = new Point2D(visualBounds.getWidth(), visualBounds.getHeight());
		Point2D target = Point2D.ZERO.interpolate(area, 0.5).add(origin);
		setWindowOrigin(window, target);
	}
	
	private static void originToOwnerCenter(Stage stage) {
		Window owner = stage.getOwner();
		Point2D ownerOrigin = new Point2D(owner.getX(), owner.getY());
		Point2D ownerArea = new Point2D(owner.getWidth(), owner.getHeight());
		Point2D target = ownerOrigin.add(Point2D.ZERO.interpolate(ownerArea, 0.5));
		setWindowOrigin(stage, target);
	}
		
	private static void onWindowSizeInit(ObservableDoubleValue size, DoubleConsumer handler) {
		ChangeListener<? super Number> sizeListener = new ChangeListener<>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double newDoubleValue = newValue.doubleValue();
				if(newDoubleValue != Double.NaN) {
					handler.accept(newDoubleValue);
				}
				size.removeListener(this);
			}
		};
		size.addListener(sizeListener);
	}
	
	static void centerAndShow(Stage stage) {
		if(stage.getOwner() == null) {
			originToActiveScreenCenter(stage);
		} else {
			originToOwnerCenter(stage);
		}
		onWindowSizeInit(stage.widthProperty(), width -> stage.setX(stage.getX() - width * 0.5));
		onWindowSizeInit(stage.heightProperty(), height -> stage.setY(stage.getY() - height * 0.5));
		stage.show();
	}
}
