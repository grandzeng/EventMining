package edu.fiu.cs.kdrg.evevt.util;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

public class DensityEval {

	/**
	 * @param miu
	 * @param sigma
	 * @param x
	 * @return
	 */
	public static double normalDensity(double miu, double sigma, double x) {
		NormalDistribution normalDistribution = new NormalDistribution(miu,
				sigma);
		return normalDistribution.density(x);
	}

	/**
	 * @param lambda
	 * @param x
	 * @return
	 */
	public static double exponentialDistribution(double lambda, double x) {

		ExponentialDistribution exp = new ExponentialDistribution(lambda);

		return exp.density(x);
	}

	/**
	 * get the value of log base E
	 * @param x
	 * @return
	 */
	public static double logBaseE(double x) { 
		return Math.log(x);
	}
	
	public static void main(String args[]){
		System.out.println(normalDensity(1000,10,-0.71));
	}
}
