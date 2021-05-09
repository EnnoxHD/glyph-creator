package com.github.ennoxhd.glyphcreator.util.io;

import java.util.Optional;

/**
 * Simple cache that stores a single value of the type {@code T}.
 * The stored data can either be {@code null} or be an object of type {@code T}.
 * @param <T> type of the stored value
 */
public class SingleValueCache<T> implements Cache<T> {
	
	/**
	 * The internal storage
	 */
	private Optional<T> data;
	
	/**
	 * Creates a new invalidated cache.
	 */
	public SingleValueCache() {
		invalidate();
	}
	
	/**
	 * Creates a new valid cache with the provided data.
	 * @param data data to store in the cache
	 */
	public SingleValueCache(T data) {
		invalidate();
		setData(data);
	}

	@Override
	public boolean isValid() {
		return data != null;
	}

	@Override
	public void invalidate() {
		data = null;
	}

	@Override
	public T getData() throws IllegalStateException {
		if(!isValid()) throw new IllegalStateException("data was not valid at the time of calling");
		return data.orElseGet(() -> null);
	}

	@Override
	public void setData(T data) throws IllegalStateException {
		if(isValid()) throw new IllegalStateException("cache was not invalidated before setting the data");
		this.data = Optional.ofNullable(data);
	}
}
