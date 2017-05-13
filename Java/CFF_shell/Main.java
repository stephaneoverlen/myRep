
// import exceptions
import java.io.IOException;
import java.util.NoSuchElementException;

// import objects
import java.io.File;
import java.lang.String;
import java.util.Scanner;
import java.util.List;
import java.util.Stack;

import ON.Dijkstra;
import ON.Floyd;
import ON.Geography.Map;
import ON.Application.GUI;
import ON.Utils;

/**
 * <h1>Main class of the program.</h1>
 * <a>This class is the parent class.</a>
 * <h2>Long description</h2>
 * <p>Entry point class of the program.</p>
 * <h3>Author(s)</h3>
 * <p>Paul ALBUQUERQUE, Orestis PILEAS MALASPINAS</p>
 * <p><b>version</b> : 1.101</p>
 * <h4>Contributors</h4>
 * <p>Stephane OVERLEN et Frederick NEY</p>
 */
public class Main {

	/**
	 * Entry point of the program.
	 *
	 * @param args (String array): arguments of the program
	 * @throws IOException : input/output exceptions of the scanner
	 */
	public static void main(String[] args) throws IOException {
		int i, j;                                                                           							// iteration variables
		int index1, index2;                                                                 							// matrix indexes
		int [][] weightMatrix;                                                              							// weight matrix
		int choice;                                                                          							// user choice
		Map virtualMap;                                                                          						// the virtual map containing cities and connection between them
		Scanner in;                                                                         							// argument scanner
		String str1, str2, str3;                                                       									// input strings
		int nbMatch, nbCities;																							// match results
		Stack<String> path;                                						             					        // LIFO

		switch(args.length) {
			case 0:
				in = new Scanner(System.in);                                                							// scanner set to stdin
				break;
			case 1:
				in = new Scanner(new File(args[0]));                                        							// scanner set to input file
				break;
			default:                                                                        							// scanner set to program arguments
				String source = args[0];
				for (i = 1; i < args.length; i++) source += " " + args[i];                  							// generate string to be scanned
				in = new Scanner(source);                                                   							// scan string
		}
		String filePath;
		if (args.length >= 1){
			filePath = System.getProperty("user.dir") + File.separator + in.next();   									// get the first item of the scanner
		}
		else {
			filePath = System.getProperty("user.dir") + File.separator + "villes.xml";   								// get default file if no arguments
		}
		virtualMap = new Map(filePath);                                                          						// instance the virtual map
		List<String> cities = virtualMap.getCitiesList();                                        						// cities list
		System.err.println("Le fichier XML " + filePath + " a été chargé\n");

		do {
			nbMatch = 0;
			nbCities = 0;
			System.err.println("Choix  0: quitter");
			System.err.println("Choix  1: liste des villes");
			System.err.println("Choix  2: matrice des poids");
			System.err.println("Choix  3: liste des poids");
			System.err.println("Choix  4: matrice des temps de parcours (Floyd)");
			System.err.println("Choix  5: matrice des précédences (Floyd)");
			System.err.println("Choix  6: temps de parcours entre deux villes (Floyd)");
			System.err.println("Choix  7: parcours entre deux villes (Floyd)");
			System.err.println("Choix  8: tableau des temps de parcours (Dijkstra)");
			System.err.println("Choix  9: tableau des précédences (Dijkstra)");
			System.err.println("Choix 10: temps de parcours entre deux villes (Dijkstra)");
			System.err.println("Choix 11: parcours entre deux villes (Dijkstra)");
			System.err.println("Choix 12: ajout d'une ville");
			System.err.println("Choix 13: ajout d'une liaison");
			System.err.println("Choix 14: suppression d'une ville");
			System.err.println("Choix 15: suppression d'une liaison");
			System.err.println("Choix 16: graphe connexe");
			System.err.println("Choix 17: sauver (format XML)");
			System.err.println("Entrez votre choice: ");
			try {
				choice = in.nextInt();                                                                                  // get user choice
				switch (choice) {
					case 1:                                                                                             // choice = list all cities
						for (i = 0; i < cities.size(); i++)
							System.out.print("[" + i + ":" + cities.get(i) + "]" + (i == cities.size() - 1 ? "" : " "));// print city
						System.out.print("\n");                                                                         // create new line
						break;
					case 2:                                                                                             // choice = weight matrix
						for (i = 0; i < cities.size(); i++) {
							for (j = 0; j < cities.size(); j++) {
								System.out.print(virtualMap.getWeight(
												cities.get(i),
												cities.get(j)
										) == Integer.MAX_VALUE ? i == j ?  												// will display 0 in case of i == j, inf in case of weight == Integer.MAX_VALUE, weight otherwise
												0 : "inf" : virtualMap.getWeight(cities.get(i), cities.get(j))
								);
								System.out.print(j == cities.size() - 1 ? "" : " ");                                    // display space between values but not at the end of the line
							}
							System.out.print("\n");                                                                     // create new line
						}
						break;
					case 3:                                                                                             // choice = display weight list
						for (i = 0; i < cities.size(); i++)
							if (virtualMap.getConnectionByName(cities.get(i)).size() != 0) {                            // if connection has link with the city
								System.out.print(cities.get(i) + " ");                                                  // display city name
								for (j = 0; j < cities.size(); j++)
									if (virtualMap.isEdge(cities.get(i), cities.get(j))) {                              // testing if edge exist
										System.out.print("[" + cities.get(j) + ":" + virtualMap.getWeight(
												                                                          cities.get(i),
														                                                  cities.get(j)
												                                                          ) + "]");     // display weight
										System.out.print(j == cities.size() - 1 ? "" : " ");                            // display space between values but not at the end of the line
									}
								System.out.print("\n");                                                                 // create new line
							}
						break;
					case 4:                                                                                             // choice = travel time matrix (Floyd)
						Floyd.printMatrix(Floyd.generateMatrix(virtualMap, false));                                     // generate and display matrix
						break;
					case 5:                                                                                             // choice = precedences' matrix (Floyd)
						Floyd.printMatrix(Floyd.generateMatrix(virtualMap, true));                                      // generate and display matrix
						break;
					case 6:                                                                                             // choice = travel time between two cities (Floyd)
						weightMatrix = Floyd.generateMatrix(virtualMap, false);                                         // generate matrix
						System.err.println("Ville d'origine:");
						str1 = in.next();                                                                               // retrieve source city
						System.err.println("Ville de destination:");
						str2 = in.next();                                                                               // retrieve target city
						i = 0;                                                                                          // initialise i
						index1 = -1;                                                                                    // initialise indexes
						index2 = -1;
						str1 = Utils.parseLine(str1);
						str2 = Utils.parseLine(str2);
						for (String city : cities) {
							if ((city.toLowerCase()).equals((str1))) {                                                  // if cities name match
								index1 = i;
								nbMatch++;
							}
							if ((city.toLowerCase()).equals((str2))) {                                                  // if cities name match
								index2 = i;
								nbMatch++;
							}
							i++;
						}
						if (nbMatch != 2) {
							System.err.println("City not found.");
							break;
						}
						System.err.print("Distance: ");
						System.out.println(weightMatrix[index1][index2]);                                               // display weight between both cities
						break;
					case 7:                                                                                             // choice = travel between two cities (Floyd)
						path = new Stack<String>();
						weightMatrix = Floyd.generateMatrix(virtualMap, true);                                          // generate precedences' matrix (Floyd)
						i = 0;                                                                                          // initialise i
						index1 = -1;                                                                                    // initialise indexes
						index2 = -1;
						System.err.println("Ville d'origine:");
						str1 = in.next();                                                                               // retrieve source city
						System.err.println("Ville de destination:");
						str2 = in.next();                                                                               // retrieve target city
						System.err.print("Parcours: ");
						str1 = Utils.parseLine(str1);
						str2 = Utils.parseLine(str2);
						for (String city : cities) {
							if ((city.toLowerCase()).equals((str1))) {                                                  // if cities name match
								index1 = i;
								nbMatch++;
							}
							if ((city.toLowerCase()).equals((str2))) {													// if cities name match
								index2 = i;
								nbMatch++;
							}
							i++;
						}
						if (nbMatch != 2) {																				// if cities did not match with cities in the list of cities
							System.err.println("City not found.");
							break;
						}
						do {
							path.push(cities.get(index2));                                                          	// add city in the stack
							index2 = weightMatrix[index1][index2];                                                      // get next index
							nbCities++;
						}
						while (index1 != weightMatrix[index1][index2]);                                                 // while index don't matching

						System.out.print("[" + cities.get(index1));                                                     // display source city
						System.out.print(":" + cities.get(index2));                                                     // display next city to reach
						for (i = 0; i < nbCities; i++) {
							System.out.print(":" + path.peek());                                                    	// Display last city added in the stack
							path.pop();                                                                             	// Delete it from the stack
						}
						System.out.println("]");
						break;
					case 8:                                                                                             // choice = travel time array (Dijkstra)
						System.err.println("Ville d'origine:");
						str1 = in.next();
						str1 = Utils.parseLine(str1);
						for (String city : cities) {
							if ((city.toLowerCase()).equals((str1).toLowerCase())) {                                    // if cities name match
								str1 = city;
								nbMatch++;
								break;
							}
						}
						if (nbMatch != 1) {																				// if city did not match with a city in the list of cities
							System.err.println("City not found.");
							break;
						}

						for (i = 0; i < cities.size(); i++) {
							System.out.print(i == cities.size() - 1 ?
					"[" + cities.get(i) + ":" + Dijkstra.findShortestPathWeight(virtualMap, str1, cities.get(i)) + "]" :
					"[" + cities.get(i) + ":" + Dijkstra.findShortestPathWeight(virtualMap, str1, cities.get(i)) + "] "
							);																							// Print array
						}
						System.out.print("\n");
						break;
					case 9:                                                                                             // choice = array of precedences (Dijkstra)
						System.err.println("Ville d'origine:");
						str1 = in.next();
						str1 = Utils.parseLine(str1);
						for (String city : cities) {
							if ((city.toLowerCase()).equals((str1).toLowerCase())) {                                    // if cities name match
								str1 = city;
								nbMatch++;
								break;
							}
						}
						if (nbMatch != 1) {																				// if city did not match with a city in the list of cities
							System.err.println("City not found.");
							break;
						}

						for (i = 0; i < cities.size(); i++) {
							if (!cities.get(i).equals(str1)) {
								System.out.print(i == cities.size() - 1 ?
						"[" + Dijkstra.findShortestPath(virtualMap, str1, cities.get(i)) + "<-" + cities.get(i) + "]" :
						"[" + Dijkstra.findShortestPath(virtualMap, str1, cities.get(i)) + "<-" + cities.get(i) + "] "
								);
							}
						}
						System.out.print("\n");
						break;
					case 10:                                                                                            // choice = travel time between 2 cities (Dijkstra)
						System.err.println("Ville d'origine:");
						str1 = in.next();
						System.err.println("Ville de destination:");
						str2 = in.next();
						i = 0;
						index1 = 0;
						index2 = 0;
						str1 = Utils.parseLine(str1);
						str2 = Utils.parseLine(str2);
						for (String city : cities) {
							if ((city.toLowerCase()).equals((str1).toLowerCase())) {                                    // if cities name match
								index1 = i;
								nbMatch++;
								break;
							}
							i++;
						}
						i = 0;
						for (String city : cities) {
							if ((city.toLowerCase()).equals((str2).toLowerCase())) {                                    // if cities name match
								index2 = i;
								nbMatch++;
								break;
							}
							i++;
						}
						if (nbMatch != 2) {																				// if cities did not match with cities in the list of cities
							System.err.println("City not found.");
							break;
						}
						System.err.print("Distance: ");
						System.out.println(Dijkstra.findShortestPathWeight(virtualMap,
								cities.get(index1),
								cities.get(index2))
						);																								// display travel time between both cities
						break;
					case 11:                                                                                            // choice = travel path between two cities (Dijkstra)
						path = new Stack<String>();
						System.err.println("Ville d'origine:");
						str1 = in.next();
						System.err.println("Ville de destination:");
						str2 = in.next();
						str1 = Utils.parseLine(str1);
						str2 = Utils.parseLine(str2);
						for (String city : cities) {
							if ((city.toLowerCase()).equals((str1).toLowerCase())) {
								str1 = city;
								nbMatch++;
								break;
							}
						}
						for (String city : cities) {
							if ((city.toLowerCase()).equals((str2).toLowerCase())) {
								str2 = city;
								nbMatch++;
								break;
							}
						}
						if (nbMatch != 2) {																				// if cities did not match with cities in the list of cities
							System.err.println("City not found.");
							break;
						}
						System.err.print("Parcours: ");
						do {
							path.push(str2);
							str2 = Dijkstra.findShortestPath(virtualMap, str1, str2);
							nbCities++;
						} while (!str1.equals(str2));
						path.push(str2);
						System.out.print("[");
						for (i = 0; i < nbCities ; i++) {
							System.out.print(i == path.size() - 1 ? path.peek() : ":" + path.peek());
							path.pop();
						}
						System.out.print("]\n");																		// print the end of the line
						break;
					case 12:                                                                                            // choice = add city
						System.err.println("Nom de la ville:");
						str1 = in.next();
						System.err.println("Longitude:");
						str2 = in.next();
						System.err.println("Latitude:");
						str3 = in.next();
						Utils.addCity(Utils.parseLine(str1), Utils.parseLine(str2), Utils.parseLine(str3), virtualMap);
						break;
					case 13:                                                                                            // choice = add connection
						System.err.println("Ville d'origine:");
						str1 = in.next();
						System.err.println("Ville de destination:");
						str2 = in.next();
						System.err.println("Temps de parcours:");
						str3 = in.next();
						Utils.addConnection(Utils.parseLine(str1),
								Utils.parseLine(str2),
								Utils.parseLine(str3),
								virtualMap
						);
						break;
					case 14:                                                                                            // choice = delete city
						System.err.println("Nom de la ville:");
						str1 = in.next();
						Utils.delCity(Utils.parseLine(str1), virtualMap);
						break;
					case 15:                                                                                            // choice = delete connection
						System.err.println("Ville d'origine:");
						str1 = in.next();
						System.err.println("Ville de destination:");
						str2 = in.next();
						Utils.delConnection(Utils.parseLine(str1), Utils.parseLine(str2), virtualMap);
						break;
					case 16:                                                                                            // choice = related graph
						GUI graphics = new GUI(virtualMap);
						if (graphics != null) {
							System.out.println(true);
						} else {
							System.out.println(true);
						}
						break;
					case 17:                                                                                            // choice = save (format XML)
						System.err.println("Nom du fichier XML:");
						str1 = in.next();
						str1 = Utils.parseLine(str1);
						virtualMap.save(str1);
						break;
				}
			} catch (NoSuchElementException e) {
				choice = 0;
			}
		} while (choice != 0);
		System.exit(0);
	}
}
