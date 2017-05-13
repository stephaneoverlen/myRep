/**
 *@Project  :   cff_shell
 *@Package  :   ON.XML
 *@Revision :   1.0.1
 *@Created  :   04/04/2017 10:53
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON.XML;

// Import exceptions

import ON.Geography.City;
import ON.Geography.Connection;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Import Objects


/**
 * <h1>class to create and write xml file.</h1>
 * <a>Class used by XMLParser class.</a>
 * <h2>Long description</h2>
 * <p>This class require XMLStruct class, it is use for generating a xml file according to the dtd file structure.
   This class is used for writing content into xml file.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.101</p>
 */
class XMLWriter {
	private Document doc;
	private String DTD  = "villes.xml.dtd";
	private XMLStruct structure;
	private String xml;
	/**
	 * Constructor of the XMLWriter object.
	 * This constructor require the xml to use and the dtd to validate the xml file structure.
	 *
	 * @param xml_name  (String): xml file to use
	 * @param dtd       (String): Dtd file to use for building the xml file.
	 */
	public XMLWriter(String xml_name, String dtd) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			DTDFile DTDExtractor = new DTDFile(null);
			if ((dtd == null)) {
				DTDExtractor.extractDTDFileTo(this.DTD);
			} else if (!(new File(dtd).exists())) {
				DTDExtractor.extractDTDFileTo(this.DTD);
			} else if (!(new File(dtd).isFile())) {
				DTDExtractor.extractDTDFileTo(this.DTD);
			}
			this.DTD = (dtd != null) ? (
					new File(dtd).exists() ? (
							new File(dtd).isFile() ? dtd : this.DTD
					) : this.DTD
			) : this.DTD;
			this.structure = new XMLStruct(this.DTD);
			builder = dbFactory.newDocumentBuilder();
			this.doc = builder.newDocument();
			DOMImplementation domImpl = this.doc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType(xml_name, "cff shell v1.0", dtd);
			this.doc.appendChild(doctype);
			this.xml = xml_name;
		} catch (ParserConfigurationException e) {
			System.err.println("XMLWriter: DocumentBuilder: Unable to configure.");
			e.getStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Function to write content into xml file.
	 *
	 * @param cities        (HashMap): List of cities object with name as key
	 * @param connections   (HashMap): List of connection between cities
	 * @param distances     (HashMap): List of distances with array of connection as key
	 * @param citiesName    (List): List of cities' name
	 * @param title         (String): Title of the xml
	 */
	public void Write(
			HashMap<String, City> cities,
			HashMap<Integer, String[]> connections,
			HashMap<String[], Integer> distances,
	        List<String> citiesName,
	        String title,
	        String titleTag
	) {
		int i = 0;
		int j = 0;
		ArrayList<String> map;
		HashMap<String[], Connection> connectionHashMap = new HashMap<String [], Connection>();
		List<String[]> connectionList = new ArrayList<String[]>();
		Element rootElement = doc.createElement(structure.getContainerName());
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
		Element element;
		map = structure.getContainerRoad(structure, titleTag);
		element = rootElement;
		element = createTree(element, map); // return the element to write content
		Element added = doc.createElement(map.get(map.size() - 1));
		element.appendChild(added);
		element = added;
		WriteContent(element, title);
		map = structure.getContainerRoad(structure, cities.get(citiesName.toArray()[0]).toString());
		element = rootElement;
		element = createTree(element, map);
		WriteElements(element, citiesName, cities, structure);
		map = structure.getContainerRoad(structure, connectionHashMap.get(connectionList.toArray()[0]).toString());
		element = rootElement;
		element = createTree(element, map);
		WriteElements(element, connectionList, connectionHashMap, structure);
		this.doc.appendChild(rootElement);
		try {
			Transformer tr = TransformerFactory.newInstance().newTransformer();

			tr.setOutputProperty(OutputKeys.INDENT, "yes");
			tr.setOutputProperty(OutputKeys.METHOD, "xml");
			tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			tr.setOutputProperty(OutputKeys.VERSION, "1.0");
			tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, this.DTD);
			tr.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, this.DTD);
			try {
				tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(xml)));
			} catch (TransformerException e) {
				System.err.println("XMLWriter: File is invalid");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.print("XMLWriter: Unable to create file " + this.xml);
				e.printStackTrace();
			}
		} catch (TransformerConfigurationException e) {
			System.out.println("XMLWriter: Transformer: Unable to create new instance");
			e.printStackTrace();
		}
	}

	/**
	 * Function to create tree elements.
	 *
	 * @param addToElement (Element):
	 * @param Strings (StringArray): String array used for creating elements.
	 * @return Last created element.
	 */
	private Element createTree (Element addToElement, ArrayList<String> Strings) {
		Element element = addToElement;
		Element added;
		for (String name : Strings) {
			if (!name.equals(addToElement.getTagName()) && !name.equals(Strings.get(Strings.size() - 1))) {
				added = doc.createElement(name);
				element.appendChild(added);
				element = added;
			}
		}
		return element;
	}


	/**
	 * Function to add element with pc data value to an element.
	 *
	 * @param addToElement (Element): where the created element will be stored
	 * @param list (List): List to get object from object
	 * @param object (HashMap): pc data to write into created element (Multiples element inside)
	 * @param structure (XMLStructure): control structure to create xml file
	 */
	public void WriteElements(Element addToElement, List list, HashMap object, XMLStruct structure) {
		XMLStruct toCreate;
		String objName;
		for (Object l : list) {
			Object o_tmp = object.get(l);
			if (o_tmp instanceof Connection) {
				Connection o = (Connection) o_tmp;
				objName = o.toString();
				toCreate = structure.findContainer(structure, objName);
				if (addToElement.getTagName().equals(toCreate.getPrevious().getContainerName())) {
					if (toCreate.getContainerName().equals(objName)) {
						Element element = doc.createElement(toCreate.getContainerName());
						addToElement.appendChild(element);
						List<XMLStruct> containers = toCreate.getSubContainer();
						if (o.hasAttribute()) {
							String[] attribute = o.getAttribute();
							int j = 0, i = 0;
							while (j < toCreate.getSubContainer().size()) {
								if (containers.get(i).isContainsData()) {
									Element subElement = doc.createElement(containers.get(i).getContainerName());
									WriteContent(subElement, attribute[j]);
									//subElement.setTextContent(attribute[j]);
									element.appendChild(subElement);
									j++;
								}
								i++;
							}
						}
					}
				}
			}
			if (o_tmp instanceof City) {
				City o = (City) o_tmp;
				objName = o.toString();
				toCreate = structure.findContainer(structure, objName);
				if (addToElement.getTagName().equals(toCreate.getPrevious().getContainerName())) {
					if (toCreate.getContainerName().equals(objName)) {
						Element element = doc.createElement(toCreate.getContainerName());
						addToElement.appendChild(element);
						List<XMLStruct> containers = toCreate.getSubContainer();
						if (o.hasAttribute()) {
							String[] attribute = o.getAttribute();
							int j = 0, i = 0;
							while (j < toCreate.getSubContainer().size()) {
								if (containers.get(i).isContainsData()) {
									Element subElement = doc.createElement(containers.get(i).getContainerName());
									WriteContent(subElement, attribute[j]);
									//subElement.setTextContent(attribute[j]);
									element.appendChild(subElement);
									j++;
								}
								i++;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Function to add element with pc data  value to an element.
	 *
	 * @param addToElement (Element): where the created element will be stored
	 * @param content (String): pc data to write into created element
	 */
	public void WriteContent (Element addToElement, String content) {
		String elementName = addToElement.getTagName();
		Element element;
		if (addToElement.getTagName().equals(addToElement.getTagName())) {
		//	if (addToElement.isContainsData()) {
			addToElement.setTextContent(content);
		//	}
		}
	}
}
