/**
 * 
 */
package edu.fiu.cs.kdrg.evevt.core;

/**
 * @author grand
 *
 */
public interface IEvent<T> {
	
	public T getProperty(String name);
	
	public void setProperty(String name,T v);
	
	public int getEventType();
	
	public long getTimestamp();
}
