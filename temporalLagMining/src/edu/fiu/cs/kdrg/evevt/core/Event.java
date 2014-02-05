package edu.fiu.cs.kdrg.evevt.core;

import java.util.HashMap;
import java.util.Map;

public class Event<T> implements IEvent<T> {

	private Map<String, T> properties = new HashMap<String, T>();

	private int eventType = -1;

	private long timestamp = -1;

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
	public long getTimestamp() {
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
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
