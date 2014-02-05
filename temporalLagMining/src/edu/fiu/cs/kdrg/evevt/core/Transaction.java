package edu.fiu.cs.kdrg.evevt.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Transaction<T> {
	
	private Set<T> items = new HashSet<T>();
	
	private int supp = 0;
	
	private double minsupE=0.0;
	
	public Transaction(){
		
	}
	
	/**
	 * @return the items
	 */
	public Set<T> getItems() {
		return items;
	}

	public boolean hit(T item){
		return items.contains(item);
	}
	
	public boolean hit(Set<T> otherItems){
		return items.containsAll(otherItems);
	}
	
	public void add(T item){
		items.add(item);
	}
	
	public void add(Set<T> otherItems){
		items.addAll(otherItems);
	}
	
	public int size(){
		return items.size();
	}
	
	public boolean isEmpty(){
		return items.isEmpty();
	}
	
	public Iterator<T> iterator(){
		return items.iterator();
	}



	/**
	 * @return the supp
	 */
	public int getSupp() {
		return supp;
	}


	/**
	 * @param supp the supp to set
	 */
	public void setSupp(int supp) {
		this.supp = supp;
	}
	
	
	public void incSupp(int c){
		this.supp+=c;
	}



	/**
	 * @return the minsupE
	 */
	public double getMinsupE() {
		return minsupE;
	}



	/**
	 * @param minsupE the minsupE to set
	 */
	public void setMinsupE(double minsupE) {
		this.minsupE = minsupE;
	}
	
	
	

}
