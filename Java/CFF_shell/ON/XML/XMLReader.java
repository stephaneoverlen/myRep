/**
 *@Project  :   cff_shell
 *@Package  :   ON.XML
 *@Revision :   1.0.1
 *@Created  :   04/04/2017 10:52
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON.XML;

// Import Exceptions

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Import Objects

/**
 * <h1>Class to get content of an xml file.</h1>
 * <a>Class used by XMLParser class.</a>
 * <h2>Long description</h2>
 * <p>This class require XMLStruct and DTDFile class. XMLStruct is used for validating xml file used and for
   retrieving it data. DTDFile is used for extrating dtd file in case of not using a custom dtd in parameters.
   This class is used for validating and getting content of the xml file.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.101</p>
 */
class XMLReader{
	private String DTD  = "villes.xml.dtd";
	private XMLStruct structure;
	private Document doc;
	public HashMap <String[], String[]> hashmap;
	public List <String[]> list;


	/**
	 * Constructor of the XMLReader object.
	 * This constructor require the xml to use and the dtd of the xml.
	 *
	 * @param xml (String): xml to use.
	 * @param dtd (String): dtd to validate xml structure.
	 */
	public XMLReader (String xml, String dtd) {
		DocumentBuilder db;
		DOMSource source;
		Transformer transformer;
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
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		TransformerFactory tf = TransformerFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
			try {
				source = new DOMSource(db.parse(new File(xml)));
				try {
					try {
						transformer = tf.newTransformer();
						transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, this.DTD);
						StringWriter writer = new StringWriter();
						StreamResult result = new StreamResult(writer);
						try {
							transformer.transform(source, result);
						} catch (TransformerException e) {  //exception caught if xml file not valid according to dtd file
							System.err.println("XMLReader: Bad xml file: the file \'"
									+ xml
									+ "\' does not respect the default dtd use by the application." );
							System.err.println("Please run the program with --dtd <your dtd file>." );
							e.printStackTrace();
							System.exit(-1);
						}
						Document doc = db.parse(new InputSource(new StringReader(writer.toString())));
						this.doc = doc;
						this.structure = new XMLStruct(DTD);
						this.hashmap = null;
						this.list = null;
					} catch (TransformerConfigurationException e) {
						System.err.println("XMLReader: Transformer: configuration error.");
						e.printStackTrace();
						System.exit(-1);
					}
				} catch (IllegalArgumentException e) {
					System.err.println("XMLReader: DocumentBuilder: Unable to instance object.");
					e.printStackTrace();
					System.exit(-1);
				} catch (SAXException e) {
					System.err.println("XMLReader: Unable to parse XML file: " + xml);
					e.getStackTrace();
					System.exit(-1);
				} catch (IOException e) {
					System.err.println("XMLReader: I/O ERROR: " + xml);
					e.getStackTrace();
					System.exit(-1);
				}
			} catch (SAXException e) {
				System.err.println("XMLReader: Unable to parse XML file : " + xml);
				e.printStackTrace();
				System.exit(-1);
			} catch (IOException e) {
				System.err.println("DOM: I/O ERROR: " + xml);
				e.printStackTrace();
				System.exit(-1);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to get xml structure.
	 *
	 * @return xml structure
	 */
	public XMLStruct getStructure() {
		return structure;
	}

	/**
	 * Function to get dtd file in use.
	 *
	 * @return String dtd file name
 	 */
	public String getDTD() {
		return DTD;
	}

	/**
	 * Function to get content of the xml file.
	 *
	 * @param KeyLength (int): number of element into key
	 * @param from (int): index where the key's element start
	 * @param container (XMLContainer): container to retrieve data from xml file
	 */
	public void getContent(int KeyLength, int from, XMLStruct container) {
		String [] obj;
		String [] key;
		list = new ArrayList<String[]>();
		hashmap = new HashMap<String[], String[]>();
		doc.getDocumentElement().normalize();
		XMLStruct struct = this.structure;
		try {
			NodeList nList = doc.getElementsByTagName(container.getContainerName());
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					obj = new String[container.getSubContainer().size() - KeyLength];
					key = new String [KeyLength];
					int j = 0;
					for (int i = 0; i < container.getSubContainer().size(); i++) {
						if (KeyLength >= 1 && from == i || i - from < KeyLength) {
							key[i - from] = eElement.getElementsByTagName(
									(container.getSubContainer().get(i)).getContainerName()
							).item(0).getTextContent().split("[\t|\0|\n|\r| ]+")[1];
						} else {
							obj[j] = eElement.getElementsByTagName(
									(container.getSubContainer().get(i)).getContainerName()
							).item(0).getTextContent().split("[\t|\0|\n|\r| ]+")[1];
							j++;
						}
					}
					list.add(temp, key);
					hashmap.put(key, obj);
				}
			}
		} catch (NullPointerException e) {
			System.err.println("XMLReader: ERROR WHILE READING FILE CONTENT");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 *
	 * @param container (XMLContainer): container to retrieve data from xml file
	 * @return (String) - content of the container
	 */
	public String getTitle(XMLStruct container) {
		return doc.getElementsByTagName(container.getContainerName()).item(0).getTextContent();
	}
}
