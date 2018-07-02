/**
* <p>Title: MedianMaintenanceAlgorithm.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>Company: CSY</p>
* @author chenshiyang
* @date Aug 10, 2015
* @version jdk8.0
*/
package partone.week6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * <p>Title: MedianMaintenanceAlgorithm</p>
 * <p>Description: </p>
 * @author chenshiyang
 * @date Aug 10, 2015
 * @time 11:10:00 PM
 */
public class MedianMaintenanceAlgorithm {
	/**
	 * A array storing the given integers.
	 */
	private int[] m_array;
	
	/**
	 * A max-heap to store the smaller half of the array.
	 */
	private PriorityQueue<Integer> m_lowHeap;
	
	/**
	 * A min-heap to store the larger half of the array.
	 */
	private PriorityQueue<Integer> m_highHeap;
	
	/**
	 * A array to  store the medians.</p>
	 * The ith entry of this array indicates the median of the first i elements in the array.
	 */
	private int[] m_medians;
	
	/**
	 * The sum of the medians moded by the length of the array.
	 */
	private int m_medianMod;
	
	
	/**
	* <p>Description: Read in integer data from given file</p>
	* <p>The integer data is stored in an array.<p>
	* @param filename
	* @throws Exception
	*/
	public void getData(String filename) throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String lineStr = "";
		int index = 0;
		ArrayList<Integer> data = new ArrayList<Integer>();
		while((lineStr = reader.readLine()) != null){
			data.add(Integer.parseInt(lineStr));
		}
		reader.close();
		m_array = new int[data.size()];
		Iterator<Integer> iterator = data.iterator();
		while(iterator.hasNext()){
			m_array[index ++] = iterator.next();
		}
	}
	
	/**
	* <p>Description: Chcek the content of the array.</p>
	*/
	public void displayArray(){
		System.out.println("===========================================");
		System.out.println("Array length is " + m_array.length);
		System.out.println("The first five elements is as follows :");
		for(int i = 0; i < 5; i ++){
			System.out.println(m_array[i]);
		}
		System.out.println("===========================================");
	}
	
	/**
	* <p>Description: The main procedure of the median maintenance algorithm</p>
	* <p> Compute the median of first i elements in the array, i range from 1 to array length.</p>
	* <p> The medians is stored in an array:m_medians. 
	*/
	public void medianMaintenance(){
		//initialize heap and median array
		m_lowHeap = new PriorityQueue<Integer>();
		m_highHeap = new PriorityQueue<Integer>();
		m_medians = new int[m_array.length];
		
		for(int i = 0; i < m_array.length; i ++){
			if(m_lowHeap.isEmpty() || m_array[i] <= -m_lowHeap.peek()){
				m_lowHeap.add(- m_array[i]);
				//adjust heap 
				if(m_lowHeap.size() - m_highHeap.size() >= 2){
					int max = - m_lowHeap.remove();
					m_highHeap.add(max);
				}
			}
			else if(m_highHeap.isEmpty() || m_array[i] > m_lowHeap.peek()){
				m_highHeap.add(m_array[i]);
				//adjust heap
				if(m_highHeap.size() - m_lowHeap.size() >= 2){
					int min = m_highHeap.remove();
					m_lowHeap.add(-min);
				}
			}
			
			//get median
			int median;
			if(m_lowHeap.size() == m_highHeap.size())
				median = - m_lowHeap.peek();//caution for minus.
			else if(m_lowHeap.size() > m_highHeap.size())
				median = - m_lowHeap.peek();
			else
				median = m_highHeap.peek();
			m_medians[i] = median;
		}
		
	}
	
	/**
	* <p>Description: Get the value of medians sum moded by array.length</p>
	* @return
	*/
	public int getMedianMean(){
		int sum = 0;
		for(int i = 0; i < m_medians.length; i ++){
			sum += m_medians[i];
		}
		m_medianMod = sum % m_array.length;
		return m_medianMod;
	}
	
	/**
	 * <p>Description: </p>
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		MedianMaintenanceAlgorithm mma = new MedianMaintenanceAlgorithm();
		String filename = "E:\\Stanford Algorithms\\week6\\Median.txt";
		mma.getData(filename);
//		mma.displayArray();//pass
		mma.medianMaintenance();
		System.out.println("median mod is " + mma.getMedianMean());
		for(int i = 0; i < mma.m_medians.length; i ++){
			System.out.println(mma.m_medians[i]);
		}
		
	}

}
