/**
 * 
 */
package edu.fiu.cs.kdrg.evevt.algorithm.em;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.fiu.cs.kdrg.event.algorithm.em.LagExponentialMixture;
import edu.fiu.cs.kdrg.event.algorithm.em.LagGaussianMixture;
import edu.fiu.cs.kdrg.event.algorithm.em.RevisedLagExponentialMixture;
import edu.fiu.cs.kdrg.evevt.util.DEventLoader;

/**
 * @author grand
 * 
 */
public class TestLagExponentialMixture {

	private LagExponentialMixture lem = null;

	private String fileName = null;

	private double lambda = 1.0;

	public void loadParameter() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("param/expparam"));
		String line = null;

		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			String[] pats = line.split("\\s+");
			fileName = pats[0];
			lambda = Double.parseDouble(pats[1]);
			break;
		}

		br.close();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		loadParameter();
		DEventLoader<String> loader = new DEventLoader<String>();
		loader.load(fileName);
		double[] A = loader.getEvent(0);
		double[] B = loader.getEvent(1);
		lem = new LagExponentialMixture(A, B, lambda);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link edu.fiu.cs.kdrg.event.algorithm.em.AbstractExpectationmMaximization#run()}
	 * .
	 */
	@Test
	public void testRun() {
		lem.run();
	}

}
