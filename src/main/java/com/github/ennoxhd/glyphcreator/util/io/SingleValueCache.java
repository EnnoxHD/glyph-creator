package com.github.ennoxhd.glyphcreator.util.io;

import java.util.NoSuchElementException;
import java.util.Optional;

public class SingleValueCache<T> implements Cache<T> {
	
	private Optional<T> data;
	
	public SingleValueCache() {
		invalidate();
	}
	
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
		try {
			return data.get();
		} catch(NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public void setData(T data) throws IllegalStateException {
		if(isValid()) throw new IllegalStateException("cache was not invalidated before setting the data");
		this.data = Optional.ofNullable(data);
	}
}
