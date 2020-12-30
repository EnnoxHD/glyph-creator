package com.github.ennoxhd.glyphcreator.util.javafx;

import java.util.function.Supplier;

import javafx.concurrent.Service;

public class BasicService<V> extends Service<V> {

	private Supplier<V> supplier;
	private BasicTask<V> task;
	
	private Supplier<V> getSupplier() {
		return this.supplier;
	}
	
	private void setSupplier(Supplier<V> supplier) {
		this.supplier = supplier;
	}
	
	public BasicTask<V> getTask() {
		return task;
	}
	
	private void setTask(BasicTask<V> task) {
		this.task = task;
	}
	
	public BasicService() {
		setTask(new BasicTask<V>() {
			@Override
			protected V call() throws Exception {
				return getSupplier().get();
			}
		});
	}
	
	public void defineWork(Supplier<V> supplier) {
		setSupplier(supplier);
	}
	
	@Override
	protected BasicTask<V> createTask() {
		return getTask();
	}
	
	@Override
	public void start() {
		if(getSupplier() == null) throw new Error("Work not defined. Use defineWork(Supplier<V>) before start().");
		super.start();
	}
}
