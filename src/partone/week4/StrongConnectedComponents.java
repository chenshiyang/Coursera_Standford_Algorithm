package partone.week4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * An implementation of Kosaraju's algorithm to compute the strong connected Components of a 
 * directed graph.
 * 
 * 
 * @author chenshiyang
 *
 */
public class StrongConnectedComponents {
	/**
	 * the original graph
	 */
	private Graph m_graph = new Graph();
	
	/**
	 * the reverse graph of the original graph
	 */
	private Graph m_reverseGraph = new Graph();
	
	
	/**
	 * record fvalue of each vertex, the ith entry stored the index of the vertex whose fvalue is i.
	 */
	private int[] m_fvalue;
	
	
	/**
	 * record the leader number of each vertex
	 */
	private int[] m_leader;
	
	/**
	 * indicate whether each vertex has been explored or not.
	 */
	private boolean[] m_explored;
	
	/**
	 * the current fvalue.
	 */
	private int m_currentFvalue = 0;
	
	/**
	 *  count the number of scc.
	 */
	private int m_sccCount = 0;
	
	
	/**
	 * read in file fname, and build graph and reverse graph.
	 * 
	 * @param fname
	 * @throws Exception
	 */
	public void buildGrpah(String fname) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(fname));
		String line = "";
		while((line = reader.readLine()) != null){
			String[] sarry = line.split(" ");
			int vertexNumber = Integer.parseInt(sarry[0]);
			int secondVertexNumber = Integer.parseInt(sarry[1]);
			//if the graph contains this vertex
			if(m_graph.m_numberToIndex.containsKey(vertexNumber)){
				m_graph.m_vertices.get(m_graph.m_numberToIndex.get(vertexNumber)).getAdjacentVertice().add(secondVertexNumber);
			}
			else{//else, add this vertex into the graph.
				Vertex vertex = new Vertex(vertexNumber);
				vertex.getAdjacentVertice().add(secondVertexNumber);
				m_graph.m_vertices.add(vertex);
				m_graph.m_numberToIndex.put(vertexNumber, m_graph.m_vertices.size() - 1);			
			}
			//add into reverse graph
			if(!m_reverseGraph.m_numberToIndex.containsKey(vertexNumber)){
				Vertex rvertex = new Vertex(vertexNumber);
				m_reverseGraph.m_vertices.add(rvertex);
				m_reverseGraph.m_numberToIndex.put(vertexNumber, m_reverseGraph.m_vertices.size() - 1);
			}

			//if the second vertex is  in the reverse graph
			if(m_reverseGraph.m_numberToIndex.containsKey(secondVertexNumber)){
				m_reverseGraph.m_vertices.get(m_reverseGraph.m_numberToIndex.get(secondVertexNumber)).getAdjacentVertice().add(vertexNumber);
			}
			else{//if the second vertex is not in the reverse graph.
				Vertex rvertex = new Vertex(secondVertexNumber);
				rvertex.getAdjacentVertice().add(vertexNumber);
				m_reverseGraph.m_vertices.add(rvertex);
				m_reverseGraph.m_numberToIndex.put(secondVertexNumber, m_reverseGraph.m_vertices.size() - 1);

			}
			//add into graph
			if(!m_graph.m_numberToIndex.containsKey(secondVertexNumber)){
				//add into  graph
				Vertex vertex = new Vertex(secondVertexNumber);
				m_graph.m_vertices.add(vertex);
				m_graph.m_numberToIndex.put(secondVertexNumber, m_graph.m_vertices.size() - 1);
			}
		}
		reader.close();
		
		//initialize m_fvalue and m_leader and m_explored
		m_fvalue = new int[m_reverseGraph.m_vertices.size()];
		m_leader = new int[m_graph.m_vertices.size()];
		m_explored = new boolean[m_graph.m_vertices.size()];
	}
	
	/**
	 * display the graph as a adjacent list.
	 * 
	 * @param graph
	 */
	public void displayGraph(Graph graph){
		for(int i = 0; i < graph.m_vertices.size(); i ++){
			System.out.print(graph.m_vertices.get(i).getVertexNumber() + ":\t");
			Iterator<Integer> iterator = graph.m_vertices.get(i).getAdjacentVertice().iterator();
			while(iterator.hasNext()){
				System.out.print(iterator.next() + ", ");
			}
			System.out.println();
		}
	}
	
	/**
	 * The first pass of the Kosaraju Algorithm.
	 * 
	 * Search the reverse graph and compute each vertex's fvalue.
	 * 
	 * @param startVertex  the vertex's index in the graph.
	 */
	public void DFSAndSetFValue(int startVertex){
		m_explored[startVertex] = true;
		Iterator<Integer> iterator = m_reverseGraph.m_vertices.get(startVertex).getAdjacentVertice().iterator();
		while(iterator.hasNext()){
			int vertexNumber = iterator.next();
			//if unexplored
			int vertexIndex = m_reverseGraph.m_numberToIndex.get(vertexNumber);
			if(!m_explored[vertexIndex]){
				DFSAndSetFValue(vertexIndex);
			}
		}
		m_fvalue[m_currentFvalue ++] = startVertex;//caution here.
	}
	
	/**
	 * The second DFS pass of the Kosaraju Algorithm.
	 * Including dfs traverse the graph and set leader for each vertex.
	 * 
	 * @param startVertexIndex
	 * @param leaderNumber
	 */
	public void DFSAndSetLeader(int startVertexIndex, int leaderNumber){
		m_explored[startVertexIndex] = true;//set explored
		m_leader[startVertexIndex] = leaderNumber;// set leader
		
		Iterator<Integer> iterator = m_graph.m_vertices.get(startVertexIndex).getAdjacentVertice().iterator();
		while(iterator.hasNext()){
			int vertexNumber = iterator.next();
			if(! m_explored[m_graph.m_numberToIndex.get(vertexNumber)]){
				DFSAndSetLeader(m_graph.m_numberToIndex.get(vertexNumber), leaderNumber);
			}
		}
	}
	
	/**
	 * The Kosaraju's two phase DFS algorithm
	 * 
	 * 
	 * @param startVertex the first vertex NUMBER in the first DFS.
	 */
	public void computeStrongConnectedComponents(){
		//the first pass dfs
		for(int i = 0; i < m_reverseGraph.m_vertices.size(); i ++){
			if(m_explored[i])
				continue;
			//else unexplored
			DFSAndSetFValue(i);
		}
		
		//reset explored array
		m_explored = new boolean[m_graph.m_vertices.size()];
		
		//the second pass dfs
		for(int i = m_fvalue.length - 1; i >= 0; i --){
			if(m_explored[m_fvalue[i]])
				continue;
			//else unexplored.
			int currentVertexIndex = m_fvalue[i];
			int leaderNumber = m_graph.m_vertices.get(currentVertexIndex).getVertexNumber();
			
			DFSAndSetLeader(currentVertexIndex, leaderNumber);
			
			m_sccCount ++;
		}		
	}
	
	public void displayStrongConnectComponents(){
		HashMap<Integer, Integer> leaderCount = new HashMap<Integer, Integer>();
		for(int i = 0; i < m_leader.length; i ++){
			int leader = m_leader[i];
			if(leaderCount.containsKey(leader)){
				int oldvalue = leaderCount.get(leader);
				leaderCount.put(leader, oldvalue + 1);
			}
			else{
				leaderCount.put(leader, 1);
			}
		}
		int[] array = new int[leaderCount.size()];

		Iterator<Integer> iterator = leaderCount.keySet().iterator();
		int i = 0;
		while(iterator.hasNext()){
			array[i] = leaderCount.get(iterator.next());
			i ++;
		}
		Arrays.sort(array);
		for(int j = array.length - 5; j < array.length; j ++){
			System.out.println(array[j]);
		}
		System.out.println();
		System.out.println("The number of scc is " + m_sccCount);
	}
	
	public static void main(String[] args) throws Exception {
		Long currentTime = System.currentTimeMillis();
		StrongConnectedComponents scc = new StrongConnectedComponents();
		String fname = "E:\\Stanford Algorithms\\week4\\SCC.txt";
		scc.buildGrpah(fname);
//		scc.displayGraph(scc.m_graph);
//		scc.displayGraph(scc.m_reverseGraph);
		scc.computeStrongConnectedComponents();
		scc.displayStrongConnectComponents();
		System.out.println("time used:" + (System.currentTimeMillis() - currentTime));
	}
	
}
