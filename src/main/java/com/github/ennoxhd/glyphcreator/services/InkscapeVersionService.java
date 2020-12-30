package com.github.ennoxhd.glyphcreator.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.github.ennoxhd.glyphcreator.model.GlyphCreatorModel;
import com.github.ennoxhd.glyphcreator.util.javafx.BasicTask;
import com.github.ennoxhd.glyphcreator.util.javafx.BasicService;
import com.github.ennoxhd.glyphcreator.util.regex.RegexUtils;

public class InkscapeVersionService {
	
	private static final String MAJOR = "major";
	private static final String MINOR = "minor";
	private static final String VERSION_PATTERN = "^\\w+ "
			+ "(?<" + MAJOR + ">\\d+).(?<" + MINOR + ">\\d+)(.\\d+)? "
			+ "\\([0-9a-fA-F]+, \\d{4}-\\d{2}-\\d{2}\\)$";

	private static String getVersion(String path) {
		if(!new File(path).isFile()) return null;
		ProcessBuilder proc = new ProcessBuilder(path, "--version");
		Process p = null;
		BufferedInputStream bis = null;
		try {
			p = proc.start();
			bis = new BufferedInputStream(p.getInputStream());
			p.waitFor();
			return new String(bis.readAllBytes()).trim();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static boolean analyzeVersion(String version) {
		if(version == null) return false;
		List<Map<String, String>> occurrences = RegexUtils.getOccurrences(VERSION_PATTERN, version);
		if(occurrences.size() < 0) return false;
		int major = Integer.valueOf(occurrences.get(0).get(MAJOR));
		int minor = Integer.valueOf(occurrences.get(0).get(MINOR));
		if(major == 1 && minor >= 0) return true;
		return false;
	}
	
	public static void checkVersion(GlyphCreatorModel model, Consumer<Boolean> onResult) {
		String path = model.inkscapePath.get();
		BasicService<Boolean> versionLookupService = new BasicService<>();
		BasicTask<Boolean> versionLookupTask = versionLookupService.getTask();
		versionLookupService.defineWork(() -> {
			boolean isCorrect = analyzeVersion(getVersion(path));
			versionLookupTask.updateProgress(1, 1);
			versionLookupTask.updateValue(isCorrect);
			return isCorrect;
		});
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
}
