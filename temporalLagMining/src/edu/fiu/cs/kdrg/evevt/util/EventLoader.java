package edu.fiu.cs.kdrg.evevt.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.fiu.cs.kdrg.evevt.core.Event;

/**
 * @author Chunqiu Zeng
 * @date Jan 26th,2014
 * 
 * @param <T>
 */
public class EventLoader<T> {

	/**
	 * the number of events
	 */
	private long eventNumber = -1;

	/**
	 * a sequence of events
	 */
	private List<Event<T>> events = null;

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
	public List<Event<T>> load(String path) throws IOException {

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
	public List<Event<T>> load(BufferedReader reader) throws IOException {
		String line = null;
		String patterns[] = null;

		eventNumber = -1;
		events = new ArrayList<Event<T>>();

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
			Event<T> event = new Event<T>();
			event.setEventType(Integer.parseInt(patterns[0]));
			event.setTimestamp(Long.parseLong(patterns[1]));
			events.add(event);
		}

		return events;
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
	public List<Event<T>> getEvents() {
		return events;
	}

}
