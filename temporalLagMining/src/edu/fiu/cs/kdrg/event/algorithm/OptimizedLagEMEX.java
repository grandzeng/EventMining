package edu.fiu.cs.kdrg.event.algorithm;

import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.UniformRandomGenerator;

/**
 * This algorithm applies EM framework to find the lag between two events.
 * 
 * @author Chunqiu Zeng
 * @date JAN 26th,2014
 * 
 */
public class OptimizedLagEMEX {

	private double[] antecedents = null;

	private double[] consequents = null;

	private double miu = 10.0;

	private double sigmaSqrt = 100;

	private double logLikelihood = 0.0;

	private double intermediate[][] = null;

	private int iteration = 0;

	/**
	 * default constructor
	 */
	public OptimizedLagEMEX() {

	}

	/**
	 * constructor
	 * 
	 * @param antecendents
	 * @param consequents
	 */
	public OptimizedLagEMEX(double[] antecendents, double[] consequents) {
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
	public OptimizedLagEMEX(double[] antecendents, double[] consequents,
			double miu, double sigmaSqrt) {
		assert (antecendents != null);
		assert (consequents != null);

		this.antecedents = antecendents;
		this.consequents = consequents;
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

		// for (int i = 0; i < PIs.length; i++) {
		// PIs[i] = average;
		// }

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
	 * @param distribution
	 * @return
	 */
	public int [] sample(double [] distribution){
		Random rand = new Random();
		rand.nextDouble();
		return null;
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
				newMiu += (intermediate[i][j] * (consequents[i] - antecedents[j]))
						/ intermediate[i][antecedents.length];
			}
		}
		newMiu = newMiu / consequents.length;
		miu = newMiu;

		// maximize the log likelihood with respect to sigma
		for (int i = 0; i < consequents.length; i++) {
			for (int j = 0; j < antecedents.length; j++) {
				temp = (consequents[i] - antecedents[j] - newMiu);
				temp = temp * temp;
				newSigmaSqrt += (intermediate[i][j] * temp)
						/ intermediate[i][antecedents.length];
			}
		}
		newSigmaSqrt = 2 * newSigmaSqrt / consequents.length;
		sigmaSqrt = newSigmaSqrt;

	}

	/**
	 * update the logLikelihood
	 */
	public void evaluateLogLikelihood() {
		double newLogLikelihood = 0.0;

		for (int i = 0; i < consequents.length; i++) {
			double sum = 0.0;
			for (int j = 0; j < antecedents.length; j++) {
				sum += (intermediate[i][j] * normalDensity(
						miu + antecedents[j], Math.sqrt(sigmaSqrt),
						consequents[i]));
			}
			newLogLikelihood += Math.log(sum);
		}
		logLikelihood = newLogLikelihood;
	}

	/**
	 * 
	 */
	public void logInfo(boolean matrix) {
		StringBuffer sb = new StringBuffer();
		sb.append("(#iteration=");
		sb.append(iteration);
		sb.append("): miu=");
		sb.append(miu);
		sb.append(",sigmaSqrt=");
		sb.append(sigmaSqrt);
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

	/**
	 * @return
	 */
	public double getMiu() {
		return miu;
	}

	/**
	 * @param miu
	 */
	public void setMiu(double miu) {
		this.miu = miu;
	}

	/**
	 * @return
	 */
	public double getSigmaSqrt() {
		return sigmaSqrt;
	}

	/**
	 * @param sigmaSqrt
	 */
	public void setSigmaSqrt(double sigmaSqrt) {
		this.sigmaSqrt = sigmaSqrt;
	}

}
