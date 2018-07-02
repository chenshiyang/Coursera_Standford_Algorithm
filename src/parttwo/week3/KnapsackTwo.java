package parttwo.week3;

import java.io.BufferedReader;
import java.io.FileReader;

import parttwo.week1.Edge;


public class KnapsackTwo{
	private int m_maxValue = 0;
	private Item[] m_item;
	private int m_capacity;
	private int m_itemNum;
	private int m_currentCapacity;
	
	public void getMaxValue() throws Exception{
		String fileName = "E:\\Standford Algorithms II\\week 3\\test11.txt";
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s;
		s = in.readLine();
		m_capacity = Integer.parseInt((s.split(" "))[0]);
		m_currentCapacity = m_capacity;
		m_itemNum = Integer.parseInt((s.split(" "))[1]);
		m_item = new Item[m_itemNum + 1];
		m_maxValue = 0;
		
		int count = 1;
		while((s = in.readLine()) != null){
			String[] stemp = s.split(" ");
			m_item[count] = new Item();
			m_item[count].itemValue = Integer.parseInt(stemp[0]);
			m_item[count].itemWeight = Integer.parseInt(stemp[1]);
			m_item[count].itemId = count;
			m_item[count].itemVPW = m_item[count].itemValue / m_item[count].itemWeight;
			count ++;
		}
		in.close();
		
		Item[] items = new Item[m_itemNum];
		for(int i = 0; i < items.length; i ++)
			items[i] = m_item[i + 1];
		items = mergeSort(items);
		for(int i = 0; i < items.length; i ++)
			m_item[i + 1] = items[i];
		
		m_maxValue = maxValue(m_itemNum, m_capacity);
		System.out.println(m_maxValue);
	}
	
	public int maxValue(int currentItem, int currentCapacity){
		if(currentCapacity < m_item[currentItem].itemWeight){
			if(currentItem == 1)
				return 0;
			return maxValue(currentItem - 1, currentCapacity);
		}
		//else capacity > itemWeight
		int max;
		if(currentItem == 1){
				max = m_item[currentItem].itemValue;
				currentCapacity -= m_item[currentItem].itemWeight;
		}
		else{//currentItem > 1
			int temp1 = maxValue(currentItem - 1, currentCapacity);//don't use currentItem
			int temp2 = maxValue(currentItem - 1, currentCapacity - m_item[currentItem].itemWeight) + m_item[currentItem].itemValue;//use current item
			max = Math.max(temp1, temp2);
		}
		return max;
	}
	
	public Item[] mergeSort(Item[] item){
		int len = item.length;
		if(len <= 1)
			return item;
		//else , divide
		Item[] left = new Item[len / 2];
		Item[] right = new Item[len - len / 2];
		for(int i = 0 ; i < left.length ; i ++)
			left[i] = item[i];
		for(int i = 0 ; i < right.length ; i ++)
			right[i] = item[i + len / 2];
		left = mergeSort(left);
		right = mergeSort(right);
		item = merge(left, right);
		return item;
	}
	
	public Item[] merge(Item[] left, Item[] right){
		Item[] item = new Item[left.length + right.length];
		int i = 0 ; int j = 0; int k = 0;
		for( ; i < item.length  && j < left.length && k < right.length; i ++){
			
			if(left[j].itemWeight <= right[k].itemWeight ){
				item[i] = left[j ++];
			}
			else
				item[i] = right[k ++];
		}
		while(j < left.length ){
			item[i ++] = left[j ++];
		}
		while(k < right.length){
			item[i ++] = right[k ++];
		}
		return item;
	}
	
	public static void main(String[] args) throws Exception {
		KnapsackTwo kst = new KnapsackTwo();
		kst.getMaxValue();
	}
	
}