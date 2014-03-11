package edu.fiu.cs.kdrg.evevt.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.fiu.cs.kdrg.evevt.core.DEvent;

/**
 * @author Chunqiu Zeng
 * @date Jan 26th,2014
 * 
 * @param <T>
 */
public class DEventLoader<T> {

	/**
	 * the number of events
	 */
	private long eventNumber = -1;

	private int maxEventType = 0;

	/**
	 * a sequence of events
	 */
	private List<DEvent<T>> events = null;

	/**
	 * the separator for fields in the events file
	 */
	private static String separator = ",";

	/**
	 * load events from file
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<DEvent<T>> load(String path) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(path));

		events = load(reader);

		reader.close();

		return events;
	}

	/**
	 * load events from BufferedReader
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public List<DEvent<T>> load(BufferedReader reader) throws IOException {
		String line = null;
		String patterns[] = null;
		int type = 0;

		eventNumber = -1;
		events = new ArrayList<DEvent<T>>();

		// get the number of events
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;

			eventNumber = Long.parseLong(line);
			break;
		}

		// read the sequence of events
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			patterns = line.split(separator);
			if (patterns.length != 2)
				continue;
			DEvent<T> event = new DEvent<T>();
			type = Integer.parseInt(patterns[0]);
			if (type > maxEventType)
				maxEventType = type;
			
			event.setEventType(Integer.parseInt(patterns[0]));
			event.setTimestamp(Double.parseDouble(patterns[1]));
			events.add(event);
		}

		return events;
	}

	public double[] getEvent(int type) {
		ArrayList<Double> array = new ArrayList<Double>();
		double[] ret = null;
		for (DEvent e : events) {
			if (e.getEventType() == type) {
				array.add(e.getTimestamp());
			}
		}

		ret = new double[array.size()];
		for (int i = 0; i < array.size(); i++) {
			ret[i] = array.get(i);
		}

		return ret;
	}

	/**
	 * @return the eventNum
	 */
	public long getEventNum() {
		return eventNumber;
	}

	/**
	 * @return the events
	 */
	public List<DEvent<T>> getEvents() {
		return events;
	}
	
	public int getMaxEventType(){
		return maxEventType;
	}

}
