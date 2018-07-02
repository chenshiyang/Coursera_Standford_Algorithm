package parttwo.week2;

import parttwo.week1.Edge;

public class Sort{
	public static Edge[] mergeSortEdge(Edge[] edges){
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
		left = Sort.mergeSortEdge(left);
		right = Sort.mergeSortEdge(right);
		edges = Sort.mergeEdge(left, right);
		return edges;
	}
	
	public static Edge[] mergeEdge(Edge[] left, Edge[] right){
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
}