
public class Interest {
	private String name;
	private int id;
	
	public Interest(String n, int i) {
		name = n;
		id = i;
	}
	
	public int hashCode() {
		String key = name + id; // this is the key. The element is the Butterfly Object
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
	
	
	@Override
	public String toString() {
		return "Interest [name=" + name + ", id=" + id + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Interest)) {
			return false;
		} else {
			if (this.name.compareTo(((Interest) o).name) == 0) {
				return true;
			}
			return false;
		}
		
	}
}
