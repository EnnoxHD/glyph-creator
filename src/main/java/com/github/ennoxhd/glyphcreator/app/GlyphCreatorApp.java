package com.github.ennoxhd.glyphcreator.app;

import com.github.ennoxhd.glyphcreator.ui.GlyphCreatorController;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseApplication;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application class
 */
public class GlyphCreatorApp extends BaseApplication {
	
	/**
	 * Instantiates the {@link BaseApplication} with general parameters
	 * for the main application class and application icon.
	 */
	public GlyphCreatorApp() {
		super(GlyphCreatorApp.class, "appicon_32x32.png");
	}

	/**
	 * Application main method calling {@link Application#launch(String...)}.
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * {@link Application#start(Stage)} method, that starts
	 * the Application via {@link BaseApplication#start(Class)}.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		start(GlyphCreatorController.class);
	}
}
