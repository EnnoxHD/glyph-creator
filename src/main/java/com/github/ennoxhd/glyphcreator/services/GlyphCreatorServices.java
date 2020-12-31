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

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GlyphCreatorServices {

	private static Path convertVectorImageBase(String inkscape, Path file) {
		final ProcessBuilder processBuilder = new ProcessBuilder(inkscape, "--batch-process",
		"--actions=\"select-all;verb:StrokeToPath;verb:SelectionCombine;verb:FileSave;file-close\"",
		file.toString());
		Process process;
		try {
			process = processBuilder.start();
			process.waitFor();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
			return file;
		}
		return null;
	}
	
	public static void convertAll(GlyphCreatorModel model, ProgressDialogController controller,
			Consumer<List<Path>> onResult) {
		final String inkscape = model.inkscapePathCache.getData();
		final String dir = model.svgFilesPath.get();
		Service<List<Path>> conversionService = new Service<>() {
			@Override
			protected Task<List<Path>> createTask() {
				return new Task<>() {
					@Override
					protected List<Path> call() throws Exception {
						try {
							ExecutorService executor = Executors.newFixedThreadPool(SystemUtils.getNumberOfCpuThreads());
							final List<Path> files = FilePathUtils.getSvgFilesInDirectoryDeep(dir);
							long max = files.size();
							updateProgress(0L, max);
							List<Path> result = new ArrayList<>(files);
							for(Path file : files) {
								executor.execute(() -> {
									if(!controller.stopped) {
										Path failedFile = convertVectorImageBase(inkscape, file);
										if(failedFile == null) result.remove(file);
										Platform.runLater(() -> {
											updateProgress(Math.round(getProgress() * max) + 1L, max);
											controller.setProgress(getProgress());
										});
									}
								});
							}
							executor.shutdown();
							executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
							updateValue(result);
							return result;
						} catch(Exception e) {
							return null;
						}
					}
				};
			}
		};
		conversionService.setOnSucceeded(e -> {
			onResult.accept(conversionService.getValue());
		});
		conversionService.setOnFailed(e -> {
			onResult.accept(conversionService.getValue());
		});
		conversionService.start();
	}
}
