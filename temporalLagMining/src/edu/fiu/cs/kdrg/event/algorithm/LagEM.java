package edu.fiu.cs.kdrg.event.algorithm;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * This algorithm applies EM framework to find the lag between two events.
 * 
 * @author Chunqiu Zeng
 * @date JAN 26th,2014
 * 
 */
public class LagEM {

	private double[] antecedents = null;

	private double[] consequents = null;

	private double[] PIs = null;

	private double miu = 10.0;

	private double sigmaSqrt = 100;

	private double logLikelihood = 0.0;

	private double intermediate[][] = null;

	private int iteration = 0;

	/**
	 * default constructor
	 */
	public LagEM() {

	}

	/**
	 * constructor
	 * 
	 * @param antecendents
	 * @param consequents
	 */
	public LagEM(double[] antecendents, double[] consequents,double miu,double sigmaSqrt) {
		assert (antecendents != null);
		assert (consequents != null);
		this.antecedents = antecendents;
		this.consequents = consequents;
		PIs = new double[antecendents.length];
		this.miu = miu;
		this.sigmaSqrt = sigmaSqrt;

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
	 * @param threshold
	 */
	public void runEM(double threshold) {
		double oldLikelihood = logLikelihood;
		double delta = 0.0;
		iteration = 0;
		initialize();
		do {
			logInfo();
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

		double average = 1.0 / PIs.length;

		for (int i = 0; i < PIs.length; i++) {
			PIs[i] = average;
		}

//		miu = 10.5;
//		sigmaSqrt = 1.0;
		// the last element of each row is the sum of all the elements of
		// current row.
		intermediate = new double[consequents.length][antecedents.length + 1];

	}

	/**
	 * expectation
	 */
	public void expectation() {

		double temp = 0.0;

		for (int i = 0; i < consequents.length; i++) {
			temp = 0.0;
			for (int j = 0; j < antecedents.length; j++) {
				intermediate[i][j] = PIs[j]
						* normalDensity(miu + antecedents[j],
								Math.sqrt(sigmaSqrt), consequents[i]);
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
		double newMiu = 0.0;
		double newSigmaSqrt = 0.0;
		double temp = 0.0;
		// maximize the log likelihood with respect to miu
		for (int i = 0; i < consequents.length; i++) {
			for (int j = 0; j < antecedents.length; j++) {
				newMiu += (intermediate[i][j] * (consequents[i] - antecedents[j]));
			}
		}
		newMiu = newMiu / consequents.length;
		miu = newMiu;

		// maximize the log likelihood with respect to sigma
		for (int i = 0; i < consequents.length; i++) {
			for (int j = 0; j < antecedents.length; j++) {
				temp = (consequents[i] - antecedents[j] - newMiu);
				temp = temp * temp;
				newSigmaSqrt += (intermediate[i][j] * temp);
			}
		}
		newSigmaSqrt = (2 * newSigmaSqrt) / consequents.length;
		sigmaSqrt = newSigmaSqrt;

		// maximize the log likelihood with respect to PI
		for (int j = 0; j < antecedents.length; j++) {
			temp = 0.0;
			for (int i = 0; i < consequents.length; i++) {
				temp += intermediate[i][j];
			}
			temp = temp / consequents.length;
			PIs[j] = temp;
		}

		// miu = newMiu;
		// sigmaSqrt = newSigmaSqrt;
	}

	/**
	 * update the logLikelihood
	 */
	public void evaluateLogLikelihood() {
		/*
		 * double newLogLikelihood = 0.0; for (int i = 0; i <
		 * consequents.length; i++) { newLogLikelihood +=
		 * Math.log(intermediate[i][antecedents.length]); } logLikelihood =
		 * newLogLikelihood;
		 */

		double newLogLikelihood = 0.0;

		for (int i = 0; i < consequents.length; i++) {
			double sum = 0.0;
			for (int j = 0; j < antecedents.length; j++) {
				sum += (PIs[j] * normalDensity(
						miu + antecedents[j], Math.sqrt(sigmaSqrt),
						consequents[i]));
			}
			newLogLikelihood += Math.log(sum);
		}
		logLikelihood = newLogLikelihood;
	}

	public void logInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("(#iteration=");
		sb.append(iteration);
		sb.append("): miu=");
		sb.append(miu);
		sb.append(",sigmaSqrt=");
		sb.append(sigmaSqrt);
		sb.append(",logLikelihood=");
		sb.append(logLikelihood);
		for (int i = 0; i < PIs.length; i++) {
			sb.append("\t");
			sb.append(PIs[i]);
		}
		System.out.println(sb.toString());

	}

}
