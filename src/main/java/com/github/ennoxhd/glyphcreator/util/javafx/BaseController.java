package com.github.ennoxhd.glyphcreator.util.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import com.github.ennoxhd.glyphcreator.util.reflection.ReflectionUtils;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Controller class for a {@link Stage} as defined in a corresponding {@code *.fxml} file.
 * The specified model instance gets created on instantiation of the controller.
 * This is done by analyzing the generic type {@link T} of the specific controller.
 * Also a reference to the main application is available.
 * @param <T> the model to use with the controller
 * @see ProcessStarter
 */
public abstract class BaseController<T extends BaseModel> implements Initializable {
	
	/**
	 * Instantiated data model for the {@link #stage}. 
	 */
	private T model;
	
	/**
	 * Loaded {@link Stage} of the corresponding {@code *.fxml} file.
	 */
	private Stage stage;
	
	private ProcessStarter processStarter;
	
	/**
	 * Gets the data model associated with this controller.
	 * @return the data model
	 */
	public T getModel() {
		return model;
	}
	
	/**
	 * Sets the data model instance.
	 * @param model the new data model to set
	 */
	private void setModel(T model) {
		this.model = model;
	}
	
	/**
	 * Gets the stage associated with this controller.
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}
	
	/**
	 * Sets the stage instance.
	 * @param stage the new stage to set
	 */
	void setStage(Stage stage) {
		this.stage = stage;
	}
	
	private ProcessStarter getProcessStarter() {
		return this.processStarter;
	}
	
	public void setProcessStarter(ProcessStarter processStarter) {
		this.processStarter = processStarter;
	}
	
	/**
	 * Instantiates the controller class and the corresponding model via
	 * analysis of the generic type {@link T}.
	 */
	protected BaseController() {
		Class<T> genericTypeClass = ReflectionUtils.getGenericTypeClass(getClass());
		setModel(ReflectionUtils.newInstance(genericTypeClass));
	}
	
	public Image getIcon() {
		return getProcessStarter().getIcon().orElse(null);
	}
	
	public <S extends BaseModel, U extends BaseController<S>> U
			start(Class<U> controller, Modality modality, Window owner) {
		return getProcessStarter().start(controller, modality, owner);
	}
	
	/**
	 * At first the {@link #init(URL, ResourceBundle)} method gets called
	 * for convenience and after that the {@link #bind(BaseModel)} method.
	 * @see #init(URL, ResourceBundle)
	 * @see #bind(BaseModel)
	 */
	@Override
	public final void initialize(URL location, ResourceBundle resources) {
		init(location, resources);
		bind(getModel());
	}
	
	/**
	 * This method may be overridden by a controller subclass to make
	 * use of the given location and resources.
	 * @param location location
	 * @param resources resources
	 * @see #initialize(URL, ResourceBundle)
	 */
	protected void init(URL location, ResourceBundle resources) {
		
	}
	
	/**
	 * This method may be overridden by a controller subclass and
	 * gives room for binding operations between the data model
	 * properties and some components' properties of the stage.
	 * The data model is given as a parameter for referencing
	 * the properties on the model side.
	 * @param model the data model
	 * @see BaseController#initialize(URL, ResourceBundle)
	 */
	protected void bind(T model) {
		
	}
}
