package com.github.ennoxhd.glyphcreator.util.javafx;

import java.util.function.DoubleConsumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;

public class WindowUtils {
	
	private static void setWindowOrigin(Window window, Point2D origin) {
		window.setX(origin.getX());
		window.setY(origin.getY());
	}
	
	private static void originToActiveScreenCenter(Window window) {
		Rectangle2D visualBounds = ScreenUtils.getActiveVisualBounds();
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

	public static Window from(ActionEvent event) {
		if(event == null) return null;
		return ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static void hide(ActionEvent event) {
		from(event).hide();
	}
}
