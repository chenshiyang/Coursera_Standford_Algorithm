package partone.week5;

import java.util.ArrayList;

/**
 * @author chenshiyang
 *
 * Aug 5, 2015
 * 11:18:25 AM
 * 
 * A self-defined heap data structure.
 * extends from built-in ArrayList.
 * Supporting:
 * extractMin, insert, minHeapify, siftUp, delete ... operations.
 */
public class Heap extends ArrayList<Vertex>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1244474725121990912L;
	
	/**
	 * get parent index
	 * @param index 
	 * @return parent index
	 */
	public int getParentIndex(int index){
		if(index == 0){
			return -1;
		}
		return (index - 1) / 2;
	}
	
	/**
	 * get left child index
	 * @param index
	 * @return left child index
	 */
	public int getLeftChildIndex(int index){
		int leftChildIndex = 2 * index + 1;
		if(leftChildIndex >= this.size()){
			return - 1;
		}
		return leftChildIndex;
	}
	
	/**get right child index
	 * @param index
	 * @return right child index
	 */
	public int getRightChildIndex(int index){
		int rightChildIndex =  2 * index + 2;
		if(rightChildIndex >= this.size()){
			return - 1;
		}
		return rightChildIndex;
	}
	
	/**
	 * Insert a vertex into the heap.
	 * Should maintain the heap properties.
	 * @param vertex vertex to be inserted
	 */
	public void insert(Vertex vertex){
		this.add(vertex);
		int currentIndex = this.size() - 1;
		vertex.vertexIndex = currentIndex;
		siftUp(currentIndex);
	}
	
	/**
	 * Extract a vertex from the heap.
	 * Should maintain the heap properties.
	 * @return the vertex extracted.
	 */
	public Vertex extractMin(){
		if(this.size() < 1)
			System.out.println("heap underflow.");
		Vertex min = this.get(0);
		swap(0, size() - 1);;//swap heap top and the last element.
		this.remove(this.size() - 1);
		//sift down
		minHeapify(0);
		return min;
	}
	
	/**
	 * Minheapify the heap from given position. i.e. sift down
	 * the given element(given by index).
	 * @param currentIndex index where the minheapify should start from.
	 */
	public void minHeapify(int currentIndex){
		int left = getLeftChildIndex(currentIndex);
		int right = getRightChildIndex(currentIndex);
		int smallest = currentIndex;
		if(left != -1 && get(currentIndex).vertexDistance > get(left).vertexDistance){
			smallest = left; 
		}
		else{
			smallest = currentIndex;
		}
		
		if(right != -1 && get(smallest).vertexDistance > get(right).vertexDistance){
			smallest = right;
		}
		if(smallest != currentIndex){
			swap(currentIndex, smallest);
			minHeapify(smallest);
		}
	}
	
	/**
	 * Sift up the given element(given by index)
	 * @param index
	 */
	public void siftUp(int index){
		int currentIndex = index;
		while(currentIndex != 0 && this.get(currentIndex).vertexDistance < this.get(this.getParentIndex(currentIndex)).vertexDistance){
			swap(currentIndex, getParentIndex(currentIndex));//sift up.
			currentIndex = getParentIndex(currentIndex);
		}
	}
	
	/**
	 * swap two element in the heap.
	 * @param index1
	 * @param index2
	 */
	public void swap(int index1, int index2){
		Vertex vertex = this.get(index1);
		vertex.vertexIndex = index2;
		
		this.set(index1, this.get(index2));
		this.get(index1).vertexIndex = index1;
		
		this.set(index2, vertex);

	}
	
	/**
	 * delete the element in the given index.
	 * this method MAY HAVE SOME PROBLEMS.
	 * @param index
	 * @return
	 */
	public Vertex delete(int index){
		Vertex vertex = this.get(index);
		this.swap(index, this.size() - 1);//swap with last element
		this.remove(this.size() - 1);//remove last
		minHeapify(index);// heapify from this position.
		return vertex;
	}
	
	public static void main(String[] args) {
		
	}
}
