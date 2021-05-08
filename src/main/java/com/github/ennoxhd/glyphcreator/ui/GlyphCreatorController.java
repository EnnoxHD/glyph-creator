package com.github.ennoxhd.glyphcreator.ui;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import com.github.ennoxhd.glyphcreator.model.GlyphCreatorModel;
import com.github.ennoxhd.glyphcreator.services.InkscapeVersionService;
import com.github.ennoxhd.glyphcreator.services.VectorImageConversionService;
import com.github.ennoxhd.glyphcreator.util.io.FilePathUtils;
import com.github.ennoxhd.glyphcreator.util.javafx.BaseController;
import com.github.ennoxhd.glyphcreator.util.javafx.Dialogs;
import com.github.ennoxhd.glyphcreator.util.javafx.WindowUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;

/**
 * Controller for the main view of the application
 */
public class GlyphCreatorController extends BaseController<GlyphCreatorModel> {

	/**
	 * Inkscape path
	 */
	@FXML
	private TextField txt_inkscape;
	
	/**
	 * Inkscape path file dialog
	 */
	@FXML
	private Button btn_inkscape;
	
	/**
	 * SVG images path
	 */
	@FXML
	private TextField txt_svgs;
	
	/**
	 * SVG images path file dialog
	 */
	@FXML
	private Button btn_svgs;
	
	/**
	 * Conversion
	 */
	@FXML
	private Button btn_convert;
	
	/**
	 * Exit application
	 */
	@FXML
	private Button btn_cancel;
	
	/**
	 * Bindings for Inkscape path and SVG images path
	 */
	@Override
	protected void bind(GlyphCreatorModel model) {
		txt_inkscape.textProperty().bindBidirectional(model.inkscapePath());
		txt_svgs.textProperty().bindBidirectional(model.svgFilesPath());
	}
	
	/**
	 * Show a dialog for empty executable path.
	 */
	private void showDialogForEmptyExecutablePath() {
		Dialogs.errorDialog("Error: Empty path", "Executable path is empty!",
				"Please specify the path to the Inkscape executable.", getIcon(), getStage());
	}
	
	/**
	 * Show a dialog for empty working path.
	 */
	private void showDialogForEmptyWorkingPath() {
		Dialogs.errorDialog("Error: Empty path", "Working directory path is empty!",
				"Please specify the path for the working directory.", getIcon(), getStage());
	}
	
	/**
	 * Shows a dialog for an incompatible version of Inkscape.
	 */
	private void showDialogForIncompatibleVersion() {
		Dialogs.warnDialog("Warning: incorrect version", "Inkscape version might not be compatible!",
				"Inkscape version 1.0 is required as a minimum. Please consider updating the software.",
				getIcon(), getStage());
	}
	
	/**
	 * If compatible, starts the conversion process and gives feedback about the completion.
	 * @param e event
	 */
	private void convert(ActionEvent e) {
		ProgressDialogController controller = start(ProgressDialogController.class,
				Modality.WINDOW_MODAL, WindowUtils.from(e));
		VectorImageConversionService.convertAll(getModel(), controller, result -> {
			controller.getStage().hide();
			if(result == null) {
				Dialogs.errorDialog("Error", "Something went wrong",
						"The conversion process failed.", getIcon(), getStage());
			} else if(result.isEmpty()) {
				Dialogs.infoDialog("Done", "Conversion completed",
						"All SVG images were successfully converted.", getIcon(), getStage());
			} else {
				List<String> areaContent = result.stream().map(Path::toString).collect(Collectors.toList());
				Dialogs.warnDialog("Canceled", "Conversion canceled",
						"The conversion was canceled. Therefore the following SVG images weren't converted:",
						areaContent, getIcon(), getStage());
			}
		});
	}
	
	/**
	 * Shows a file dialog to choose the Inkscape executable.
	 * @param e event
	 */
	@FXML
	private void btn_inkscape_onAction(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Inkscape: Executable");
		ExtensionFilter defaultExtensionFilter = new ExtensionFilter("Executable",
				"inkscape", "inkscape.exe", "inkscape.com");
		fileChooser.getExtensionFilters().addAll(defaultExtensionFilter,
				new ExtensionFilter("All files", "*"));
		fileChooser.setSelectedExtensionFilter(defaultExtensionFilter);
		File recentLocation = FilePathUtils.getRecentDirectory(getModel().inkscapePath().get());
		fileChooser.setInitialDirectory(recentLocation);
		File selectedFile = fileChooser.showOpenDialog(WindowUtils.from(e));
		if(selectedFile == null) return;
		getModel().inkscapePath().set(selectedFile.getPath());
		InkscapeVersionService.checkVersion(getModel(), isCompatible -> {
			if(!isCompatible) showDialogForIncompatibleVersion();
		});
	}
	
	/**
	 * Shows a file dialog to choose a base folder for conversion.
	 * @param e event
	 */
	@FXML
	private void btn_svgs_onAction(ActionEvent e) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Folder: Path to SVGs");
		File recentLocation = FilePathUtils.getRecentDirectory(getModel().svgFilesPath().get());
		directoryChooser.setInitialDirectory(recentLocation);
		File selectedFolder = directoryChooser.showDialog(WindowUtils.from(e));
		if(selectedFolder == null) return;
		getModel().svgFilesPath().set(selectedFolder.getPath());
	}
	
	/**
	 * Checks for version compatibility and converts the selected base folder.
	 * @param e event
	 */
	@FXML
	private void btn_convert_onAction(ActionEvent e) {
		if(getModel().inkscapePath().getValue().isBlank()
				|| !new File(getModel().inkscapePath().getValue()).isFile()) {
			showDialogForEmptyExecutablePath();
			return;
		}
		if(getModel().svgFilesPath().getValue().isBlank()
				|| !new File(getModel().svgFilesPath().getValue()).isDirectory()) {
			showDialogForEmptyWorkingPath();
			return;
		}
		InkscapeVersionService.checkVersion(getModel(), isCompatible -> {
			if(isCompatible) {
				convert(e);
			} else {
				showDialogForIncompatibleVersion();
			}
		});
	}
	
	/**
	 * Exits the application by hiding the main window.
	 * @param e event
	 */
	@FXML
	private void btn_cancel_onAction(ActionEvent e) {
		WindowUtils.hide(e);
	}
}
