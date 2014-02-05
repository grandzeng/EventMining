package edu.fiu.cs.kdrg.event.algorithm;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * This algorithm applies EM framework to find the lag between two events.
 * 
 * @author Chunqiu Zeng
 * @date JAN 26th,2014
 * 
 */
public class LagEME {

	private double[] antecedents = null;

	private double[] consequents = null;

	// private double[] PIs = null;

	private double lambda = 10.0;

	private double logLikelihood = 0.0;

	private double intermediate[][] = null;

	private int iteration = 0;

	/**
	 * default constructor
	 */
	public LagEME() {

	}

	/**
	 * constructor
	 * 
	 * @param antecendents
	 * @param consequents
	 */
	public LagEME(double[] antecendents, double[] consequents) {
		assert (antecendents != null);
		assert (consequents != null);
		this.antecedents = antecendents;
		this.consequents = consequents;

	}

	/**
	 * Constructor
	 * 
	 * @param antecendents
	 * @param consequents
	 * @param sigmaSqrt
	 * @param miu
	 */
	public LagEME(double[] antecendents, double[] consequents, double lambda) {
		assert (antecendents != null);
		assert (consequents != null);

		this.antecedents = antecendents;
		this.consequents = consequents;
		this.lambda = lambda;

	}

	/**
	 * get the density at x in normal distribution
	 * 
	 * @param miu
	 * @param sigma
	 * @param x
	 * @return the density at x point
	 */
	public double normalDensity(double miu, double sigma, double x) {
		NormalDistribution normalDistribution = new NormalDistribution(miu,
				sigma);

		return normalDistribution.density(x);
	}

	/**
	 * get the density at x in exponential distribution
	 * 
	 * @param lambda
	 * @param x
	 * @return
	 */
	public double expDistribution(double lambda, double x) {

		ExponentialDistribution exp = new ExponentialDistribution(lambda);

		return exp.density(x);
	}

	/**
	 * @param threshold
	 */
	public void runEM(double threshold, boolean matrix) {
		double oldLikelihood = logLikelihood;
		double delta = 0.0;
		iteration = 0;
		initialize();
		do {
			logInfo(matrix);
			oldLikelihood = logLikelihood;
			expectation();
			maximization();
			evaluateLogLikelihood();
			delta = Math.abs(logLikelihood - oldLikelihood);
			iteration++;
		} while (delta > threshold);
	}

	/**
	 * initialization
	 */
	public void initialize() {

		double average = 1.0 / antecedents.length;


		// the last element of each row is the sum of all the elements of
		// current row.
		intermediate = new double[consequents.length][antecedents.length + 1];
		for (int i = 0; i < consequents.length; i++) {
			for (int j = 0; j < antecedents.length; j++) {
				intermediate[i][j] = average;
			}
			intermediate[i][antecedents.length] = 1.0;
		}

	}

	/**
	 * expectation
	 */
	public void expectation() {

		double temp = 0.0;

		for (int i = 0; i < consequents.length; i++) {
			temp = 0.0;
			for (int j = 0; j < antecedents.length; j++) {
				intermediate[i][j] = intermediate[i][j]
						* expDistribution(lambda, consequents[i]
								- antecedents[j]);
				temp += intermediate[i][j];
			}
			
			for (int j = 0; j < antecedents.length; j++) {
				intermediate[i][j] = intermediate[i][j] / temp;
			}

			intermediate[i][antecedents.length] = 1.0;
		}

	}

	/**
	 * maximization
	 */
	public void maximization() {
		double newLambda = 0.0;

		double temp = 0.0;
		// maximize the log likelihood with respect to miu
		for (int i = 0; i < consequents.length; i++) {
			for (int j = 0; j < antecedents.length; j++) {
				temp += (intermediate[i][j] * (consequents[i] - antecedents[j]));
			}
		}
		temp = temp / consequents.length;
		lambda = 1.0 / temp;

	}

	/**
	 * update the logLikelihood
	 */
	public void evaluateLogLikelihood() {
		double newLogLikelihood = 0.0;

		for (int i = 0; i < consequents.length; i++) {
			double sum = 0.0;
			for (int j = 0; j < antecedents.length; j++) {
				sum += (intermediate[i][j] * expDistribution(lambda,
						consequents[i] - antecedents[j]));
			}
			newLogLikelihood += Math.log(sum);
		}
		logLikelihood = newLogLikelihood;
	}

	
	/**
	 * @param matrix
	 */
	public void logInfo(boolean matrix) {
		StringBuffer sb = new StringBuffer();
		sb.append("(#iteration=");
		sb.append(iteration);
		sb.append("): lambda=");
		sb.append(lambda);
		sb.append(",logLikelihood=");
		sb.append(logLikelihood);

		if (matrix)
			for (int i = 0; i < intermediate.length; i++) {
				sb.append("\n");
				for (int j = 0; j < intermediate[i].length; j++) {
					sb.append("\t");
					sb.append(intermediate[i][j]);
				}
			}
		System.out.println(sb.toString());

	}

	public double getLambda() {
		return lambda;
	}

	public void setLambda(double lambda) {
		this.lambda = lambda;
	}

}
