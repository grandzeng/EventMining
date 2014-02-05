package edu.fiu.cs.kdrg.evevt.algorithm;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.fiu.cs.kdrg.event.algorithm.TPattern;
import edu.fiu.cs.kdrg.evevt.core.Event;
import edu.fiu.cs.kdrg.evevt.core.Rule;
import edu.fiu.cs.kdrg.evevt.util.EventLoader;

public class TestTPattern {
	private TPattern tp = null;
	
	@Before
	public void setUp() throws Exception {
		tp = new TPattern();
		EventLoader<String> el = new EventLoader<String>();
		el.load("data/sequence.txt");
		tp.setEvents(el.getEvents());
		
	}

	@Test
	public void testZeroTruncate() {
		List<Event<String>> events = tp.zeroTruncate();
		int size = events.size();
		long timestamp  = 0;
		long prevTimestamp = 0;
		for(int i=0;i<size;i++){
			Event<String> e = events.get(i);
			timestamp = e.getTimestamp();
			if(i == 0){
				assert(timestamp == 0);
			}
			assert(timestamp>=0);
			assert(timestamp >= prevTimestamp);
			prevTimestamp = timestamp;
		}
	}

	@Test
	public void testIsDependent() {

		boolean flag = tp.isDependent(17, 1);
		assertTrue(flag);
		
	}
	@Test
	public void testFindPeriod(){
		double period = tp.findPeriod(17, 1, 300);
		System.out.println("period="+period);
	}
	
	@Test
	public void testFindTPattern(){
		double delta=300;
		List<Rule> rules = tp.findTPattern(delta);
//		for (Rule r: rules){
//			System.out.println(r);
//		}
		
	}

}
