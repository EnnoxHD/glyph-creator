package com.github.ennoxhd.glyphcreator.model;

import com.github.ennoxhd.glyphcreator.util.io.Cache;
import com.github.ennoxhd.glyphcreator.util.io.SingleValueCache;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GlyphCreatorModel extends BaseModel {
	
	public StringProperty inkscapePath;
	public Cache<String> inkscapePathCache;
	public StringProperty svgFilesPath;
	
	public GlyphCreatorModel() {
		inkscapePath = new SimpleStringProperty("");
		inkscapePathCache = new SingleValueCache<String>();
		svgFilesPath = new SimpleStringProperty("");
		
		inkscapePath.addListener((obs, oldV, newV) -> {
			inkscapePathCache.invalidate();
		});
	}
}
