package edu.fiu.cs.kdrg.evevt.algorithm;

import org.apache.commons.math3.distribution.ExponentialDistribution;

public class Test {
	
	public static void main(String args[]){
		ExponentialDistribution exp = new ExponentialDistribution(5);
		System.out.println(exp.density(-1));
	}

}
