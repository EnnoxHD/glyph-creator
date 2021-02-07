package com.github.ennoxhd.glyphcreator.util.javafx;

import com.github.ennoxhd.glyphcreator.model.GlyphCreatorModel;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Base class for an application with some additional methods
 * to support application process creation.
 */
public abstract class BaseApplication extends Application {
	
	/**
	 * General {@link ProcessStarter} for this application.
	 */
	private static ProcessStarter processStarter;
	
	/**
	 * The first {@link BaseController} to load and show.
	 */
	private static Class<? extends BaseController<GlyphCreatorModel>> firstController;
	
	/**
	 * The application icon resource string.
	 */
	private static String icon;
	
	/**
	 * Gets the general {@link ProcessStarter} of this application.
	 * @return the {@link ProcessStarter}
	 */
	protected static ProcessStarter getProcessStarter() {
		return processStarter;
	}
	
	/**
	 * Sets a new {@link ProcessStarter} for this application.
	 * In general only used once on the start of the application.
	 * @param processStarter the new {@link ProcessStarter}
	 */
	private static void setProcessStarter(ProcessStarter processStarter) {
		BaseApplication.processStarter = processStarter;
	}
	
	/**
	 * Gets the first {@link BaseController}.
	 * @return the first {@link BaseController}
	 */
	private static Class<? extends BaseController<GlyphCreatorModel>> getFirstController() {
		return BaseApplication.firstController;
	}
	
	/**
	 * Sets the first {@link BaseController} that will be loaded and shown.
	 * @param firstController the first {@link BaseController}
	 */
	private static void setFirstController(Class<? extends BaseController<GlyphCreatorModel>> firstController) {
		BaseApplication.firstController = firstController;
	}
	
	/**
	 * Gets the application icon resource string.
	 * @return the icon resource
	 */
	private static String getIcon() {
		return icon;
	}
	
	/**
	 * Sets the application icon resource string for later use.
	 * @param icon the application icon resource to use
	 */
	private static void setIcon(String icon) {
		BaseApplication.icon = icon;
	}
	
	/**
	 * Instantiates the basic application class and
	 * creates a new {@link ProcessStarter} for this application.
	 */
	public BaseApplication() {
		setProcessStarter(new ProcessStarter(this));
		getProcessStarter().setIcon(getIcon());
	}
	
	/**
	 * Launches the {@code appClass} application with the defined arguments and loads the application icon.
	 * It also loads and shows the first Stage of the application
	 * which is defined by the parameter {@code firstController}.
	 * @param appClass the application class to load
	 * @param args the command line arguments to pass
	 * @param icon the general application icon to use
	 * @param firstController the first Stage to show
	 */
	protected static void launch(Class<? extends Application> appClass, String[] args, String icon,
			Class<? extends BaseController<GlyphCreatorModel>> firstController) {
		setIcon(icon);
		setFirstController(firstController);
		launch(appClass, args);
	}
	
	/**
	 * Shows the first Stage defined by {@link BaseApplication#firstController}
	 * after the application has started.
	 * @param stage created main Stage, not used
	 */
	@Override
	public void start(Stage stage) throws Exception {
		getProcessStarter().start(getFirstController());
	}
}
