package parttwo.week2;

/**
 * This Node class is designed for the UnionFindSet data strucrure.
 * 
 * @author chenshiyang
 *
 */
public class Node{
	private int id;
	private Node leader;
	private Node child;
	
	Node(){
		
	}
	
	Node(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Node getLeader() {
		return leader;
	}
	public void setLeader(Node leader) {
		this.leader = leader;
	}
	public Node getChild() {
		return child;
	}
	public void setChild(Node child) {
		this.child = child;
	}

}