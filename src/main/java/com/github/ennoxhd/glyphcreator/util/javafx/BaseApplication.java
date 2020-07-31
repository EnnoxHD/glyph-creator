package com.github.ennoxhd.glyphcreator.util.javafx;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Window;

public abstract class BaseApplication extends Application {
	
	private static ProcessStarter starter;
	private static Class<? extends BaseApplication> appClass;
	private static Image icon;
	
	public BaseApplication(Class<? extends BaseApplication> appClass) {
		starter = new ProcessStarter();
		BaseApplication.appClass = appClass;
	}
	
	public BaseApplication(Class<? extends BaseApplication> appClass, String icon) {
		starter = new ProcessStarter(getClass(), icon);
		BaseApplication.appClass = appClass;
	}
	
	public static <S extends BaseModel, T extends BaseController<S>> T start(Class<T> controller) {
		return starter.start(controller);
	}
	
	public static <S extends BaseModel, T extends BaseController<S>> T start(Class<T> controller, Modality modality, Window owner) {
		return starter.start(controller, modality, owner);
	}
	
	public static Image getIcon() {
		if(starter.getIconResource() == null) return null;
		if(icon == null) icon = new Image(appClass.getResourceAsStream(starter.getIconResource()));
		return icon;
	}
}
