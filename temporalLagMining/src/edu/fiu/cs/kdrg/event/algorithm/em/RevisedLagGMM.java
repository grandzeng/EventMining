/**
 * 
 */
package edu.fiu.cs.kdrg.event.algorithm.em;

import edu.fiu.cs.kdrg.evevt.util.DensityEval;

/**
 * This class is used to mine the relation ship A-->[lag] B. L follows the
 * normal distribution N(miu, sigmaSqrt). This class is revised from
 * LagGuassianMixture
 * 
 * @author Chunqiu Zeng
 * @date Feb 5th, 2014
 * 
 */
public class RevisedLagGMM extends AbstractExpectationmMaximization {

	/**
	 * a sequence of time stamps of event A's occurrence
	 */
	private double[] antecedents = null;

	/**
	 * a sequence of time stamps of event B's occurrence
	 */
	private double[] consequents = null;

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
	 * Default Constructor
	 */
	public RevisedLagGMM() {
	}

	/**
	 * Constructor
	 * 
	 * @param antecedents
	 * @param consequents
	 * @param miu
	 * @param sigmaSqrt
	 */
	public RevisedLagGMM(double[] antecedents, double[] consequents,
			double miu, double sigmaSqrt) {
		this.antecedents = antecedents;
		this.consequents = consequents;
		this.miu = miu;
		this.sigmaSqrt = sigmaSqrt;
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
		sb.append("): miu=");
		sb.append(miu);
		sb.append(",sigmaSqrt=");
		sb.append(sigmaSqrt);
		sb.append(",logLikelihood=");
		sb.append(logLikelihood);

		// if (matrix)
		// for (int i = 0; i < intermediate.length; i++) {
		// sb.append("\n");
		// for (int j = 0; j < intermediate[i].length; j++) {
		// sb.append("\t");
		// sb.append(intermediate[i][j]);
		// }
		// }
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

		// the last element of each row is the sum of all the elements of
		// current row.
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
		double avg = 1.0/antecedents.length;

		for (int i = 0; i < consequents.length; i++) {
			temp = 0.0;
			for (int j = 0; j < antecedents.length; j++) {
				intermediate[i][j] = intermediate[i][j]
						* DensityEval.normalDensity(miu, Math.sqrt(sigmaSqrt),
								consequents[i] - antecedents[j]);
				temp += intermediate[i][j];
			}
			
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
		newSigmaSqrt = 2 * newSigmaSqrt / consequents.length;
		sigmaSqrt = newSigmaSqrt;
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
				sum += (intermediate[i][j] * DensityEval.normalDensity(miu,
						Math.sqrt(sigmaSqrt), consequents[i] - antecedents[j]));
			}
			newLogLikelihood += Math.log(sum);
		}
		logLikelihood = newLogLikelihood;
	}

}
