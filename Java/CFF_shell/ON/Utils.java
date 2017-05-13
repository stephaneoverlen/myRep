/**
 *@Project  :   cff_shell
 *@Package  :   ON
 *@Revision :   1.0.1
 *@Created  :   13/04/2017 20:36
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON;

// import Objects
import java.util.List;

import ON.Geography.Map;

/**
 * <h1>Short description.</h1>
 * <a>Use by.</a>
 * <h2>Long description</h2>
 * <p>Description</p>
 * <h3>Author(s)</h3>
 * <p>Stephane OVERLEN et Frederick NEY</p>
 * <p><b>version</b> : 1.101</p>
 */
public class Utils {
    /**
     * Function to parse line.
     *
     * @param line          (String): line to parse
     * @return              (String)- line parsed
     */
    public static String parseLine (String line){
        String buffer [];
        buffer = line.split("[\t|\0|\n|\r| ]+");
        for (String name : buffer) {
            if (name.length() > 0) {
                line = name;
                break;
            }
        }
        return line;
    }

    /**
     * Function for adding city.
     *
     * @param name          (String): city's name
     * @param longitude     (String): city's longitude
     * @param latitude      (String): city's latitude
     * @param virtualMap    (Map)   : virtual map to use
     */
    public static void addCity (String name, String longitude, String latitude, Map virtualMap) {
        List<String> cities = virtualMap.getCitiesList();
        boolean found = false;
        for (String city : cities) {
            if ((city.toLowerCase()).equals((name).toLowerCase())) {
                found = true;
                break;
            }
        }
        if (!found) {                                                                                                   // if city not found in the list of cities
            virtualMap.addCity(name, Integer.valueOf(longitude), Integer.valueOf(latitude));                            // adding
        }
    }

    /**
     * Function to delete city.
     *
     * @param name          (String): city's name
     * @param virtualMap    (Map)   : virtual map to use
     */
    public static void delCity (String name, Map virtualMap) {
        List<String> cities = virtualMap.getCitiesList();
        int i = 0;
        for (String city : cities) {
            if ((city.toLowerCase()).equals((name).toLowerCase())) {                                                    // if city found in the list of cities
                virtualMap.delCity(cities.get(i));                                                                      // deleting
                break;
            }
            i++;
        }
    }

    /**
     * Function to add new connection between two cities.
     *
     * @param city          (String): first city of the connection
     * @param nextCity      (String): second city of the connection
     * @param weight        (String): connection weight
     * @param virtualMap    (Map)   : virtual map to use
     */
    public static void addConnection (String city, String nextCity, String weight, Map virtualMap) {
        List<String []> connections = virtualMap.getConnectionsList();
        boolean found = false;
        int nbMatch = 0;
        List<String> cities = virtualMap.getCitiesList();
        for (String[] connection : connections) {
            if (
                    ((connection[0].toLowerCase()).equals(city) &&
                            (connection[1].toLowerCase()).equals(nextCity)) ||
                            ((connection[0].toLowerCase()).equals(nextCity) &&
                                    (connection[1].toLowerCase()).equals(city))
                    ) {
                found = true;
                break;
            }
        }
        if (!found) {
            for (String cityTmp : cities) {
                if ((cityTmp.toLowerCase()).equals(city.toLowerCase())) {
                    city = cityTmp;
                    nbMatch++;
                }
                if ((city.toLowerCase()).equals(nextCity.toLowerCase())) {
                    nextCity = cityTmp;
                    nbMatch++;
                }
            }
            if (nbMatch == 2)
                virtualMap.addConnection(city, nextCity, Integer.valueOf(weight));
        }
    }

    /**
     * Function to delete connection between two cities.
     *
     * @param city          (String): first city of the connection
     * @param nextCity      (String): second city of the connection
     * @param virtualMap    (Map)   : virtual map to use
     */
    public static void delConnection(String city, String nextCity, Map virtualMap) {
        List<String []> connections = virtualMap.getConnectionsList();
        for (String[] connection : connections) {
            if (
                    ((connection[0].toLowerCase()).equals(city) &&
                            (connection[1].toLowerCase()).equals(nextCity)) ||
                            ((connection[0].toLowerCase()).equals(nextCity) &&
                                    (connection[1].toLowerCase()).equals(city))
                    ) {
                virtualMap.delConnection(connection);
                break;
            }
        }
    }
}
