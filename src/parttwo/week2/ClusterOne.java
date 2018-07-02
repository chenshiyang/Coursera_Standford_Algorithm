package parttwo.week2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import parttwo.week1.Edge;

public class ClusterOne{
	public static int edgeNum;
	public static int nodeNum;
	public static int K = 4;//the number of clusters
	public int[] ufs;
	public Edge[] edge;
	
	public Edge[] readFile() throws IOException{
		String file = "E:\\Standford Algorithms II\\week 2\\clustering1.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		String s = in.readLine();
		nodeNum = Integer.parseInt(s);
		edgeNum = nodeNum*(nodeNum - 1) / 2;
		int i = 0;
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
	
	public int cluster() throws IOException{
		edge = this.readFile();
		edge = Sort.mergeSortEdge(edge);//sort the edge
/*		for(int i = 0; i < edge.length; i ++){
			System.out.print(edge[i].cost + "\t");
			System.out.println();
		}*/
		ufs = UnionFindSet.makeUnionFindSet(ufs, nodeNum);//initiate the node ufs
		int count = nodeNum;//count the number of clusters, initiate as number of nodes.
		int maxSpacing = 0;// the max spacing 
		int i = 0;// the for loop counter
		for( ; i < edge.length; i ++){
			int left = edge[i].left - 1;//edge's left node
			int right = edge[i].right - 1;//edge's right node
			int x = UnionFindSet.find(left, ufs);//left's leader
			int y = UnionFindSet.find(right, ufs);//right's leader
			if(x == y)
				continue;
			else{
				UnionFindSet.union(ufs, x, y);
				count --;//the number of cluster decreases by 1 since we merge two set together.
			}
/*			for(int j = 0; j < ufs.length; j ++){
				System.out.print(ufs[j] + "  ");
			}
			System.out.println();*/
			if(count == K)
				break;
		}
		// from the last for loop, we get 4 clusters.
		for( i += 1; i < edge.length ; i ++){//i +=1, means that start from the next edge.
			int left = edge[i].left - 1;//edge's left node
			int right = edge[i].right - 1;//edge's right node
			int x = UnionFindSet.find(left, ufs);
			int y = UnionFindSet.find(right, ufs);
			if(x == y)
				continue;
			else{
				maxSpacing = edge[i].cost;
				break;
			}
		}
		return maxSpacing;
	}
	
	//test case
	/**test 1 :
	 * for 2 clusters: 6
	 * for 3 clusters: 5
	 * for 4 clusters: 2
	 * test 2 :
	 * For 2 clusters: 4472
	 * For 3 clusters: 3606
	 * For 4 clusters: 1414
	 * clustering1:
	 * For 4 clusters£º106
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		long current = System.currentTimeMillis();
		ClusterOne cc = new ClusterOne();
		int res = cc.cluster();
		System.out.println(res);
		System.out.println((System.currentTimeMillis() - (double)current)/ 1000);
	}
}