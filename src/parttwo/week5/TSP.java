package parttwo.week5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;

import parttwo.week5.Edge;

/**
 * Coursera MIT Algorithm week 5 programming assignments
 * TSP problem for 25 cities.
 * 
 * Procedure:
 * 1. get distance data.  getData() function
 * 2. Here we use a iterative function to compute the minLength.
 * 		in fact, it is a backtracking + bound .
 * 	  The current minLength is set as 26443. 但是实际上，这个当前最小值
 *  	不应该是凭空产生的，我们可以通过调用一个贪心算法来产生这个最小值。
 *  然后调用   tspBB() function ： 它做如下行为：
 *  	1.graph 为当前使用的节点的集合， 初始化为 0 号节点
 *      2.candidate 为备选节点，初始化为 1号 到24号节点（在一共有25个城市的情况下）
 *      3.调用递归子程序 tspSolver()
 * 
 * 
 * 
 * @author chenshiyang
 *
 */
public class TSP{
	private double[][] distanceTable;
	private int nodeNum;//node number
	private double minLength = 6660;//the current minimum length
	private int[] minSequence;//the current best solution
	
	/**
	 * 从输入程序中读取：
	 * 1. 节点数目（输入文件的第一行）
	 * 2. 节点坐标： 每一行表示一个节点，节点的坐标由横坐标和纵坐标表示，中间用空格隔开。
	 * 将节点之间的距离保存在 distanceTable 中。
	 * 
	 * test1.txt : 12
	 * test2.txt : 10
	 * test3.txt : 3.50116
	 * tsp.txt:
	 * 
	 */
	public void getData() throws Exception{
		String fileName = "E:\\Standford Algorithms II\\week 5\\test1.txt";
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s = in.readLine();
		nodeNum = Integer.parseInt(s);
		distanceTable = new double[nodeNum][nodeNum];
		minSequence = new int[nodeNum];
		double [][] nodePosition = new double[nodeNum][2];
		int count = 0;
		//read in the coordinates information
		while((s = in.readLine()) != null){
			String[] sarry = s.split(" ");
			double xindex = Double.parseDouble(sarry[0]);
			double yindex = Double.parseDouble(sarry[1]);
			nodePosition[count][0] = xindex;
			nodePosition[count][1] = yindex;
			count ++;
		}
		//compute the distance , and fill the distanceTable
		for(int i = 0; i < distanceTable.length - 1; i ++){
			for(int j = i + 1; j < distanceTable.length; j ++){
				double distance = Math.sqrt(Math.pow((nodePosition[i][0] - nodePosition[j][0]), 2) + Math.pow((nodePosition[i][1] - nodePosition[j][1]), 2));
				distanceTable[i][j] = distance;
				distanceTable[j][i] = distance;
			}
		}
		//report the distanceTable
/*		for(int i = 0; i < nodeNum; i ++){
			for(int j = 0; j < nodeNum; j ++){
				System.out.print(distanceTable[i][j] + "\t");
			}
			System.out.println();
		}*/
	}
	
	public void getDataII() {
		nodeNum = 5;
		minSequence = new int[nodeNum];

		double[][] distance = {{0,8,4,5,9},
				{8,0,1,7,3},
				{4,1,0,6,2},
				{5,7,6,0,5},
				{9,3,2,5,0}
		};
		distanceTable = distance;
		//report the distanceTable
/*		for(int i = 0; i < nodeNum; i ++){
			for(int j = 0; j < nodeNum; j ++){
				System.out.print(distanceTable[i][j] + "\t");
			}
			System.out.println();
		}*/
	}
	
	/**
	 * 递归计算最小路程的子程序
	 * 对candidate list中的每个节点node：
	 *    将它从candidate中删除，并加入graph节点中。
	 *    更新graph的当前路径长度
	 *    计算将node加入graph后的下界
	 *    	如果下界大于历史最短路径，则剪掉。
	 *      如果graph中的节点已包括所有节点，则比较当前路径长度和历史最短路径长度。
	 *    否则，递归调用tspSolver()子程序
	 * 
	 * @param graph the nodes that have been used.
	 * @param candidate the nodes that have not been used yet.
	 * @param the sequence array to record the nodes we have travel
	 * @param the number of nodes we want to travel
	 */
	public void tspSolver (Graph graph, Graph candidate, int[] sequence, int currentIndex) {
		for(int i = 0; i < candidate.nodes.size(); i ++){
			int node = candidate.nodes.get(i);
			Graph g = new Graph(graph);
			Graph candi = new Graph(candidate);
			g.nodes.add(node);
			candi.nodes.remove(i);
			g.currentLength += distanceTable[graph.nodes.getLast()][node];
			//bound
			double bound = bound(g, candi, node);//choose candidate's ith node to add into graph
			if(bound > minLength)
				continue;
			sequence[currentIndex] = node;
			//if all nodes were selected.
			if(g.nodes.size() == nodeNum){
				double length = g.currentLength + distanceTable[g.nodes.getLast()][0];//compute current length
				if(length < minLength){
					minLength = length;
					minSequence = sequence.clone();
				}
				continue;
			}

			tspSolver(g, candi, sequence, currentIndex + 1);
			sequence[currentIndex] = -1;
		}
	}
	
	/**
	 * 
	 */
	public void tspBB(){
		//initialize the selected graph
		LinkedList<Integer> nodes = new LinkedList<Integer>();
		nodes.add(0);
		Graph graph = new Graph(0, 0, nodes);//start with only nodes 0, currentlength = 0
		
		//initialize the candidate graph.
		LinkedList<Integer> candiNodes = new LinkedList<Integer>();
		for(int i = 1; i < nodeNum; i ++){
			candiNodes.add(i);
		}
		Graph candidate = new Graph(0, -1, candiNodes);
		//initialize the sequence array
		int[] sequence = new int[nodeNum];
		for(int i = 1; i < nodeNum; i ++){
			sequence[i] = -1;
		}
		sequence[0] = 0;
		//start compute
		tspSolver(graph, candidate, sequence, 1);
		//output the minimum length
		System.out.println("minimum length : " + minLength);
		//output the solution
		System.out.println("minimum solution : ");
		for(int i = 0; i < minSequence.length; i ++)
			System.out.print(minSequence[i] + "\t");
		System.out.println();
	}
	
	
	/**
	 * 计算将 节点 node 从candidate加入graph后的下界。
	 * 
	 * @param graph the selected nodes
	 * @param candidate the nodes unselected with node
	 * @param node the node of the candidate
	 * @return
	 */
	public double bound(Graph graph, Graph candidate, int node){
		if(candidate.nodes.size() == 0){
			return graph.currentLength + distanceTable[graph.nodes.getLast()][0];
		}
		// bound = 1. the current length
		double bound = graph.currentLength;
//		bound = graph.currentLength + distanceTable[graph.nodes.get(graph.nodes.size() - 1)][node];
		//2. plus the min distance between node and nodes
		bound += minDistance(node, candidate.nodes);
		//3. plus the length of the MST of candidate nodes
		// compute the MST length of candidate.nodes
		bound += getMSTLength(candidate.nodes);
		//4. plus the mindistance between nodes and node 0.
		bound += minDistance(0, candidate.nodes);
		return bound;
	}
	
	/**return the minimum distance between node and the list of nodes.
	 * 返回node与nodes中的节点间的最短距离
	 * @param node 
	 * @param nodes
	 * @return
	 */
	public double minDistance(int node, LinkedList<Integer> nodes){
		double minDistance = distanceTable[node][nodes.getFirst()];
		for(int i : nodes){
			minDistance = Math.min(minDistance, distanceTable[node][i]);
		}
		return minDistance;
	}
	
	/**return the MST length of nodes.
	 * 返回nodes节点所构成的完全图中的最小生成树的长度
	 * 
	 * 这里用到了week1 里面计算最小生成树的思路
	 * 类似没用 Union-Find-Set 的 Kruscal 算法
	 * @param nodes
	 * @return
	 */
	public double getMSTLength(LinkedList<Integer> nodes) {
		//compute edge number
		int edgeNum = nodes.size() * (nodes.size() - 1) / 2;
		//initialize the edge array
		Edge[] edges = new Edge[edgeNum];
		int count = 0;
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = i + 1; j < nodes.size(); j++) {
				edges[count] = new Edge();
				edges[count].left = nodes.get(i);
				edges[count].right = nodes.get(j);
				edges[count].cost = distanceTable[nodes.get(i)][nodes.get(j)];
				count++;
			}
		}
		HashSet<Integer> hs = new HashSet<Integer>();
		edges = mergeSort(edges);
		double cost = 0;// the current cost, initiate as 0.
		// first, arbitrary select a node into hash set hs, here we select min = node[0]
		int min = nodes.get(0);
		hs.add(min);
		// indicate whether the edge has been selected, if selected then 1, else
		// 0
		boolean[] isSelected = new boolean[edges.length];
		while (hs.size() <= nodes.size()) {
			if (hs.size() == nodes.size())
				return cost;
			for (int i = 0; i < edges.length; i++) {
				// if this edge has not been selected yet and its left node is
				// in set ,right node not in set,then use it
				if ((!isSelected[i]) && hs.contains(edges[i].left)
						&& (!hs.contains(edges[i].right))) {
					isSelected[i] = true;
					hs.add(edges[i].right);
					cost += edges[i].cost;
					// System.out.println("select edge " + edges[i].cost +
					// "node " + edges[i].right +""
					// + " was added into the set, the cost is now " + cost);
					break;
				} else if ((!isSelected[i]) && hs.contains(edges[i].right)
						&& (!hs.contains(edges[i].left))) {
					isSelected[i] = true;
					hs.add(edges[i].left);
					cost += edges[i].cost;
					// System.out.println("select edge " + edges[i].cost +
					// "node " + edges[i].left +""
					// + " was added into the set, the cost is now " + cost);
					break;
				}
			}
		}
		return cost;

	}
	
	/** A merge sort algorithm for edges basing on edge's cost
	 * @param edges
	 * @return
	 */
	public Edge[] mergeSort(Edge[] edges){
		int len = edges.length;
		if(len <= 1)
			return edges;
		//else , divide
		Edge[] left = new Edge[len / 2];
		Edge[] right = new Edge[len - len / 2];
		for(int i = 0 ; i < left.length ; i ++)
			left[i] = edges[i];
		for(int i = 0 ; i < right.length ; i ++)
			right[i] = edges[i + len / 2];
		left = mergeSort(left);
		right = mergeSort(right);
		edges = merge(left, right);
		return edges;
	}
	
	public Edge[] merge(Edge[] left, Edge[] right){
		Edge[] edges = new Edge[left.length + right.length];
		int i = 0 ; int j = 0; int k = 0;
		for( ; i < edges.length  && j < left.length && k < right.length; i ++){
			
			if(left[j].cost <= right[k].cost ){
				edges[i] = left[j ++];
			}
			else
				edges[i] = right[k ++];
		}
		while(j < left.length ){
			edges[i ++] = left[j ++];
		}
		while(k < right.length){
			edges[i ++] = right[k ++];
		}
		return edges;
	}
	
	public static void main(String[] args) throws Exception{
		TSP tsp = new TSP();
		long now = System.currentTimeMillis();
		tsp.getDataII();
		tsp.tspBB();
		System.out.println("time used : " + (System.currentTimeMillis() - now));
//		System.out.println(2*Math.sqrt(5) + 2 * Math.sqrt(2) + 4);
	}
}