package com.github.ennoxhd.glyphcreator.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.github.ennoxhd.glyphcreator.model.GlyphCreatorModel;
import com.github.ennoxhd.glyphcreator.ui.ProgressDialogController;
import com.github.ennoxhd.glyphcreator.util.io.FilePathUtils;
import com.github.ennoxhd.glyphcreator.util.io.SystemUtils;
import com.github.ennoxhd.glyphcreator.util.regex.RegexUtils;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class GlyphCreatorServices {
	
	private static boolean isCorrectInkscapeVersionBase(String path) {
		if(new File(path).isFile()) {
			ProcessBuilder proc = new ProcessBuilder(path, "--version");
			Process p = null;
			BufferedInputStream bis = null;
			try {
				p = proc.start();
				bis = new BufferedInputStream(p.getInputStream());
				p.waitFor();
				String versionString = new String(bis.readAllBytes()).trim();
				String versionPattern = "^(?<name>\\w+) (?<version>(?<major>\\d+).(?<minor>\\d+)) "
						+ "\\((?<hash>[0-9a-fA-F]+), (?<date>(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2}))\\)$";
				List<Map<String, String>> occurrences = RegexUtils.getOccurrences(versionPattern, versionString);
				if(occurrences.size() < 0) return false;
				int major = Integer.valueOf(occurrences.get(0).get("major"));
				int minor = Integer.valueOf(occurrences.get(0).get("minor"));
				if(major == 1 && minor >= 0) return true;
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static void isCorrectInkscapeVersion(GlyphCreatorModel model, Consumer<Boolean> onResult) {
		String path = model.inkscapePath.get();
		Service<Boolean> versionLookupService = new Service<>() {
			@Override
			protected Task<Boolean> createTask() {
				return new Task<>() {
					@Override
					protected Boolean call() throws Exception {
						boolean result = isCorrectInkscapeVersionBase(path);
						Platform.runLater(() -> updateProgress(1, 1));
						updateValue(result);
						return result;
					}
				};
			}
		};
		versionLookupService.setOnSucceeded(e -> {
			boolean serviceResult = versionLookupService.getValue();
			model.inkscapePathCache.invalidate();
			if(serviceResult) {
				model.inkscapePathCache.setData(path);
			} else {
				model.inkscapePathCache.setData(null);
			}
			onResult.accept(serviceResult);
		});
		versionLookupService.setOnFailed(e -> {
			model.inkscapePathCache.invalidate();
			model.inkscapePathCache.setData(null);
			onResult.accept(false);
		});
		versionLookupService.start();
	}
	
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
							final int threads = SystemUtils.getNumberOfCpuThreads() * 2;
							ExecutorService executor = Executors.newFixedThreadPool(threads);
							final List<Path> files = FilePathUtils.getSvgFilesInDirectoryDeep(dir);
							long max = files.size();
							Platform.runLater(() -> updateProgress(0L, max));
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
