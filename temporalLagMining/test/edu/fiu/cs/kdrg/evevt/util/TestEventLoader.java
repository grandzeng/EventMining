package edu.fiu.cs.kdrg.evevt.util;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.fiu.cs.kdrg.evevt.util.EventLoader;


public class TestEventLoader {
	
	EventLoader<Integer> loader = new EventLoader<Integer>();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoad() {
		try {
			loader.load("data/sequence.txt");
			assertEquals(999997,loader.getEventNum());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
