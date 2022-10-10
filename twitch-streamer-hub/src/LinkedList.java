
import java.util.NoSuchElementException;

public class LinkedList<T> {
	private class Node {
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;

	/**** CONSTRUCTORS ****/

	/**
	 * Instantiates a new LinkedList with default values
	 * 
	 * @postcondition Creates a LinkedList Object
	 */
	public LinkedList() {
		length = 0;
		first = null;
		last = null;
		iterator = null;
	}

	/**
	 * Converts the given array into a LinkedList
	 * 
	 * @param array the array of values to insert into this LinkedList
	 * @postcondition Creates a LinkedList Object, populated by array values
	 */
	public LinkedList(T[] array) {
		if (array == null) {
			return;
		}
		if (array.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			for (int i = 0; i < array.length; i++) {
				addLast(array[i]);
			}
			iterator = null;
		}

	}

	/**
	 * Instantiates a new LinkedList by copying another List
	 * 
	 * @param original the LinkedList to copy
	 * @postcondition a new List object, which is an identical, but separate, copy
	 *                of the LinkedList original
	 */
	public LinkedList(LinkedList<T> original) {
		if (original == null) {
			return;
		}
		if (original.length == 0) {
			length = 0;
			first = null;
			last = null;
			iterator = null;
		} else {
			Node temp = original.first;
			while (temp != null) {
				addLast(temp.data);
				temp = temp.next;
			}
			iterator = null;
		}
	}

	/**** ACCESSORS ****/

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition !isEmpty()
	 * @return the value stored at node first
	 * @throws NoSuchElementException The list is empty!
	 */
	public T getFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getFirst(): The list is empty!");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 * 
	 * @precondition !isEmpty()
	 * @return the value stored in the node last
	 * @throws NoSuchElementException "getLast(): The list is empty!"
	 */
	public T getLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("getLast(): The list is empty!");
		}
		return last.data;
	}

	/**
	 * Returns the data stored in the iterator node
	 * 
	 * @precondition iterator is offEnd
	 * @throw NullPointerException iterator is null
	 */
	public T getIterator() throws NullPointerException {
		if (offEnd())
			throw new NullPointerException("getIterator(): iterator is null");
		return iterator.data;
	}

	/**
	 * Returns the current length of the LinkedList
	 * 
	 * @return the length of the LinkedList from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns whether the LinkedList is currently empty
	 * 
	 * @return whether the LinkedList is empty
	 */
	public boolean isEmpty() {
		return first == null && last == null;
	}

	/**
	 * Returns whether the iterator is offEnd, i.e. null
	 * 
	 * @return whether the iterator is null
	 */
	public boolean offEnd() {
		return iterator == null;
	}

	/**** MUTATORS ****/
	/**
	 * Creates a new first element
	 * 
	 * @param data the data to insert at the front of the LinkedList
	 * @postcondition Inserts a new first element
	 */
	public void addFirst(T data) {
		if (length == 0) {
			first = last = new Node(data);
		} else {
			Node N = new Node(data);
			first.prev = N;
			N.next = first;
			first = N;
		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data the data to insert at the end of the LinkedList
	 * @postcondition Inserts a new last element
	 */
	public void addLast(T data) {
		if (length == 0) {
			first = last = new Node(data);
		} else {
			last.next = new Node(data);
			last.next.prev = last;
			last = last.next;
		}
		length++;
	}

	/**
	 * Inserts a new element after the iterator
	 * 
	 * @param data the data to insert
	 * @precondition Iterator is not null
	 * @throws NullPointerException Iterator is null
	 * @postcondition new element is inserted after the iterator
	 */
	public void addIterator(T data) throws NullPointerException {
		if (iterator == null)
			throw new NullPointerException("addIterator(): Iterator is null");
		else if (iterator == last) {
			addLast(data);
		} else {
			Node n = new Node(data);
			n.next = iterator.next;
			n.prev = iterator;
			iterator.next.prev = n;
			iterator.next = n;
		}
		length++;
	}

	/**
	 * removes the element at the front of the LinkedList
	 * 
	 * @precondition LinkedList is not empty
	 * @postcondition Removes the first element
	 * @throws NoSuchElementException "removeFirst(): Can't remove from an empty
	 *                                List!"
	 */
	public void removeFirst() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("removeFirst(): Can't remove from an empty List!");
		} else if (length == 1) {
			first = last = null;
		} else {
			if (iterator == first) {
				iterator = null;
			}
			first = first.next;
			first.prev = null;

		}
		length--;
	}

	/**
	 * removes the element at the end of the LinkedList
	 * 
	 * @precondition Linked List is not empty
	 * @postcondition Removes the last element
	 * @throws NoSuchElementException "removeFirst(): Can't remove from an empty
	 *                                List!"
	 */
	public void removeLast() throws NoSuchElementException {
		if (length == 0) {
			throw new NoSuchElementException("removeFirst(): Can't remove from an empty List!");
		} else if (length == 1) {
			first = last = null;
		} else {
			if (iterator == last) {
				iterator = null;
			}
			last = last.prev;
			last.next = null;

		}
		length--;
	}

	/**
	 * removes the element referenced by the iterator
	 * @precondition Iterator is not null
	 * @postcondition Element referenced by iterator is removed from LinkedList
	 * @throws NullPointerException Iterator is null
	 */
	public void removeIterator() throws NullPointerException {
		if (iterator == null) {
			throw new NullPointerException("removeIterator(): Iterator is null");
		} else if (iterator == first) {
			removeFirst();
		} else if (iterator == last) {
			removeLast();
		} else {
			iterator.next.prev = iterator.prev;
			iterator.prev.next = iterator.next;
			iterator = null;
			length--;
		}
	}

	/**
	 * Places the iterator at the first node
	 * 
	 * @postcondition iterator is assigned to first node of LinkedList
	 */
	public void positionIterator() {
		iterator = first;
	}

	/**
	 * Moves the iterator one node towards the last
	 * 
	 * @precondition Iterator is not null
	 * @postcondition Iterator is moved to the next node approaching the last node
	 * @throws NullPointerException Iterator is null
	 */
	public void advanceIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("advanceIterator(): Iterator is null");
		}
		iterator = iterator.next; // general case
	}

	/**
	 * Moves the iterator one node towards the first
	 * 
	 * @precondition Iterator is not null
	 * @postcondition Iterator is moved to the previous node approaching the first
	 *                node
	 * @throws NullPointerException Iterator is null
	 */
	public void reverseIterator() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("advanceIterator(): Iterator is null");
		}
		iterator = iterator.prev;
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * Converts the LinkedList to a String, with each value separated by a blank
	 * line At the end of the String, place a new line character
	 * 
	 * @return the LinkedList as a String
	 */
	@Override
	public String toString() {
		String out = "";
		Node temp = first;
		while (temp != null) {
			out += temp.data + " ";
			temp = temp.next;
		}
		return out + "\n";
	}
	
	public void print() {
		positionIterator();
		while(!offEnd()) {
			System.out.println(getIterator().toString());
			advanceIterator();
		}
	}

	/**
	 * Determines whether the given Object is another LinkedList, containing the
	 * same data in the same order
	 * 
	 * @param o another Object
	 * @return whether there is equality
	 */
	@SuppressWarnings("unchecked") // good practice to remove warning here
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof LinkedList)) {
			return false;
		} else {
			LinkedList<T> L = (LinkedList<T>) o;
			if (length != L.getLength()) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null && temp2 != null) {
					if (temp1.data != temp2.data)
						return false;
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/**
	 * Points the iterator at first and then advances it to the specified index
	 * 
	 * @param index the index where the iterator should be placed
	 * @precondition 0 < index <= length
	 * @throws IndexOutOfBoundsException when precondition is violated
	 */
	public void iteratorToIndex(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException("iteratorToIndex(): Specified index is out of bounds.");
		}
		this.positionIterator();
		for (int i = 0; i < index; i++) {
			this.advanceIterator();
		}
	}

	/**
	 * Searches the List for the specified value using the linear search algorithm
	 * 
	 * @param value the value to search for
	 * @return the location of value in the List or -1 to indicate not found Note
	 *         that if the List is empty we will consider the element to be not
	 *         found post: position of the iterator remains unchanged
	 */
	public int linearSearch(T value) {
		int index = 0;
		positionIterator();
		while (!offEnd()) {
			if (value.equals(getIterator()))
				return index;
			index++;
			advanceIterator();
		}
		return -1;
	}
	
	public Object linearSearchAndSpit(T value) {
		int index = 0;
		positionIterator();
		while (!offEnd()) {
			if (value.equals(getIterator()))
				return getIterator();
			index++;
			advanceIterator();
		}
		return null;
	}

	/** CHALLENGE METHODS */

	/**
	 * Zippers two LinkedLists to create a third List which contains alternating
	 * values from this list and the given parameter For example: [1,2,3] and
	 * [4,5,6] -> [1,4,2,5,3,6] For example: [1, 2, 3, 4] and [5, 6] -> [1, 5, 2, 6,
	 * 3, 4] For example: [1, 2] and [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
	 * 
	 * @param list the second LinkedList in the zipper
	 * @return a new LinkedList, which is the result of zippering this and list
	 * @postcondition this and list are unchanged
	 */
	public LinkedList<T> zipperLists(LinkedList<T> list) {
		LinkedList res = new LinkedList();
		this.positionIterator();
		list.positionIterator();
		while (!list.offEnd() && !this.offEnd()) {
			res.addLast(this.getIterator());
			res.addLast(list.getIterator());
			this.advanceIterator();
			list.advanceIterator();
		}
		while (!list.offEnd()) {
			res.addLast(list.getIterator());
			list.advanceIterator();
		}
		while (!offEnd()) {
			res.addLast(this.getIterator());
			this.advanceIterator();
		}
		return res;
	}

	/**
	 * Determines whether any of the next or prev links in the List are broken, i.e.
	 * referencing the wrong Node or null Used by the programmer for error checking
	 * 
	 * @return whether a broken links exist
	 */
	public boolean containsBrokenLinks() {
		int c = 0;
		Node temp = first;
		if (length == 1) {
			return temp.prev != null || temp.next != null;
		}
		while (c < length) {
			if (temp == first) {
				if (temp.prev != null || temp.next.prev != temp) {
					return true;
				}
			} else if (temp == last) {
				if (temp.prev.next != temp || temp.next != null) {
					return true;
				}
			} else {
				if (temp.prev.next != temp || temp.next.prev != temp) {
					return true;
				}
			}
			c++;
		}

		return false;
	}

}