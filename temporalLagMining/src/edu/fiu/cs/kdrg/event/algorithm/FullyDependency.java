package edu.fiu.cs.kdrg.event.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.fiu.cs.kdrg.evevt.core.*;

public class FullyDependency {

	private Map<Integer, Integer> itemSupport = new HashMap<Integer, Integer>();

	private List<Transaction<Integer>> transactions = null;

	private List<Transaction<Integer>> dependency = new ArrayList<Transaction<Integer>>();

	// for alpha=0.05
	private double zAlpha = 1.64;

	public FullyDependency() {

	}

	// support for each item
	public void scanForItemSupport() {
		assert (transactions != null);
		Integer i;
		for (Transaction<Integer> trans : transactions) {
			Iterator<Integer> iter = trans.iterator();
			while (iter.hasNext()) {
				i = iter.next();
				Integer value = itemSupport.get(i);
				if (value == null) {
					itemSupport.put(i, 1);
				} else {
					itemSupport.put(i, value + 1);
				}
			}
		}
	}

	public boolean isDPattern(Transaction<Integer> itemset) {
		int countE = 0;
		int n = transactions.size();
		for (Transaction<Integer> trans : transactions) {
			if (trans.hit(itemset.getItems()))
				countE++;
		}

		itemset.incSupp(countE);

		double pStar = 1.0;
		Iterator<Integer> it = itemset.iterator();
		while (it.hasNext()) {
			Integer item = it.next();
			pStar = pStar * itemSupport.get(item) / n;
		}

		double minsupE = n * pStar + zAlpha
				* Math.sqrt((n * pStar * (1 - pStar)));
		
		itemset.setMinsupE(minsupE);
		if (countE >= minsupE)
			return true;
		return false;
	}

	public void levelWiseDPattern() {
		// L1
		List<Transaction<Integer>> L1 = new ArrayList<Transaction<Integer>>();
		Iterator<Integer> it = itemSupport.keySet().iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			Transaction<Integer> trans = new Transaction<Integer>();
			trans.add(it.next());
			trans.incSupp(itemSupport.get(key));
			L1.add(trans);
		}

		// L2
		List<Transaction<Integer>> L2 = new ArrayList<Transaction<Integer>>();
		for (int i = 0; i < L1.size(); i++) {
			for (int j = i + 1; j < L1.size(); j++) {
				Transaction<Integer> trans = new Transaction<Integer>();
				trans.add(L1.get(i).getItems());
				trans.add(L1.get(j).getItems());
				if (isDPattern(trans))
					L2.add(trans);
			}
		}

		if (L2.size() == 0)
			return;
		dependency.addAll(L2);

		List<Transaction<Integer>> Lk = L2;
		while (Lk.size() > 0) {
			List<Transaction<Integer>> LkPlus1 = new ArrayList<Transaction<Integer>>();
			for (int i = 0; i < Lk.size(); i++) {
				Set<Integer> set = new HashSet<Integer>();
				set.addAll(Lk.get(i).getItems());
				for (int j = i + 1; j < Lk.size(); j++) {
					set.addAll(Lk.get(j).getItems());
					Transaction<Integer> trans = new Transaction<Integer>();
					trans.add(set);
					if (trans.size() == Lk.get(i).size() + 1) {
						if (isDPattern(trans))
							LkPlus1.add(trans);
					}
				}
			}
			if (LkPlus1.size() == 0)
				break;
			dependency.addAll(LkPlus1);
			Lk = LkPlus1;
		}
	}

	public void output(String path,Object[] map) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(path));
		StringBuffer sb = new StringBuffer();
		for (Transaction<Integer> trans : dependency) {
			sb.setLength(0);
			sb.append("[");
			boolean first = true;
			for (Integer item : trans.getItems()) {
				if (first != true)
					sb.append(",");
				first =false;
				sb.append(map[item]);
			}
			sb.append("]");
			sb.append("(");
			sb.append(trans.getSupp());
			sb.append(">=");
			sb.append(trans.getMinsupE());
			sb.append(")");
			pw.println(sb);
		}
		pw.close();
	}

	/**
	 * @return the itemSupport
	 */
	public Map<Integer, Integer> getItemSupport() {
		return itemSupport;
	}

	/**
	 * @param itemSupport
	 *            the itemSupport to set
	 */
	public void setItemSupport(Map<Integer, Integer> itemSupport) {
		this.itemSupport = itemSupport;
	}

	/**
	 * @return the transactions
	 */
	public List<Transaction<Integer>> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions
	 *            the transactions to set
	 */
	public void setTransactions(List<Transaction<Integer>> transactions) {
		this.transactions = transactions;
	}

	/**
	 * @return the zAlpha
	 */
	public double getzAlpha() {
		return zAlpha;
	}

	/**
	 * @param zAlpha
	 *            the zAlpha to set
	 */
	public void setzAlpha(double zAlpha) {
		this.zAlpha = zAlpha;
	}

}
