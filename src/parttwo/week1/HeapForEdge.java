package parttwo.week1;

public class HeapForEdge{
	private int arrayLength;
	private int heapSize;
	private Edge[] A;
	//constructor.
	HeapForEdge(Edge[] array){
		heapSize = array.length;
		arrayLength = array.length;
		A = array;
	}
	//return A[i]'s left child index 
	private int left(Edge[] A, int i){
		if(2 * i + 1>= heapSize)
			return -1;
		else
			return 2*i + 1;
	}
	//return A[i]'s right child index
	private int right(Edge[] A, int i){
		if(2 * i + 2 >= heapSize)
			return -1;
		else
			return 2*i + 2;
	}
	//return A[i]'s parent index
	private int parent(Edge[] A, int i){
		if(i == 0)
			return -1;
		else
			return (i - 1) / 2;
	}
	
	public Edge[] getEdge(){
		return A;
	}
	//maintain the heap
	/**
	 * 
	 * check if A[i] satisfies the heap properties, if not , exchange it with the larger value of its
	 * child, then, continue to update the child which has been exchanged on.
	 * 
	 * @param A 
	 * @param i current root index to be checked
	 * @return
	 */
	public Edge[] minHeapify(Edge[] A, int i){
		int l = left(A,i);
		int r = right(A,i);
		int smallest;
		if(l != -1 && A[l].cost > A[i].cost)
			smallest = l;
		else
			smallest = i;
		if(r != -1 && A[r].cost > A[smallest].cost)
			smallest = r;
		if(smallest != i){
			Edge temp = A[smallest];
			A[smallest] = A[i];
			A[i] = temp;
			A = minHeapify(A, smallest);
		}
		return A;
	}
	
	/**
	 * 
	 * use array A to build a min heap.
	 * @param A the array to be built on.
	 * @return
	 */
	public Edge[] buildMinHeap(Edge[] A){
		heapSize = A.length;
		if(A.length == 1)
			return A;
		for(int i = heapSize / 2 - 1; i >=0 ; i --){
			A = minHeapify(A, i);
//			System.out.print("for the " + i + "   th element    ");
//			outputHeap(A);
		}
		return A;
	}
	//output the heap.
	public void outputHeap(Edge[] A){
		for(int i = 0; i< A.length; i ++)
			System.out.print(A[i].cost + "\t");
		System.out.println();
	}
	
	public Edge[] heapSort(Edge[] A){
		A = buildMinHeap(A);
		for(int i = heapSize - 1; i > 0 ; i --){
			//exchange A[i] and A[0]
			Edge temp = A[i];
			A[i] = A[0];
			A[0] = temp;
			heapSize --;
			minHeapify(A, 0);
		}
		return A;
	}
	
	public Edge heapExtractMin(){
		if(heapSize < 1)
			return null;
		Edge res = A[0];
		A[0] = A[heapSize - 1];
		heapSize --;
		minHeapify(A, 0);
		return res;
	}
}