
import java.util.ArrayList;

public class Graph {
	private int vertices;
	private int edges;
	private ArrayList<LinkedList<Integer>> adj;
	private ArrayList<Character> color;
	private ArrayList<Integer> distance;
	private ArrayList<Integer> parent;
	private ArrayList<Integer> discoverTime;
	private ArrayList<Integer> finishTime;
	private static int time = 0;

	/** Constructors and Destructors */

	/**
	 * initializes an empty graph, with n vertices and 0 edges
	 * 
	 * @param n the number of vertices in the graph
	 */
	public Graph(int n) {
		vertices = n;
		edges = 0;
		adj = new ArrayList<LinkedList<Integer>>(n);
		for(int i = 0; i <= n; i++) {
			adj.add(new LinkedList<Integer>());
		}
		color = new ArrayList<Character>(n);
		for(int i = 0; i <= n; i++) {
			color.add('W');
		}
		distance = new ArrayList<Integer>(n);
		for(int i = 0; i <= n; i++) {
			distance.add(-1);
		}
		parent = new ArrayList<Integer>(n);
		for(int i = 0; i <= n; i++) {
			parent.add(0);
		}
		discoverTime = new ArrayList<Integer>(n);
		for(int i = 0; i <= n; i++) {
			discoverTime.add(-1);
		}
		finishTime = new ArrayList<Integer>(n);
		for(int i = 0; i <= n; i++) {
			finishTime.add(-1);
		}
	}

	/*** Accessors ***/

	/**
	 * Returns the number of edges in the graph
	 * 
	 * @return the number of edges
	 */
	public int getNumEdges() {
		return edges;
	}

	/**
	 * Returns the number of vertices in the graph
	 * 
	 * @return the number of vertices
	 */
	public int getNumVertices() {
		return vertices;
	}

	/**
	 * returns whether the graph is empty (no edges)
	 * 
	 * @return whether the graph is empty
	 */
	public boolean isEmpty() {
		return edges == 0;
	}

	/**
	 * Returns the value of the distance[v]
	 * 
	 * @param v a vertex in the graph
	 * @precondition 0 < v <= vertices
	 * @return the distance of vertex v
	 * @throws IndexOutOfBoundsException when v is out of bounds
	 */
	public Integer getDistance(Integer v) throws IndexOutOfBoundsException {
		if(v <= 0 || v > vertices) {
			throw new IndexOutOfBoundsException("getDistance: v is out of bounds");
		}
		return distance.get(v);
	}

	/**
	 * Returns the value of the parent[v]
	 * 
	 * @param v a vertex in the graph
	 * @precondition 0 < v <= vertices
	 * @return the parent of vertex v
	 * @throws IndexOutOfBoundsException when v is out of bounds
	 */
	public Integer getParent(Integer v) throws IndexOutOfBoundsException {
		if(v <= 0 || v > vertices) {
			throw new IndexOutOfBoundsException("getParent: v is out of bounds");
		}
		return parent.get(v);
	}

	/**
	 * Returns the value of the color[v]
	 * 
	 * @param v a vertex in the graph
	 * @precondition 0 < v <= vertices
	 * @return the color of vertex v
	 * @throws IndexOutOfBoundsException when v is out of bounds
	 */
	public Character getColor(Integer v) throws IndexOutOfBoundsException {
		if(v <= 0 || v > vertices) {
			throw new IndexOutOfBoundsException("getColor: v is out of bounds");
		}
		return color.get(v);
	}

	/**
	 * Returns the value of the discoverTime[v]
	 * 
	 * @param v a vertex in the graph
	 * @precondition 0 < v <= vertices
	 * @return the discover time of vertex v
	 * @throws IndexOutOfBoundsException when v is out of bounds
	 */
	public Integer getDiscoverTime(Integer v) throws IndexOutOfBoundsException {
		if(v <= 0 || v > vertices) {
			throw new IndexOutOfBoundsException("getDiscoverTime: v is out of bounds");
		}
		return discoverTime.get(v);
	}

	/**
	 * Returns the value of the finishTime[v]
	 * 
	 * @param v a vertex in the graph
	 * @precondition 0 < v <= vertices
	 * @return the finish time of vertex v
	 * @throws IndexOutOfBoundsException when v is out of bounds
	 */
	public Integer getFinishTime(Integer v) throws IndexOutOfBoundsException {
		if(v <= 0 || v > vertices) {
			throw new IndexOutOfBoundsException("getFinishTime: v is out of bounds");
		}
		return finishTime.get(v);
	}

	/**
	 * Returns the LinkedList stored at index v
	 * 
	 * @param v a vertex in the graph
	 * @return the adjacency LinkedList a v
	 * @precondition 0 < v <= vertices
	 * @throws IndexOutOfBoundsException when v is out of bounds
	 */
	public LinkedList<Integer> getAdjacencyList(Integer v) throws IndexOutOfBoundsException {
		if(v <= 0 || v > vertices) {
			throw new IndexOutOfBoundsException("getAdjacencyList: v is out of bounds");
		}
		return adj.get(v);
	}

	/*** Manipulation Procedures ***/

	/**
	 * Inserts vertex v into the adjacency list of vertex u (i.e. inserts v into the
	 * list at index u) @precondition, 0 < u, v <= vertices
	 * 
	 * @throws IndexOutOfBounds exception when u or v is out of bounds
	 */
	public void addDirectedEdge(Integer u, Integer v) throws IndexOutOfBoundsException {
		if((v <= 0 || v > vertices) || (u <= 0 || u > vertices)) {
			throw new IndexOutOfBoundsException("addDirectedEdge: u or v is out of bounds");
		}
		adj.get(u).addLast(v);
		edges++;
	}

	/**
	 * Inserts vertex v into the adjacency list of vertex u (i.e. inserts v into the
	 * list at index u) and inserts u into the adjacent vertex list of
	 * v @precondition, 0 < u, v <= vertices
	 * 
	 * @throws IndexOutOfBoundsException when u or v is out of bounds
	 */
	public void addUndirectedEdge(Integer u, Integer v) throws IndexOutOfBoundsException {
		if((v <= 0 || v > vertices) || (u <= 0 || u > vertices)) {
			throw new IndexOutOfBoundsException("addUndirectedEdge: u or v is out of bounds");
		}
		adj.get(u).addLast(v);
		adj.get(v).addLast(u);
		edges++;
	}

	/*** Additional Operations ***/

	/**
	 * Creates a String representation of the Graph Prints the adjacency list of
	 * each vertex in the graph, vertex: <space separated list of adjacent vertices>
	 */
	@Override
	public String toString() {
		String out = "";
		for(int i = 1; i <= vertices; i++) {
			LinkedList<Integer> list = adj.get(i);
			out += i + ": ";
			list.positionIterator();
			while(!list.offEnd()) {
				out += list.getIterator() + " ";
				list.advanceIterator();
			}
			out += "\n";
		}
		return out;
	}

	/**
	 * Performs breath first search on this Graph give a source vertex
	 * 
	 * @param source the starting vertex
	 * @precondition source is a vertex in the graph
	 * @throws IndexOutOfBoundsException when the source vertex is out of bounds of
	 *                                   the graph
	 */

	public void BFS(Integer source) throws IndexOutOfBoundsException {
		if((source <= 0 || source > vertices)) {
			throw new IndexOutOfBoundsException("addUndirectedEdge: source is out of bounds");
		}
		LinkedList<Integer> queue = new LinkedList<Integer>();
		
		for(int i = 0; i <= vertices; i++) {
			color.set(i, 'W');
			distance.set(i, -1);
			parent.set(i, 0);
		}
		
		color.set(source, 'G');
		distance.set(source, 0);
		queue.addLast(source);
		while(!queue.isEmpty()) {
			Integer xIndex = queue.getFirst();
			LinkedList<Integer> xList = adj.get(xIndex);
			
			queue.removeFirst();
			
			xList.positionIterator();
			while(!xList.offEnd()) {
				if(color.get(xList.getIterator()) == 'W') {
					
					color.set(xList.getIterator(), 'G');
					distance.set(xList.getIterator(), distance.get(xIndex) + 1);
					parent.set(xList.getIterator(), xIndex);
					queue.addLast(xList.getIterator());
					
				}
				xList.advanceIterator();
			}
			
			color.set(xIndex, 'B');
		}
		
	}

	/**
	 * Performs depth first search on this Graph in order of vertex lists
	 */
	public void DFS() {
		for(int i = 0; i <= vertices; i++) {
			color.set(i, 'W');
			parent.set(i, 0);
			discoverTime.set(i, -1);
			finishTime.set(i, -1);
		}
		
		time = 0;
		
		for(int i = 1; i <= vertices; i++) {
			if(color.get(i) == 'W') {
				visit(i);
			}
		}
	}

	/**
	 * Private recursive helper method for DFS
	 * 
	 * @param vertex the vertex to visit
	 */
	private void visit(int vertex) {
		color.set(vertex, 'G');
		discoverTime.set(vertex, ++time);
		LinkedList<Integer> xList = adj.get(vertex);
		
		xList.positionIterator();
		while(!xList.offEnd()) {
			if(color.get(xList.getIterator()) == 'W') {
				parent.set(xList.getIterator(), vertex);
				visit(xList.getIterator());
			}
			xList.advanceIterator();
		}
		
		color.set(vertex, 'B');
		finishTime.set(vertex, ++time);
	}

}