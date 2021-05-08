package com.github.ennoxhd.glyphcreator.model;

import com.github.ennoxhd.glyphcreator.ui.ProgressDialogController;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Simple model for the progress dialog.
 */
public record ProgressDialogModel(
		DoubleProperty progress,
		StringProperty status) implements BaseModel {
	
	/**
	 * Instantiates the model and sets a negative progress
	 * as well as an initial status message.
	 * @see #ProgressDialogModel(DoubleProperty, StringProperty)
	 */
	public ProgressDialogModel() {
		this(new SimpleDoubleProperty(-0.1),
				new SimpleStringProperty("Initializing"));
	}
	
	/**
	 * Instantiates the model.
	 * @param progress progress of the current action
	 * @param status current status message derived from the progress
	 * @see ProgressDialogController
	 */
	public ProgressDialogModel {}
}
