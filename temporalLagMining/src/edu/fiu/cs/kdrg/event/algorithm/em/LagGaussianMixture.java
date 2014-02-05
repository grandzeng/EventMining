package edu.fiu.cs.kdrg.event.algorithm.em;

import edu.fiu.cs.kdrg.evevt.util.DensityEval;

/**
 * This class is used to mining the relation ship A-->[lag] B. L follows the
 * normal distribution N(miu, sigmaSqrt).
 * 
 * @author Chunqiu Zeng
 * @date Feb 5th, 2014
 * 
 */
public class LagGaussianMixture extends AbstractExpectationmMaximization {

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
	private double miu = 10.0;

	/**
	 * the variance of normal distribution
	 */
	private double sigmaSqrt = 100.0;

	/**
	 * this is used to record the intermediate result
	 */
	private double intermediate[][] = null;

	/**
	 * default constructor
	 */
	public LagGaussianMixture() {
	}

	/**
	 * Constructor
	 * 
	 * @param ants
	 * @param cons
	 * @param miu
	 * @param sigmaSqrt
	 */
	public LagGaussianMixture(double[] ants, double[] cons, double miu,
			double sigmaSqrt) {
		assert (ants != null);
		assert (cons != null);
		this.antecedents = ants;
		this.consequents = cons;
		this.miu = miu;
		this.sigmaSqrt = sigmaSqrt;
		PIs = new double[ants.length];
	}

	@Override
	public void logInfo() {
		// TODO Auto-generated method stub
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

	@Override
	public void initialization() {
		// TODO Auto-generated method stub
		double average = 1.0 / PIs.length;
		for (int i = 0; i < PIs.length; i++) {
			PIs[i] = average;
		}

		// the last element of each row is the sum of all the elements of
		// current row.
		intermediate = new double[consequents.length][antecedents.length + 1];

	}

	@Override
	public void expectation() {
		// TODO Auto-generated method stub
		double temp = 0.0;

		for (int i = 0; i < consequents.length; i++) {
			temp = 0.0;
			for (int j = 0; j < antecedents.length; j++) {
				intermediate[i][j] = PIs[j]
						* DensityEval.normalDensity(miu + antecedents[j],
								Math.sqrt(sigmaSqrt), consequents[i]);
				temp += intermediate[i][j];
			}

			for (int j = 0; j < antecedents.length; j++) {
				intermediate[i][j] = intermediate[i][j] / temp;
			}
			intermediate[i][antecedents.length] = 1.0;
		}
	}

	@Override
	public void maximization() {
		// TODO Auto-generated method stub
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

	}

	@Override
	public void evaluation() {
		// TODO Auto-generated method stub
		double newLogLikelihood = 0.0;

		for (int i = 0; i < consequents.length; i++) {
			double sum = 0.0;
			for (int j = 0; j < antecedents.length; j++) {
				sum += (PIs[j] * DensityEval.normalDensity(
						miu + antecedents[j], Math.sqrt(sigmaSqrt),
						consequents[i]));
			}
			newLogLikelihood += Math.log(sum);
		}
		logLikelihood = newLogLikelihood;
	}

}
