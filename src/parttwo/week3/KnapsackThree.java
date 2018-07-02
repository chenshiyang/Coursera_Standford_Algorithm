package parttwo.week3;

import java.io.BufferedReader;
import java.io.FileReader;

public class KnapsackThree{
	
	private int m_itemNum;
	private int m_capacity;
	private int[] m_table;
	private int m_maxValue;
	private int[] m_itemValue;
	private int[] m_itemWeight;
	
	/**
	 * test11 : 1398904
	 * 
	 * @throws Exception
	 */
	public void getMaxValue() throws Exception{
		String fileName = "E:\\Standford Algorithms II\\week 3\\knapsack_big.txt";
		BufferedReader input = new BufferedReader(new FileReader(fileName));
		String s;
		s = input.readLine();
		m_capacity = Integer.parseInt((s.split(" "))[0]);
		m_itemNum = Integer.parseInt((s.split(" "))[1]);
		m_table = new int[m_capacity + 1];
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

		for(int i = 0; i < m_table.length; i ++)
			m_table[i] = 0;
		
		for(int i = 1; i < m_itemWeight.length; i ++){// the loop of item
			for(int j = m_capacity; j >= m_itemWeight[i]; j --){//the loop of knapsack's capacity
/*				if(i % (m_itemNum / 100) == 0 && j % m_capacity == 0)
					System.out.println(100 * i / m_itemNum + " %...");*/
				m_table[j] = Math.max(m_table[j], m_table[j - m_itemWeight[i]] + m_itemValue[i]);					
			}
		}
		m_maxValue = m_table[m_capacity];
		System.out.println(m_table[m_capacity]);		
	}
	
	public void getItemList(){
		boolean[] itemUsed = new boolean[m_itemNum + 1];
		int j = m_capacity;
		for(int i = m_itemNum; i > 0; i --){
			if(m_table[j] == m_table[j - m_itemWeight[i]] + m_itemValue[i]){
				itemUsed[i] = true;
				j -= m_itemWeight[i];
			}
			if(j == 0)
				break;
		}
		int total = 0;
		for(int i = 1; i < m_itemNum + 1; i ++){
			if(itemUsed[i])
				total += m_itemValue[i];
		}
		System.out.println("total : " + total);
	}
	
	
	public static void main(String[] args) throws Exception{
		KnapsackThree ks = new KnapsackThree();
		ks.getMaxValue();
		ks.getItemList();
		System.out.println("hello");
	}
}