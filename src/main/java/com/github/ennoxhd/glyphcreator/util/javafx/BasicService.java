package com.github.ennoxhd.glyphcreator.util.javafx;

import java.util.function.Function;

import javafx.concurrent.Service;

/**
 * Encapsulates a {@link Service} to minimize the overhead
 * of nested anonymous classes.
 * @param <V> any return type of the service
 */
public class BasicService<V> extends Service<V> {

	/**
	 * The task to execute.
	 */
	private BasicTask<V> task;
	
	/**
	 * Gets the task that is run by the service.
	 * @return the task
	 */
	private BasicTask<V> getTask() {
		return task;
	}
	
	/**
	 * Sets the task that is used in this service.
	 * @param task the task to use
	 */
	private void setTask(BasicTask<V> task) {
		this.task = task;
	}
	
	/**
	 * Instantiates a new service with the given work task.
	 * @param work the work task that the service will use
	 */
	public BasicService(Function<BasicTask<V>, V> work) {
		setTask(new BasicTask<V>() {
			@Override
			protected V call() {
				return work.apply(this);
			}
		});
	}
	
	/**
	 * On task creation use the provided {@link #task}.
	 */
	@Override
	protected BasicTask<V> createTask() {
		return getTask();
	}
}
