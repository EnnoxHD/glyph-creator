package com.github.ennoxhd.glyphcreator.app;

import com.github.ennoxhd.glyphcreator.ui.GlyphCreatorController;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseApplication;

import javafx.stage.Stage;

public class GlyphCreatorApp extends BaseApplication {
		
	public GlyphCreatorApp() {
		super(GlyphCreatorApp.class, "appicon_32x32.png");
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		start(GlyphCreatorController.class);
	}
}
