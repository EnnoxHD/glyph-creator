package com.github.ennoxhd.glyphcreator.util.io;

import java.net.URL;
import java.util.Optional;

import com.github.ennoxhd.glyphcreator.util.javafx.BaseController;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseModel;

import javafx.scene.image.Image;

/**
 * Contains some resource files related utility functions.
 */
public class ResourceUtils {

	/**
	 * Tries to load an image resource.
	 * @param clazz base class
	 * @param image relative path of the resource from the base class
	 * @return an {@link Optional} of an image if the resource loading was successful,
	 * otherwise an {@link Optional#empty()} object
	 */
	public static Optional<Image> loadImage(Class<?> clazz, String image) {
		return Optional.of(new Image(clazz.getResourceAsStream(image)));
	}
	
	/**
	 * Creates the {@link URL} for the {@code *.fxml} resource file of the related {@code controller}.
	 * @param controller the related controller class
	 * @return the relative {@link URL} to the {@code *.fxml} resource
	 */
	public static URL getFxmlUrl(Class<? extends BaseController<? extends BaseModel>> controller) {
		final String className = controller.getSimpleName();
		return controller.getResource(className.substring(0, className.indexOf("Controller")) + ".fxml");
	}
}
