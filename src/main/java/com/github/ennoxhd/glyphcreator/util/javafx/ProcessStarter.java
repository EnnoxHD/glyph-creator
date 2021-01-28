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
	
	private BaseApplication app;
	
	private Optional<Image> icon;
	
	private BaseApplication getApp() {
		return app;
	}
	
	private void setApp(BaseApplication app) {
		this.app = app;
	}
	
	public Optional<Image> getIcon() {
		return icon;
	}
	
	private void setIcon(Optional<Image> icon) {
		this.icon = icon;
	}
	
	public void setIcon(String icon) {
		setIcon(ResourceUtils.loadImage(getApp().getClass(), icon));
	}
	
	public ProcessStarter(BaseApplication app) {
		setApp(app);
		setIcon(Optional.empty());
	}
	
	private String getFxmlResourceUrl(Class<? extends BaseController<? extends BaseModel>> controller) {
		final String className = controller.getSimpleName();
		try {
			return className.substring(0, className.indexOf("Controller")) + ".fxml";
		} catch(IndexOutOfBoundsException e) {
			throw new Error("No matching fxml file found.", e);
		}
	}
	
	public <S extends BaseModel, T extends BaseController<S>> T start(Class<T> controller) {
		return start(controller, null, null);
	}
	
	public <S extends BaseModel, T extends BaseController<S>> T
			start(Class<T> controller, Modality modality, Window owner) {
		T controllerInstance = ReflectionUtils.newInstance(controller);
		controllerInstance.setProcessStarter(this);
		FXMLLoader loader = new FXMLLoader(controller.getResource(getFxmlResourceUrl(controller)));
		loader.setController(controllerInstance);
		Stage stage = null;
		try {
			stage = loader.<Stage>load();
		} catch (IOException e) {
			throw new Error("File could not be loaded.", e);
		}
		controllerInstance.setStage(stage);
		if(getIcon().isPresent()) stage.getIcons().add(getIcon().get());
		if(modality != null) stage.initModality(modality);
		if(owner != null) stage.initOwner(owner);
		WindowUtils.centerAndShow(stage);
		return controllerInstance;
	}
}
