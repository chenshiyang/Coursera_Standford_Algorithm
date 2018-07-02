/**
* <p>Title: BST.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>Company: CSY</p>
* @author chenshiyang
* @date Aug 6, 2015
* @version jdk8.0
*/
package partone.week5;

import java.util.Queue;

/**
 * <p>Title: BST</p>
 * <p>Description: </p>
 * @author chenshiyang
 * @date Aug 6, 2015
 * @time 9:36:13 AM
 */
public class BST<Key extends Comparable<Key>, Value> {
	private Node root;
	private class Node{
		private Key key;
		private Value value;
		private Node left, right;
		private int N;
		
		public Node(Key key, Value value, int N){
			this.key = key;
			this.value = value;
			this.N = N;
		}
	}
	
	/**
	* <p>Description: return the size of this tree</p>
	* @return
	*/
	public int size(){
		return size(root);
	}
	
	/**
	* <p>Description: return the size of subtree rooted at node x.</p>
	* @param x
	* @return
	*/
	private int size(Node x){
		if(x == null) return 0;
		else return x.N;
	}
	
	/**
	* <p>Description: return the value of node in this tree given node key.
	* 	</p>if the tree is null, return null.</p>
	* @param key
	* @return
	*/
	public Value get(Key key){
		return get(root, key);
	}
	
	/**
	* <p>Description: Return value associated with key in the subtree rooted at x;
	* 	</p>
	* return null if key not present in subtree  rooted at x.
	* @param x
	* @param key
	* @return
	*/
	private Value get(Node x, Key key){
		//Return value associated with key in the subtree rooted at x;
		//return null if key not present in subtree  rooted at x.
		if(x == null) return null;
		int cmp = key.compareTo(x.key);
		if(cmp < 0) return get(x.left, key);
		else if(cmp > 0) return get(x.right, key);
		else return x.value;			
	}
	
	/**
	* <p>Description: Search for key. Update value if found; grow table if new.</p>
	* @param key
	* @param value
	*/
	public void put(Key key, Value value){
		//Search for key. Update value if found; grow table if new.
		root = put(root, key, value);
	}
	
	/**
	* <p>Description: Change key's value to value if key in subtree rooted at x.</p>
	* Otherwise, add new node to subtree associating key with value.
	* @param x
	* @param key
	* @param value
	* @return the new root
	*/
	private Node put(Node x, Key key, Value value){
		//Change key's value to value if key in subtree rooted at x.
		//otherwise, add new node to subtree associating key with value.
		if(x == null) return new Node(key, value, 1);
		int cmp = key.compareTo(x.key);
		if(cmp < 0) x.left = put(x.left, key, value);
		else if (cmp > 0) x.right = put(x.right, key, value);
		else x.value = value;
		x.N = x.left.N + x.right.N + 1;
		return x;
	}
	
	/**
	* <p>Description: Return the minimum key in this tree</p>
	* @return
	*/
	public Key min(){
		return min(root).key;
	}
	
	/**
	* <p>Description: Return the node with the minimum key in the subtree rooted at node x.</p>
	* @param x
	* @return
	*/
	private Node min(Node x){
		if(x.left == null) return x;
		return min(x.left);
	}
	
	/**
	* <p>Description: Return the maximum key in this tree</p>
	* @return
	*/
	public Key max(){
		return max(root).key;
	}
	
	/**
	* <p>Description: Return the node with the maximum key in the subtree rooted at node x.</p>
	* @param x
	* @return
	*/
	private Node max(Node x){
		if(x.right == null) return x;
		return max(x.right);
	}
	
	/**
	* <p>Description: return the largest key that is smaller than the key at the root of a BST</p>
	* @param key
	* @return
	*/
	public Key floor(Key key){
		Node x = floor(root, key);
		if(x == null) return null;
		return x.key;
	}
	
	private Node floor(Node x, Key key){
		if(x == null) return null;
		int cmp = key.compareTo(x.key);
		if(cmp == 0) return x;
		else if(cmp < 0) return floor(x.left, key);
		Node t = floor(x.right, key);
		if(t != null) return t;
		else return x;
	}
	
	public Key ceil(Key key){
		Node x = ceil(root, key);
		if(x == null) return null;
		return x.key;
	}
	
	private Node ceil(Node x, Key key){
		if(x == null) return null;
		int cmp = key.compareTo(x.key);
		if(cmp == 0) return x;
		else if(cmp > 0) return ceil(x.right, key);
		Node t = floor(x.left, key);
		if(t != null) return t;
		else return x;
	}
	
	/**
	* <p>Description: Return the key of node whose rank is equal to k.</p>
	* @param k
	* @return
	*/
	public Key select(int k){
		return select(root, k).key;
	}
	
	private Node select (Node x, int k){
		//Return node containing key of rank k.
		if(x == null) return null;
		int t = size(x.left);
		if(t > k) return select(x.left, k);
		else if (t < k) return select(x.right, k - t - 1);
		else return x;
	}
	
	/**
	* <p>Description: Return the given key's rank in this tree.</p>
	* @param key
	* @return
	*/
	public int rank(Key key){
		return rank(root, key);
	}
	
	private int rank(Node x, Key key){
		if(x == null) return 0;
		int cmp = key.compareTo(x.key);
		if(cmp < 0) return rank(x.left, key);
		else if(cmp > 0) return size(x.left) + 1 + rank(x.right, key);
		else return size(x.left);
	}
	
	/**
	* <p>Description: Delete the node with minimum key in this tree.</p>
	*/
	public void deleteMin(){
		root = deleteMin(root);
	}
	
	private Node deleteMin(Node x){
		if(x.left == null) return x.right;//simply delete x itself.
		x.left = deleteMin(x.left);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	/**
	* <p>Description: Delete the node with maximum key in this tree.</p>
	*/
	public void deleteMax(){
		root = deleteMax(root);
	}
	
	private Node deleteMax(Node x){
		if(x.right == null) return x.left;// simply delete x itself.
		x.right = deleteMax(x.right);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	/**
	* <p>Description: Given key, delete the corresponding node. </p>
	* @param key
	*/
	public void delete(Key key){
		root = delete(root, key);
	}
	
	/**
	* <p>Description: Delete the node whose key is equal to given key in the subtree rooted at node x.</p>
	* And return the new root.<\p>
	* @param x
	* @param key
	* @return the new root.
	*/
	private Node delete(Node x, Key key){
		if(x == null) return null;
		int cmp = key.compareTo(x.key);
		if(cmp < 0) x.left = delete(x.left, key);
		else if(cmp > 0) x.right = delete(x.right, key);
		else{
			if(x.right == null) return x.left;//if x has only left child, simply delete x and return x's left child.
			if(x.left == null) return x.right;//if x has only right child, simply delete x and return x's right child.
			Node t = x;
			x = min(x.right);//new x is x's successor.
			x.right = deleteMin(t.right);//x's right subtree is the original x's subtree except the min in subtree.
			x.left = t.left;//new x's left is old x's left.
		}
		//Update counts.
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}
	
	public Iterable<Key> keys(){
		return keys(min(), max());
	}
	
	public Iterable<Key> keys(Key low, Key high){
		Queue<Key> queue = new Queue<Key>();
		keys(root, queue, low, high);
		return queue;
	}
	
	private void keys(Node x, Queue<Key> queue, Key low, Key high){
		if(x == null) return;
		int cmplow = low.compareTo(x.key);
		int cmphigh = high.compareTo(x.key);
		if(cmplow < 0) keys(x.left, queue, low, high);
		if(cmplow <= 0 && cmphigh >= 0)
			queue.add(x.key);
		if(cmphigh > 0 ) keys(x.right, queue, low, high);
	}
	
	public static void main(String[] args) {
		BST bst = new BST<Integer, String>();
		bst.put(1, "one");
	}
}
