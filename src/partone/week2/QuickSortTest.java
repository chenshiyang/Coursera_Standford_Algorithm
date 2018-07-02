package partone.week2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class QuickSortTest{
	public int[] array;
	public int numCompare = 0;
	public int pivotType = 0;//determine how to choose pivot
	
	/**
	 * read in the array.
	 * @throws Exception
	 */
	public void readFile() throws Exception{
		String fileName = "E:\\Stanford Algorithms\\week2\\QuickSort.txt";
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String s = null;
		int idx = 0;
		ArrayList<Integer> alist = new ArrayList<Integer>();
		while((s = reader.readLine()) != null){
			alist.add(Integer.parseInt(s));
		}
		int length = alist.size();
		array = new int[length];
		for(int num : alist){
			array[idx] = num;
			idx ++;
		}
		reader.close();
	}
	
	public void quickSort(int left, int right){
		partition(left, right);
	}
	
	public void partition(int left, int right){
		if(right - left < 1)
			return;//this array contains only or less than one element.
		int pivot = getPivot(left, right);
		int pivotIdx = left;
		
		numCompare += (right - left);
		int i = left + 1;
		for(int j = i; j <= right; j ++){
			if(array[j] < pivot){
				//swap array[i] and array[j]
				swap(i, j);
				i ++;
			}
		}
		//swap the pivot and array[i - 1], array[i - 1] is the last one that small than pivot.
		swap(pivotIdx, i - 1);
		//recurse on first half
		partition(left, i - 2);
		//recurse on second half
		partition(i, right);
	}
	
	public int getPivot(int left, int right){
		int result = 0;
		//edition 1
		if(pivotType == 0){
			result = array[left];
		}
		
		//edition 2
		else if(pivotType == 1){
			result = array[right];
			swap(left, right);
		}
		
		//edition 3
		else if(pivotType == 2){
			int middle = (left + right) / 2;
			if((array[left] < array[middle] && array[right] >= array[middle])||(array[left] > array[middle] && array[right] <= array[middle]))
				swap(left, middle);
			else if((array[left] < array[right] && array[right] <= array[middle])||(array[left] > array[right] && array[right] >= array[middle]))
				swap(left, right);
			result = array[left];
		}
		return result;
	}
	
	public void swap(int i, int j){
		if(i == j)
			return;
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public static void main(String[] args) throws Exception {
		QuickSortTest qst = new QuickSortTest();
		qst.readFile();
		qst.pivotType = 2;
		qst.quickSort(0, qst.array.length - 1);
		
		for(int i = 0; i < qst.array.length; i ++)
			System.out.println(qst.array[i]);
		System.out.println("compare time is " + qst.numCompare);
	}
}
