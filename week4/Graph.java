package partone.week4;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph{
	/**
	 * vertices in this graph.
	 */
	public ArrayList<Vertex> m_vertices = new ArrayList<Vertex>();
	
	/**
	 * value of each vertex in this graph
	 */
	public int[] m_fvalue;
	
	/**
	 * leader of each vertex.
	 */
	public int[] m_leader;
	
	/**
	 * indicates whether each vertex has been explored or not.
	 */
	public boolean[] m_explored;
	
	/**
	 *  a map of each vertex from name to index.
	 */
	public HashMap<Integer, Integer> m_numberToIndex = new HashMap<Integer, Integer>();
	
	public ArrayList<Vertex> getM_vertices() {
		return m_vertices;
	}

	public void setM_vertices(ArrayList<Vertex> m_vertices) {
		this.m_vertices = m_vertices;
	}

	public int[] getM_fvalue() {
		return m_fvalue;
	}

	public void setM_value(int[] m_value) {
		this.m_fvalue = m_value;
	}

	public boolean[] getM_explored() {
		return m_explored;
	}

	public void setM_explored(boolean[] m_explored) {
		this.m_explored = m_explored;
	}

	public HashMap<Integer, Integer> getM_numberToIndex() {
		return m_numberToIndex;
	}

	public void setM_numberToIndex(HashMap<Integer, Integer> m_numberToIndex) {
		this.m_numberToIndex = m_numberToIndex;
	}


	
}