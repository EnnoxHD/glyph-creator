package com.github.ennoxhd.glyphcreator.util.javafx;

import java.util.function.Function;

import javafx.concurrent.Service;

public class BasicService<V> extends Service<V> {

	private BasicTask<V> task;
	
	private BasicTask<V> getTask() {
		return task;
	}
	
	private void setTask(BasicTask<V> task) {
		this.task = task;
	}
	
	public BasicService(Function<BasicTask<V>, V> work) {
		setTask(new BasicTask<V>() {
			@Override
			protected V call() throws Exception {
				return work.apply(this);
			}
		});
	}
	
	@Override
	protected BasicTask<V> createTask() {
		return getTask();
	}
}
