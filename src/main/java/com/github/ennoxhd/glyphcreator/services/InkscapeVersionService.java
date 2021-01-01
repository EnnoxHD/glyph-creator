package com.github.ennoxhd.glyphcreator.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.github.ennoxhd.glyphcreator.model.GlyphCreatorModel;
import com.github.ennoxhd.glyphcreator.util.javafx.BasicService;
import com.github.ennoxhd.glyphcreator.util.regex.RegexUtils;

/**
 * Service that checks the Inkscape version for compatibility
 */
public class InkscapeVersionService {
	
	/**
	 * Regex group name for the major part of the version number 
	 */
	private static final String MAJOR = "major";
	
	/**
	 * Regex group name for the minor part of the version number
	 */
	private static final String MINOR = "minor";
	
	/**
	 * Regex pattern for the Inkscape version number as given
	 * by {@code inkscape --version}
	 */
	private static final String VERSION_PATTERN = "^\\w+ "
			+ "(?<" + MAJOR + ">\\d+).(?<" + MINOR + ">\\d+)(.\\d+)? "
			+ "\\([0-9a-fA-F]+, \\d{4}-\\d{2}-\\d{2}\\)$";

	/**
	 * Invokes an Inkscape process with {@code --version} parameter to get the version.
	 * @param path path to the Inkscape executable
	 * @return version string as printed by the program
	 */
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
	
	/**
	 * Analyzes the version string for compatibility.
	 * @param version version string
	 * @return {@code true} if compatible, {@code false} otherwise
	 * @see #getVersion(String)
	 */
	private static boolean analyzeVersionForCompatibility(String version) {
		if(version == null) return false;
		List<Map<String, String>> occurrences = RegexUtils.getOccurrences(VERSION_PATTERN, version);
		if(occurrences.size() < 0) return false;
		int major = Integer.valueOf(occurrences.get(0).get(MAJOR));
		int minor = Integer.valueOf(occurrences.get(0).get(MINOR));
		if(major == 1 && minor >= 0) return true;
		return false;
	}
	
	/**
	 * Check the Inkscape executable for compatibility.
	 * @param model model with the Inkscape path
	 * @param onResult action to perform on success or failure
	 */
	public static void checkVersion(GlyphCreatorModel model, Consumer<Boolean> onResult) {
		String path = model.inkscapePath.get();
		BasicService<Boolean> versionLookup = new BasicService<>(task -> {
			boolean isCompatible = analyzeVersionForCompatibility(getVersion(path));
			task.updateProgress(1, 1);
			task.updateValue(isCompatible);
			return isCompatible;
		});
		versionLookup.setOnSucceeded(e -> {
			boolean serviceResult = versionLookup.getValue();
			model.inkscapePathCache.invalidate();
			if(serviceResult) {
				model.inkscapePathCache.setData(path);
			} else {
				model.inkscapePathCache.setData(null);
			}
			onResult.accept(serviceResult);
		});
		versionLookup.setOnFailed(e -> {
			model.inkscapePathCache.invalidate();
			model.inkscapePathCache.setData(null);
			onResult.accept(false);
		});
		versionLookup.start();
	}
}
