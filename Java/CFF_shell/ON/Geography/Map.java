/**
 *@Project  :   cff_shell
 *@Package  :   ON.Geography
 *@Revision :   1.0.1
 *@Created  :   17/03/2017 11:57
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */
package ON.Geography;

import ON.XML.XMLParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>This class contains all method for getting and setting content of the virtual map.</h1>
 * <a>Class used by the main class.</a>
 * <h2>Long description</h2>
 * <p>This class is used for storing the virtual map generated by the XMLParser class.
   It can be compared to a database due to his data representation.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.201</p>
 */
public class Map {

	/**
	 * This object is the string of the xml file in use.
	 */
	String XML;

	/**
	 * This object contains the list of all cities' name.
	 */
	private List<String> cities_l;

	/**
	 * This object is an hash map of all cities.
	 */
	private HashMap<String, City> cities;

	/**
	 * This object is an hash map of all connections.
	 * Key is an unique id.
	 * Object is an array containing tha name of the two connected cities.
	 */
	private HashMap<Integer, String []> connections;

	/**
	 * This object is an hash map of all distances.
	 * Key is an array containing the name of the two connected cities.
	 * Object is the weight of the connection.
	 */
	private HashMap<String [], Integer> distances;

	/**
	 * This object contains the list of all array containing the name of the two connected cities.
	 */
	private List<String []> connections_l;

	/**
	 * This object is the xml parser use to retrieve data from an xml file and store data to an xml file.
	 */
	private XMLParser parser;

	/**
	 * Function to get number of cities.
	 *
	 * @return 				(int)			- number of cities
	 */
	public int size() { return cities_l.size(); }

	/**
	 * Constructor of the virtual map.
	 		This constructor require the name of the file for getting the virtual map.
	 *
	 * @param file 			(String) 		: file to use for generating virtual map
	 */
	public Map(String file){
		parser = new XMLParser(file);
		parser.getContent();
		this.cities_l = parser.getCitiesList();
		this.cities = parser.getCities();
		this.connections = parser.getConnections();
		this.distances = parser.getDistances();
		this.XML = file;
		this.connections_l = parser.getConnectionsList();
	}

	/**
	 * Function to get connection weight.
	 *
	 * @param city 			(String)		: name of the first city
	 * @param nextCity		(String)		: name of the second city
	 * @return 				(int) 			- weight of the connection ( is an error)
	 */
	public Integer getWeight(String city, String nextCity) {
		for (String [] connection : connections_l) {
			if (connection[0].equals(city) && connection [1].equals(nextCity) && connections.containsValue(connection))
			{
				if (this.cities.containsKey(city) && this.cities.containsKey(nextCity)) {
					return this.distances.get(connection);
				}
				else {
					delConnection(connection);
					System.err.println("Deleting connection " + city + " " + nextCity);
					try {
						System.in.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (connection[0].equals(nextCity) && connection [1].equals(city) && connections.containsValue(connection))
			{
				if (this.cities.containsKey(city) && this.cities.containsKey(nextCity)) {
					return this.distances.get(connection);
				}
				else {
					delConnection(connection);
					System.err.println("Deleting connection " + city + " " + nextCity);
					try {
						System.in.read();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
		return Integer.MAX_VALUE;
	}

	/**
	 * Function to get connection weight.
	 *
	 * @param connection 	(String array)	: String array of the connection (contains name of the both connected cities)
	 * @return 				(int)			- weight of the connection (-1 is an error)
	 */
	public Integer getWeight(String [] connection) {
		String tmp;
		if (this.connections.containsValue(connection)) {
			if (this.cities.containsKey(connection[0]) && this.cities.containsKey(connection[1])) {
				return this.distances.get(connection);
			}
			else
				delConnection(connection);
		}
		tmp = connection[0];
		connection[0] = connection[1];
		connection[1] = tmp;
		if (this.connections.containsValue(connection)) {
			if (this.cities.containsKey(connection[0]) && this.cities.containsKey(connection[1])) {
				return this.distances.get(connection);
			}
			else
				delConnection(connection);
		}
		return Integer.MAX_VALUE;
	}

	/**
	 *
	 * @param name 			(String)		: name of the city
	 * @return 				(City)			- City's object
	 */
	public City getCity(String name) { return this.cities.get(name); }

	/**
	 * Function to get longitude of a specific city.
	 *
	 * @param name 			(String)		: name of the city
	 * @return 				(int)			- longitude of the city
	 */
	public int getLongitude(String name) { return this.cities.get(name).getLongitude(); }

	/**
	 * Function to get latitude of a specific city.
	 *
	 * @param name 			(String)		: name of the city
	 * @return 				(int)			- latitude of the city
	 */
	public int getLatitude(String name) { return this.cities.get(name).getLatitude(); }

	/**
	 * Function to get List of cities.
	 *
	 * @return				(List)			- List of cities.
	 */
	public List<String> getCitiesList() { return  this.cities_l; }

	/**
	 * Function to get hash map of cities.
	 *
	 * @return				(HashMap)		- Hash map of cities.
	 */
	public HashMap<String, City> getCities() { return this.cities; }

	/**
	 * Function to get connections' hashmap size.
	 *
	 * @return				(int)			- connections' hashmap size.
	 */
	public int getConnectionsSize() { return this.connections.size(); }

	/**
	 * Function to get List of connections.
	 *
	 * @return				(List)			- List of connections.
	 */
	public List<String []> getConnectionsList () { return this.connections_l; }

	/**
	 * Function to get all connection for given city's name.
	 *
	 * @param cityName		(String)		: City's name
	 * @return 				(List)			- List of connections.
	 */
	public List<String[]> getConnectionByName(String cityName) {
		List<String[]> array = new ArrayList<String[]>();
		String[] tmp;
		for (String [] connection : this.connections_l) {
			if (connection[0].equals(cityName) || connection[1].equals(cityName)) {
				array.add(connection);
			}
		}
		return array;
	}

	/**
	 * Function to get hash map of distances.
	 *
	 * @return 				(HashMap)		- Hash map of distances.
	 */
	public HashMap<String[], Integer> getDistances() { return this.distances; }

    /**
	 * Function to get existence of a connection.
	 *
	 * @param connection	(String array)	: String array of the connection (contains name of the both connected cities)
	 * @return				(boolean)
	 */
	public boolean isEdge(String [] connection) {

		for (String [] tmp : connections_l) {
			if (
				tmp[0].equals(connection[0]) && tmp[1].equals(connection[1]) ||
				tmp[1].equals(connection[0]) && tmp[0].equals(connection[1])
			) {
				if (this.connections.containsValue(tmp))
					return true;
			}
		}
		return false;
	}

	/**
	 * Function to get existence of a connection.
	 *
	 * @param city			(String)		: name of the first city
	 * @param nextCity		(String)		: name of the second city
	 * @return 				(boolean)
	 */
	public boolean isEdge(String city, String nextCity) {
		for (String [] tmp : connections_l) {
			if (tmp[0].equals(city) && tmp[1].equals(nextCity) || tmp[1].equals(city) && tmp[0].equals(nextCity)) {
				if (this.connections.containsValue(tmp))
					return true;
			}
		}
		return false;
	}

	/**
	 * Function to get connection between two cities.
	 *
	 * @param city1			(String)		: First city to get connect
	 * @param city2			(String)		: Second city to get connect
	 * @return				(int[][] | null): coordinate of the both cities
	 */
	public Double[][] getConnection(String city1, String city2) {
		String [] connection = null;
		for (String [] tmp : connections_l) {
			if (tmp[0].equals(city1) && tmp[1].equals(city2) || tmp[1].equals(city1) && tmp[0].equals(city2)) {
				connection = tmp;
				break;
			}
		}
		if (connection != null) {
			Double[][] connectionCoordinate = new Double[2][2];
			if (this.connections.containsValue(connection)) {
				if (this.cities.containsKey(city1) && this.cities.containsKey(city2)) {
					connectionCoordinate[0][0] = Double.valueOf(this.cities.get(city1).getLongitude());
					connectionCoordinate[0][1] = Double.valueOf(this.cities.get(city1).getLatitude());
					connectionCoordinate[1][0] = Double.valueOf(this.cities.get(city2).getLongitude());
					connectionCoordinate[1][1] = Double.valueOf(this.cities.get(city2).getLatitude());
					return connectionCoordinate;
				}
			}
		}
		return null;
	}

	/**
	 * Function to add city to the map.
	 *
	 * @param name			(String)		: name of the city
	 * @param longitude		(Integer)		: longitude of the city
	 * @param latitude		(Integer)		: latitude of the city
	 * @return				(boolean)
	 */
	public boolean addCity(String name, int longitude, int latitude) {
		if (!this.cities.containsKey(name)) {
			this.cities.put(name, new City(name, longitude, latitude));
			this.cities_l.add(name);
			return true;
		}
		return false;
	}

	/**
	 * Function to add connection between two cities.
	 *
	 * @param city1			(String)		: Name of the first city of the connection
	 * @param city2			(String)		: Name of the Second city of the connection
	 * @param weight		(int)			: weight of the connection
	 * @return				(boolean)
	 */
	public boolean addConnection (String city1, String city2, int weight) {
		String [] connection = new String[2];
		if (this.cities.containsKey(city1) && this.cities.containsKey(city2)) {
			connection [0] = city1;
			connection [1] = city2;
			this.connections.put(this.connections.size(), connection);
			this.distances.put(connection, weight);
			this.connections_l.add(connection);
			return  true;
		}
		return false;
	}

	/**
	 * Function to delete specific city and all of it relative data such connections and distances of them.
	 *
	 * @param name		(String)			: name of the city
	 */
	public void delCity (String name){
		int size = connections_l.size();
		if (this.cities.containsKey(name)) {
			for (int i = 0; i < size; i++)
				if (connections_l.get(i)[0].equals(name) || connections_l.get(i)[1].equals(name)) {
					this.distances.remove(connections_l.remove(i));
					this.connections.remove(i);
					size = connections_l.size();
				}
			this.cities.remove(name);
			this.cities_l.remove(name);
		}
	}

	/**
	 * Function to delete connection between specific cities.
	 *
	 * @param city1			(String)		: name of the first city of the connection
	 * @param city2			(String)		: name of the second city of the connection
	 */
	public void delConnection(String city1, String city2) {
		String [] connection = new String[2];
		connection[0] = city1;
		connection[1] = city2;
		if (this.connections.containsValue(connection)) {
			for (int i = 0; i < this.connections.size(); i++) {
				if (this.connections.get(i) == connection)
					this.distances.remove(this.connections.remove(i));
			}
		}
		connection[0] = city2;
		connection[1] = city1;
		if (this.connections.containsValue(connection)) {
			for (int i = 0; i < this.connections.size(); i++) {
				if (this.connections.get(i) == connection)
					this.distances.remove(this.connections.remove(i));
			}
		}
	}

	/**
	 * Function to delete connection between specific cities.
	 *
	 * @param connection	(String array)	: String array of the connection (contains name of the both connected cities)
	 */
	public void delConnection(String [] connection) {
		String tmp;
		if (this.connections.containsValue(connection)) {
			for (int i = 0; i < this.connections.size(); i++) {
				if (this.connections.get(i) == connection)
					this.distances.remove(this.connections.remove(i));
			}
		}
		tmp = connection[0];
		connection[0] = connection[1];
		connection[1] = tmp;
		if (this.connections.containsValue(connection)) {
			for (int i = 0; i < this.connections.size(); i++) {
				if (this.connections.get(i) == connection)
					this.distances.remove(this.connections.remove(i));
			}
		}
	}

	/**
	 * Function to save the map into xml file.
	 *
	 * @param file			(String)		: filename where the content will be stored
	 */
	public void save(String file) {
		parser.setCities(this.cities);
		parser.setCitiesList(this.cities_l);
		parser.setConnections(connections);
		parser.setConnectionsList(connections_l);
		parser.setDistances(this.distances);
		parser.parseContent(file);
	}
}