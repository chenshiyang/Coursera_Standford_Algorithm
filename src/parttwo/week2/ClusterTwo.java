package parttwo.week2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ClusterTwo{
	public static int K;//number of clusters
	public HashMap<Integer,Integer> set;//save node's bit data,so that we can use bit data to access node.
	public static int nodeNum;
	public static int bitNum = 24;
	public int[] ufs;//ufs for nodes
	public int[] nodes;//sava nodes bit data, so that we can use id(index) to access node.
	public static int[] xor1 = {1,2,4,8,16,32,64,128,256,512,1024,2048,4096,
								8192,16384,32768,65536,131072,262144,524288,1048576,2097152,
								4194304,8388608};
	public static int[] xor2 = new int[276];
	
	public HashMap<Integer,Integer> readFile() throws IOException{
		String file = "E:\\Standford Algorithms II\\week 2\\clustering_big.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String[] ss = in.readLine().split(" ");
		String s;
		nodeNum = Integer.parseInt(ss[0]);
		bitNum = Integer.parseInt(ss[1]);
		
		setXOR2();//set the distance == 2 xor table
		HashMap<Integer,Integer> nodeSet = new HashMap<Integer,Integer>();
		nodes = new int[nodeNum];
		int i = 0;//node's id start from 0
		int code;
		while((s = in.readLine() ) != null){
			s = s.replace(" ", "");
			code = Integer.parseUnsignedInt(s, 2);
			if(nodeSet.get(code) == null){
				nodeSet.put(code, i);
				nodes[i] = code;
				i ++;
			}
		}

		nodeNum = i;
		in.close();
		return nodeSet;
	}
	
/*	public int getHammingDistance(String s1, String s2){
		int distance = 0;
		 for(int i = 0 ; i < s1.length(); i ++){
			 if(s1.charAt(i) != s2.charAt(i))
				 distance ++;
		 }
		 return distance;
	}*/
	
	public void setXOR2(){
		int k = 0;
		for(int i = 0; i < bitNum - 1; i ++){
			for(int j = i + 1; j < bitNum; j ++){
				xor2[k] = xor1[i] + xor1[j];
				k ++;
			}
		}
	}
	
/*	public ArrayList<String> getOneNeighbor(String s){
		ArrayList<String> neighbor = new ArrayList<String>();
		char[] cs = s.toCharArray();
		for(int i = 0; i < bitNum; i ++){
			if(cs[i] == '0'){
				cs[i] = 1;
				neighbor.add(new String(cs));
				cs[i] = 0;
			}
			else if(cs[i] == '1'){
				cs[i] = 0;
				neighbor.add(new String(cs));
				cs[i] = 1;
			}
		}
		return neighbor;
	}*/
	
/*	public char reverseChar(char c){
		if(c == '1')
			return'0';
		else
			return '1';
	}*/
	
/*	public ArrayList<String> getTwoNeighbor(String s){
		ArrayList<String> neighbor = new ArrayList<String>();
		char[] cs = s.toCharArray();
		for(int i = 0; i < bitNum; i ++){
			cs[i] = reverseChar(cs[i]);
			for(int j = i + 1; j < bitNum; j ++){
				cs[j] = reverseChar(cs[j]);
				neighbor.add(new String(cs));
				cs[j] = reverseChar(cs[j]);
			}
			cs[i] = reverseChar(cs[i]);
		}
		return neighbor;
	}*/
	
	/**
	 * 既然我们已经知道所有距离<= 2的点最终都会在一个簇里，我们这里就不用分开
	 * 考虑 是 1邻居还是2邻居了，直接求出给定点的所有邻居，不分1，2.
	 * 而且1，2邻居 merge的先后次序并不会影响最终簇的数量。因为我们的终止条件
	 * 是固定的。
	 * 
	 * @param s
	 * @return
	 */
/*	public ArrayList<String> getNeighbor(String s){
		ArrayList<String> neighbor = new ArrayList<String>();
		char[] cs = s.toCharArray();
		for(int i = 0; i < bitNum - 1; i ++){
			cs[i] = reverseChar(cs[i]);
			for(int j = i + 1; j < bitNum; j ++){
				cs[j] = reverseChar(cs[j]);
				neighbor.add(new String(cs));
				cs[j] = reverseChar(cs[j]);
			}
			cs[i] = reverseChar(cs[i]);
		}
		
		for(int i = 0; i < bitNum; i ++){
			if(cs[i] == '0'){
				cs[i] = '1';
				neighbor.add(new String(cs));
				cs[i] = '0';
			}
			else if(cs[i] == '1'){
				cs[i] = '0';
				neighbor.add(new String(cs));
				cs[i] = '1';
			}
			else
				System.out.println("here");
		}
		return neighbor;
	}*/
	public ArrayList<Integer> getNeighbor(int code){
		ArrayList<Integer> neighbor = new ArrayList<Integer>();
		for(int i : xor1)
			neighbor.add(code ^ i);
		for(int j : xor2)
			neighbor.add(code ^ j);
		return neighbor;
	}
	
	
	public int cluster() throws IOException{
		set = this.readFile();
		long current = System.currentTimeMillis();
		ufs = UnionFindSet.makeUnionFindSet(ufs, nodeNum);
		int clusterCount = nodeNum;// the number of cluster, initiate as node number.
		
		for(int i = 0; i < nodeNum; i ++){
			ArrayList<Integer> neighbor = getNeighbor(nodes[i]);
			for(int s : neighbor){
				if(set.get(s) != null){
					//put node[i] and node[map.get(s)] into the same cluster
					int first = UnionFindSet.find(i, ufs);
					int second = UnionFindSet.find((int)(set.get(s)), ufs);
					if(first == second)
						continue;
					else{
						UnionFindSet.union(ufs, first, second);
						clusterCount --;
					}
				}
			}
		}
		System.out.println(System.currentTimeMillis() - current);
		return clusterCount;
	}
	
	public static void main(String[] args) throws IOException{
		long current = System.currentTimeMillis();
		ClusterTwo ct = new ClusterTwo();
		System.out.println(ct.cluster());
		System.out.println((System.currentTimeMillis() - current) + " ms");

	}
}