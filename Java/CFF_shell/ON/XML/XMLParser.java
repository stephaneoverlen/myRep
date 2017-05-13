/**
 *@Project  :   cff_shell
 *@Package  :   ON.XML
 *@Revision :   1.0.1
 *@Created  :   17/03/2017 11:57
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON.XML;

// Import Objects

import ON.Geography.City;
import ON.Geography.Connection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * <h1>Class used to parse xml files.</h1>
 * <a>Class used by Map class.</a>
 * <h2>Long description</h2>
 * <p>This class is use for retrieving data from a xml file and writing data to an xml file.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.101</p>
 */
public class XMLParser {
	protected HashMap<String, City> cities_d;
	protected HashMap<Integer, String []> connections;
	protected HashMap<String [], Integer> distances;
	protected List<String> cities_l;
	protected List<String []> connections_l;
	protected XMLStruct structure;
	private String file;
	private String dtd = null;
	private String XML;
	private String title;
	private String TitleTag;

	/**
	 * This function is used to retrieve the data from the XML map file.
	 *
	 * @param File (String): The file name of the initial map to use.
	 */
	public XMLParser (String File) {
		File XML = new File(File);
		this.file = File;
		cities_d = new HashMap<String, City>();
		connections = new HashMap<Integer, String []>();
		distances = new HashMap<String [], Integer>();
		cities_l = new ArrayList<String>();
		connections_l = new ArrayList<String []>();
	}


	public XMLParser (
			List<String> cities_l,
		 	HashMap<String, City> cities_d,
			HashMap<Integer, String[]> connections,
			HashMap<String [], Integer> distances,
			List<String []> connections_l,
			String file,
			String XML
	) {
		this.cities_d = cities_d;
		this.cities_l = cities_l;
		this.connections = connections;
		this.connections_l = connections_l;
		this.distances = distances;
		this.file = file;
		this.XML = XML;
	}
	/**
	 *
	 * @param argv (String): Command line arguments of the program
	 */
	public static void main(String argv[]) {
		try {
			File fXmlFile = new File(argv[0]);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName(argv[1]);
			System.out.println("----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println(argv[2] + " " + eElement.getElementsByTagName(argv[2]).item(0).getTextContent());
					System.out.println(argv[3] + " " + eElement.getElementsByTagName(argv[3]).item(0).getTextContent());
					System.out.println(argv[4] + " " + eElement.getElementsByTagName(argv[4]).item(0).getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String split(String toSplit, String split) {
		return toSplit.split(split)[0];
	}

	/**
	 * Function to get content of the xml file.
	 */
	public void getContent() {
		List<String []> keys;
		HashMap<String [], String []> map;
		XMLReader reader = new XMLReader(this.file, this.dtd);
		this.structure = reader.getStructure();
		this.TitleTag = structure.getSubContainer().get(0).getContainerName();
		this.title = reader.getTitle(structure.getSubContainer().get(0));
		reader.getContent(1, 0, structure.getSubContainer().get(1));
		keys = reader.list;
		map = reader.hashmap;
		for (String[] key : keys) {
			cities_d.put(
					key[0],
					new City(
							key[0],
							Integer.valueOf(map.get(key)[0]),
							Integer.valueOf(map.get(key)[1])
					)
			);
			cities_l.add(key[0]);
		}
		reader.getContent(2, 0, structure.getSubContainer().get(2));
		keys = reader.list;
		map = reader.hashmap;
		connections_l = keys;
		for (int i = 0; i < keys.size(); i++) {
			connections.put(i, keys.get(i));
			distances.put(keys.get(i), Integer.valueOf(map.get(keys.get(i))[0]));
		}
	}

	/**
	 * Function to get content of the xml file using specific dtd.
	 *
	 * @param dtd (String): specific dtd file
	 */
	public void getContent(String dtd) {
		List<String []> keys;
		HashMap<String [], String []> map;
		XMLReader reader = new XMLReader(this.file, dtd);
		this.structure = reader.getStructure();
		this.TitleTag = structure.getSubContainer().get(0).getContainerName();
		this.title = reader.getTitle(structure.getSubContainer().get(0));
		reader.getContent(1, 0, structure.getSubContainer().get(1));
		keys = reader.list;
		map = reader.hashmap;
		for (String[] key : keys) {
			cities_d.put(key[0], new City(key[0], Integer.valueOf(map.get(key)[0]), Integer.valueOf(map.get(key)[1])));
			cities_l.add(key[0]);
		}
		reader.getContent(2, 0, structure.getSubContainer().get(2));
		keys = reader.list;
		map = reader.hashmap;
		connections_l = keys;
		for (int i = 0; i < keys.size(); i++) {
			connections.put(i, keys.get(i));
			distances.put(keys.get(i), Integer.valueOf(map.get(keys.get(i))[0]));
		}
	}

	/**
	 * Function to write XML file using content of this object and dtd structure.
	 */
	public void parseContent(String file) {
		XMLWriter writer = new XMLWriter(file, dtd);
		writer.Write(
				this.cities_d,
				this.connections,
				this.distances,
				this.cities_l,
				this.title,
				this.TitleTag
		);
	}

	/**
	 * Function to write XML file using specific content and dtd structure.
	 * @param cities (HashMap): List of cities object whit name as key
	 * @param connections (HashMap): List of connection between cities
	 * @param distances (HashMap): List of distances with array of connection as key
	 * @param citiesName (List): Array of string, it contains all cities' name.
	 */
	public void parseContent(
			HashMap<String, City> cities,
            HashMap<Integer, String []> connections,
            HashMap<String [], Integer> distances,
	        List<String> citiesName
	) {
		XMLWriter writer = new XMLWriter(file, dtd);
		writer.Write(cities, connections, distances, citiesName, this.title, this.TitleTag);
		File f = new File(file);
		if(f.exists() && !f.isDirectory()) {
			int i = 0;
			int j = 0;
			ArrayList<String> map;
			HashMap<String[], Connection> connectionHashMap = new HashMap<String [], Connection>();
			List<String[]> connectionList = new ArrayList<String[]>();
			while (j < connections.size()) {
				if (connections.containsKey(i)) {
					String [] connection = connections.get(i);
					Integer distance = distances.get(connection);
					connectionHashMap.put(connection, new Connection(connection[0], connection[1], distance));
					connectionList.add(j, connection);
					j++;
				}
				i++;
			}
			ObjectWriter<String, String> Writer = new ObjectWriter<>(file, dtd);
			Element rootElement = Writer.createRoot(structure);
			Writer.setTitle(title, rootElement, structure);
			ObjectWriter<String, City> OWriter = new ObjectWriter<String, City>(Writer.getDoc());
			OWriter.Write(structure,cities, citiesName, rootElement);
			ObjectWriter<String[], Connection> OWriter1 = new ObjectWriter<String[], Connection>(OWriter.getDoc());
			OWriter1.Write(structure, connectionHashMap, connectionList, rootElement);
		}
	}

	/**
	 * Function to get cities data with city's name as key.
	 *
	 * @return private cities' data HashMap
	 */
	public HashMap<String, City> getCities() { return this.cities_d; }

	/**
	 * Function to get cities' connection unique key.
	 *
	 * @return private cities' connection HashMap
	 */
	public HashMap<Integer, String []> getConnections() { return  this.connections; }

	/**
	 * Function to get cities' connection distance with city's connection as key.
	 *
	 * @return private cities' connection distance HashMap
	 */
	public HashMap<String [], Integer> getDistances() { return this.distances; }

	/**
	 * Function to get cities' name list.
	 *
	 * @return private cities list
	 */
	public List<String> getCitiesList() { return this.cities_l; }

	/**
	 * Function to set cities data with city's name as key.
	 *
	 * @param cities (HashMap): List of cities object with name as key
	 */
	public void setCities(HashMap<String, City> cities) { this.cities_d = cities; }

	/**
	 * Function to set cities' connection unique key.
	 *
	 * @param connections (HashMap): List of connection between cities
	 */
	public void setConnections(HashMap<Integer, String []> connections) { this.connections = connections; }

	/**
	 * Function to set cities' connection distance with city's connection as key.
	 *
	 * @param distances (HashMap): List of distances with array of connection as key
	 */
	public void setDistances(HashMap<String [], Integer> distances) { this.distances = distances; }

	/**
	 ** Function to get connections' list.
	 *
	 * @return private connections list
	 */
	public List<String []> getConnectionsList() { return this.connections_l; }

	/**
	 ** Function to set connections' list.
	 *
	 * @param list (List): list of connections
	 */
	public void setConnectionsList(List<String []> list)  { this.connections_l = list; }

	/**
	 * Function to set cities' name list.
	 *
	 * @param list (List): list of cities (name only)
	 */
	public void setCitiesList(List<String> list) { this.cities_l = list; }
}

