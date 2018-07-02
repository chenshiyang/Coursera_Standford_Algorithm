package parttwo.week6;

import java.util.ArrayList;

public class SATInstance{
	ArrayList<Integer> variable = new ArrayList<Integer>();
	ArrayList<Integer> clause = new ArrayList<Integer>();
	
	public void parseAndAddClause(String line){
		String[] sarry = line.split(" ");
		for(int i = 0; i < sarry.length; i ++){
			int itemp = Integer.parseInt(sarry[i]);
			if(itemp < 0){
				Node node = new Node();
				variable.add(- itemp);
				clause.add(2 * (- itemp) + 1);
			}
			
		}
	}
	public String toString(){
		String s= "variable : \n";
		for(int i : variable)
			s = s + i + "\t";
		s = s + "clause :  \n";
		for(int i : clause)
			s = s + i + "\t";
		return s;
			
	}
}