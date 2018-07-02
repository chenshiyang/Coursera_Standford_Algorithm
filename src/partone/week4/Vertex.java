package partone.week4;

import java.util.HashSet;

public class Vertex {
	private int vertexNumber;
	private HashSet<Integer> adjacentVertice = new HashSet<Integer>();
	
	public Vertex(){
		
	}

	public Vertex(int vertexNumber){
		this.vertexNumber = vertexNumber;
	}
	
	public HashSet<Integer> getAdjacentVertice() {
		return adjacentVertice;
	}

	public void setAdjacentVertice(HashSet<Integer> adjacentVertice) {
		this.adjacentVertice = adjacentVertice;
	}

	public int getVertexNumber() {
		return vertexNumber;
	}

	public void setVertexNumber(int vertexNumber) {
		this.vertexNumber = vertexNumber;
	}
	
	
}
