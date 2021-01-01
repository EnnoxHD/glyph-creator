package com.github.ennoxhd.glyphcreator.util.io;

/**
 * Contains some system related utility functions.
 */
public class SystemUtils {
	
	/**
	 * Gets the number of CPU threads available to the application.
	 * @return number of threads
	 */
	public static int getNumberOfCpuThreads() {
		return Runtime.getRuntime().availableProcessors();
	}
}
