package partone.week5;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

class Node {
	private int label;			//�ڵ���
	private int dist;			//Դ�ڵ㵽�ýڵ�ľ���
	
	public Node(int l, int d) {
		label = l;
		dist = d;
	}
	
	//Override
	public String toString() {
		return "" + label + "  " + dist;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}
}
public class Dijkstra {
	private Map<Integer,List<Node>> graph;
	private Queue<Node> queue;
	private int MAX = 1000000; 
	Map<Integer,Integer> result = new HashMap<Integer,Integer>();
	public void buildGraph() throws Exception{
		String path = "E:\\Stanford Algorithms\\week5\\dijkstraData.txt";
		File f = new File(path);
		Scanner sc = new Scanner(f);
		String regex = "\\s";
		String regex1 = ",";
		String line;
		String[] strArr;
		int label = -1;
		graph = new HashMap<Integer,List<Node>>();	//��ʼ��
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			strArr = line.split(regex);
			if(strArr.length > 0) {
				label = Integer.valueOf(strArr[0]);
			}
			List<Node> tempList = new ArrayList<Node>();
			int n,d;
			for(int i = 1; i < strArr.length; i++) {
				n = Integer.valueOf(strArr[i].split(regex1)[0]);
				d = Integer.valueOf(strArr[i].split(regex1)[1]);
				Node node = new Node(n,d);
				tempList.add(node);
			}
			if(label != -1) graph.put(label, tempList);
		}
		sc.close();
	}
	
	public void buildQueue() {
		queue = new PriorityQueue<Node>(10, new Comparator<Node>(){
			@Override
			public int compare(Node n1, Node n2) {
				if(n1.getDist() > n2.getDist()) return 1;
				else if(n1.getDist() < n2.getDist()) return -1;
				else return 0;
			}			
			//�˺�������Ҫ����
//			@Override
//			public boolean equals(Object obj) {
//				return true;
//			}
		});
		for(int x : graph.keySet()) {
			Node node;
			if(x == 1) node = new Node(x,0);
			else node = new Node(x,MAX);
			queue.add(node);
		}
	}
	
	//ʹ�����ȶ��еĻ���Ҫ�����ȶ����н����ڵ�ͽڵ������ȶ�����λ������֮�����ϵ �������ܿ����޸ĵ�Դ�ڵ�ľ�����Ϣ
	//���Ҫ���Լ�ʵ�����ȶ��� ��Ϊ��ɾ���Ѿ�������Ľڵ��ʱ��λ����Ϣ��仯 ���ֱ仯��Ҫ��ʱ���񲢼�¼����
	public void DijkstraSingleSourceShortestPath() throws Exception{
		buildGraph();
//		for(int i = 1; i < 10; i++)
//			System.out.println(graph.get(1).toString());
		buildQueue();
		Node node;
		//���ʱ�临�Ӷ��е����
		while(!queue.isEmpty()) {
			int minDist = MAX;
			node = queue.peek();
			for(Node nod : queue) {
				if(nod.getDist() < minDist) {
					minDist = nod.getDist();
					node = nod;
				}
			}
			queue.remove(node);
			result.put(node.getLabel(), node.getDist());
			Set<Integer> set = new HashSet<Integer>();
			for(Node nd : graph.get(node.getLabel())) {
				set.add(nd.getLabel());
			}
			for(Node nd : queue) {
				if(set.contains(nd.getLabel())) {
					int dist = 0;		//distһ���ᱻ���¸�ֵ
					for(Node n : graph.get(node.getLabel())) {		//�����ǿ����Ż���
						if(n.getLabel() == nd.getLabel())
							dist = n.getDist();
					}
					if(nd.getDist() > (node.getDist() + dist))
						nd.setDist((node.getDist() + dist));
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		long current = System.currentTimeMillis();
		Dijkstra dij = new Dijkstra();
		dij.DijkstraSingleSourceShortestPath();
		int[] test = {7,37,59,82,99,115,133,165,188,197};
		for(int x : test)
			System.out.print(dij.result.get(x) + ",");
//		System.out.print(dij.result.toString());
		System.out.println();
		System.out.println("time used:" + (System.currentTimeMillis() - current) + "ms");
	}
}
