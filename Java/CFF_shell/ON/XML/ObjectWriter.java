/**
 *@Project  :   cff_shell
 *@Package  :   ON.XML
 *@Revision :   1.0.1
 *@Created  :   04/04/2017 10:51
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Import Objects

/**
 * <h1>Class used in case of XMLWriter failed.</h1>
 * <a>This classe is used by XMLParser class.</a>
 * <h2>Long description</h2>
 * <p>This class require XMLStruct class to write content into xml file. it is an abstract class for getting content of
   HashMap object that use different representation of data.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.101</p>
 */
class ObjectWriter<Key, Obj> {

	private Document doc;

	/**
	 * Constructor of the object ObjectWriter<Key, Obj>.
	 * This constructor require the document to use.
	 *
	 * @param doc (Document) : Document to use for writing data
	 */
	public ObjectWriter(Document doc) { this.doc = doc; }

	/**
	 * Constructor of the object ObjectWriter<Key, Obj>.
	 * This constructor require the xml to use and the dtd to validate the xml file structure.
	 *
	 * @param xml_name  (String): xml file to use
	 * @param dtd       (String): Dtd file to use for building the xml file.
	 */
	public ObjectWriter(String xml_name, String dtd) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = dbFactory.newDocumentBuilder();
			this.doc = builder.newDocument();
			DOMImplementation domImpl = this.doc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType(xml_name, "cff shell v1.0", dtd);
			this.doc.appendChild(doctype);
		} catch (ParserConfigurationException e) {
			System.err.println("XMLWriter: DocumentBuilder: Unable to configure.");
			e.getStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Function for setting content into xml file.
	 *
	 * @param title (String): Title of the xml
	 * @param rootElement (Element): root element
	 * @param structure (XMLStruct): Structure of the xml file to write
	 */
	public void setTitle(String title, Element rootElement, XMLStruct structure) {
		WriteContent(rootElement, title);
	}

	/**
	 * Function for getting document.
	 *
	 * @return (Document)
	 */
	public Document getDoc () { return this.doc; }

	/**
	 * Function for creating root tag into the xml file.
	 *
	 * @param structure (XMLStruct): Structure of the xml file to write
	 * @return (Element) - root element
	 */
	public Element createRoot(XMLStruct structure) {
		return doc.createElement(structure.getContainerName());
	}

	/**
	 * Function to write content into xml file.
	 *
	 * @param structure     (XMLStruct): Structure of the xml file to write
	 * @param object        (HashMap): hash map of object
	 * @param objectList    (List): List of object
	 * @param rootElement   (Element): root element to use
	 */
	public void Write(
			XMLStruct structure,
			HashMap<Key, Obj> object,
			List<Key> objectList,
			Element rootElement
	) {
		ArrayList<String> map;
		this.doc.appendChild(rootElement);
		Element element;
		//WriteContent(rootElement, title, structure);
		map = structure.getContainerRoad(structure, object.get(objectList.toArray()[0]).toString());
		element = doc.getElementById(structure.getContainerName());
		element = createTree(element, map);
		WriteElements(element, objectList, object, structure);
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
			added = doc.createElement(name);
			element.appendChild(added);
			element = added;
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
	public void WriteElements(Element addToElement, List<Key> list, HashMap<Key, Obj> object, XMLStruct structure) {
		XMLStruct toCreate;
		String objName;
		for (Key l : list) {
			Obj o_tmp = object.get(l);
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
		if (addToElement.getTagName().equals(addToElement.getTagName())) {
			//	if (addToElement.isContainsData()) {
			addToElement.setTextContent(content);
			//	}
		}
	}
}
