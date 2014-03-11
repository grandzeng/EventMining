/**
 * 
 */
package edu.fiu.cs.kdrg.event.algorithm.em;

/**
 * This abstract class gives the skeleton of expectation-maximization algorithm
 * 
 * @author Chunqiu Zeng
 * @date Feb. 5th, 2014
 */

public abstract class AbstractExpectationmMaximization {

	/**
	 * running time
	 */
	protected long milliseconds = 0l;

	/**
	 * The objective is to maximize the logLikelihood
	 */
	protected double logLikelihood = Double.NEGATIVE_INFINITY;

	/**
	 * the # of iteration
	 */
	protected int iteration = 0;

	/**
	 * The stopping criteria
	 */
	protected double threshold = 0.0;

	/**
	 * This function is used to log the information for monitoring the algorithm
	 */
	public abstract void logInfo();

	/**
	 * initialization
	 */
	public abstract void initialization();

	/**
	 * calculate the posterior probability
	 */
	public abstract void expectation();

	/**
	 * calculate the parameters for maximization
	 */
	public abstract void maximization();

	/**
	 * Based on the new updated parameters, the new loglikelihood is evaluated
	 */
	public abstract void evaluation();

	/**
	 * The entry for running Expectation-Maximization algorithm
	 */
	public void run() {
		double oldLogLikelihood = logLikelihood;
		double delta = 0.0;
		this.milliseconds = System.currentTimeMillis();
		initialization();
		 logInfo();
		do {
			expectation();
			maximization();
			evaluation();
			delta = Math.abs(logLikelihood - oldLogLikelihood);
			iteration++;
			oldLogLikelihood = logLikelihood;
			 logInfo();
		} while (delta > threshold);
		this.milliseconds = System.currentTimeMillis() - this.milliseconds;
	}

	/**
	 * @return the threshold
	 */
	public double getThreshold() {
		return threshold;
	}

	/**
	 * @param threshold
	 *            the threshold to set
	 */
	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

}
