
public class Interest implements Comparable<Interest>{
	private String name;
	private int id;
	
	public Interest(String n, int i) {
		name = n;
		id = i;
	}
	
	public int hashCode() {
		String key = name; 
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Interest [name=" + name + ", id=" + id + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Interest)) {
			return false;
		} else {
			if (this.name.compareTo(((Interest) o).name) == 0) {
				return true;
			}
			return false;
		}
		
	}

	@Override
	public int compareTo(Interest o) {
		if (this.name.compareTo(((Interest) o).name) != 0) {
			return this.name.compareTo(((Interest) o).name);
		} else {
			return ((Integer)this.id).compareTo(  (Integer)(((Interest) o).id) );
		}
		
	}
}