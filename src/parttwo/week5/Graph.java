package parttwo.week5;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 * @author chenshiyang
 *
 */
public class Graph{
	public int[] nodeTable;
	public double currentLength;
	public double minSpanTeeSize;
	public LinkedList<Integer> nodes;
	
	public Graph(){
		
	}
	
	/**
	 * @param clen
	 * @param mstsize
	 * @param nodes
	 */
	public Graph( double clen, double mstsize, LinkedList<Integer> nodes){
//		this.nodeTable = nodeTable;
		currentLength = clen;
		minSpanTeeSize = mstsize;//暂时没用到
		this.nodes = nodes;
	}
	public Graph(Graph graph){
//		this.nodeTable = graph.nodeTable.clone();
		this.currentLength = graph.currentLength;
		this.minSpanTeeSize = graph.minSpanTeeSize;
		this.nodes = (LinkedList<Integer>) graph.nodes.clone();
	}
}