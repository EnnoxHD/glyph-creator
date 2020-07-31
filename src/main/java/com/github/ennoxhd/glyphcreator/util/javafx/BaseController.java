package com.github.ennoxhd.glyphcreator.util.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import com.github.ennoxhd.glyphcreator.util.reflection.ReflectionUtils;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class BaseController<T extends BaseModel> implements Initializable {
	
	private T model;
	private Stage stage;
	
	public T getModel() {
		return model;
	}
	
	private void setModel(T model) {
		this.model = model;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	protected void setStage(Stage stage) {
		this.stage = stage;
	}
	
	protected BaseController() {
		Class<T> genericTypeClass = ReflectionUtils.getGenericTypeClass(getClass());
		setModel(ReflectionUtils.newInstance(genericTypeClass));
	}
	
	@Override
	public final void initialize(URL location, ResourceBundle resources) {
		init(location, resources);
		bind(getModel());
	}
	
	protected void init(URL location, ResourceBundle resources) {
		
	}
	
	protected void bind(T model) {
		
	}
}
