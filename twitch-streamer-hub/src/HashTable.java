import java.util.ArrayList;
import java.util.NoSuchElementException;

public class HashTable<T extends Comparable<T>> {

	private ArrayList<LinkedList<T>> Table;
	private int size;

	/**
	 * Constructor for the HashTable class. Initializes the Table to be sized
	 * according to value passed in as a parameter Inserts size empty Lists into the
	 * table. Sets size to 0
	 * 
	 * @param size the table size
	 */
	public HashTable(int size) {
		Table = new ArrayList<LinkedList<T>>(size);
		for (int i = 0; i < size; i++) {
			Table.add(i, new LinkedList<T>());
		}
		this.size = 0;
	}

	/** Accessors */

	/**
	 * returns the absolute value of the hashCode for a given Object scaled to the
	 * size of the Table
	 * 
	 * @param t the Object
	 * @return the index in the Table
	 */
	public int hash(T t) {
		int code = t.hashCode();
		return Math.abs(code % Table.size());
	}

	/**
	 * counts the number of elements at this index
	 * 
	 * @param index the index in the Table
	 * @precondition 0 <= index < Table.length
	 * @return the count of elements at this index
	 * @throws IndexOutOfBoundsException
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= Table.size()) {
			throw new IndexOutOfBoundsException("countBucket(): Index is not in Table");
		}
		return Table.get(index).getLength();
	}

	/**
	 * returns total number of elements in the Table
	 * 
	 * @return total number of elements
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Determines whether the table has any elements
	 * 
	 * @return whether the table is empty
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * searches for a specified element in the Table
	 * 
	 * @param t the element to search for
	 * @return whether the specified element exists in the table
	 */
	public boolean search(T t) {
		for (int i = 0; i < Table.size(); i++) {
			if (Table.get(i).linearSearch(t) != -1) {
				return true;
			}
		}
		return false;
	}
	
	public Object searchAndSpit(T t) {
		for (int i = 0; i < Table.size(); i++) {
			Object o = Table.get(i).linearSearchAndSpit(t);
			if (o != null) {
				return o;
			}
		}
		return null;
	}

	/** Manipulation Procedures */

	/**
	 * inserts a new element in the Table calls the hash method to determine
	 * placement
	 * 
	 * @param t the element to insert
	 */
	public void insert(T t) {
		int index = this.hash(t);
		LinkedList<T> list_at_index = Table.get(index);
		list_at_index.addLast(t);
		this.size++;
	}

	/**
	 * removes the element t from the Table calls the hash method on the key to
	 * determine correct placement has no effect if t is not in the Table
	 * 
	 * @param t the key to remove
	 * @precondition t must be in the table H the element is not in the table
	 */
	public void remove(T t) {
		if (!this.search(t)) {
			return;
		}
		for (int i = 0; i < Table.size(); i++) {
			LinkedList<T> list = Table.get(i);
			if (list.linearSearch(t) != -1) {
				int index = list.linearSearch(t);
				list.iteratorToIndex(index);
				list.removeIterator();
			}
		}
	}

	/** Additional Methods */

	/**
	 * Resets the Table back to empty
	 */
	public void clear() {
		Table.clear();
		for (int i = 0; i < size; i++) {
			Table.add(i, new LinkedList<T>());
		}
		this.size = 0;
	}

	/**
	 * Prints to the console all the keys at a specified bucket in the Table. Each
	 * element displayed on its own line, with a blank line separating each element
	 * Above the elements, prints the message "Printing bucket #<bucket>:" Note that
	 * there is no <> in the output
	 * 
	 * @param bucket the index in the Table
	 * @throws IndexOutOfBoundsException
	 */
	public void printBucket(int bucket) throws IndexOutOfBoundsException {
		if (bucket < 0 || bucket >= Table.size()) {
			throw new IndexOutOfBoundsException("countBucket(): Bucket is not in Table");
		}
		LinkedList<T> list = Table.get(bucket);
		System.out.println("Printing bucket #" + bucket + ":");
		list.positionIterator();
		System.out.println("\n");
		for (int i = 0; i < list.getLength(); i++) {
			System.out.println(list.getIterator().toString());
			System.out.println("\n");
			list.advanceIterator();
		}
	}
	
	/**
	 * Prints to the console all the keys at a specified bucket in the Table. Each
	 * element displayed on its own line, with a blank line separating each element
	 * Above the elements, prints the message "Printing bucket #<bucket>:" Note that
	 * there is no <> in the output
	 * 
	 * @param bucket the index in the Table
	 * @throws IndexOutOfBoundsException
	 */
	public LinkedList<T> getBucket(Object o){
		int bucket = this.hash((T)o);
		if (bucket < 0 || bucket >= Table.size()) {
			System.out.print("NULL BUCK");
			return null;
		}
		return Table.get(bucket);
	}

	/**
	 * Prints the first element at each bucket along with a count of the total
	 * elements with the message "+ <count> -1 more at this bucket." Each bucket
	 * separated with two blank lines. When the bucket is empty, prints the message
	 * "This bucket is empty." followed by two blank lines
	 */
	public void printTable() {
		for (int i = 0; i < Table.size(); i++) {
			System.out.println("Bucket: " + i);
			LinkedList<T> list = Table.get(i);
			if (list.getLength() != 0) {
				System.out.println(
						list.getFirst().toString() + "\n\n+ " + (list.getLength() - 1) + " more at this bucket.");
			} else {
				System.out.println("This bucket is empty");
			}
			System.out.println("\n");
		}
	}

	/**
	 * Starting at the first bucket, and continuing in order until the last bucket,
	 * concatenates all elements at all buckets into one String
	 */
	@Override
	public String toString() {
		String out = "";

		for (int i = 0; i < Table.size(); i++) {
			LinkedList<T> list = Table.get(i);
			list.positionIterator();
			while (!list.offEnd()) {
				out += list.getIterator().toString() + "\n";
				list.advanceIterator();
			}
		}

		return out;
	}

}