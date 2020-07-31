package com.github.ennoxhd.glyphcreator.util.io;

public class SystemUtils {
	
	public static int getNumberOfCpuThreads() {
		return Runtime.getRuntime().availableProcessors();
	}
}
