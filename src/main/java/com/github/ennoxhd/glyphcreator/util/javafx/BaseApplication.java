package com.github.ennoxhd.glyphcreator.util.javafx;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Window;

public abstract class BaseApplication extends Application {
	
	private ProcessStarter starter;
	private Class<? extends BaseApplication> appClass;
	private Image icon;
	
	public BaseApplication(Class<? extends BaseApplication> appClass) {
		starter = new ProcessStarter();
		this.appClass = appClass;
	}
	
	public BaseApplication(Class<? extends BaseApplication> appClass, String icon) {
		starter = new ProcessStarter(getClass(), icon);
		this.appClass = appClass;
	}
	
	public <S extends BaseModel, T extends BaseController<S>> T start(Class<T> controller) {
		return starter.start(controller);
	}
	
	public <S extends BaseModel, T extends BaseController<S>> T start(Class<T> controller, Modality modality, Window owner) {
		return starter.start(controller, modality, owner);
	}
	
	public Image getIcon() {
		if(starter.getIconResource() == null) return null;
		if(icon == null) icon = new Image(appClass.getResourceAsStream(starter.getIconResource()));
		return icon;
	}
}
