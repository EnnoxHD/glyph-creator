package com.github.ennoxhd.glyphcreator.util.javafx;

import com.github.ennoxhd.glyphcreator.model.GlyphCreatorModel;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Base class for an application with some additional methods
 * to support application process creation.
 */
public abstract class BaseApplication extends Application {
	
	private static ProcessStarter processStarter;
	
	private static Class<? extends BaseController<GlyphCreatorModel>> firstController;
	
	protected ProcessStarter getProcessStarter() {
		return processStarter;
	}
	
	public BaseApplication() {
		BaseApplication.processStarter = new ProcessStarter(this);
	}
	
	protected static void launch(Class<? extends Application> appClass, String[] args, String icon,
			Class<? extends BaseController<GlyphCreatorModel>> firstController) {
		BaseApplication.firstController = firstController;
		BaseApplication.processStarter.setIcon(icon);
		launch(appClass, args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		getProcessStarter().start(firstController);
	}
}
