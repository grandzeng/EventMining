package edu.fiu.cs.kdrg.evevt.algorithm;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.fiu.cs.kdrg.event.algorithm.LagEM;
import edu.fiu.cs.kdrg.event.algorithm.LagEME;
import edu.fiu.cs.kdrg.evevt.util.DEventLoader;

public class TestLagEME {

	private LagEME lagEME = null;

	private String fileName = null;

	private double lambda = 1.0;


	/**
	 * @throws IOException
	 * @throws java.lang.Exception
	 */

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

	@Before
	public void setUp() throws Exception {
		loadParameter();
		DEventLoader<String> loader = new DEventLoader<String>();
		loader.load(fileName);
		double[] A = loader.getEvent(0);
		double[] B = loader.getEvent(1);
		lagEME = new LagEME(A, B, lambda);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRunEM() {
		lagEME.runEM(0.0, false);
		// lagEMEX.runEM(0.0,true);
	}

}
