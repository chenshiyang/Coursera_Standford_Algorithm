package partone.week5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


/**
* <p>Title: DijkstraAlgorithm</p>
* <p>Description:An implementation of Dijkstra algorithm for computing single source
* shortest path problem using a self-difined heap data structure. </p>
* 
* @author chenshiyang
* @date Aug 5, 2015
* @time 11:43:16 AM
*/
public class DijkstraAlgorithm {
	/**
	 * The graph
	 */
	public Graph m_graph = new Graph();
	
	/**
	 * A map from vertex id to vertex index in the verteces list.
	 */
	public HashMap<Integer, Integer> m_IdToIndex = new HashMap<Integer, Integer>();
	
	/**
	 * Heap used in Dijkstra Algorithm.
	 */
	public Heap m_heap = new Heap();
	
	/**
	 * A set of vertices that are selected by dijkstra.
	 */
	public HashSet<Integer> m_choose = new HashSet<Integer>();
	
	/**
	 * Build the graph from given filename.
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void buildGraph(String filename) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String lineStr = "";
		while((lineStr = reader.readLine()) != null){
			String[] sarry = lineStr.split("\\s");
			int vId = Integer.parseInt(sarry[0]);
			Vertex vertex = new Vertex(vId);
			m_graph.vertices.add(vertex);
			m_IdToIndex.put(vId, m_graph.vertices.size() - 1);
			for(int i = 1; i < sarry.length; i ++){
				String[] sarray = sarry[i].split(",");
				int vertexId = Integer.parseInt(sarray[0]);
				int edgeLength = Integer.parseInt(sarray[1]);
				vertex.adjacentVertices.add(vertexId);
				m_graph.edges.put(generateKey(vId, vertexId), edgeLength);
			}
		}
		reader.close();
	}
	
	/**
	 * show the graph.
	 */
	public void displayGraph(){
		System.out.println("The adjacent list representation graph is as below :");
		for (Vertex vertex : m_graph.vertices){
			System.out.print("Header " + vertex.vertexId + "\t" + vertex.vertexDistance + "\t");
			for(Integer anotherVertexId : vertex.adjacentVertices){
				System.out.print(anotherVertexId + " , ");
			}
			System.out.println();
		}
		System.out.println("the edges are like below:");
		
		Iterator<String> iterator = m_graph.edges.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			System.out.println(key + " : " + m_graph.edges.get(key));
		}
	}
	
	/**
	 * generate a hashmap key(String type) using two vertex id(int type).
	 * 
	 * @param vid1 first vertex id
	 * @param vid2 second vertex id
	 * @return the key
	 */
	public String generateKey(int vid1, int vid2){
		String key;
		if(vid1 < vid2)
			key = vid1 + "," + vid2;
		else
			key = vid2 + "," + vid1;
		return key;
	}
	
	/**
	 * Build the heap using the vertex list.
	 */
	public void buildHeap(){
		Iterator<Vertex> iterator = m_graph.vertices.iterator();
		while(iterator.hasNext()){
			Vertex vertex = iterator.next();
			m_heap.insert(vertex);
		}
	}
	
	/**
	 * Main procedure of dijkstra algorithm.
	 * @param sourceVertexId the source vertex's id.
	 * 
	 * the distance from each vertex to the source vertex is stored in each vertex's vertexDistance
	 * field.
	 */
	public void dijkstra(int sourceVertexId){
		//first, set the first vertex as the source vertex.
		//set its vertexDistance to be 0.
		m_graph.vertices.get(m_IdToIndex.get(sourceVertexId)).setVertexDistance(0);
		
		//build heap
		buildHeap();
		//
		while(m_choose.size() < m_graph.vertices.size()){
			//extract one vertex from heap top.
			Vertex vertex = m_heap.extractMin();
			//add it into set: choose
			m_choose.add(vertex.vertexId);
			//for any edge starting with vertex
			Iterator<Integer> iterator = vertex.adjacentVertices.iterator();
			while(iterator.hasNext()){
				int vertexId = iterator.next();
				//if the edge's head not in set:choose. i.e. it is in the heap.
				if(!m_choose.contains(vertexId)){
					//get this vertex
					Vertex head = m_graph.vertices.get(m_IdToIndex.get(vertexId));
					//compute this vertex's new distance
					int newDistance = vertex.getVertexDistance() + m_graph.edges.get(generateKey(vertex.vertexId, vertexId));
					//if new distance < old distance, then 
					//modify its distance
					//and sift it up.
					if(newDistance < head.getVertexDistance()){
						head.setVertexDistance(newDistance);
						m_heap.siftUp(head.vertexIndex);
					}
				}
			}
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		long current = System.currentTimeMillis();
		DijkstraAlgorithm da = new DijkstraAlgorithm();
		da.buildGraph("E:\\Stanford Algorithms\\week5\\dijkstraData.txt");
//		da.displayGraph();
		da.dijkstra(1);//set source vertex as vertex 1.
		HashSet<Integer> testset = new HashSet<Integer>();
		// 7,37,59,82,99,115,133,165,188,197
		int[] test = {7,37,59,82,99,115,133,165,188,197};
		for(int i : test){
			testset.add(i);
		}
		for(int i = 0; i < da.m_graph.vertices.size(); i ++){
			if(testset.contains(da.m_graph.vertices.get(i).vertexId))
//				System.out.println(da.m_graph.vertices.get(i).toString());
				System.out.print(da.m_graph.vertices.get(i).vertexDistance + ",");
		}
		System.out.println();
		System.out.println("time used:" + (System.currentTimeMillis() - current) + "ms");
	}

}
