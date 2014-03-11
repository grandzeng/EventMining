package edu.fiu.cs.kdrg.evevt.util;

import java.util.Random;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

public class DensityEval {

	private static Random bernoulli = new Random();

	private static Random uniform = new Random();

	private static Random normalDistribution = new Random();

	/**
	 * @param miu
	 * @param sigma
	 * @param x
	 * @return
	 */
	public static double normalDensity(double miu, double sigma, double x) {
		double twoPISqrtRootInverse = 0.39894228062936166;
		return 1.0 / sigma * twoPISqrtRootInverse
				* Math.exp(-1 * (x - miu) * (x - miu) / (2 * sigma * sigma));
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
	 * 
	 * @param x
	 * @return
	 */
	public static double logBaseE(double x) {
		return Math.log(x);
	}

	/**
	 * @param rate
	 * @param newRandom
	 * @return
	 */
	public static boolean bernoulli(double rate, boolean newRandom) {
		if (newRandom)
			bernoulli = new Random();
		double val = bernoulli.nextDouble();
		if (val < rate)
			return true;
		else
			return false;
	}

	/**
	 * @param max
	 * @param newRandom
	 * @return
	 */
	public static int uniform(int max, boolean newRandom) {
		if (newRandom)
			uniform = new Random();
		double val = uniform.nextDouble();
		return (int) (val * max);
	}

	public static double nextGuassian(double miu, double sigma,
			boolean newRandom) {
		if (newRandom) {
			normalDistribution = new Random();
		}

		return sigma * normalDistribution.nextGaussian() + miu;
	}

	/**
	 * @param newRandom
	 * @return
	 */
	public static double uniform(boolean newRandom) {
		if (newRandom)
			uniform = new Random();
		return uniform.nextDouble();
	}

	public static void main(String args[]) {
		System.out.println(exponentialDistribution(2, 0));
	}
}
