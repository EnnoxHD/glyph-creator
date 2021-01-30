package com.github.ennoxhd.glyphcreator.app;

import com.github.ennoxhd.glyphcreator.ui.GlyphCreatorController;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseApplication;

/**
 * Main application class
 */
public class GlyphCreatorApp extends BaseApplication {

	/**
	 * Application main method which launches the application
	 * via {@link BaseApplication#launch(Class, String[], String, Class)}.
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		launch(GlyphCreatorApp.class, args,	"appicon_32x32.png", GlyphCreatorController.class);
	}
}
