package com.github.ennoxhd.glyphcreator.model;

import com.github.ennoxhd.glyphcreator.ui.ProgressDialogController;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Simple model class for the progress dialog.
 */
public class ProgressDialogModel extends BaseModel {
	
	/**
	 * Progress of the current action.
	 */
	public SimpleDoubleProperty progress;
	
	/**
	 * Current status message derived from the progress.
	 * @see ProgressDialogController
	 */
	public SimpleStringProperty status;

	/**
	 * Instantiates the model and sets a negative progress
	 * as well as an initial status message.
	 */
	public ProgressDialogModel() {
		progress = new SimpleDoubleProperty(-0.1);
		status = new SimpleStringProperty("Initializing");
	}
}
