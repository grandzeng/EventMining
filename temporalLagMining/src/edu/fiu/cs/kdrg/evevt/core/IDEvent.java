package edu.fiu.cs.kdrg.evevt.core;

public interface IDEvent<T> {
	public T getProperty(String name);

	public void setProperty(String name, T v);

	public int getEventType();

	public double getTimestamp();
}
