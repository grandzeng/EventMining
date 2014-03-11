package edu.fiu.cs.kdrg.event.algorithm;

import gnu.trove.iterator.TDoubleIntIterator;
import gnu.trove.map.TDoubleIntMap;
import gnu.trove.map.hash.TDoubleIntHashMap;

/**
 * This class applies brute force algorithm to find the lag between two events.
 * 
 * @author Chunqiu Zeng
 * @date JAN 26th,2014
 * 
 */
public class BruteForce {

	private double[] antecedents = null;

	private double[] consequents = null;

	private double miu = 0.0;

	private double sigmaSqrt = 0.0;

	private double lags[][] = null;

	/**
	 * default constructor
	 */
	public BruteForce() {

	}

	/**
	 * Constructor
	 * 
	 * @param antecedents
	 * @param consequents
	 */
	public BruteForce(double[] antecedents, double[] consequents) {
		assert (antecedents != null);
		assert (consequents != null);
		this.antecedents = antecedents;
		this.consequents = consequents;
		lags = new double[consequents.length][antecedents.length];
	}

	/**
	 * enumerate all the lags
	 */
	public void enumerateLags() {
		double sum = 0.0;
		double avg = 0.0;
		
		for (int i = 0; i < consequents.length; i++) {
			for (int j = 0; j < antecedents.length; j++) {
				lags[i][j] = consequents[i] - antecedents[j];
				sum += lags[i][j];
			}
		}
		
		avg = sum/(consequents.length * antecedents.length);
		System.out.println("average="+avg);
		
	}

	/**
	 * run brute force to discover the possible lags
	 */
	public double[] retrieveTopK(int k) {
		double[] array = new double[k];
		// find the most frequent lags
		TDoubleIntMap countMap = new TDoubleIntHashMap();
		for (int i = 0; i < lags.length; i++) {
			for (int j = 0; j < lags[i].length; j++) {
				int cnt = 0;
				if (countMap.containsKey(lags[i][j])) {
					cnt = countMap.get(lags[i][j]);
					countMap.put(lags[i][j], cnt + 1);
				} else {
					countMap.put(lags[i][j], 1);
				}
			}
		}

		// find the most frequent lags
		TDoubleIntIterator iter = countMap.iterator();
		int maxCnt = 0;
		double key = 0.0;
		while (iter.hasNext()) {
			iter.advance();
			if (iter.value() > maxCnt){
				key = iter.key();
				maxCnt = iter.value();
				System.out.println("(lag="+iter.key()+",count="+iter.value()+")");
			}
//			System.out.println("(lag="+iter.key()+",count="+iter.value()+")");
		}
		
		array[0] = key;
		return array;

	}

	public void run() {
		
		enumerateLags();
		retrieveTopK(1);
	}

}
