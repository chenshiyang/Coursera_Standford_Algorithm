package parttwo.week4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Stack;

/**
 * This class use the floyd-warshall algorithm to solve the 
 * all pairs shortest paths(APSP) problem.
 * 
 * @author chenshiyang
 *
 */
public class FloydWarshall{
	private int[][][] m_table;
	private HashMap<String, Integer> m_edges = new HashMap<String, Integer>();
	private int nodeNum = 0;
	private int edgeNum = 0;
	private int[][] m_path;
	
	public void getData() throws Exception {
		String fileName = "E:\\Standford Algorithms II\\week 4\\g3.txt";
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s = in.readLine();
		nodeNum = Integer.parseInt(s.split(" ")[0]);
		edgeNum = Integer.parseInt(s.split(" ")[1]);
		m_path = new int[nodeNum + 1][nodeNum + 1];//use to record the last hop from i to j.
		m_table = new int[nodeNum + 1][nodeNum + 1][2];
		//initiate m_path table:
		for(int i = 1; i < nodeNum + 1; i ++){
			for(int j = 1; j < nodeNum + 1; j ++){
				m_path[i][j] = -1;
			}
		}
		//base cases: k == 0
		for(int i = 1; i < nodeNum + 1; i ++){
			for(int j = 1; j < nodeNum + 1; j ++){
				if(i == j){
					m_table[i][j][0] = 0;
//					m_path[i][j] = i;
				}
				else
					m_table[i][j][0] = 1000000;
			}
		}
		
		String[] sarry;
		while((s = in.readLine()) != null){
			sarry = s.split(" ");
			int indexI = Integer.parseInt(sarry[0]);
			int indexJ = Integer.parseInt(sarry[1]);
			int cost = Integer.parseInt(sarry[2]);
			m_table[indexI][indexJ][0] = cost;
			m_path[indexI][indexJ] = indexJ;
/*			String key = sarry[0] + "," + sarry[1];
			m_edges.put(key, Integer.parseInt(sarry[2]));*/
		}
		in.close();
	}
	
	/**Ϊ�˽�ʡ�ռ䣬��ʵ����û�б�Ҫʹ��һ�� nodeNum * nodeNum * nodeNum ����ά����
	 * ֻ��Ҫֻ��һ��nodeNum * nodeNum * 2�Ķ�ά����
	 * ��Ϊÿ��������Ƕ�ֻ��Ҫʹ����һ�εĽ��������2��nodeNum*nodeNum �ľ����Ѿ��㹻
	 * �� kk = k % 2.
	 * ��k == odd ʱ������ʹ�� 1�ű�������һ�ε� 1- kk  = 0 �ű������㵱ǰ��1�ű�
	 * �� k == even ʱ�� ����ʹ��0�ű� ������һ�ε� 1- kk = 1�ű������㵱ǰ��0�ű�
	 * ���ս��k = nodeNum = 1000,Ϊż�����������ս��Ӧ�ñ����� 0�ű�
	 * 
	 * 
	 * test1 : -10003
	 * test2 : -6
	 * test3 : negative cycle
	 */
	public void floydWarshall() {
		//base cases:
/*		for(int i = 1; i < nodeNum + 1; i ++){
			for(int j = 1; j < nodeNum + 1; j ++){
				if(i == j){
					m_table[i][j][0] = 0;
					continue;
				}
				String key = i + "," + j;
				if( m_edges.get(key) != null)
					m_table[i][j][0] = m_edges.get(key);
				else
					m_table[i][j][0] = Integer.MAX_VALUE / 2;
			}
		}*/
/*		for(int i = 1; i < nodeNum + 1 ; i ++){
			for(int j = 1; j < nodeNum + 1; j ++){
				System.out.print(m_table[i][j][0] + "\t\t");
			}
			System.out.println();
		}*/
		
		//the loop of floydWarshall:
		for(int k = 1; k < nodeNum + 1; k ++){
			int kk = k % 2;
			for(int i = 1; i < nodeNum + 1; i ++){
				for(int j = 1; j < nodeNum + 1; j ++){
					if(m_table[i][j][1 - kk] < m_table[i][k][1 - kk] + m_table[k][j][1 - kk]){
						m_table[i][j][kk] = m_table[i][j][1 - kk];
					}
					else{
						m_table[i][j][kk] = m_table[i][k][1 - kk] + m_table[k][j][1 - kk];
						m_path[i][j] = m_path[i][k];
					}
				}
			}
		}
	}
	
	/**
	 * check whether there exists a negative cycle in the graph
	 * @return
	 */
	public boolean checkNegativeCycle(){
		boolean flag = false;
		for(int i = 1; i < nodeNum + 1; i ++){
			if(m_table[i][i][0] < 0)
				flag = true;
		}
		return flag;
	}
	
	public void getMinPath(){
		if(checkNegativeCycle())
			System.out.println("There are negative cycle.");
		else{
			int min = 0;
/*			int i = 1;
			int j = 1;*///�����i��j��������������ͻ�õ�����Ĵ� -12
			for(int i = 1 ; i < nodeNum + 1; i ++){
				for(int j = 1 ; j < nodeNum + 1; j ++){
					min = Math.min(min, m_table[i][j][0]);
				}
			}
			System.out.println("The min distance is "  + min);
		}
	}
	
	public void getPath(int start, int end){
/*		Stack<Integer> stack = new Stack<Integer>();
		stack.push(end);
		end = m_path[start][end];
		while(end != start){
			stack.push(end);
			end = m_path[start][end];
		}
		//hop = start
		stack.push(start);
		while(!stack.isEmpty()){
			System.out.print(stack.pop() + " , ");
		}*/
		System.out.println("m_path :" + m_path[start][end]);
		if(m_path[start][end] == -1){
			System.out.println("no edge");
			return;
		}
		System.out.print(start + ",");
		while(start != end){
			start = m_path[start][end];
			System.out.println(start);
			System.out.println(start + ",");
		}
	}
	
	public static void main(String[] args) throws Exception{
		FloydWarshall fw = new FloydWarshall();
		fw.getData();
		long now = System.currentTimeMillis();
		fw.floydWarshall();
		fw.getMinPath();
		System.out.println(System.currentTimeMillis() - now);
//		fw.getPath(1000, 874);
		System.out.println(fw.m_path[1000][874]);
		System.out.println(fw.m_table[1][237][0]);
		System.out.println(fw.m_table[1000][15][0]);
	}
}