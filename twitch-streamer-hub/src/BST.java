
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>> {
	private class Node {
		private T data;
		private Node left;
		private Node right;

		public Node(T data) {
			this.data = data;
			left = null;
			right = null;
		}
	}

	private Node root;

	/*** CONSTRUCTORS ***/

	/**
	 * Default constructor for BST sets root to null
	 */
	public BST() {
		root = null;
	}

	/**
	 * Copy constructor for BST
	 * 
	 * @param bst the BST to make a copy of
	 */
	public BST(BST<T> bst) {
		if (bst == null) {
			return;
		}
		copyHelper(bst.root);

	}

	/**
	 * Helper method for copy constructor
	 * 
	 * @param node the node containing data to copy
	 */
	private void copyHelper(Node node) {
		if (node == null) {
			return;
		} else {
			insert(node.data);
			copyHelper(node.left);
			copyHelper(node.right);
		}

	}

	/**
	 * Creates a BST of minimal height given an array of values
	 * 
	 * @param array the list of values to insert
	 * @precondition array must be sorted in ascending order
	 * @throws IllegalArgumentException when the array is unsorted
	 */
	public BST(T[] array) throws IllegalArgumentException {
		if (array == null) {
			root = null;
			return;
		}
		if (!arraySorted(array)) {
			throw new IllegalArgumentException("BST(T[] array): Array is not sorted.");
		}
		arrayCopyHelper(array, 0, array.length - 1);
	}

	private void arrayCopyHelper(T[] array, int low, int high) {
		int mid = (high + low) / 2;
		if (high < low)
			return;
		insert(array[mid]);
		arrayCopyHelper(array, low, mid - 1);
		arrayCopyHelper(array, mid + 1, high);
	}

	private boolean arraySorted(T[] array) {
		for (int i = 0; i < array.length - 2; i++) {
			if (array[i].compareTo(array[i + 1]) > 0) {
				return false;
			}
		}
		return true;
	}

	/*** ACCESSORS ***/

	/**
	 * Returns the data stored in the root
	 * 
	 * @precondition !isEmpty()
	 * @return the data stored in the root
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getRoot() throws NoSuchElementException {
		if (root == null)
			throw new NoSuchElementException("getRoot(): Root is null.");
		return root.data;
	}

	/**
	 * Determines whether the tree is empty
	 * 
	 * @return whether the tree is empty
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns the current size of the tree (number of nodes)
	 * 
	 * @return the size of the tree
	 */
	public int getSize() {
		return getSize(root);
	}

	/**
	 * Helper method for the getSize method
	 * 
	 * @param node the current node to count
	 * @return the size of the tree
	 */
	private int getSize(Node node) {
		if (node == null) {
			return 0;
		} else {
			return (1 + getSize(node.left) + getSize(node.right));
		}

	}

	/**
	 * Returns the height of tree by counting edges.
	 * 
	 * @return the height of the tree
	 */
	public int getHeight() {
		return getHeight(root);
	}

	/**
	 * Helper method for getHeight method
	 * 
	 * @param node the current node whose height to count
	 * @return the height of the tree
	 */
	private int getHeight(Node node) {
		if (node == null) {
			return -1;
		} else {
			int leftHeight = getHeight(node.left);
			int rightHeight = getHeight(node.right);

			if (leftHeight > rightHeight)
				return (leftHeight + 1);
			else
				return (rightHeight + 1);
		}
	}

	/**
	 * Returns the smallest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the smallest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMin() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("findMin(): the tree is empty");
		return findMin(root);
	}

	/**
	 * Helper method to findMin method
	 * 
	 * @param node the current node to check if it is the smallest
	 * @return the smallest value in the tree
	 */
	private T findMin(Node node) {
		if (node.left != null)
			return findMin(node.left);
		return node.data;
	}

	/**
	 * Returns the largest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the largest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMax() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException("findMax(): the tree is empty");
		return findMax(root);
	}

	/**
	 * Helper method to findMax method
	 * 
	 * @param node the current node to check if it is the largest
	 * @return the largest value in the tree
	 */
	private T findMax(Node node) {
		if (node.right != null)
			return findMax(node.right);
		else
			return node.data;
	}

	/*** MUTATORS ***/

	/**
	 * Inserts a new node in the tree
	 * 
	 * @param data the data to insert
	 */
	public void insert(T data) {
		if (root == null) {
			root = new Node(data);
		} else {
			insert(data, root);
		}
	}

	/**
	 * Helper method to insert Inserts a new value in the tree
	 * 
	 * @param data the data to insert
	 * @param node the current node in the search for the correct location in which
	 *             to insert
	 */
	private void insert(T data, Node node) {
		if (data.compareTo(node.data) <= 0) {
			if (node.left == null)
				node.left = new Node(data);
			else
				insert(data, node.left);
		} else {
			if (node.right == null)
				node.right = new Node(data);
			else
				insert(data, node.right);
		}
	}

	/**
	 * Removes a value from the BST
	 * 
	 * @param data the value to remove
	 * @precondition !isEmpty()
	 * @precondition the data is located in the tree
	 * @throws IllegalStateException when BST is empty
	 */
	public void remove(T data) throws IllegalStateException {
		if (isEmpty())
			throw new IllegalStateException("remove(): the tree is empty");
		else if (!search(data)) {
			return;
		} else {
			root = remove(data, root);
		}
	}

	/**
	 * Helper method to the remove method
	 * 
	 * @param data the data to remove
	 * @param node the current node
	 * @return an updated reference variable
	 */
	private Node remove(T data, Node node) {
		if (node == null) {
			return node;
		} else if (data.compareTo(node.data) < 0) {
			node.left = remove(data, node.left);
		} else if (data.compareTo(node.data) > 0) {
			node.right = remove(data, node.right);
		} else {
			if (node.left == null && node.right == null) {
				node = null;
			} else if (node.left != null && node.right == null) {
				node = node.left;
			} else if (node.left == null && node.right != null) {
				node = node.right;
			} else {
				T rmin = findMin(node.right);
				node.data = rmin;
				node.right = remove(rmin, node.right);//here

			}
		}
		return node;
	}

	/*** ADDITIONAL OPERATIONS ***/

	/**
	 * Searches for a specified value in the tree
	 * 
	 * @param data the value to search for
	 * @return whether the value is stored in the tree
	 */
	public boolean search(T data) {
		if (root == null) {
			return false;
		} else {
			return search(data, root);
		}
	}

	/**
	 * Helper method for the search method
	 * 
	 * @param data the data to search for
	 * @param node the current node to check
	 * @return whether the data is stored in the tree
	 */
	private boolean search(T data, Node node) {
		if (data.compareTo(node.data) == 0) {
			return true;
		}
		if (data.compareTo(node.data) < 0) {
			if (node.left == null) {
				return false;
			} else {
				return search(data, node.left);
			}
		} else {
			if (node.right == null) {
				return false;
			} else {
				return search(data, node.right);
			}
		}
	}
	
	
//WORK IN PROGRESS
	
	public ArrayList<T> searchAll(T data) {
		
		ArrayList<T> list = new ArrayList<T>();
		
		if (root == null) {
			return null;
		} else {
			searchAll(data, root, list);
			return list;
		}
	}

	/**
	 * Helper method for the search method
	 * 
	 * @param data the data to search for
	 * @param node the current node to check
	 * @return whether the data is stored in the tree
	 */
	private void searchAll(T data, Node node, ArrayList<T> list) {
		if (node == null) {
			return;
		} else {
			if(data.compareTo(node.data) == 0)
				list.add(node.data);
			searchAll(data,node.left, list);
			searchAll(data,node.right, list);
		}

	}

	/**
	 * Determines whether a BST is balanced using the definition given in the course
	 * lesson notes Note that we will consider an empty tree to be trivially
	 * balanced
	 * 
	 * @return whether the BST is balanced
	 */
	public boolean isBalanced() {
		if (root == null) {
			return true;
		}
		return isBalanced(root);
	}

	/**
	 * Helper method for isBalanced to determine if a BST is balanced
	 * 
	 * @param n a Node in the tree
	 * @return whether the BST is balanced at the level of the given Node
	 */
	private boolean isBalanced(Node n) {
		if (n != null) {
			if (Math.abs(getHeight(n.left) - getHeight(n.right)) > 1) {
				return false;
			}
			return isBalanced(n.left) && isBalanced(n.right);
		}
		return true;
	}

	/**
	 * Returns a String containing the data in post order
	 * 
	 * @return a String of data in post order
	 */
	public String preOrderString() {
		StringBuilder preOrder = new StringBuilder();
		preOrderString(root, preOrder);
		preOrder.append("\n");
		return preOrder.toString();
	}

	/**
	 * Helper method to preOrderString Inserts the data in pre order into a String
	 * 
	 * @param node     the current Node
	 * @param preOrder a String containing the data
	 */
	private void preOrderString(Node node, StringBuilder preOrder) {
		if (node == null) {
			return;
		} else {
			preOrder.append(node.data + " ");
			preOrderString(node.left, preOrder);
			preOrderString(node.right, preOrder);
		}

	}

	/**
	 * Returns a String containing the data in order
	 * 
	 * @return a String of data in order
	 */
	public String inOrderString() {
		StringBuilder inOrder = new StringBuilder();
		inOrderString(root, inOrder);
		inOrder.append("\n");
		return inOrder.toString();
	}

	/**
	 * Helper method to inOrderString Inserts the data in order into a String
	 * 
	 * @param node    the current Node
	 * @param inOrder a String containing the data
	 */
	private void inOrderString(Node node, StringBuilder inOrder) {
		if (node == null) {
			return;
		} else {
			inOrderString(node.left, inOrder);
			inOrder.append(node.data + " ");
			inOrderString(node.right, inOrder);
		}
	}

	/**
	 * Returns a String containing the data in post order
	 * 
	 * @return a String of data in post order
	 */
	public String postOrderString() {
		StringBuilder postOrder = new StringBuilder();
		postOrderString(root, postOrder);
		postOrder.append("\n");
		return postOrder.toString();
	}

	/**
	 * Helper method to postOrderString Inserts the data in post order into a String
	 * 
	 * @param node      the current Node
	 * @param postOrder a String containing the data
	 */
	private void postOrderString(Node node, StringBuilder postOrder) {
		if (node == null) {
			return;
		} else {
			postOrderString(node.left, postOrder);
			postOrderString(node.right, postOrder);
			postOrder.append(node.data + " ");
		}
	}
	
	private LinkedList<User> toLinkedList(){
		return null;
	}

}