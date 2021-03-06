package com.github.ennoxhd.glyphcreator.util.javafx;

import javafx.concurrent.Task;

/**
 * Wrapper class for a {@link Task} that exposes several methods for convenience
 * and is generally used by a {@link BasicService}.
 * @param <V> the result type of the task
 */
public abstract class BasicTask<V> extends Task<V> {
	
	@Override
	public void updateProgress(long workDone, long max) {
		super.updateProgress(workDone, max);
	}
	
	@Override
	public void updateProgress(double workDone, double max) {
		super.updateProgress(workDone, max);
	}
	
	@Override
	public void updateMessage(String message) {
		super.updateMessage(message);
	}
	
	@Override
	public void updateTitle(String title) {
		super.updateTitle(title);
	}
	
	@Override
	public void updateValue(V value) {
		super.updateValue(value);
	}
}
