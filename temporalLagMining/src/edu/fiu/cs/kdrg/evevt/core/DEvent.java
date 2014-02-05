package edu.fiu.cs.kdrg.evevt.core;

import java.util.HashMap;
import java.util.Map;

public class DEvent<T> implements IDEvent<T> {

	private Map<String, T> properties = new HashMap<String, T>();

	private int eventType = -1;

	private double timestamp = -1;

	@Override
	public T getProperty(String name) {
		// TODO Auto-generated method stub
		return properties.get(name);
	}

	@Override
	public void setProperty(String name, T v) {
		// TODO Auto-generated method stub
		properties.put(name, v);
	}

	@Override
	public int getEventType() {
		// TODO Auto-generated method stub
		return eventType;
	}

	@Override
	public double getTimestamp() {
		// TODO Auto-generated method stub
		return timestamp;
	}

	/**
	 * @param eventType
	 *            the eventType to set
	 */
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}

}
