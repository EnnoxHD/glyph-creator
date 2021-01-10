package com.github.ennoxhd.glyphcreator.util.io;

/**
 * Represents a cache that can store a value of a specific type.
 * This cache can be invalidated or be in a valid state if data is set.
 * @param <T> type of the value to hold in the cache
 */
public interface Cache<T> {
	
	/**
	 * Checks if the cache is in a valid state.
	 * @return validity of the cache
	 */
	public boolean isValid();
	
	/**
	 * Invalidates the cache.
	 */
	public void invalidate();
	
	/**
	 * Gets the data that is stored in the valid cache.
	 * @return cached data
	 * @throws IllegalStateException if not valid
	 * @see #isValid()
	 */
	public T getData() throws IllegalStateException;
	
	/**
	 * Sets the data to store in the cache if it is invalid.
	 * @param data data to be stored
	 * @throws IllegalStateException if not invalid
	 * @see #invalidate()
	 */
	public void setData(T data) throws IllegalStateException;
}
