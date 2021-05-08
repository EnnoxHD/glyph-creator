package com.github.ennoxhd.glyphcreator.model;

import com.github.ennoxhd.glyphcreator.util.io.Cache;
import com.github.ennoxhd.glyphcreator.util.io.SingleValueCache;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Simple model for the main view of the application.
 */
public record GlyphCreatorModel(
		StringProperty inkscapePath,
		Cache<String> inkscapePathCache,
		StringProperty svgFilesPath) implements BaseModel {
	
	/**
	 * Instantiates the model and sets empty paths as well as
	 * invalidating the cached executable path.
	 * @see #GlyphCreatorModel(StringProperty, Cache, StringProperty)
	 */
	public GlyphCreatorModel() {
		this(new SimpleStringProperty(""),
				new SingleValueCache<String>(),
				new SimpleStringProperty(""));
	}
	
	/**
	 * Instantiates the model.
	 * @param inkscapePath path to the executable as given by the user (not validated)
	 * @param inkscapePathCache cached and validated path to the executable;
	 * gets invalidated on changes to {@link #inkscapePath}
	 * @param svgFilesPath path to the SVG files to convert as given by the user
	 */
	public GlyphCreatorModel {
		inkscapePath.addListener((obs, oldV, newV) -> {
			inkscapePathCache.invalidate();
		});
	}
}
