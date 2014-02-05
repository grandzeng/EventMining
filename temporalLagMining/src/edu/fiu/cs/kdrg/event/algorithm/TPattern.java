package edu.fiu.cs.kdrg.event.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.fiu.cs.kdrg.evevt.core.*;
import edu.fiu.cs.kdrg.evevt.util.MapEvent;

public class TPattern {

	/**
	 * 
	 */
	private List<Event<String>> events = null;
	
	/**
	 * 
	 */
	private String[] mapEvent = null;

	/**
	 * default constructor
	 */
	public TPattern() {

	}

	public TPattern(List<Event<String>> evts) {
		events = evts;
	}

	/**
	 * @return the events
	 */
	public List<Event<String>> getEvents() {
		return events;
	}

	/**
	 * @param events
	 *            the events to set
	 */
	public void setEvents(List<Event<String>> events) {
		this.events = events;
	}

	public String[] getMapEvent() {
		return mapEvent;
	}

	public void setMapEvent(String[] mapEvent) {
		this.mapEvent = mapEvent;
	}

	public List<Event<String>> zeroTruncate() {
		int size = events.size();
		long base = 0;
		long timestamp;
		for (int i = 0; i < size; i++) {
			timestamp = events.get(i).getTimestamp();
			if (i == 0) {
				base = timestamp;
			}
			events.get(i).setTimestamp(timestamp - base);
		}
		return events;
	}

	public boolean isDependent(int A, int B) {
		// number of A followed by B
		int na = 0;
		double T = 0;
		// significant level alpha=0.5
		double zAlpha = 1.96;
		int size = events.size();
		double ta = -1;
		double tb = -1;
		double tbSquareSum = 0;
		double tbCubeSum = 0;
		double tabSum = 0;

		for (int i = 0; i < size; i++) {
			Event<String> e = events.get(i);
			if (e.getEventType() == A) {
				ta = e.getTimestamp();
			} else if (e.getEventType() == B) {
				tb = e.getTimestamp();
				if (ta != -1) {
					tabSum += (tb - ta);
					ta = -1;
					na++;
				}
				tbSquareSum += (tb - T) * (tb - T);
				tbCubeSum += (tb - T) * (tb - T) * (tb - T);

				T = tb;
			}
		}

		if (na == 0)
			return false;
		double Mb = (1.0 / (2 * T)) * tbSquareSum;
		double Mab = (1.0 / na) * tabSum;
		double var = ((1.0 / (3 * T)) * tbCubeSum - ((1.0 / (2 * T)) * tbSquareSum)
				* ((1.0 / (2 * T)) * tbSquareSum))
				/ na;
		double criteria = Math.abs(Mb - Mab) / Math.sqrt(var);
		// System.out.println(criteria);
		if (criteria > zAlpha)
			return true;
		else
			return false;

	}

	public double estimatePTao(double period, double delta, double N, double T) {
		double lamda = N / T;
		return 2 * delta * Math.exp((-1) * lamda * period);
	}

	public double estimateThreshold(double N, double Ptao) {
		return Math.sqrt(3.84 * N * Ptao * (1 - Ptao)) + N * Ptao;
	}

	public boolean chiSquareTest(double Ctao, double period, double delta,
			double N, double T) {
		double Ptao = estimatePTao(period, delta, N, T);
		double threshold = estimateThreshold(N, Ptao);
		if (Ctao > threshold)
			return true;

		return false;
	}

	public double findPeriod(int A, int B, double delta) {

		double period = -1;
		double T = 0;
		double maxCTao = 0;
		double d = 0;
		List<Double> interArrivals = new ArrayList<Double>();
		int size = events.size();

		for (int i = 0; i < size; i++) {
			Event<String> ei = events.get(i);
			if (ei.getEventType() == B && ei.getTimestamp() > T)
				T = ei.getTimestamp();
			if (ei.getEventType() == A) {
				for (int j = i + 1; j < size; j++) {
					Event<String> ej = events.get(j);
					if (ej.getEventType() == B) {
						interArrivals.add((double) (ej.getTimestamp() - ei
								.getTimestamp()));
						// find the nearest B
						break;
					}
				}
			}
		}

		int sizeArrivals = interArrivals.size();
		double p = 0;
		double Ctao = 0;

		for (int j = 0; j < sizeArrivals; j++) {
			p = interArrivals.get(j);
			Ctao = 0;

			if (delta > p)
				d = p;
			else
				d = delta;
			// calculate cTao
			for (int r = 0; r < sizeArrivals; r++) {
				if (interArrivals.get(r) > p - d
						&& interArrivals.get(r) < p + d) {
					Ctao += 1;
				}
			}

			// period test
			if (chiSquareTest(Ctao, p, d, sizeArrivals, T)) {
				if (Ctao > maxCTao) {
					maxCTao = Ctao;
					period = p;
				}
			}

		}

		return period;
	}

	public List<Rule> findTPattern(double delta) {
		Set<Integer> eventTypes = new HashSet<Integer>();
		for (Event e : events) {
			eventTypes.add(e.getEventType());
		}
		int[] types = new int[eventTypes.size()];
		Iterator<Integer> it = eventTypes.iterator();
		int index = 0;
		while (it.hasNext()) {
			types[index++] = it.next();
		}

		Arrays.sort(types);

		List<Rule> rules = new ArrayList<Rule>();
		for (int i = 0; i < types.length; i++) {
			for (int j = 0; j < types.length; j++) {
				if (i == j)
					continue;

				if (isDependent(types[i], types[j])) {
					System.out.println(mapEvent[types[i]] + " ------> "
							+ mapEvent[types[j]]);
					double intval = findPeriod(types[i], types[j], delta);
					if (intval != -1) {
						Rule r = new Rule(types[i], types[j], intval);
						System.out.println(ruleString(r));
						rules.add(r);
					} else {
						System.out.println(mapEvent[types[i]]
								+ "--- (-1) ---> " + mapEvent[types[j]]);
					}
				} else {
					System.out.println(mapEvent[types[i]] + " ---XXX--- "
							+ mapEvent[types[j]]);
				}
			}
		}

		return rules;

	}

	public String ruleString(Rule r) {
		StringBuffer sb = new StringBuffer();
		sb.append(mapEvent[r.getAntecedent()]);
		sb.append("---");
		sb.append(r.getInterval());
		sb.append("--->");
		sb.append(mapEvent[r.getConsequent()]);
		return sb.toString();
	}

}
