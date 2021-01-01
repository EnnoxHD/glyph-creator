package com.github.ennoxhd.glyphcreator.util.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains some file system related utility functions.
 */
public class FilePathUtils {
	
	/**
	 * Gets the parent folder of a normal file.
	 * If the {@code formerLocation} is a folder path it is returned instead.
	 * If the {@code formerLocation} does not exist {@code null} is returned.
	 * @param formerLocation file path
	 * @return folder path
	 */
	public static File getRecentDirectory(String formerLocation) {
		File recentLocation = new File(formerLocation);
		if(recentLocation.exists()) {
			if(recentLocation.isFile()) {
				recentLocation = recentLocation.getParentFile();
			}
			return recentLocation;
		}
		return null;
	}
	
	/**
	 * Collects a list of files within a directory and its subdirectories.
	 * The file extension must match the provided one.
	 * @param directory the base directory to search in
	 * @param extension the file extension to filter for
	 * @return a plain list of all the file paths
	 */
	public static List<Path> getFilesInDirectoryDeep(String directory, String extension) {
		if(directory == null || directory.isBlank()) return List.of();
		String ending = "." + extension == null ? "" : extension;
		try {
			return Files.walk(Paths.get(directory))
					.filter(file -> Files.isRegularFile(file))
					.filter(file -> file.toString().endsWith(ending))
					.collect(Collectors.toList());
		} catch (IOException e) {
			return List.of();
		}
	}
}
