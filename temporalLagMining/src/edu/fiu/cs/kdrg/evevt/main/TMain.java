package edu.fiu.cs.kdrg.evevt.main;

import java.io.IOException;
import java.util.List;

import edu.fiu.cs.kdrg.event.algorithm.TPattern;
import edu.fiu.cs.kdrg.evevt.core.Rule;
import edu.fiu.cs.kdrg.evevt.util.EventLoader;
import edu.fiu.cs.kdrg.evevt.util.MapEvent;

public class TMain {
	
	public static void main(String args[]) throws IOException{
		Object[]map = MapEvent.loadMap("data/eventmap.txt");
		String [] mapstring = new String[map.length];
		for(int i=0;i<map.length;i++){
			mapstring[i] = (String) map[i];
		}
		TPattern tp = new TPattern();
		tp.setMapEvent(mapstring);
		EventLoader<String> el = new EventLoader<String>();
		el.load("data/sequence.txt");
		tp.setEvents(el.getEvents());
		double delta=300;
		List<Rule> rules = tp.findTPattern(delta);
		
	}

}
