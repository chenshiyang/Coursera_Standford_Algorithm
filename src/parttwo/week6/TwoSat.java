package parttwo.week6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**使用SCC(Strong connected components)的话，需要数据结构：
 * 1. graph{
 * 		int id
 * 		List<Node> node
 * }//用来表示强连通分支
 * 2. Node{
 * 		int id
 *      int fu//finish time
 *      int du//discover time
 *      int color//color
 *      List<Node> neighbor//以当前结点为起点能到达的结点
 * }
 *3. Edge{
 *		Node left
 *		Node right
 *}
 *4. Edge{
 *		Graph left
 *		Graph right
 *}//缩图后由强连通分支形成的图
 *
 *
 *使用backtracking 的方法
 * 
 * 
 * 
 * @author chenshiyang
 *
 */
public class TwoSat{
	public int nodeNum;
	public int count = 0;
	public int clauseCount = 0;
	public Node[] nodes;
	public SATInstance[] sats;
	public HashMap<Integer, Integer> nodeMap = new HashMap<Integer, Integer>();
	public ArrayList<List<Integer>> watchList = new  ArrayList<List<Integer>>();
	public int[] assignment;
	
	public void getData() throws Exception{
		String fileName = "E:\\Standford Algorithms II\\week 6\\test3.txt";
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		nodeNum = Integer.parseInt(in.readLine());
		nodes = new Node[nodeNum];
		sats = new SATInstance[nodeNum];
		assignment = new int[nodeNum];
		for(int i = 0; i < nodeNum; i ++)
			assignment[i] = -1;//initialize as -1, meaning unassigned
		String line;
		while((line = in.readLine())!= null){
			parseAndAddClause(line);
		}
		in.close();
	}
	
	public void parseAndAddClause(String line){
		String[] sarry = line.split(" ");
		SATInstance sat = new SATInstance();
		sats[clauseCount] = sat;
		clauseCount ++;
		for(int i = 0; i < sarry.length; i ++){
			int itemp = Integer.parseInt(sarry[i]);
//			boolean isNew = false;
			Integer index;
			if((index = nodeMap.get(Math.abs(itemp))) == null){
				nodeMap.put(Math.abs(itemp), count);
				index = count;
				count ++;
//				isNew = true;
			}
			if(itemp < 0){
//				Node node = new Node(-itemp);
				
				sat.variable.add(index);
				sat.clause.add(2 * index + 1);
			}
			else{
				sat.variable.add(index);
				sat.clause.add(2 * index);
			}
			
		}
	}
	
	
	public void setUpWatchList(){
		for(int i = 0; i < 2 * nodeNum; i ++){
			ArrayList list = new ArrayList<Integer>();
			watchList.add(list);
		}
		for(int i = 0; i < sats.length; i ++){
			int clause0 = sats[i].clause.get(0);
			watchList.get(clause0).add(i);
		}
	}
	
	/**
	 * @param falseLiteral
	 * @return
	 */
	public boolean updateWatchList(int falseLiteral){
		while(watchList.get(falseLiteral).size() != 0){
			int clause = watchList.get(falseLiteral).get(0);//clause is sat's index in the sats array
			boolean foundAlternative = false;
			for(int alternative : sats[clause].clause){
				int absvalue = alternative >> 1;//get this literal 's abs valuse
				int sign = alternative & 1;//get this literal's sign
				if(assignment[absvalue] == -1 || (assignment[absvalue] == 1 - sign )){//if unassigned or 
					foundAlternative = true;
					watchList.get(falseLiteral).remove(0);//remove it.
					watchList.get(alternative).add(clause);
					break;
				}
			}
			
			if( ! foundAlternative){
				return false;
			}
		}//while loop for all sats who is watching at literal
		return true;
	}
	
	public boolean solve(int variableIndex){
		if(variableIndex == nodeNum)
			return true;
		//assign 0
		int a = 0;
		assignment[variableIndex] = a;
		boolean res = false;
		if(updateWatchList(variableIndex << 1 | a)){
			res = solve(variableIndex + 1);
		}
		if(res)
			return true;
		//assign 1
		a = 1;
		assignment[variableIndex] = a;
		if(updateWatchList(variableIndex << 1 | a)){
			res = solve(variableIndex + 1);
		}
		if(res)
			return res;
		assignment[variableIndex] = -1;
		return false;
	}
	
	public void twoSat(){
		boolean res = solve(0);
		System.out.println(res);
	}
	
	public static void main(String[] args) throws Exception{
		TwoSat ts  = new TwoSat();
		ts.getData();
/*		for(SATInstance sat : ts.sats)
			System.out.println(sat);*/
		ts.setUpWatchList();
		System.out.println("start");
//		System.out.println(ts.watchList.size());
//		System.out.println(ts.watchList.get(1).size());
		ts.twoSat();
		for(int i = 0; i < ts.nodeNum; i ++)
			System.out.print(ts.assignment[i] + "\t");
	}
	
}