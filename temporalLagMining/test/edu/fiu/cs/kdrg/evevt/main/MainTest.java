package edu.fiu.cs.kdrg.evevt.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainTest {
	private Main main = null;

	@Before
	public void setUp() throws Exception {
		main = new Main();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testFunc() {
		main.func();
	}

}
