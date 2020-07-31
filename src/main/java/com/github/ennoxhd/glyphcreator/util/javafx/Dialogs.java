package com.github.ennoxhd.glyphcreator.util.javafx;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Dialogs {
	
	private static void genericDialog(AlertType type, String title, String header, String content,
			List<String> areaContent, Image icon, Window owner) {
		Alert dialog = new Alert(type);
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		if(icon != null) {
			((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(icon);
		}
		if(owner != null) {
			dialog.initOwner(owner);
			dialog.initModality(Modality.WINDOW_MODAL);
		}
		if(areaContent != null) {
			String text = areaContent.stream().collect(Collectors.joining("\n"));
			TextArea area = new TextArea(text);
			area.setEditable(false);
			area.setWrapText(false);
			dialog.getDialogPane().setExpandableContent(area);
		}
		dialog.showAndWait();
	}
	
	// ERROR
	
	public static void errorDialog(String title, String header, String content, Image icon) {
		errorDialog(title, header, content, icon, null);
	}
	
	public static void errorDialog(String title, String header, String content, Image icon, Window owner) {
		errorDialog(title, header, content, null, icon, owner);
	}
	
	public static void errorDialog(String title, String header, String content, List<String> areaContent, Image icon) {
		errorDialog(title, header, content, areaContent, icon, null);
	}
	
	public static void errorDialog(String title, String header, String content, List<String> areaContent, Image icon, Window owner) {
		genericDialog(AlertType.ERROR, title, header, content, areaContent, icon, owner);
	}
	
	// INFO
	
	public static void infoDialog(String title, String header, String content, Image icon) {
		infoDialog(title, header, content, icon, null);
	}
	
	public static void infoDialog(String title, String header, String content, Image icon, Window owner) {
		infoDialog(title, header, content, null, icon, owner);
	}
	
	public static void infoDialog(String title, String header, String content, List<String> areaContent, Image icon) {
		infoDialog(title, header, content, areaContent, icon, null);
	}
	
	public static void infoDialog(String title, String header, String content, List<String> areaContent, Image icon, Window owner) {
		genericDialog(AlertType.INFORMATION, title, header, content, areaContent, icon, owner);
	}
	
	// WARN
	
	public static void warnDialog(String title, String header, String content, Image icon) {
		warnDialog(title, header, content, icon, null);
	}
	
	public static void warnDialog(String title, String header, String content, Image icon, Window owner) {
		warnDialog(title, header, content, null, icon, owner);
	}
	
	public static void warnDialog(String title, String header, String content, List<String> areaContent, Image icon) {
		warnDialog(title, header, content, areaContent, icon, null);
	}
	
	public static void warnDialog(String title, String header, String content, List<String> areaContent, Image icon, Window owner) {
		genericDialog(AlertType.WARNING, title, header, content, areaContent, icon, owner);
	}
}
