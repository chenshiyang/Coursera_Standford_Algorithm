package partone.week5;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author chenshiyang
 *
 * Aug 5, 2015
 * 11:16:59 AM
 * 
 * the graph class
 */
public class Graph {
	/**
	 * a arraylist of vertices in this graph.
	 */
	
	public ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	
	/**
	 * a map<edge name, edge length> of edges in this graph.
	 */
	public HashMap<String, Integer> edges = new HashMap<String, Integer>();
}
