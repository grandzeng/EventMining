package edu.fiu.cs.kdrg.evevt.core;

public class Rule {
	private int antecedent;
	private int consequent;
	private double interval;

	public Rule() {
	}

	public Rule(int ant, int con, double intval) {
		antecedent = ant;
		consequent = con;
		interval = intval;
	}

	public int getAntecedent() {
		return antecedent;
	}

	public void setAntecedent(int antecedent) {
		this.antecedent = antecedent;
	}

	public int getConsequent() {
		return consequent;
	}

	public void setConsequent(int consequent) {
		this.consequent = consequent;
	}

	public double getInterval() {
		return interval;
	}

	public void setInterval(double interval) {
		this.interval = interval;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(antecedent);
		sb.append("]---");
		sb.append(interval);
		sb.append("--->[");
		sb.append(consequent);
		sb.append("]");
		return sb.toString();
	}

}
