package parttwo.week2;

public class UnionFindSetII{
	public int number = 0;//number of node in this set.
	public Node leader = null;
	//constructor
	UnionFindSetII(Node node){
		number = 1;
		leader = node;
		node.setLeader(node);
	}
	/**
	 * given node, return the leader node's number of the set which
	 * node belongs to.
	 * you can't not use this method  for a node that doesn't belong to any UFS
	 * needs to add path compression!!!!!!!!!!!!!!!!!!!!!!
	 * 
	 * @param node
	 * @return
	 */
	public static int find(Node node){
		Node no = node;
		while(no.getLeader().getId() != no.getId()){
			no = no.getLeader();
		}
		return no.getId();
	}
	
	/**
	 * combine two unionfindset set1 and set2 into a unionfindset
	 * and return it.
	 * the set with smaller number will be installed as the child of
	 * the set with larger number.
	 * 
	 * @param set1
	 * @param set2
	 * @return
	 */
	public static UnionFindSetII union(UnionFindSetII set1, UnionFindSetII set2){
		if(set1.number >= set2.number){
			set2.leader.setLeader(set1.leader);
			set1.number += set2.number;
			return set1;
		}
		else{
			set1.leader.setLeader(set2.leader);
			set2.number += set1.number;
			return set2;
		}
	}
	
	public void addNode(Node node){
		if(this.leader == null)
			this.leader = node;
		node.setLeader(leader);//set the new node's leader
		leader.setChild(node);// to be improved
		number ++;
	}
	
	//test
	public static void main(String[] args) {
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		Node n4 = new Node(4);
		Node n5 = new Node(5);
		//first set each node to belong to a single UFS
		UnionFindSetII ufs1 = new UnionFindSetII(n1);
		UnionFindSetII ufs2 = new UnionFindSetII(n2);
		UnionFindSetII ufs3 = new UnionFindSetII(n3);
		UnionFindSetII ufs4 = new UnionFindSetII(n4);
		UnionFindSetII ufs5 = new UnionFindSetII(n5);
		System.out.println(UnionFindSetII.find(n1));
		System.out.println(UnionFindSetII.find(n2));
		System.out.println(UnionFindSetII.find(n3));
		System.out.println(UnionFindSetII.find(n4));
		UnionFindSetII.union(ufs1, ufs2);
		System.out.println(UnionFindSetII.find(n1));
		System.out.println(UnionFindSetII.find(n2));
		UnionFindSetII.union(ufs1, ufs3);
		System.out.println(UnionFindSetII.find(n2));
		System.out.println(ufs1.number);
	}
}