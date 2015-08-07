package partone.week3;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * A implementation of random contraction algorithm for computing 
 * the minimum cut of a undirected connected graph.
 * 
 * @author chenshiyang
 *
 */
public class MinCutTest{
	
	private ArrayList<Vertice> m_graph;
	private ArrayList<Vertice> m_backGraph;
	private int m_minCutMin = 1000;
	private int m_nodeCount = 0;
	
	public void recoverGraph(){
		m_nodeCount = m_backGraph.size();
		m_graph = new ArrayList<Vertice>();
		for(int i = 0; i < m_backGraph.size(); i ++){
			m_graph.add(new Vertice(m_backGraph.get(i)));
		}
	}
	
	public void getGraphData(String fName) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(fName));
		String lineString = "";
		m_graph = new ArrayList<Vertice>();
		m_backGraph = new ArrayList<Vertice>();
		int  verticeInx = 1;
		while((lineString = reader.readLine()) != null){
			String[] sarry = lineString.split("\t");
			ArrayList<Integer> nodes = new ArrayList<Integer>();

			for(int i = 1; i < sarry.length; i ++){
				nodes.add(Integer.parseInt(sarry[i].trim()));
			}
			
			Vertice vertice = new Vertice();
			HashSet<Integer> verticeName = new HashSet<Integer>();
			verticeName.add(verticeInx);
			vertice.setVerticeInx(verticeName);
			vertice.setAdjacentNodes(nodes);
			m_graph.add(vertice);
			Vertice backupVertice = new Vertice(vertice);
			m_backGraph.add(backupVertice);
			verticeInx ++;
		}
		m_nodeCount = m_graph.size();
//		m_minCutMin = m_graph.get(0).getAdjacentNodes().size();
		reader.close();
/*		System.out.println(m_graph.size());
		for(int i = 0; i < m_graph.size(); i ++){
			System.out.println(m_graph.get(i).getAdjacentNodes().size());
		}*/
	}
	
	public void randomContraction(int seed){
		Random rand = new Random(seed);
		while(m_graph.size() > 2){
			int currentSize = m_graph.size();
			// randomly choose first vertice
			int firstNodeIndex = rand.nextInt(currentSize);
			/***************method one *********************
			 * 遍历第一个点的邻接点
			 *    如果这个点出现在graph里的其他点中，则返回*/
			int secondNodeIndex = -1;
			
			int secondNodeIndexInFirst = rand.nextInt(m_graph.get(firstNodeIndex).getAdjacentNodes().size());
			int vertexNum = m_graph.get(firstNodeIndex).getAdjacentNodes().get(secondNodeIndexInFirst);
			for(int i = 0; i < m_graph.size(); i ++){
				if(i == firstNodeIndex)
					continue;
				if(m_graph.get(i).getVerticeInx().contains(vertexNum)){
					secondNodeIndex = i;
					break;
				}
			}
			/***************method two **********************
			 * 遍历graph里的点。
			 * 对graph里的一个点
			 *    如果第一个点的邻接点点包含它则返回*/
/*			int secondNodeIndex = rand.nextInt(currentSize);
			Iterator<Integer> iterator;
			//indicates if second node is contained in the first node's adjacent nodes.
			boolean contain = false;

			while(!contain){
				//randomly choose second node.
				secondNodeIndex = rand.nextInt(currentSize);
				//if the second node is not the first node's adjacent node, regenerate it.
				while(secondNodeIndex == firstNodeIndex){
					secondNodeIndex = rand.nextInt(currentSize);
				}
//				System.out.println("truck");
				iterator = m_graph.get(secondNodeIndex).getVerticeInx().iterator();
				while(iterator.hasNext()){
					int temp = iterator.next();
					if(m_graph.get(firstNodeIndex).getAdjacentNodes().contains(temp)){
						contain = true;
						break;
					}
				}
			}*/
//			System.out.println("hi");
			//merge the two vertice
			m_graph.get(firstNodeIndex).getAdjacentNodes().addAll(m_graph.get(secondNodeIndex).getAdjacentNodes());
			
			//change the vertice name
			m_graph.get(firstNodeIndex).getVerticeInx().addAll(m_graph.get(secondNodeIndex).getVerticeInx());
			
			//remove self loop
			m_graph.get(firstNodeIndex).getAdjacentNodes().removeAll(m_graph.get(firstNodeIndex).getVerticeInx());
						
			//remove the second vertice
			m_graph.remove(secondNodeIndex);
		}
		int currentMinCutCount = m_graph.get(0).getAdjacentNodes().size();
		if(currentMinCutCount < m_minCutMin)
			m_minCutMin = currentMinCutCount;
//		System.out.println("Cut Number: " + m_minCutMin);
	}
	
	public void randomContractionAlgorithm(String fName) throws Exception{
		getGraphData(fName);
		int loopCount = (int)(Math.log(m_nodeCount) * Math.pow(m_nodeCount, 2));
		for(int i = 0; i < loopCount; i ++){
			randomContraction(i);
			recoverGraph();
//			System.out.println("node Count:" + m_nodeCount);
		}
	}
	
	public static void main(String[] args) throws Exception {
		long current = System.currentTimeMillis();
		MinCutTest mct = new MinCutTest();
//		String fName = "E:\\Stanford Algorithms\\week3\\test2.txt";
		String fName = "E:\\Stanford Algorithms\\week3\\kargerMinCut.txt";
		mct.randomContractionAlgorithm(fName);
		System.out.println(mct.m_minCutMin);
		System.out.println((System.currentTimeMillis() - current) / 1000 + "s"
				+ "");
	}
	
}