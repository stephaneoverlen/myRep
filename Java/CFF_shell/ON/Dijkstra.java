/**
 *@Project  :   cff_shell
 *@Package  :   ON
 *@Revision :   2.0.1
 *@Created  :   17/03/2017 11:57
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON;

import ON.Geography.Map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;


/**
 * <h1>Class used for calculating shortest path between a source and a target.</h1>
 * <a>Class used by the Main class.</a>
 * <h2>Long description</h2>
 * <p>It can be used without creating an new instance of this class.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.201</p>
 */
public class Dijkstra {

	protected static PriorityQueue<Vertex> vertexPriorityQueue = null;
	protected static List<Vertex> vertexes = null;
	private static List<String> alreadyVisited = null;
	private static HashMap<String, Vertex> way = null;

	/**
	 * Function to find the shortest path from source to target.
	 *
	 * @param map		(Map)			: Virtual map
	 * @param source	(String)		: source
	 * @param target	(String)		: destination
	 * @return 			(int)			- weight
	 */
	public static int findShortestPathWeight(Map map, String source, String target) {
		way = new HashMap<String, Vertex>();
		boolean found = false;
		int distance = 0;
		Vertex current;
		String src = source;
		alreadyVisited = new ArrayList<String>();
		if (vertexes == null) {
			vertexes = new ArrayList<Vertex>();
		}
		if (vertexPriorityQueue == null) {
			VertexPriority vertexPriority = new VertexPriority();
			vertexPriorityQueue = new PriorityQueue<Vertex>(map.size() * map.size(), vertexPriority);
		}
		while (!found) {
			if (target.equals(src)) {
				found = true;
				return distance;
			} else {
				// Put source into visited list. it will be reused by getPath.
				alreadyVisited.add(src);
				populateVertexPriorityQueue(map, src, distance, alreadyVisited);
				current = vertexPriorityQueue.poll();
				distance = current.getWeight();
				src = current.getName();
				//put vertex into hash map for retrieving path.
				way.put(current.getName(), current);
			}
		}
		return -1;
	}

	/**
	 * Function to find the shortest path from source to target.
	 *
	 * @param map		(Map)			: Virtual map
	 * @param source	(String)		: source
	 * @param target	(String)		: destination
	 * @return 			(String)		- name
	 */
	public static String findShortestPath(Map map, String source, String target) {
		way = new HashMap<String, Vertex>();
		boolean found = false;
		int distance = 0;
		Vertex current;
		String src = source;
		alreadyVisited = new ArrayList<String>();
		if (vertexes == null) {
			vertexes = new ArrayList<Vertex>();
		}
		if (vertexPriorityQueue == null) {
			VertexPriority vertexPriority = new VertexPriority();
			vertexPriorityQueue = new PriorityQueue<Vertex>(map.size()*map.size(), vertexPriority);
		}
		current = null;
		while (!found) {
			if (target.equals(src)) {
				found = true;
				return current.getPreviousCity();
			}
			else {
				// Put source into visited list. it will be reused by getPath.
				alreadyVisited.add(src);
				populateVertexPriorityQueue(map, src, distance, alreadyVisited);
				current = vertexPriorityQueue.poll();
				distance = current.getWeight();
				src = current.getName();
				//put vertex into hash map for retrieving path.
				way.put(current.getName(), current);
			}
		}
		return null;
	}

	/**
	 * Function to get shortest path from source to target .
	 *
	 * @param target	(String)		: city's name target
	 * @param source	(String)		: city's name source
	 * @return 			(List)			- ordered List of the shortest pat from source to target.
	 */
	public static List<Vertex> getPath(String target, String source) {
		PriorityQueue<Vertex> vertex = generatePath(target, source);
		List<Vertex> vertexList = new ArrayList<Vertex>();
		while (!vertex.isEmpty()) {
			vertexList.add(vertex.poll());
		}
		return vertexList;
	}

	/**
	 * Function to generate path.
	 *
	 * @param target 	(String)		: city's name target
	 * @param source 	(String)		: city's name source
	 * @return 			(PriorityQueue)	- ordered queue of the shortest pat from source to target.
	 */
	private static PriorityQueue<Vertex> generatePath(String target, String source) {
		System.err.println(alreadyVisited.size());
		System.err.println(way.size());
		PriorityQueue<Vertex> vertex = new PriorityQueue<Vertex>(alreadyVisited.size(), new VertexPriority());
		String src = target;
		while (!src.equals(source)) {
			vertex.add(way.get(src));
			src = way.get(src).getPreviousCity();
		}
		return vertex;
	}

	/**
	 * Function to print the shortest path from source to target.
	 *
	 * @param target	(String)		: city's name target
	 * @param source	(String)		: city's name source
	 */
	public static void printPath(String target, String source) {
		List<Vertex> vertexList = getPath(target, source);
		for (Vertex vertex : vertexList) {
			System.out.print(vertex);
		}
	}

	public static void getWeight(Map virtualMap, String source, String target) {
		findShortestPath(virtualMap, source, target);
	}

	/**
	 * Function to populate vertexPriorityQueue and auto set size if no more space left.
	 *
	 * @param map		(Map)			: Virtual map
	 * @param source	(String)		: source
	 * @param weight	(int)			: weight of the previous connection
	 */
	private static void populateVertexPriorityQueue(Map map, String source, int weight, List<String> visited) {
		// Getting list of connection for sources.
		List<String []> connections = map.getConnectionByName(source);
		Vertex newVertex;
		// Testing if the size of the queue have enough space
		if (vertexPriorityQueue.size() < connections.size() + vertexPriorityQueue.size()) {
			VertexPriority vertexPriority = new VertexPriority();
			PriorityQueue<Vertex> newQueue = new PriorityQueue<Vertex>(
					connections.size() + vertexPriorityQueue.size(),
					vertexPriority
			);
			// Generating a new queue.
			while (!vertexPriorityQueue.isEmpty()) {
				newQueue.add(vertexPriorityQueue.poll());
			}
			// Affecting new queue to the old one.
			vertexPriorityQueue = newQueue;
		}
		// For loop testing if connections between both object match with the source and if it has already been visited.
		for (String [] connection : connections) {
			if (connection[0].equals(source) && !visited.contains(connection[1])) {
				// Vertex not visited.
				newVertex = new Vertex(connection[1], source, weight + map.getWeight(connection));
				add(newVertex);
			}
			else if (connection[1].equals(source) && !visited.contains(connection[0])) {
				// Vertex not visited.
				newVertex = new Vertex(connection[0], source, weight + map.getWeight(connection));
				add(newVertex);
			}
		}
	}

	/**
	 * Function to add new vertex into priority queue.
	 *
	 * @param newVertex		(Vertex) 	: new vertex to add
	 */
	private static void add(Vertex newVertex) {
		if (!vertexPriorityQueue.contains(newVertex)) {
			// vertex not in the priority queue.
			vertexPriorityQueue.add(newVertex);
			vertexes.add(newVertex);
		}
		else {
			// vertex in the priority queue.
			for (int i = 0; i < vertexes.size(); i++) {
				if (vertexes.get(i).equals(newVertex) && vertexes.get(i).getWeight() > newVertex.getWeight()) {
					vertexPriorityQueue.remove(vertexes.get(i));
					vertexes.set(i, newVertex);
					vertexPriorityQueue.add(newVertex);
				}
			}
		}
	}
}

/**
 * <h1>lass to store data of the vertex used by Dijkstra's method.</h1>
 * <a>Class used by Dijkstra class.</a>
 * <h2>Long description</h2>
 * <p></p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.201</p>
 */
class Vertex {

	/**
	 * The name of the previous vertex.
	 */
	protected String previousCity;

	/**
	 * The weight of the vertex.
	 */
	protected int weight;

	/**
	 * The name of the vertex.
	 */
	protected String name;

	/**
	 * Constructor of the Vertex object.
	 * 		This constructor require the name and te weight of the vertex.
	 *
	 * @param name			(String)	: Vertex's name
	 * @param weight		(int)		: Vertex's weight
	 */
	public Vertex(String name, int weight) {
		this.weight = weight;
		this.name = name;
		this.previousCity = null;
	}

	/**
	 * Constructor of the Vertex object.
	 * 		This constructor require the name and te weight of the vertex.
	 *
	 * @param name			(String)	: Vertex's name
	 * @param weight		(int)		: Vertex's weight
	 */
	public Vertex(String name, String previousCity, int weight) {
		this.weight = weight;
		this.name = name;
		this.previousCity = previousCity;
	}

	/**
	 * Function to get weight of the vertex.
	 *
	 * @return				(int)		- weight
	 */
	public int getWeight() { return this.weight; }

	/**
	 * Function to get name of the vertex.
	 *
	 * @return				(String)	- name
	 */
	public String getName() { return this.name; }

	/**
	 * Function to set vertex weight.
	 *
	 * @param name			(String)	: Vertex's name
	 * @param weight		(int)		: Vertex's weight
	 */
	public void setWeight(String name, int weight) {
		if (this.name.equals(name)) {
			this.weight = weight;
		}
	}

	/**
	 * Function to get name of the previous vertex.
	 *
	 * @return				(String)	- name
	 */
	public String getPreviousCity() { return this.previousCity; }

	/**
	 * Function to set vertex.
	 *
	 * @param name			(String)	: Vertex's name
	 * @param weight		(int)		: Vertex's weight
	 */
	public void setVertex(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	/**
	 * Function to test if it has the same name as this one.
	 *
	 * @param o				(Object)	: object to test
	 * @return				(boolean)
	 */
	@Override
	public boolean equals(Object o) { return o instanceof Vertex ? (((Vertex) o).getName().equals(this.name)): false; }
}

/**
 * <h1>Class use to use overridden method compare while using PriorityQueue.add() method.</h1>
 * <a>Class used by Dijkstra class for setting the comparator while adding element into the Priority queue.</a>
 * <h2>Long description</h2>
 * <p>This class is an abstract class. It will allows PriorityQueue instance to compare specific element of the T object.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.201</p>
 */
class VertexPriority implements Comparator<Object> {

	/**
	 * Function to compare objects.
	 * 		both parameters require getWeight method.
	 *
	 * @param object		(object)	: First object to compare
	 * @param nextObject	(object)	: Second object to compare
	 * @return				(int)		- comparison between objects' weight
	 */
	@Override
	public int compare(Object object, Object nextObject) {
		if (object instanceof Vertex) {
			Vertex vertex = (Vertex) object;
			Vertex nextVertex = (Vertex) nextObject;
			return Integer.compare(vertex.getWeight(), nextVertex.getWeight());
		}
		return 0;
	}
}