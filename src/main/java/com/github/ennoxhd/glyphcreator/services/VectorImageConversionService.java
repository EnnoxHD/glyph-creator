package com.github.ennoxhd.glyphcreator.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.github.ennoxhd.glyphcreator.model.GlyphCreatorModel;
import com.github.ennoxhd.glyphcreator.ui.ProgressDialogController;
import com.github.ennoxhd.glyphcreator.util.io.FilePathUtils;
import com.github.ennoxhd.glyphcreator.util.io.SystemUtils;
import com.github.ennoxhd.glyphcreator.util.javafx.BasicService;

import javafx.application.Platform;

public class VectorImageConversionService {

	private static final String ACTIONS =
			"select-all;verb:StrokeToPath;verb:SelectionCombine;verb:FileSave;file-close";
	
	private static Path convertVectorImage(String inkscape, Path file) {
		final ProcessBuilder processBuilder = new ProcessBuilder(inkscape,
				"--batch-process", "--actions=\"" + ACTIONS + "\"", file.toString());
		try {
			Process process = processBuilder.start();
			process.waitFor();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return file;
		}
		return null;
	}
	
	private static List<Path> getSvgFiles(String directory) {
		return FilePathUtils.getFilesInDirectoryDeep(directory, "svg");
	}
	
	public static void convertAll(GlyphCreatorModel model, ProgressDialogController controller,
			Consumer<List<Path>> onResult) {
		final String inkscape = model.inkscapePathCache.getData();
		final String dir = model.svgFilesPath.get();
		BasicService<List<Path>> conversionService = new BasicService<>(task -> {
			try {
				final List<Path> files = getSvgFiles(dir);
				long amount = files.size();
				List<Path> result = new ArrayList<>(files);
				ExecutorService executor = Executors.newFixedThreadPool(SystemUtils.getNumberOfCpuThreads());
				task.updateProgress(0L, amount);
				for(Path file : files) {
					executor.execute(() -> {
						if(controller.isStopped()) return;
						Path failedFile = convertVectorImage(inkscape, file);
						if(failedFile == null) result.remove(file);
						Platform.runLater(() -> {
							task.updateProgress(Math.floor(task.getProgress() * amount) + 1L, amount);
							controller.setProgress(task.getProgress());
						});
					});
				}
				task.updateProgress(amount, amount);
				executor.shutdown();
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
				task.updateValue(result);
				return result;
			} catch(Exception e) {
				return null;
			}
		});
		conversionService.setOnSucceeded(e -> {
			onResult.accept(conversionService.getValue());
		});
		conversionService.setOnFailed(e -> {
			onResult.accept(conversionService.getValue());
		});
		conversionService.start();
	}
}
