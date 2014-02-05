/**
 * 
 */
package edu.fiu.cs.kdrg.evevt.algorithm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.fiu.cs.kdrg.event.algorithm.LagEM;
import edu.fiu.cs.kdrg.evevt.util.DEventLoader;

/**
 * @author grand
 *
 */
public class TestLagEM {
	
	private LagEM lagEM = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		DEventLoader loader = new DEventLoader();
		loader.load("data/sythetic_0.500000_100_3.000000_1.000000.data");
		double[] A = loader.getEvent(0);
		double[] B = loader.getEvent(1);
		double miu = 20;
		double sigmaSqrt = 100;
		lagEM = new LagEM(A,B,miu,sigmaSqrt);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testRunEM(){
		lagEM.runEM(0.0);
	}

}
