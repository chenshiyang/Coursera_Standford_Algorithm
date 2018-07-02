package parttwo.week1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SumOfCompletionTimeII{
	public static int num ;
	
	/**
	 * 
	 * read data from file and save them into array jobs;
	 * test1:Ratio: 31814, difference: 31814
	 * test2:Ratio: 60213, difference: 61545
	 * test3:Ratio: 674634, difference: 688647
	 * @throws IOException
	 */
	public Job[] readFile() throws IOException{
		String file = "E:\\Standford Algorithms II\\week 1\\jobs.txt";
		BufferedReader in = new BufferedReader(new FileReader(file));
		num = Integer.parseInt(in.readLine());
		String s;
		int i = 0;
		Job[] jobs = new Job[num];
		while((s = in.readLine()) != null){
			String[] intString = s.split(" ");
			jobs[i] = new Job();
			jobs[i].id = i + 1;
			jobs[i].length = Integer.parseInt(intString[1]);
			jobs[i].weight = Integer.parseInt(intString[0]);
			jobs[i].ratio = (float)jobs[i].weight / jobs[i].length;
			i ++;
		}
		in.close();
		return jobs;
	}
	
	public Job[] mergeSort(Job[] jobs){
		int len = jobs.length;
		if (jobs.length <= 1)
			return jobs;
		//divide
		Job[] left = new Job[len / 2];
		Job[] right = new Job[len - len / 2];
		for(int i = 0 ; i < left.length; i ++)
			left[i] = jobs[i];
		for(int i = 0 ; i < right.length ; i ++)
			right[i] = jobs[i + len / 2];
		left = mergeSort(left);
		right = mergeSort(right);
		//merge
		jobs = merge(left, right);
		return jobs;
	}
	
	public Job[] merge(Job[] left, Job[] right){
		Job[] res = new Job[left.length + right.length];
		int j = 0 ;int k = 0; int i = 0;
		for( ; i < res.length  && j < left.length && k < right.length; i ++){
			
			if(left[j].ratio >= right[k].ratio ){
				res[i] = left[j ++];
			}
			else
				res[i] = right[k ++];
		}
		while(j < left.length ){
			res[i ++] = left[j ++];
		}
		while(k < right.length){
			res[i ++] = right[k ++];
		}
		return res;
	}
	
	public static void main(String[] args) throws IOException{
		long currentTime = System.currentTimeMillis();
		SumOfCompletionTimeII so = new SumOfCompletionTimeII();
		//read in data
		Job[] jobs = so.readFile();
		//output the array
		for(int i = 0 ; i < jobs.length ; i ++){
			System.out.print(jobs[i].weight + "\t" + jobs[i].length + "\t" + jobs[i].ratio + "\n");
		}
		System.out.println("*******************");
		//sort the jobs
		jobs = so.mergeSort(jobs);
		//output the array
		for(int i = 0 ; i < jobs.length ; i ++){
			System.out.print(jobs[i].weight + "\t" + jobs[i].length + "\t" + jobs[i].ratio + "\n");
		}
		long sum = 0;
		int current = 0;
		for(int i = 0 ; i < num; i ++){
			sum += (current + jobs[i].length) * jobs[i].weight;
			current += jobs[i].length;
		}
		System.out.println(sum);
		System.out.println(System.currentTimeMillis() - currentTime);
		
	}
}