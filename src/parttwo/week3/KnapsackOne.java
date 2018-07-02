
package parttwo.week3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class KnapsackOne{
	private int m_itemNum;
	private int m_capacity;
	private int[][] m_table;
	private int m_maxValue;
	private int[] m_itemValue;
	private int[] m_itemWeight;
	
	/**
	 * test11 : 1398904
	 * 
	 * @throws Exception
	 */
	public void getMaxValue() throws Exception{
		String fileName = "E:\\Standford Algorithms II\\week 3\\knapsack1.txt";
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		String s;
		s = input.readLine();
		m_capacity = Integer.parseInt((s.split(" "))[0]);
		m_itemNum = Integer.parseInt((s.split(" "))[1]);
		m_table = new int[m_itemNum + 1][m_capacity + 1];
		m_itemValue = new int[m_itemNum + 1];
		m_itemWeight = new int[m_itemNum + 1];
		m_maxValue = 0;

		int count = 1;
		while((s = input.readLine()) != null){
			String[] stemp = s.split(" ");
			m_itemValue[count] = Integer.parseInt(stemp[0]);
			m_itemWeight[count] = Integer.parseInt(stemp[1]);
			count ++;
		}
		input.close();
		
		for(int i = 0; i < m_table[0].length; i ++)
			m_table[0][i] = 0;
		
		for(int i = 1; i < m_table.length; i ++){// the loop of item
			for(int j = 0; j < m_table[0].length; j ++){//the loop of knapsack's capacity
				if(i % (m_itemNum / 100) == 0 && j % m_capacity == 0)
					System.out.println(100 * i / m_itemNum + " %...");
				if(m_itemWeight[i] > j){//then we can't want item[i]
					m_table[i][j] = m_table[i - 1][j];
					continue;
				}
				//else
				m_table[i][j] = Math.max(m_table[i - 1][j], m_table[i - 1][j - m_itemWeight[i]] + m_itemValue[i]);
					
			}
		}
		m_maxValue = m_table[m_itemNum][m_capacity];
		System.out.println(m_table[m_itemNum][m_capacity]);
		
		
	}
	
	public void getItemList(){
		boolean[] itemUsed = new boolean[m_itemNum + 1];
		for(int i = m_itemNum,  j = m_capacity; i > 0 ; i --){
			if(m_table[i][j] == m_table[i - 1][j])
				;
			else{
				itemUsed[i] = true;
				j -= m_itemWeight[i];
			}
				
		}
		int totalWeight = 0;
		for(int i = 1; i < itemUsed.length; i ++){
			if(itemUsed[i]){
				totalWeight += m_itemWeight[i];
				System.out.print(m_itemWeight[i]);
				if(i != m_itemNum)
					System.out.print(" + ");
			}
		}
		System.out.println(" = " + totalWeight);
	}
	
	public static void main(String[] args) throws Exception{
		KnapsackOne ks = new KnapsackOne();
		ks.getMaxValue();
		ks.getItemList();
	}
}