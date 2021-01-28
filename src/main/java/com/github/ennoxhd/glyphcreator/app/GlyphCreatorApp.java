package com.github.ennoxhd.glyphcreator.app;

import com.github.ennoxhd.glyphcreator.ui.GlyphCreatorController;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseApplication;

import javafx.application.Application;

/**
 * Main application class
 */
public class GlyphCreatorApp extends BaseApplication {
	
	/**
	 * The icon resource for the application to use.
	 */
	private static final String APP_ICON = "appicon_32x32.png";
	
//	/**
//	 * Instantiates the {@link BaseApplication} with general parameters
//	 * for the main application class and application icon.
//	 */
//	public GlyphCreatorApp() {
//		super(APP_ICON);
//	}

	/**
	 * Application main method calling {@link Application#launch(String...)}.
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		launch(GlyphCreatorApp.class, args, APP_ICON, GlyphCreatorController.class);
	}

//	/**
//	 * {@link Application#start(Stage)} method, that starts
//	 * the Application via {@link ProcessStarter#start(Class)}.
//	 */
//	@Override
//	public void start(Stage stage) throws Exception {
//		getProcessStarter().start(GlyphCreatorController.class);
//	}
}
