package edu.fiu.cs.kdrg.evevt.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.fiu.cs.kdrg.evevt.util.EventLoader;
import edu.fiu.cs.kdrg.evevt.util.SeqToTransactions;

public class TestSeqToTransactions {
	SeqToTransactions<Integer> stt = new SeqToTransactions<Integer>(300) ;
	EventLoader<Integer> loader = new EventLoader<Integer>();

	@Before
	public void setUp() throws Exception {
		loader.load("data/sequence.txt");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConvert() {
		stt.convert(loader.getEvents());
		assertTrue(stt.getTransactions().size()>0);
	}

	@Test
	public void testGetWindow() {
		assertEquals(300,stt.getWindow());
	}

	@Test
	public void testSetWindow() {
		stt.setWindow(300);
		assertEquals(300,stt.getWindow());
	}

}
