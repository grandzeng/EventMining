package edu.fiu.cs.kdrg.event.algorithm;

/**
 * This algorithm applies brute force algorithm to find the lag between two events.
 * 
 * @author Chunqiu Zeng
 * @date JAN 26th,2014
 * 
 */
public class BruteForce {
	
	private double[] antecedents = null;

	private double[] consequents = null;
	
	private double miu = 0.0;
	
	private double sigmaSqrt = 0.0;
	
	/**
	 * default constructor
	 */
	public BruteForce(){
		
	}
	
	/**
	 * Constructor
	 * @param antecedents
	 * @param consequents
	 */
	public BruteForce(double [] antecedents, double [] consequents){
		this.antecedents = antecedents;
		this.consequents = consequents;
	}
	
	
	public void runBruteForce(){
		
	}
	
}
