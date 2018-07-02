package partone.week3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Vertice{
	private HashSet<Integer> m_verticeInx;
	private ArrayList<Integer> m_adjacentNodes;
	
	public Vertice(){
		
	}
	
	public Vertice(Vertice vertix){
		this.m_verticeInx = new HashSet<Integer>();
		Iterator<Integer> iterator = vertix.m_verticeInx.iterator();
		while(iterator.hasNext()){
			m_verticeInx.add(iterator.next());
		}
		this.m_adjacentNodes = new ArrayList<Integer>();
		iterator = vertix.m_adjacentNodes.iterator();
		while(iterator.hasNext()){
			this.m_adjacentNodes.add(iterator.next());
		}
	}
	
	public HashSet<Integer> getVerticeInx() {
		return m_verticeInx;
	}
	public void setVerticeInx(HashSet<Integer> verticeInx) {
		this.m_verticeInx = verticeInx;
	}
	public ArrayList<Integer> getAdjacentNodes() {
		return m_adjacentNodes;
	}
	public void setAdjacentNodes(ArrayList<Integer> adjacentNodes) {
		this.m_adjacentNodes = adjacentNodes;
	}
}