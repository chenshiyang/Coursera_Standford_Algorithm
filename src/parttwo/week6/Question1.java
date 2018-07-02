package parttwo.week6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.BitSet;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;


public class Question1 {
	public static void main(String[] args) throws Exception {
		Question1 q1 = new Question1();
//		boolean result = q1.scc(args[0]);
//		System.out.println(args[0] + " : " + result);
		
		String fileName = "E:\\Standford Algorithms II\\week 6\\2sat6.txt";
		System.out.println(q1.scc(fileName));
	}
	
	@SuppressWarnings("unchecked")
	public boolean scc(String input) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(input));
		String line = in.readLine();
		int n = Integer.valueOf(line.trim());
		HashSet<Integer>[] graph = new HashSet[2*n+1];
		HashSet<Integer>[] iGraph = new HashSet[2*n+1];
		BitSet gColors = new BitSet(2*n+1);
		line = in.readLine();
		while (line != null) {
			String[] tmp = line.trim().split(" ");
			int u = Integer.valueOf(tmp[0]);
			int v = Integer.valueOf(tmp[1]);
			// -u => v
			int muIndex = -u > 0 ? -u : u + n;
			int vIndex = v > 0 ? v : -v + n;
			if (graph[muIndex] == null) {
				graph[muIndex] = new HashSet<Integer>();
			}
			graph[muIndex].add(vIndex);
			if (iGraph[vIndex] == null) {
				iGraph[vIndex] =  new HashSet<Integer>();
			}
			iGraph[vIndex].add(muIndex);
			// -v => u
			int mvIndex = -v > 0 ? -v : v +n;
			int uIndex = u > 0 ? u : -u + n;
			if (graph[mvIndex] == null) {
				graph[mvIndex] = new HashSet<Integer>();
			}
			graph[mvIndex].add(uIndex);
			if (iGraph[uIndex] == null) {
				iGraph[uIndex] = new HashSet<Integer>();
			}
			iGraph[uIndex].add(mvIndex);
			
			line = in.readLine();
		}
		
		// DFS on graph
		// place vertex in a FILO queue of the finishing time in DFS
		Deque<Integer> s = new LinkedList<Integer>();
		gColors.clear();
		for (int i = 1; i < 2*n+1; ++i) {
			if (gColors.get(i) == false) {
				dfs1(i, graph, s, gColors);
			}
		}
		
		// take vertex from the head of the generated FILO queue
		// and DFS on inverted graph
		// output visited vertices as the SCC of the starting node
		gColors.clear();
		BitSet scc = new BitSet(n);
		Iterator<Integer> it = s.iterator();
		while (it.hasNext()) {
			int source = it.next();
			if (gColors.get(source) == false) {
				scc.clear();
				if (dfs2(source, iGraph, gColors, scc, n) == false) {
					return false;
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void dfs1(Integer source, HashSet[] graph, Deque<Integer> s, BitSet gColors) {
		gColors.set(source);
		HashSet<Integer> nb = graph[source];
		if (nb != null) {
			for (Integer k : nb) {
				if (gColors.get(k) == false) {
					dfs1(k, graph, s, gColors);
				}
			}
		}
		s.push(source);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean dfs2(Integer source, HashSet[] graph, BitSet gColors, BitSet scc, int n) {
		gColors.set(source);
		scc.set(source%n);
		HashSet<Integer> nb = graph[source];
		if (nb != null) {
			for (int k : nb) {
				if (gColors.get(k) == false) {
					if (scc.get(k%n) == true) {
						return false;
					} else {
						dfs2(k, graph, gColors, scc, n);
					}
				}
			}
		}
		return true;
	}
	

}
