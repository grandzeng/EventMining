/**
 * 
 */
package edu.fiu.cs.kdrg.event.algorithm.em;

import edu.fiu.cs.kdrg.evevt.util.DensityEval;

/**
 * This class is used to mine the relation ship A-->[lag] B. L follows the
 * normal distribution E(lambda).
 * 
 * @author Chunqiu Zeng
 * @date Feb 5th, 2014
 * 
 */
public class RevisedLagExponentialMixture extends
		AbstractExpectationmMaximization {

	/**
	 * a sequence of time stamps of event A's occurrence
	 */
	private double[] antecedents = null;

	/**
	 * a sequence of time stamps of event B's occurrence
	 */
	private double[] consequents = null;

	/**
	 * this is used to record the intermediate result
	 */
	private double intermediate[][] = null;

	/**
	 * This is the mean of exponential distribution
	 */
	private double lambda = 10.0;

	/**
	 * default constructor
	 */
	public RevisedLagExponentialMixture() {
	}

	/**
	 * Constructor
	 * 
	 * @param antecedents
	 * @param consequents
	 * @param lambda
	 */
	public RevisedLagExponentialMixture(double[] antecedents,
			double[] consequents, double lambda) {
		assert (antecedents != null);
		assert (consequents != null);
		this.antecedents = antecedents;
		this.consequents = consequents;
		this.lambda = lambda;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.fiu.cs.kdrg.event.algorithm.em.AbstractExpectationmMaximization#logInfo
	 * ()
	 */
	@Override
	public void logInfo() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("(#iteration=");
		sb.append(iteration);
		sb.append("): lambda=");
		sb.append(lambda);
		sb.append(",logLikelihood=");
		sb.append(logLikelihood);
		System.out.println(sb.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.fiu.cs.kdrg.event.algorithm.em.AbstractExpectationmMaximization#
	 * initialization()
	 */
	@Override
	public void initialization() {
		// TODO Auto-generated method stub
		double average = 1.0 / antecedents.length;

		intermediate = new double[consequents.length][antecedents.length];
		for (int i = 0; i < consequents.length; i++) {
			for (int j = 0; j < antecedents.length; j++) {
				intermediate[i][j] = average;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.fiu.cs.kdrg.event.algorithm.em.AbstractExpectationmMaximization#
	 * expectation()
	 */
	@Override
	public void expectation() {
		// TODO Auto-generated method stub
		double temp = 0.0;

		for (int i = 0; i < consequents.length; i++) {
			temp = 0.0;
			for (int j = 0; j < antecedents.length; j++) {
				intermediate[i][j] = intermediate[i][j]
						* DensityEval.exponentialDistribution(lambda,
								consequents[i] - antecedents[j]);
				temp += intermediate[i][j];
			}

			double avg = 1.0 / antecedents.length;
			for (int j = 0; j < antecedents.length; j++) {
				if (temp != 0.0)
					intermediate[i][j] = intermediate[i][j] / temp;
				else
					intermediate[i][j] = avg;
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.fiu.cs.kdrg.event.algorithm.em.AbstractExpectationmMaximization#
	 * maximization()
	 */
	@Override
	public void maximization() {
		// TODO Auto-generated method stub

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.fiu.cs.kdrg.event.algorithm.em.AbstractExpectationmMaximization#
	 * evaluation()
	 */
	@Override
	public void evaluation() {
		// TODO Auto-generated method stub
		double newLogLikelihood = 0.0;

		for (int i = 0; i < consequents.length; i++) {
			double sum = 0.0;
			for (int j = 0; j < antecedents.length; j++) {
				sum += (intermediate[i][j] * DensityEval
						.exponentialDistribution(lambda, consequents[i]
								- antecedents[j]));
			}
			newLogLikelihood += Math.log(sum);
		}
		logLikelihood = newLogLikelihood;
	}

}
