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
		txt_inkscape.textProperty().bindBidirectional(model.inkscapePath);
		txt_svgs.textProperty().bindBidirectional(model.svgFilesPath);
	}
	
	/**
	 * Shows a dialog if the Inkscape version is not compatible.
	 * @param isCompatible Inkscape version compatibility flag
	 * @see InkscapeVersionService#checkVersion(GlyphCreatorModel, java.util.function.Consumer)
	 */
	private void showDialogForIncompatibleVersion(boolean isCompatible) {
		if(isCompatible) return;
		Dialogs.warnDialog("Warning: incorrect version", "Inkscape version might not be compatible!",
				"Inkscape version 1.0 is required as a minimum. Please consider updating the software.",
				getApp().getIcon());
	}
	
	/**
	 * If compatible, starts the conversion process and gives feedback about the completion.
	 * @param isCompatible Inkscape version compatibility flag
	 * @param e event
	 */
	private void convert(boolean isCompatible, ActionEvent e) {
		if(!isCompatible) return;
		ProgressDialogController controller = getApp().start(ProgressDialogController.class,
				Modality.WINDOW_MODAL, WindowUtils.from(e));
		VectorImageConversionService.convertAll(getModel(), controller, result -> {
			controller.getStage().hide();
			if(result == null) {
				Dialogs.errorDialog("Error", "Something went wrong",
						"The conversion process failed.", getApp().getIcon(), getStage());
			} else if(result.isEmpty()) {
				Dialogs.infoDialog("Done", "Conversion completed",
						"All SVG images were successfully converted.", getApp().getIcon(), getStage());
			} else {
				List<String> areaContent = result.stream().map(Path::toString).collect(Collectors.toList());
				Dialogs.warnDialog("Canceled", "Conversion canceled",
						"The conversion was canceled. Therefore the following SVG images weren't converted:",
						areaContent, getApp().getIcon(), getStage());
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
		File recentLocation = FilePathUtils.getRecentDirectory(getModel().inkscapePath.get());
		fileChooser.setInitialDirectory(recentLocation);
		File selectedFile = fileChooser.showOpenDialog(WindowUtils.from(e));
		if(selectedFile == null) return;
		getModel().inkscapePath.set(selectedFile.getPath());
		InkscapeVersionService.checkVersion(getModel(),
				this::showDialogForIncompatibleVersion);
	}
	
	/**
	 * Shows a file dialog to choose a base folder for conversion.
	 * @param e event
	 */
	@FXML
	private void btn_svgs_onAction(ActionEvent e) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Folder: Path to SVGs");
		File recentLocation = FilePathUtils.getRecentDirectory(getModel().svgFilesPath.get());
		directoryChooser.setInitialDirectory(recentLocation);
		File selectedFolder = directoryChooser.showDialog(WindowUtils.from(e));
		if(selectedFolder == null) return;
		getModel().svgFilesPath.set(selectedFolder.getPath());
	}
	
	/**
	 * Checks for version compatibility and converts the selected base folder.
	 * @param e event
	 */
	@FXML
	private void btn_convert_onAction(ActionEvent e) {
		InkscapeVersionService.checkVersion(getModel(), isCompatible -> {
			showDialogForIncompatibleVersion(isCompatible);
			convert(isCompatible, e);
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
