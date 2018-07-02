package parttwo.week1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class MST{
	public static int nodeNum = 0;
	public static int edgeNum = 0;
	public static HashSet<Integer> hs = new HashSet<Integer>();// the set to store nodes that have been selected
	
	/**
	 * 
	 * read in file
	 * test4:Tree:[1, 2, 3] sum :6
	 * test5:Tree: [1, 1, 2] sum :4
	 * test6:Tree: [-10, -1, -8, -3, 6] sum : -16
	 * @return
	 * @throws IOException
	 */
	public Edge[] readFile() throws IOException{
		String file = "E:\\Standford Algorithms II\\week 1\\edges.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String[] ss = in.readLine().split(" ");
		nodeNum = Integer.parseInt(ss[0]);
		edgeNum = Integer.parseInt(ss[1]);
		int i = 0;
		String s;
		Edge[] edges = new Edge[edgeNum];
		while((s = in.readLine()) != null){
			String[] intString = s.split(" ");
			edges[i] = new Edge();
			edges[i].id = i + 1;
			edges[i].left = Integer.parseInt(intString[0]);
			edges[i].right = Integer.parseInt(intString[1]);
			edges[i].cost = Integer.parseInt(intString[2]);
			i ++;
		}
		in.close();
		return edges;
	}
	
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
	
	/**
	 * first we travel the edges array, select a edge that satisfies:
	 * 1. it has not been selected yet;
	 * 2. it one of its node has been choose, either left or right.
	 * a edge satisfies these two condition is selected, since we sort the edges according to their cost
	 * before, we guarantee that we have selected the min cost edge. After we have select an edge,
	 * we break the loop, to find next node.
	 * every time we choose a node, add it into the set, then , we retravel the edge arrays to find
	 * another node.
	 * the terminal condition is that the number of node in the set equals to the total number of nodes. 
	 * 
	 * @return
	 * @throws IOException
	 */
	public int getCost() throws IOException{
		Edge[] edges;
		edges = readFile();//read in the edges data and build a array.
//		for(int i = 0 ; i < edges.length ; i ++){
//			System.out.print(edges[i].left + "\t" + edges[i].right + "\t" + edges[i].cost + "\n");
//		}
//		System.out.println("*********************");
		edges = mergeSort(edges);
//		for(int i = 0 ; i < edges.length ; i ++){
//			System.out.print(edges[i].left + "\t" + edges[i].right + "\t" + edges[i].cost + "\n");
//		}
		int cost = 0;//the current cost, initiate as 0.
		int min = 0;
		//first, arbitrary select a node into hash set hs, here we select min = 0
		hs.add(min);
		//indicate whether the edge has been selected, if selected then 1, else 0
		boolean [] isSelected = new boolean[edges.length];
		while(hs.size() <= nodeNum){
			if(hs.size() == nodeNum)
				return cost;
			for(int i = 0 ; i < edges.length ; i ++){
				//if this edge has not been selected yet and its left node is in set ,right node not in set,then use it
				if((!isSelected[i]) && hs.contains(edges[i].left) && (! hs.contains(edges[i].right))){
					isSelected[i] = true;
					hs.add(edges[i].right);
					cost += edges[i].cost;
//					System.out.println("select edge " + edges[i].cost + "node " + edges[i].right +""
//							+ " was added into the set, the cost is now " + cost);
					break;
				}
				else if((!isSelected[i]) && hs.contains(edges[i].right) && (! hs.contains(edges[i].left))){
					isSelected[i] = true;
					hs.add(edges[i].left);
					cost += edges[i].cost;
//					System.out.println("select edge " + edges[i].cost + "node " + edges[i].left +""
//							+ " was added into the set, the cost is now " + cost);
					break;
				}
			}
		}
		return cost;
	}
	public static void main(String[] args) throws IOException{
		long current = System.currentTimeMillis();
		MST mst = new MST();
		System.out.println(mst.getCost());
		System.out.println(System.currentTimeMillis()- current);
	}
}