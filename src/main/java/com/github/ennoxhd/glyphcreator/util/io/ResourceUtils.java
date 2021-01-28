package com.github.ennoxhd.glyphcreator.util.io;

import java.util.Optional;

import javafx.scene.image.Image;

public class ResourceUtils {

	public static Optional<Image> loadImage(Class<?> clazz, String image) {
		try {
			return Optional.of(new Image(clazz.getResourceAsStream(image)));
		} catch(Exception e) {
			return Optional.empty();
		}
	}
}
