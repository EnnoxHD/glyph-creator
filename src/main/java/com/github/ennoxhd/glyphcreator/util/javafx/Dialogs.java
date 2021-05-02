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

/**
 * Displays various dialogs.
 */
public class Dialogs {
	
	/**
	 * Generic method to display a variety of customized dialogs.
	 * Every parameter is optional and may be {@code null}.
	 * @param type type of the dialog
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param areaContent detailed feedback text field
	 * @param icon the application icon to use on the dialog
	 * @param owner the owner of the dialog (modality)
	 */
	public static void genericDialog(AlertType type, String title, String header, String content,
			List<? extends String> areaContent, Image icon, Window owner) {
		Alert dialog = new Alert(type == null ? AlertType.INFORMATION : type);
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
	
	/**
	 * Shows an error dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param icon the application icon to use on the dialog
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void errorDialog(String title, String header, String content, Image icon) {
		errorDialog(title, header, content, icon, null);
	}
	
	/**
	 * Shows an error dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param icon the application icon to use on the dialog
	 * @param owner the owner of the dialog (modality)
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void errorDialog(String title, String header, String content, Image icon, Window owner) {
		errorDialog(title, header, content, null, icon, owner);
	}
	
	/**
	 * Shows an error dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param areaContent detailed feedback text field
	 * @param icon the application icon to use on the dialog
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void errorDialog(String title, String header, String content,
			List<? extends String> areaContent, Image icon) {
		errorDialog(title, header, content, areaContent, icon, null);
	}
	
	/**
	 * Shows an error dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param areaContent detailed feedback text field
	 * @param icon the application icon to use on the dialog
	 * @param owner the owner of the dialog (modality)
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void errorDialog(String title, String header, String content,
			List<? extends String> areaContent, Image icon, Window owner) {
		genericDialog(AlertType.ERROR, title, header, content, areaContent, icon, owner);
	}
	
	// INFO
	
	/**
	 * Shows an info dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param icon the application icon to use on the dialog
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void infoDialog(String title, String header, String content, Image icon) {
		infoDialog(title, header, content, icon, null);
	}
	
	/**
	 * Shows an info dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param icon the application icon to use on the dialog
	 * @param owner the owner of the dialog (modality)
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void infoDialog(String title, String header, String content, Image icon, Window owner) {
		infoDialog(title, header, content, null, icon, owner);
	}
	
	/**
	 * Shows an info dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param areaContent detailed feedback text field
	 * @param icon the application icon to use on the dialog
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void infoDialog(String title, String header, String content,
			List<? extends String> areaContent, Image icon) {
		infoDialog(title, header, content, areaContent, icon, null);
	}
	
	/**
	 * Shows an info dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param areaContent detailed feedback text field
	 * @param icon the application icon to use on the dialog
	 * @param owner the owner of the dialog (modality)
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void infoDialog(String title, String header, String content,
			List<? extends String> areaContent, Image icon, Window owner) {
		genericDialog(AlertType.INFORMATION, title, header, content, areaContent, icon, owner);
	}
	
	// WARN
	
	/**
	 * Shows a warning dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param icon the application icon to use on the dialog
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void warnDialog(String title, String header, String content, Image icon) {
		warnDialog(title, header, content, icon, null);
	}
	
	/**
	 * Shows a warning dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param icon the application icon to use on the dialog
	 * @param owner the owner of the dialog (modality)
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void warnDialog(String title, String header, String content, Image icon, Window owner) {
		warnDialog(title, header, content, null, icon, owner);
	}
	
	/**
	 * Shows a warning dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param areaContent detailed feedback text field
	 * @param icon the application icon to use on the dialog
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void warnDialog(String title, String header, String content,
			List<? extends String> areaContent, Image icon) {
		warnDialog(title, header, content, areaContent, icon, null);
	}
	
	/**
	 * Shows a warning dialog.
	 * @param title the title
	 * @param header the short description
	 * @param content the description
	 * @param areaContent detailed feedback text field
	 * @param icon the application icon to use on the dialog
	 * @param owner the owner of the dialog (modality)
	 * @see Dialogs#genericDialog(AlertType, String, String, String, List, Image, Window)
	 */
	public static void warnDialog(String title, String header, String content,
			List<? extends String> areaContent, Image icon, Window owner) {
		genericDialog(AlertType.WARNING, title, header, content, areaContent, icon, owner);
	}
}
