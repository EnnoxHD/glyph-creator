package com.github.ennoxhd.glyphcreator.ui;

import com.github.ennoxhd.glyphcreator.model.ProgressDialogModel;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * Controller for the display of the conversion progress
 */
public class ProgressDialogController extends BaseController<ProgressDialogModel> {
	
	/**
	 * Visual representation of the progress
	 */
	@FXML
	private ProgressBar pgb_progress;
	
	/**
	 * Progress status message
	 */
	@FXML
	private Label lbl_status;
	
	/**
	 * Cancel the conversion process
	 */
	@FXML
	private Button btn_cancel;
	
	/**
	 * Flag to stop the conversion process
	 */
	private Boolean stopped = false;
	
	/**
	 * Lock object for {@link #stopped}
	 */
	private final Object stoppedLock = new Object();
	
	/**
	 * Is the conversion process stopped by the user?
	 * @return {@code true} if the process is stopped, {@code false} otherwise
	 */
	public boolean isStopped() {
		synchronized (stoppedLock) {
			return stopped;
		}
	}
	
	/**
	 * Sets the {@link #stopped} flag so the conversion will be stopped if {@code true}.
	 * @param stopped provide {@code true} if the conversion should be stopped,
	 * providing {@code false} has no effect
	 */
	private void setStopped(boolean stopped) {
		synchronized (stoppedLock) {
			this.stopped = stopped;
		}
	}
	
	/**
	 * Bindings for the progress and status
	 */
	@Override
	protected void bind(ProgressDialogModel model) {
		pgb_progress.progressProperty().bind(model.progress);
		lbl_status.textProperty().bind(model.status);
		
		model.progress.addListener((obs, oldVal, newVal) -> {
			model.status.set(statusFromProgress(newVal.doubleValue()));
		});
	}
	
	/**
	 * Sets a new progress value if it is greater than the last one.
	 * @param progress new progress value to set
	 */
	public void setProgress(double progress) {
		if(progress > getModel().progress.get()) {
			getModel().progress.set(progress);
		}
	}
	
	/**
	 * Stops the conversion process.
	 * @param e event
	 */
	@FXML
	private void btn_cancel_onAction(ActionEvent e) {
		if(!isStopped()) setStopped(true);
		btn_cancel.setDisable(true);
	}
	
	/**
	 * Generates a status message from the progress value.
	 * <ul>
	 * <li>{@code value == null} or {@code value < 0}: Initializing</li>
	 * <li>{@code value < 1}: 0% ... 100%</li>
	 * <li>{@code value > 1}: Done</li>
	 * </ul>
	 * @param value progress value
	 * @return status message
	 */
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
