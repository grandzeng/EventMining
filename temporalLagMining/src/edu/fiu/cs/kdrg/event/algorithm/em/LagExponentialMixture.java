/**
 * 
 */
package edu.fiu.cs.kdrg.event.algorithm.em;

import edu.fiu.cs.kdrg.evevt.util.DensityEval;

/**
 * @author Chunqiu Zeng
 * 
 */
public class LagExponentialMixture extends AbstractExpectationmMaximization {

	/**
	 * a sequence of time stamps of event A's occurrence
	 */
	private double[] antecedents = null;

	/**
	 * a sequence of time stamps of event B's occurrence
	 */
	private double[] consequents = null;

	/**
	 * The priori probability that an event B is implied by some event A
	 */
	private double[] PIs = null;

	/**
	 * the mean of normal distribution
	 */
	private double lambda = 10.0;

	/**
	 * this is used to record the intermediate result
	 */
	private double intermediate[][] = null;

	/**
	 * default constructor
	 */
	public LagExponentialMixture() {
	}

	/**
	 * constructor
	 * 
	 * @param antecedents
	 * @param consequents
	 * @param lambda
	 */
	public LagExponentialMixture(double antecedents[], double[] consequents,
			double lambda) {
		assert (antecedents != null);
		assert (consequents != null);
		this.antecedents = antecedents;
		this.consequents = consequents;
		this.lambda = lambda;
		PIs = new double[antecedents.length];

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
		double average = 1.0 / PIs.length;
		for (int i = 0; i < PIs.length; i++) {
			PIs[i] = average;
		}

		intermediate = new double[consequents.length][antecedents.length];
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
				intermediate[i][j] = PIs[j]
						* DensityEval.exponentialDistribution(lambda,
								consequents[i] - antecedents[j]);
				temp += intermediate[i][j];
			}
			if (temp != 0)
				for (int j = 0; j < antecedents.length; j++) {
					intermediate[i][j] = intermediate[i][j] / temp;
				}
			else
				for (int j = 0; j < antecedents.length; j++) {
					intermediate[i][j] = PIs[j];
				}
			// intermediate[i][antecedents.length] = 1.0;
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
		double newLambda = 0.0;
		double temp = 0.0;

		// maximize the log likelihood with respect to lambda
		for (int i = 0; i < consequents.length; i++) {
			for (int j = 0; j < antecedents.length; j++) {
				newLambda += (intermediate[i][j] * (consequents[i] - antecedents[j]));
			}
		}
		newLambda = newLambda / consequents.length;
		lambda = newLambda;

		// maximize the log likelihood with respect to PI
		for (int j = 0; j < antecedents.length; j++) {
			temp = 0.0;
			for (int i = 0; i < consequents.length; i++) {
				temp += intermediate[i][j];
			}
			temp = temp / consequents.length;
			PIs[j] = temp;
		}
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
