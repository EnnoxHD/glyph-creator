package com.github.ennoxhd.glyphcreator.util.javafx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.github.ennoxhd.glyphcreator.util.reflection.ReflectionUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ProcessStarter {
	
	private Class<? extends BaseApplication> app;
	
	private String iconResource;
	
	private Class<? extends BaseApplication> getApp() {
		return this.app;
	}
	
	String getIconResource() {
		return this.iconResource;
	}
	
	public ProcessStarter() {
		this(null, null);
	}
	
	public ProcessStarter(Class<? extends BaseApplication> app, String iconResource) {
		this.app = app;
		this.iconResource = iconResource;
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
	
	public <S extends BaseModel, T extends BaseController<S>> T start(Class<T> controller, Modality modality, Window owner) {
		T controllerInstance = ReflectionUtils.newInstance(controller);
		FXMLLoader loader = new FXMLLoader(controller.getResource(getFxmlResourceUrl(controller)));
		loader.setController(controllerInstance);
		Stage stage = null;
		try {
			stage = loader.<Stage>load();
		} catch (IOException e) {
			throw new Error("File could not be loaded.", e);
		}
		controllerInstance.setStage(stage);
		if(getIconResource() != null) {
			Objects.requireNonNull(getApp());
			InputStream iconResourceStream = getApp().getResourceAsStream(getIconResource());
			if(iconResourceStream != null) stage.getIcons().add(new Image(iconResourceStream));
			else throw new Error("Resource not found for: '" + getIconResource() + "'.");
		}
		if(modality != null) stage.initModality(modality);
		if(owner != null) stage.initOwner(owner);
		WindowUtils.centerAndShow(stage);
		return controllerInstance;
	}
}
