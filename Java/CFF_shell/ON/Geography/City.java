/**
 *@Project  :   cff_shell
 *@Package  :   ON.Geography
 *@Revision :   1.0.1
 *@Created  :   17/03/2017 11:57
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */
package ON.Geography;

/**
 * <h1>This class is used for storing data for cities.</h1>
 * <a>Class used by Map and XMLParser class.</a>
 * <h2>Long description</h2>
 * <p>This class can be extended to implement new methods over it.
     It is just providing the minimal use of object.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.101</p>
 */
public class City {
	protected String name;
	protected int longitude;
	protected int latitude;
	/**
	 * Protected string object is used by the XMLWriter for storing this object under the correct xml tag.
	 */
	protected String Object;
	/**
	 * Protected String array attribute is use for writing content of this object to xml tag.
	 */
	protected String []attribute = null;

	/**
	 * Constructor of the City's object.
	 * This constructor need the name, longitude, latitude of the city.
	 *
	 * @param name (String): Name of the city
	 * @param longitude (Integer): City's longitude
	 * @param latitude (Integer): City's latitude
	 */
	public City(String name, int longitude,  int latitude) {
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.attribute = new String[3];
		this.Object = "ville";
		this.attribute[0] = name;
		this.attribute[1] = String.valueOf(longitude);
		this.attribute[2] = String.valueOf(latitude);
	}

	/**
	 * Constructor of the City's object.
	 * This constructor need the name, longitude, latitude of the city.
	 *
	 * @param name (String): Name of the city
	 * @param longitude (Integer): City's longitude
	 * @param latitude (Integer): City's latitude
	 * @param attribute (String array): object attribute
	 */
	public City(String name, int longitude,  int latitude, String [] attribute) {
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.Object = "ville";
		this.attribute = attribute;
	}

	/**
	 * Constructor of the City's object.
	 * This constructor need the name, longitude, latitude of the city.
	 *
	 * @param name (String): Name of the city
	 * @param longitude (Integer): City's longitude
	 * @param latitude (Integer): City's latitude
	 * @param Object (String): Object name
	 * @param attribute (String array): object attribute
	 */
	public City(String name, int longitude,  int latitude, String Object, String [] attribute) {
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.Object = Object;
		this.attribute = attribute;
	}


	/**
	 * Function to set city's name.
	 *
	 * @param name (String): City's name
	 */
	public void setName(String name) { this.name = name; }

	/**
	 * Function to set longitude of a city.
	 *
	 * @param longitude (Integer): Longitude of the city
	 */
	public void setLongitude(int longitude) { this.longitude = longitude; }

	/**
	 * Function to set latitude of a city.
	 *
	 * @param latitude (Integer): Latitude of the city
	 */
	public void setLatitude(int latitude) { this.latitude = latitude; }

	/**
	 * Function to get the city's name.
	 *
	 * @return String
	 */
	public String getName() { return this.name;	}

	/**
	 * Function to get longitude of a city.
	 *
	 * @return Integer
	 */
	public int getLongitude() { return this.longitude; }

	/**
	 * Function to get latitude of a city.
	 *
	 * @return Integer
	 */
	public int getLatitude() { return this.latitude; }

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
