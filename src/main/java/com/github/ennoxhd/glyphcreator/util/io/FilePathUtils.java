package com.github.ennoxhd.glyphcreator.util.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FilePathUtils {
	
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
