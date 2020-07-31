package com.github.ennoxhd.glyphcreator.model;

import com.github.ennoxhd.glyphcreator.util.javafx.BaseModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProgressDialogModel extends BaseModel {
	
	public SimpleDoubleProperty progress;
	public SimpleStringProperty status;
	
	public ProgressDialogModel() {
		progress = new SimpleDoubleProperty(-0.1);
		status = new SimpleStringProperty("Initializing");
	}
}
