package partone.week5;

import java.util.ArrayList;

/**
 * @author chenshiyang
 *
 * Aug 5, 2015
 * 11:16:35 AM
 * 
 * the Vertex class.
 * 
 */
public class Vertex {
	/**
	 * vertex id.
	 */
	public int vertexId;
	
	/**
	 * vertex distance to the source vertex, dafalut as 1000000
	 */
	public int vertexDistance;
	
	/**
	 * this vertex's adjacent vertices
	 */
	public ArrayList<Integer> adjacentVertices;
	
	/**
	 * this vertex's index in the heap.
	 * May be SHOULD NOT keep here.
	 */
	public int vertexIndex;//index in the heap...
	
	public Vertex(){
		
	}
	
	public Vertex(int vertexId){
		this.vertexId = vertexId;
		this.vertexDistance = 1000000;//dafault distance
		this.adjacentVertices = new ArrayList<Integer>();
	}
	
	public int getVertexId() {
		return vertexId;
	}
	public void setVertexId(int vertexId) {
		this.vertexId = vertexId;
	}
	public int getVertexDistance() {
		return vertexDistance;
	}
	public void setVertexDistance(int vertexDistance) {
		this.vertexDistance = vertexDistance;
	}
	public ArrayList<Integer> getAdjacentVertices() {
		return adjacentVertices;
	}
	public void setAdjacentVertices(ArrayList<Integer> adjacentVertices) {
		this.adjacentVertices = adjacentVertices;
	}

	public int getVertexIndex() {
		return vertexIndex;
	}

	public void setVertexIndex(int vertexIndex) {
		this.vertexIndex = vertexIndex;
	}

	@Override
	public String toString() {
		return "vertex id " + this.vertexId + ", vertex distance " + this.getVertexDistance() + ", vertex index " + this.vertexIndex + "\n";
	}
	
	
}
