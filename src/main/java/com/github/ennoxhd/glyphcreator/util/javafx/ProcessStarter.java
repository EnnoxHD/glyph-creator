package com.github.ennoxhd.glyphcreator.util.javafx;

import java.io.IOException;
import java.util.Optional;

import com.github.ennoxhd.glyphcreator.util.io.ResourceUtils;
import com.github.ennoxhd.glyphcreator.util.reflection.ReflectionUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Starts application processes by a given Controller class.
 * The Controller gets instantiated and a Stage is loaded from a file resource.
 * The file name of the {@code *.fxml} file and the class name
 * of the controller ({@code *Controller.java}) must match as well as the resource path.
 * E.g. {@code Test.fxml} and {@code TestController.java} do match as defined by the mechanism.
 */
public class ProcessStarter {
	
	/**
	 * Back reference to the main application class
	 */
	private BaseApplication app;
	
	/**
	 * Optional application image
	 */
	private Optional<Image> icon;
	
	/**
	 * Gets the main application class.
	 * @return the main application class
	 */
	private BaseApplication getApp() {
		return app;
	}
	
	/**
	 * Sets the main application class.
	 * Generally set only once on instantiation.
	 * @param app the main application class
	 */
	private void setApp(BaseApplication app) {
		this.app = app;
	}
	
	/**
	 * Gets the optional application icon.
	 * @return the icon
	 */
	public Optional<Image> getIcon() {
		return icon;
	}
	
	/**
	 * Sets the optional main application icon.
	 * @param icon the application icon
	 */
	private void setIcon(Optional<Image> icon) {
		this.icon = icon;
	}
	
	/**
	 * Tries to load the given application icon resource.
	 * Sets the optional icon.
	 * @param icon the relative resource path from the application main class
	 */
	public void setIcon(String icon) {
		setIcon(ResourceUtils.loadImage(getApp().getClass(), icon));
	}
	
	/**
	 * Instantiates a {@link ProcessStarter} and
	 * sets the back reference to the main application class.
	 * @param app the main application class
	 */
	public ProcessStarter(BaseApplication app) {
		setApp(app);
		setIcon(Optional.empty());
	}
	
	/**
	 * Starts a new application process.
	 * @param <T> the {@link BaseController}
	 * @param controller the controller class to start
	 * @return the controller instance
	 */
	public <T extends BaseController<? extends BaseModel>> T start(Class<T> controller) {
		return start(controller, null, null);
	}
	
	/**
	 * Starts a new application process.
	 * @param <T> the {@link BaseController}
	 * @param controller the Controller and therefore Stage to load
	 * @param modality the modality of a potential dialog
	 * @param owner the owner of a potential dialog
	 * @return the instance of {@code controller}
	 */
	public <T extends BaseController<? extends BaseModel>> T
			start(Class<T> controller, Modality modality, Window owner) {
		@SuppressWarnings("unchecked")
		T controllerInstance = (T) ReflectionUtils.newInstance(controller);
		controllerInstance.setProcessStarter(this);
		FXMLLoader loader = new FXMLLoader(ResourceUtils.getFxmlUrl(controller));
		loader.setController(controllerInstance);
		Stage stage = null;
		try {
			stage = loader.<Stage>load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		controllerInstance.setStage(stage);
		if(getIcon().isPresent()) stage.getIcons().add(getIcon().get());
		if(modality != null) stage.initModality(modality);
		if(owner != null) stage.initOwner(owner);
		WindowUtils.centerAndShow(stage);
		return controllerInstance;
	}
}
