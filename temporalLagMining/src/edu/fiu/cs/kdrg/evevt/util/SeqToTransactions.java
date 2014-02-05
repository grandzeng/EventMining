package edu.fiu.cs.kdrg.evevt.util;

import java.util.ArrayList;
import java.util.List;

import edu.fiu.cs.kdrg.evevt.core.*;

public class SeqToTransactions<T> {

	private List<Transaction<Integer>> transactions = new ArrayList<Transaction<Integer>>();

	/** seconds */
	private int window = 300;

	public SeqToTransactions(int w) {
		window = w;
	}

	public void convert(List<Event<T>> events) {
		long start = 0;
		long end = start + window;
		Transaction<Integer> trans = null;
		for (Event<T> e : events) {
			if (e.getTimestamp() > end) {
				start = e.getTimestamp();
				end = start + window;
				if (trans != null && !trans.isEmpty())
					transactions.add(trans);
				trans = new Transaction<Integer>();
			}
			trans.add(e.getEventType());
		}
		if (trans != null && !trans.isEmpty())
			transactions.add(trans);
	}

	/**
	 * @return the window
	 */
	public int getWindow() {
		return window;
	}

	/**
	 * @param window
	 *            the window to set
	 */
	public void setWindow(int window) {
		this.window = window;
	}

	/**
	 * @return the transactions
	 */
	public List<Transaction<Integer>> getTransactions() {
		return transactions;
	}
	
	

}
