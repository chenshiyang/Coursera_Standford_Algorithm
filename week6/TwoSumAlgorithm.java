/**
* <p>Title: TwoSumAlgorithm.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>Company: CSY</p>
* @author chenshiyang
* @date Aug 10, 2015
* @version jdk8.0
*/
package partone.week6;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>Title: TwoSumAlgorithm</p>
 * <p>Description: </p>
 * @author chenshiyang
 * @date Aug 10, 2015
 * @time 4:39:16 PM
 */
public class TwoSumAlgorithm {
	/**
	 * store the integer array, integer can be negective.
	 */
	private long[] m_larray;
		
	/**
	 * used to store the integer.
	 */
	private Map<Long, Integer> m_table;
	
	/**
	 * the number of target value t in the given interval[low, high](inclusive) such that there are distinct</p>
	 * numbers x, y in the array that satisfy x + y = t.
	 */
	private int m_targetValueCount = 0;
	
	/**
	 * <p>Description: Read in data from given file name,</p>
	 * <p>and store in array: m_larray
	 * 
	 * @throws Exception 
	 */
	public void buildArray(String filename) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String lineStr = "";
		int index = 0;//index of the array
		ArrayList<Long> data = new ArrayList<Long>();
		while((lineStr = reader.readLine()) != null){
			data.add(Long.parseLong(lineStr));
		}
		//initialize  m_larray
		m_larray = new long[data.size()];
		Iterator<Long> iterator = data.iterator();
		while(iterator.hasNext()){
			m_larray[index ++] = iterator.next();
		}
	}
	
	/**
	* <p>Description: Show the data.</p>
	*/
	public void displayData(){
		System.out.println("========================================");
		System.out.println("Array length is " + m_larray.length);
		System.out.println("The first ten elements is as follows:");
		for(int i = 0; i < 10; i ++){
			System.out.println(m_larray[i]);
		}
		System.out.println("========================================");
	}
	
	/**
	* <p>Description: Compute the number of target values t in the interval [low,high] (inclusive) such that there are distinct numbers x,y in the input file that satisfy x+y=t</p>
	* @param low the low bound
	* @param high the high bound
	* @throws Exception
	*/
	public void computeTargetCount(int low, int high) throws Exception{
		//build hash map
		buildTable();
		PrintWriter writer = new PrintWriter("E:\\Stanford Algorithms\\week6\\outcome.txt");
		for(int i = low; i <= high; i ++){
			//look up the talbe for each entry of the array
			for(int j = 0; j < m_larray.length; j ++){
				long key = i - m_larray[j];
				if(m_table.get(key) != null && (key != m_larray[j] || m_table.get(key) >= 2)){
					m_targetValueCount ++;
//					System.out.println(i + " = " + m_larray[j] + " + " + key);
					writer.write(i + " = " + m_larray[j] + " + " + key + "\n");
					break;
				}
			}
		}
		writer.flush();
		writer.close();
	}
	
	/**
	* <p>Description: Build the hash table. </p>
	* <p>Use array[index] as key,</p>
	* <p>1 as the value.</p>
	* <p>In fact, it does no matter what the value is, a HashMap can achieve this.
	* <p>If there is a key-value pair that key == value, then returns true directly. 
	* </p>Else, return false at last, indicating</p>
	* that there no two distinct number with the same value that satisfy the </p>
	* two sum property.</p>
	*/
	public void buildTable(){
		m_table = new HashMap<Long, Integer>();
		for(int i = 0; i < m_larray.length; i ++){
			if(m_table.containsKey(m_larray[i])){
				int value = m_table.get(m_larray[i]);
				m_table.put(m_larray[i], value + 1);
			}
			else
				m_table.put(m_larray[i], 1);
		}
	}
	
	/**
	 * <p>Description: </p>
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		long current = System.currentTimeMillis();
		TwoSumAlgorithm tsa = new TwoSumAlgorithm();
		String filename = "E:\\Stanford Algorithms\\week6\\algo1-programming_prob-2sum.txt";
//		String filename = "E:\\Stanford Algorithms\\week6\\twosumtest2.txt";
		tsa.buildArray(filename);
//		tsa.displayData();// pass.
		int lowbound = -10000;
		int upbound = 10000;
		tsa.computeTargetCount(lowbound, upbound);
		System.out.println("target number is " + tsa.m_targetValueCount);
		System.out.println("time used is " + (System.currentTimeMillis() - current) / 1000 + "s");
	}

}
