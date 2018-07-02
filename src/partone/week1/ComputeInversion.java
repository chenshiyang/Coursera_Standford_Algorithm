package partone.week1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ComputeInversion {
	private long count = 0;
	private int[] array;
	
	public int[] mergeSort(int[] array){
		int len = array.length;
		if(len <= 1)
			return array;
		//else , divide
		int[] left = new int[len / 2];
		int[] right = new int[len - len / 2];
		for(int i = 0 ; i < left.length ; i ++)
			left[i] = array[i];
		for(int i = 0 ; i < right.length ; i ++)
			right[i] = array[i + len / 2];
		left = mergeSort(left);
		right = mergeSort(right);
		array = merge(left, right);
		return array;
	}
	
	public int[] merge(int[] left, int[] right){
		int[] array = new int[left.length + right.length];
		int i = 0 ; int j = 0; int k = 0;
		for( ; i < array.length  && j < left.length && k < right.length; i ++){
			
			if(left[j] <= right[k] ){
				array[i] = left[j ++];
			}
			else{
				array[i] = right[k ++];
				count += left.length - j;
			}
		}
		while(j < left.length ){
			array[i ++] = left[j ++];
		}
		while(k < right.length){
			array[i ++] = right[k ++];
		}
		return array;
	}
	
	/**
	 * test1 6number  3inversion
	 * test2 5 number 4 inversion
	 * test3 5 number 10 inversion
	 * test 15 number 56 inversion
	 * 
	 * @throws IOException
	 */
	public void getFile() throws IOException{
		String file = "E:\\Stanford Algorithms\\week1\\test4.txt";
		BufferedReader reader = new BufferedReader(new FileReader(file));
		array = new int[15];
		String s = null;
		int i = 0;//index
		while((s = reader.readLine()) != null){
			array[i] = Integer.parseInt(s);
			i ++;
		}
		reader.close();		
	}
	
	public void mergeSortAlgorithm(){
		mergeSort(array);
		System.out.println(count);
	}
	
	public static void main(String[] args) throws IOException {
		ComputeInversion ci = new ComputeInversion();
		ci.getFile();
		ci.mergeSortAlgorithm();
	}
}
