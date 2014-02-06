package edu.fiu.cs.kdrg.evevt.algorithm.em;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.fiu.cs.kdrg.event.algorithm.em.LagGaussianMixture;
import edu.fiu.cs.kdrg.event.algorithm.em.RevisedLagGMM;
import edu.fiu.cs.kdrg.evevt.util.DEventLoader;

public class TestRevisedLagGMM {

	private RevisedLagGMM rlgm = null;

	private String fileName = null;

	private double miu = 1.0;

	private double sigmaSqrt = 10.0;

	public void loadParameter() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("param/param"));
		String line = null;

		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.equals(""))
				continue;
			String[] pats = line.split("\\s+");
			fileName = pats[0];
			miu = Double.parseDouble(pats[1]);
			sigmaSqrt = Double.parseDouble(pats[2]);
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
		rlgm = new RevisedLagGMM(A, B, miu, sigmaSqrt);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRun() {
		rlgm.run();
	}

}
