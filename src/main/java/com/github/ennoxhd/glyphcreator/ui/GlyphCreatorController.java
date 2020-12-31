package com.github.ennoxhd.glyphcreator.ui;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import com.github.ennoxhd.glyphcreator.app.GlyphCreatorApp;
import com.github.ennoxhd.glyphcreator.model.GlyphCreatorModel;
import com.github.ennoxhd.glyphcreator.services.VectorImageConversionService;
import com.github.ennoxhd.glyphcreator.services.InkscapeVersionService;
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

public class GlyphCreatorController extends BaseController<GlyphCreatorModel> {

	@FXML
	private TextField txt_inkscape;
	@FXML
	private Button btn_inkscape;
	@FXML
	private TextField txt_svgs;
	@FXML
	private Button btn_svgs;
	@FXML
	private Button btn_convert;
	@FXML
	private Button btn_cancel;
	
	@Override
	protected void bind(GlyphCreatorModel model) {
		txt_inkscape.textProperty().bindBidirectional(model.inkscapePath);
		txt_svgs.textProperty().bindBidirectional(model.svgFilesPath);
	}
	
	private static void showDialogForIncompatibleVersion(boolean isCompatible) {
		if(!isCompatible) {
			Dialogs.warnDialog("Warning: incorrect version", "Inkscape version might not be compatible!",
					"Inkscape version 1.0 is required as a minimum. Please consider updating the software.",
					GlyphCreatorApp.getIcon());
		}
	}
	
	private void convert(boolean isCompatible, ActionEvent e) {
		if(isCompatible) {
			ProgressDialogController controller = GlyphCreatorApp.start(ProgressDialogController.class,
					Modality.WINDOW_MODAL, WindowUtils.from(e));
			VectorImageConversionService.convertAll(getModel(), controller, result -> {
				controller.getStage().hide();
				if(result == null) {
					Dialogs.errorDialog("Error", "Something went wrong",
							"The conversion process failed.", GlyphCreatorApp.getIcon(), getStage());
				} else if(result.isEmpty()) {
					Dialogs.infoDialog("Done", "Conversion completed",
							"All SVG images were successfully converted.", GlyphCreatorApp.getIcon(), getStage());
				} else {
					List<String> areaContent = result.stream().map(Path::toString).collect(Collectors.toList());
					Dialogs.warnDialog("Canceled", "Conversion canceled",
							"The conversion was canceled. Therefore the following SVG images weren't converted:",
							areaContent, GlyphCreatorApp.getIcon(), getStage());
				}
			});
		}
	}
	
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
		if(selectedFile != null) {
			getModel().inkscapePath.set(selectedFile.getPath());
			InkscapeVersionService.checkVersion(getModel(),
					GlyphCreatorController::showDialogForIncompatibleVersion);
		}
	}
	
	@FXML
	private void btn_svgs_onAction(ActionEvent e) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Folder: Path to SVGs");
		File recentLocation = FilePathUtils.getRecentDirectory(getModel().svgFilesPath.get());
		directoryChooser.setInitialDirectory(recentLocation);
		File selectedFolder = directoryChooser.showDialog(WindowUtils.from(e));
		if(selectedFolder != null) getModel().svgFilesPath.set(selectedFolder.getPath());
	}
	
	@FXML
	private void btn_convert_onAction(ActionEvent e) {
		InkscapeVersionService.checkVersion(getModel(), isCompatible -> {
			showDialogForIncompatibleVersion(isCompatible);
			convert(isCompatible, e);
		});
	}
	
	@FXML
	private void btn_cancel_onAction(ActionEvent e) {
		WindowUtils.hide(e);
	}
}
