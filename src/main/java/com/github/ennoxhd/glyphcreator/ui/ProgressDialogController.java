package com.github.ennoxhd.glyphcreator.ui;

import com.github.ennoxhd.glyphcreator.model.ProgressDialogModel;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class ProgressDialogController extends BaseController<ProgressDialogModel> {
	
	@FXML
	private ProgressBar pgb_progress;
	@FXML
	private Label lbl_status;
	@FXML
	private Button btn_cancel;
	
	private Boolean stopped = false;
	
	public boolean isStopped() {
		synchronized (stopped) {
			return stopped;
		}
	}
	
	private void setStopped(boolean stopped) {
		synchronized (this.stopped) {
			this.stopped = stopped;
		}
	}
	
	@Override
	protected void bind(ProgressDialogModel model) {
		pgb_progress.progressProperty().bind(model.progress);
		lbl_status.textProperty().bind(model.status);
		
		model.progress.addListener((obs, oldVal, newVal) -> {
			model.status.set(statusFromProgress(newVal.doubleValue()));
		});
	}
	
	public void setProgress(double progress) {
		if(progress > getModel().progress.get()) {
			getModel().progress.set(progress);
		}
	}
	
	@FXML
	private void btn_cancel_onAction(ActionEvent e) {
		if(!isStopped()) setStopped(true);
		btn_cancel.setDisable(true);
	}
	
	private String statusFromProgress(Double value) {
		if(value == null || value < 0) {
			return "Initializing";
		} else if(value < 1) {
			return String.valueOf(Math.round(value * 100)) + "%";
		} else {
			return "Done";
		}
	}
}
