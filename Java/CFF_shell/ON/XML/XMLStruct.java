/**
 *@Project  :   cff_shell
 *@Package  :   ON.XML
 *@Revision :   1.0.1
 *@Created  :   04/04/2017 10:53
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON.XML;

// Import Exceptions

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Import Objects

/**
 * <h1>Xml structure</h1>
 * <a>Class used by XMLParser, XMLWriter, XMLReader and ObjectWriter class.</a>
 * <h2>Long description</h2>
 * <p>This class is used for getting structure of the xml file using a dtd file.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.101</p>
 */
class XMLStruct {
	private boolean ContainsData;
	private String ContainerName;
	private List<ON.XML.XMLStruct> subContainer;
	private ON.XML.XMLStruct previous;
	/**
	 * Only used by the root element while adding sub container with no references.
	 */
	private List<ON.XML.XMLStruct> unAdd;

	/**
	 * Constructor of the object XMLStruct.
	 *      this constructor require the filename of the dtd file.
	 *
	 * @param dtd (string): dtd file.
	 */
	public XMLStruct(String dtd) {
		String line;
		this.previous = null;
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(dtd));
			this.subContainer = new ArrayList<ON.XML.XMLStruct>();
			this.unAdd = new ArrayList<ON.XML.XMLStruct>();
			while((line = buffer.readLine()) != null) {
				parseStruct(line);
			}
			if (unAdd.size() != 0) {
				tryAddUnAdd();
			}
		} catch (FileNotFoundException e) {
			System.err.println("XMLStruct: File \'" + dtd + "\' not found.");
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("XMLStruct: I/O error.");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * default constructor.
	 *      This constructor is only used by this object.
	 */
	private XMLStruct() {
		this.subContainer = new ArrayList<ON.XML.XMLStruct>();
		this.unAdd = new ArrayList<ON.XML.XMLStruct>();
	}

	/**
	 * XMLStruct object constructor.
	 *      This constructor is only used by this object.
	 */
	private XMLStruct(ON.XML.XMLStruct struct) {
		this.subContainer = new ArrayList<ON.XML.XMLStruct>();
		this.unAdd = new ArrayList<ON.XML.XMLStruct>();
		this.ContainerName = struct.getContainerName();
		this.subContainer = struct.getSubContainer();
		this.ContainsData = struct.isContainsData();
		this.previous = struct.getPrevious();

	}

	/**
	 * XMLStructure constructor.
	 *  this constructor require the name of the container and a boolean to set data flag.
	 *
	 * @param name (String): name of this container.
	 * @param containData (boolean): set data container to false.
	 */
	private XMLStruct(String name, boolean containData, ON.XML.XMLStruct previous) {
		this.ContainerName = name;
		this.ContainsData = containData;
		this.previous = previous;

	}

	/**
	 * Recursive function to find container with given name.
	 *
	 * @param struct (XMLStruct):
	 * @param containerName (String):
	 * @return XMLStruct
	 */
	public ON.XML.XMLStruct findContainer(ON.XML.XMLStruct struct, String containerName) {
		ON.XML.XMLStruct tmpStruct = new ON.XML.XMLStruct(struct);
		List<ON.XML.XMLStruct> containers = tmpStruct.getSubContainer();
		if (tmpStruct.getContainerName().equals(containerName))
			return tmpStruct;
		else {
			if (containers == null) {
				return struct;
			}
			for (ON.XML.XMLStruct container : containers) {
				tmpStruct = findContainer(container, containerName);
				if (tmpStruct.getContainerName().equals(containerName)) {
					return tmpStruct;
				}
			}
		}
		return tmpStruct;
	}

	/**
	 * Recursive function to get container's road with given name.
	 *
	 * @param structure (XMLStruct):
	 * @param containerName (String):
	 * @return ArrayList of the road to go to container
	 */
	public ArrayList<String> getContainerRoad(ON.XML.XMLStruct structure, String containerName) {
		ON.XML.XMLStruct struct = new ON.XML.XMLStruct(structure);
		ArrayList<String> road = new ArrayList<String>();
		ArrayList<String> tmproad = new ArrayList<String>();
		boolean found = false;
		List<ON.XML.XMLStruct> containers = struct.getSubContainer();
		if (struct.getContainerName().equals(containerName))
			return road;
		else {
			road.add(struct.getContainerName());
			for (ON.XML.XMLStruct container : containers) {
				if (container.getContainerName().equals(containerName)) {
					road.add(container.getContainerName());
					return road;
				}
				else {
					tmproad = getContainerRoad(container, containerName);
					for (String road_object : tmproad)
						if (road_object.equals(containerName))
						{
							found = true;
						}
					if (found) {
						road.addAll(getContainerRoad(container, containerName));
						return road;
					}
				}
			}
		}
		return road;
	}


	/**
	 * Function trying to add un-added sub container.
	 */
	private void tryAddUnAdd() {
		int index;
		for (ON.XML.XMLStruct tryAdd : unAdd) {
				if (setSubContainer(this, tryAdd, tryAdd.getContainerName())) {
				}
		}
	}
	/**
	 * Recursive function to set container with given name.
	 *
	 * @param ref (XMLStruct):
	 * @param toAdd (XMLStruct):
	 * @param containerName (String):
	 * @return XMLStruct
	 */
	public boolean setSubContainer(ON.XML.XMLStruct ref, ON.XML.XMLStruct toAdd, String containerName) {
		boolean test = false;
		int index;
		List<ON.XML.XMLStruct> containers = ref.getSubContainer();
		if (ref.getContainerName().equals(containerName) || ref.getContainerName().equals(containerName)) {
			ref.setContainsData(toAdd.isContainsData());
			ref.setSubContainer(toAdd.getSubContainer());
			return true;
		}
		else {
			//if (containers == null) {
			//	containers = new ArrayList<XMLStruct>();
			//}
			if (containers == null) {
				return false;
			}
			for (int i = 0; i < containers.size(); i++) {
				ON.XML.XMLStruct container = containers.get(i);
				test = setSubContainer(container, toAdd, containerName);
				if (test)
					return test;
			}
			return test;
		}
	}

	/**
	 * Function to parse dtd file to a java structure.
	 *
	 * @param line (String): A line of the DTD file
	 */
	private void parseStruct(String line) {
		ON.XML.XMLStruct subContainer = new ON.XML.XMLStruct();
		String pcdata = "#PCDATA";
		String Elem = "!ELEMENT";
		List<ON.XML.XMLStruct> subContainerList;
		ON.XML.XMLStruct container;
		boolean This = false;
		String content;
		String RES [] = line.split("[\t|\0|\n|\r| |,|(|)|>|<]+");// parse line
		if (line.contains(Elem)) {
			if (RES.length > 2) {
				content = RES[2];
				if (this.ContainerName == null) {
					this.ContainerName = content;
					This = true;
				}
				else {
					subContainer.setContainerName(content);
				}
				if (line.contains(pcdata) && RES.length > 3) {
					if (This) {
						this.ContainsData = true;
					}
					else {
						subContainer.setContainsData(true);
					}
					for (int i = 3; i < RES.length; i++) {
						if (!RES[i].equals(pcdata)) {
							if (This) {
								this.subContainer.add(new ON.XML.XMLStruct(RES[i], false, this));
							}
							else {
								subContainerList = subContainer.getSubContainer();
								subContainerList.add(new ON.XML.XMLStruct(RES[i], false, subContainer));
								subContainer.setSubContainer(subContainerList);
							}
						}
					}
					if (!This) {
						container = findContainer(this, subContainer.getContainerName());
						if (!subContainer.getContainerName().equals(container.getContainerName())) {
							unAdd.add(unAdd.size(), subContainer);
						}
						else {
							if (!setSubContainer(this, subContainer, subContainer.getContainerName()))
								unAdd.add(unAdd.size(), subContainer);
						}
					}
				} else if (RES.length > 3) {
					for (int i = 3; i < RES.length; i++) {
						if (This) {
							this.subContainer.add(new ON.XML.XMLStruct(RES[i], false, this));
						}
						else {
							subContainerList = subContainer.getSubContainer();
							subContainerList.add(new ON.XML.XMLStruct(RES[i], false, subContainer));
							subContainer.setSubContainer(subContainerList);
						}
					}
					if (!This) {
						container = findContainer(this, subContainer.getContainerName());
						if (!subContainer.getContainerName().equals(container.getContainerName())) {
							unAdd.add(unAdd.size(), subContainer);
						}
						else {
							if (!setSubContainer(this, subContainer, subContainer.getContainerName()))
								unAdd.add(unAdd.size(), subContainer);
						}
					}
				}
			}
		}
	}

	/**
	 * Function to test data flag.
	 *
	 * @return boolean
 	 */
	public boolean isContainsData() {
		return ContainsData;
	}

	/**
	 * Function to set data flag.
	 *
	 * @param containsData (boolean): data flag
	 */
	public void setContainsData(boolean containsData) {
		ContainsData = containsData;
	}

	/**
	 * Function to get container's name.
	 *
	 * @return String: Container's name
	 */
	public String getContainerName() {
		return ContainerName;
	}

	/**
	 * Function to set container's name.
	 *
	 * @param containerName (String): Container's name
	 */
	public void setContainerName(String containerName) {
		ContainerName = containerName;
	}

	/**
	 * Function to get sub-containers list.
	 *
	 * @return List<XMLStruct>: Sub-containers list
	 */
	public List<ON.XML.XMLStruct> getSubContainer() {
		return subContainer;
	}

	/**
	 * Function to set sub-containers list.
	 *
	 * @param subContainer (List<XMLStruct>): Sub-containers list
	 */
	public void setSubContainer(List<ON.XML.XMLStruct> subContainer) {
		this.subContainer = subContainer;
	}

	/**
	 * Function to get previous container.
	 *
	 * @return XMLStruct previous container
	 */
	public ON.XML.XMLStruct getPrevious() { return this.previous; }
}
