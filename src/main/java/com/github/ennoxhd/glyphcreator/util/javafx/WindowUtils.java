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

/**
 * Contains some window related utility functions.
 */
public class WindowUtils {
	
	/**
	 * Sets the origin of a given window to the given screen point.
	 * @param window the window where to set the new origin
	 * @param origin new top left origin of the window
	 */
	private static void setWindowOrigin(Window window, Point2D origin) {
		window.setX(origin.getX());
		window.setY(origin.getY());
	}
	
	/**
	 * Sets the origin of the {@code window} to the center of the active screen.
	 * @param window the window on which to set the origin
	 * @see ScreenUtils#getActiveVisualBounds()
	 * @see #setWindowOrigin(Window, Point2D)
	 */
	private static void originToActiveScreenCenter(Window window) {
		Rectangle2D visualBounds = ScreenUtils.getActiveVisualBounds();
		Point2D origin = new Point2D(visualBounds.getMinX(), visualBounds.getMinY());
		Point2D area = new Point2D(visualBounds.getWidth(), visualBounds.getHeight());
		Point2D target = Point2D.ZERO.interpolate(area, 0.5).add(origin);
		setWindowOrigin(window, target);
	}
	
	/**
	 * Sets the origin of the given {@code stage} to the center of the owner of it.
	 * @param stage the stage on which to modify the origin and get the owner from
	 * @see #setWindowOrigin(Window, Point2D)
	 */
	private static void originToOwnerCenter(Stage stage) {
		Window owner = stage.getOwner();
		Point2D ownerOrigin = new Point2D(owner.getX(), owner.getY());
		Point2D ownerArea = new Point2D(owner.getWidth(), owner.getHeight());
		Point2D target = ownerOrigin.add(Point2D.ZERO.interpolate(ownerArea, 0.5));
		setWindowOrigin(stage, target);
	}
	
	/**
	 * Helper function to set the position of a window after the dimensions were calculated.
	 * Immediately removes the the window size listeners after the initial size calculation.
	 * @param size the window size property to act on
	 * @param handler consumer of the calculated size property
	 * in which the stage position may be modified
	 */
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
	
	/**
	 * Center the {@code stage} on the active screen or the owner window if known and
	 * show the {@link Stage}.
	 * @param stage the {@link Stage} to show
	 * @see #originToActiveScreenCenter(Window)
	 * @see #originToOwnerCenter(Stage)
	 * @see #onWindowSizeInit(ObservableDoubleValue, DoubleConsumer)
	 */
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
	
	/**
	 * Gets the {@link Window} from an event.
	 * @param event the event to get the window from
	 * @return the window from where the event was fired
	 * @see ActionEvent#getSource()
	 */
	public static Window from(ActionEvent event) {
		if(event == null) return null;
		return ((Node) event.getSource()).getScene().getWindow();
	}
	
	/**
	 * Hides the window from where the event was fired.
	 * @param event the event reference to get the window from
	 * @see #from(ActionEvent)
	 */
	public static void hide(ActionEvent event) {
		from(event).hide();
	}
}
