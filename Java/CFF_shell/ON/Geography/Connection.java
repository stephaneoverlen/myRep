/**
 *@Project  :   cff_shell
 *@Package  :   ON.Geography
 *@Revision :   1.0.1
 *@Created  :   27/03/2017 12:10
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON.Geography;

/**
 * <h1>This class is use for storing data of connection between cities before storing it data into an xml file.</h1>
 * <a>Class used by XMLParser class.</a>
 * <h2>Long description</h2>
 * <p>his class can be extended to implement new methods over it.
     It is just providing the minimal use of object.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.101</p>
 */
public class Connection {
	protected String city_1;
	protected String city_2;
	protected int distance;
	/**
	 * Protected string object is used by the XMLWriter for storing this object under the correct xml tag.
	 */
	protected String Object;
	/**
	 * Protected String array attribute is use for writing content of this object to xml tag.
	 */
	protected String []attribute = null;

	public Connection (String city_1, String city_2, int distance) {
		this.city_1 = city_1;
		this.city_2 = city_2;
		this.distance = distance;
		this.Object = "liaison";
		this.attribute = new String[3];
		this.attribute[0] = city_1;
		this.attribute[1] = city_2;
		this.attribute[2] = String.valueOf(distance);
	}

	/**
	 * Constructor of the Connection's object.
	 * This constructor need the name, longitude, latitude of the city.
	 *
	 * @param city_1 (String): Name of the city
	 * @param city_2 (String): City's longitude
	 * @param distance (Integer): City's latitude
	 * @param attribute (String array): object attribute
	 */
	public Connection(String city_1, String city_2,  int distance, String [] attribute) {
		this.city_1 = city_1;
		this.city_2 = city_2;
		this.distance = distance;
		this.Object = "liaison";
		this.attribute = attribute;
	}

	/**
	 * Constructor of the Connection's object.
	 * This constructor need the name, longitude, latitude of the city.
	 *
	 * @param city_1 (String): Name of the city
	 * @param city_2 (String): City's longitude
	 * @param distance (Integer): City's latitude
	 * @param Object (String): Object name
	 * @param attribute (String array): object attribute
	 */
	public Connection(String city_1, String city_2,  int distance, String Object, String [] attribute) {
		this.city_1 = city_1;
		this.city_2 = city_2;
		this.distance = distance;
		this.Object = Object;
		this.attribute = attribute;
	}

	/**
	 * Function to set city's name.
	 *
	 * @param name (String): City's name
	 */
	public void setCity_1(String name) { this.city_1 = name; }

	/**
	 * Function to set city's name.
	 *
	 * @param name (String): City's name
	 */
	public void setCity_2(String name) { this.city_1 = name; }

	/**
	 * Function to set longitude of a city.
	 *
	 * @param distance (Integer): connection distance
	 */
	public void setDistance(int distance) { this.distance = distance; }

	/**
	 * Function to get the city's name.
	 *
	 * @return String
	 */
	public String getCity_1() { return this.city_1;	}

	/**
	 * Function to get the city's name.
	 *
	 * @return String
	 */
	public String getCity_2() { return this.city_2;	}


	/**
	 * Function to get connection's distance
	 *
	 * @return Integer
	 */
	public int getDistance() { return this.distance; }

	/**
	 * Function to get attribute
	 *
	 * @return private String array attribute
	 */
	public String [] getAttribute(){ return this.attribute;	}

	/**
	 * Function to test if it has attribute.
	 *
	 * @return boolean
	 */
	public boolean hasAttribute(){ return (this.attribute.length != 0);	}

	/**
	 * Function to get object string.
	 *
	 * @return object name
	 */
	@Override
	public String toString() {
		return Object;
	}

}