package parttwo.week1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class MSTwithHeap{
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
	
	public int getCost() throws IOException{
		Edge[] edges;
		edges = readFile();//read in the edges data and build a array.
		HeapForEdge heap = new HeapForEdge(edges);
		heap.buildMinHeap(edges);
		//arbitrary select a node in to hs, say 0.
		hs.add(0);
		 return 0;
	}
	
	public static void main(String[] args) {
		
	}
}