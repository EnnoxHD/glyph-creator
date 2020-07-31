package com.github.ennoxhd.glyphcreator.util.io;

public interface Cache<T> {
	public boolean isValid();
	public void invalidate();
	public T getData() throws IllegalStateException;
	public void setData(T data) throws IllegalStateException;
}
