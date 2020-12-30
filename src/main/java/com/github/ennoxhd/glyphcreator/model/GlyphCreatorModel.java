package com.github.ennoxhd.glyphcreator.model;

import com.github.ennoxhd.glyphcreator.util.io.Cache;
import com.github.ennoxhd.glyphcreator.util.io.SingleValueCache;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Simple model class for the main view of the application.
 */
public class GlyphCreatorModel extends BaseModel {
	
	/**
	 * Path to the executable as given by the user (not validated).
	 */
	public StringProperty inkscapePath;
	
	/**
	 * Cached and validated path to the executable.
	 * Gets invalidated on changes to {@link #inkscapePath}.
	 */
	public Cache<String> inkscapePathCache;
	
	/**
	 * Path to the SVG files to convert as given by the user.
	 */
	public StringProperty svgFilesPath;
	
	/**
	 * Instantiates the model and sets empty paths as well as
	 * invalidating the cached executable path.
	 */
	public GlyphCreatorModel() {
		inkscapePath = new SimpleStringProperty("");
		inkscapePathCache = new SingleValueCache<String>();
		svgFilesPath = new SimpleStringProperty("");
		
		inkscapePath.addListener((obs, oldV, newV) -> {
			inkscapePathCache.invalidate();
		});
	}
}
