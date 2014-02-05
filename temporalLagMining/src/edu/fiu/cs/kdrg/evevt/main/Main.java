package edu.fiu.cs.kdrg.evevt.main;

import java.io.IOException;

import edu.fiu.cs.kdrg.event.algorithm.FullyDependency;
import edu.fiu.cs.kdrg.evevt.util.EventLoader;
import edu.fiu.cs.kdrg.evevt.util.MapEvent;
import edu.fiu.cs.kdrg.evevt.util.SeqToTransactions;

public class Main {
	
	public void func(){}
	
	public static void main(String args[]) throws IOException {
		EventLoader<Integer> loader = new EventLoader<Integer>();
		loader.load("data/sequence.txt");
		SeqToTransactions<Integer> stt = new SeqToTransactions<Integer>(300) ;
		stt.convert(loader.getEvents());
		
		System.out.println("the # of transactions:"+stt.getTransactions().size());
		
		FullyDependency fd = new FullyDependency();
		fd.setTransactions(stt.getTransactions());
		fd.scanForItemSupport();
		fd.levelWiseDPattern();
		Object[]map = MapEvent.loadMap("data/eventmap.txt"); 
		fd.output("data/fulldep.txt",map);
		
	}
}
