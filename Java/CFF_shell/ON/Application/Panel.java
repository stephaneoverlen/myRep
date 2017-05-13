package ON.Application;

// import Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

// import Objects
import javax.swing.JPanel;

import java.awt.Graphics;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import ON.Geography.Map;
import ON.Geometry.PointD2;
import ON.Geometry.SegmentD2;
import ON.Geometry.VilleD2;

/**
 * <h1>Panel of the application.</h1>
 * <a>Class used by GUI class.</a>
 * <h2>Long description</h2>
 * <p>This class is used for drawing the virtual map.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.101</p>
 */
public class Panel extends JPanel {
 
    Double val0=0.0, val1=0.0, val2=0.0, val3=0.0;
    Double x1 = 0.0, y1 = 0.0, x2 = 0.0, y2 = 0.0;
    Double count = 0.0;
    Double minX = 0.0, maxX = 0.0, minY = 0.0, maxY = 0.0, etendueX = 0.0, etendueY = 0.0;
    Double FactEchX = 0.0,FactEchY = 0.0;
    Double NouveauX = 0.0, NouveauY = 0.0;
    String name;
    Map virtualMap;

    public Panel(Map virtualMap) {
        this.virtualMap = virtualMap;
    }

    /**
     * Calcul du facteur de redimensionnement.
     * @param pFacteur point à modifier.
     * @return nouveau point avec les nouvelles valeurs calculées.
     */
    PointD2 facteur(PointD2 pFacteur){							// calculer le facteur de redimensionnement du point
        NouveauX = ((pFacteur.getX() - minX) * FactEchX);		// abscisse
        NouveauY = ((pFacteur.getY() - minY) * FactEchY);		// ordonnée
        pFacteur.setX(NouveauX);								// attribuer la nouvelle abscisse au point en paramètre
        pFacteur.setX(NouveauY);								// attribuer la nouvelle ordonnée au point en paramètre
        return pFacteur;										// retourner le point
    }

/**
 *
 * @param xe
 * @param ye
 */
    void etendueXY(Double xe, Double ye){	// calculer l'étendue entre le point le plus petit et le plus grand :
        if(count == 0){						// à la première itération, le point actuel est défini comme référence
            minX = xe;						// minX = x
            maxX = xe;						// maxX = x
            minY = ye;						// minY = y
            maxY = ye;						// maxY = y
            count++;						// première itération terminée
        }
        else if(xe < minX) minX = xe;		// si x < minX : minX = x
        else if(xe > maxX) maxX = xe;		// si x > maxX : maxX = x
        else if(ye < minY) minY = ye;		// si y < minY : minY = y
        else if(ye > maxY) maxY = ye;		// si y > maxY : maxY = y
    }
  

/**
 * Affichage des points et droites.
 * @param g = objet graphique
 */
    public void paintComponent(Graphics g){										// affichage des points et droites
    
        ListePoints listeP = new ListePoints();									// array de points
        ListeSegments listeS = new ListeSegments();								// array de segments
        ListeVilles listeV = new ListeVilles();									// array de villes
        String[] valeurs;														// récupérera les valeurs dans le fichier
        PointD2 p;																// point générique
        SegmentD2 s;															// segment générique
        VilleD2 v;																// ville générique


        LineNumberReader lecteurDeLignes = null;								// création d'un lecteur de lignes
        try {
            lecteurDeLignes = new LineNumberReader(new FileReader("suisse.txt"));	// associer le lecteur de lignes au fichier de points
            String texteDeLigne = null;											// récupérera les valeurs en chaînes de caractères
       
            while ((texteDeLigne = lecteurDeLignes.readLine()) != null) {		// jusqu'à la dernière ligne :
                StringTokenizer ligne = new StringTokenizer(texteDeLigne);		// lire chaque ligne
                valeurs = texteDeLigne.split(" ");								// les valeurs sont séparées par des espaces
             
                if(ligne.countTokens() == 2) {									// si la ligne contient 2 valeurs :
                    try {														// tester que ce sont bien des entiers
                        val0 = Double.parseDouble(valeurs[0]);					// val0 récupère le premier entier
                        val1 = Double.parseDouble(valeurs[1]);					// val1 récupère le deuxième entier
                    } catch (NumberFormatException e) {							// si ce ne sont pas des entiers
                        System.err.println("Erreur fichier: doit contenir des lignes de 2 entiers, 1 nom suivi de 2 entiers ou 4 entiers.");
                    }
                    p = new PointD2(val0, val1);								// créer un point à partir de ces valeurs
                    listeP.ajoutValeur(p);										// ajouter le point dans un array
                    etendueXY(val0, val1);										// calculer les valeurs min et max des points du fichier
                }
           
                else if(ligne.countTokens() == 3) {								// si la ligne contient 3 valeurs :
                    
                    try {														// tester que ce sont bien des entiers
                        name = valeurs[0];										// name récupère la première chaîne de caractères
                        val0 = Double.parseDouble(valeurs[1]);					// val0 récupère le premier entier
                        val1 = Double.parseDouble(valeurs[2]);                  // val1 récupère le deuxième entier
                    } catch (NumberFormatException e) {							// afficher un message si ce n'est pas le cas
                        System.err.println("Erreur fichier: doit contenir des lignes de 2 entiers, 1 nom suivi de 2 entiers ou 4 entiers.");
                    }
                    
                    v = new VilleD2(val0, val1, name);							// créer un point
                    listeV.ajoutValeur(v);										// ajouter le point dans un array
                    etendueXY(val0, val1);										// calculer les valeurs min et max des points du fichier
                }
                
                else if(ligne.countTokens() == 4) {								// si la ligne contient 4 valeurs :
                 
                    try {														// tester que ce sont bien des entiers
                        val0 = Double.parseDouble(valeurs[0]);					// val0 récupère la valeur entière de valeurs[0]
                        val1 = Double.parseDouble(valeurs[1]);					// val1 récupère la valeur entière de valeurs[1]
                        val2 = Double.parseDouble(valeurs[2]);					// val2 récupère la valeur entière de valeurs[2]
                        val3 = Double.parseDouble(valeurs[3]);					// val3 récupère la valeur entière de valeurs[3]
                    } catch (NumberFormatException e) {							// afficher un message si ce n'est pas le cas
                        System.err.println("Erreur fichier: doit contenir des lignes de 2 entiers, 1 nom suivi de 2 entiers ou 4 entiers.");
                    }
                 
                    s = new SegmentD2(val0, val1, val2, val3);					// créer une droite
                    listeS.ajoutValeur(s);										// ajout de la droite dans un array
                    etendueXY(val0, val1);										// calculer les valeurs min et max des points du fichier
                    etendueXY(val2, val3);										// calculer les valeurs min et max des points du fichier
                }
           
                else {															// nombre d'arguments invalide
                    System.err.println("Arguments invalides : 2 ou 4 valeurs.");
                }
            }
        }
        catch(FileNotFoundException e) {										// fichier inexistant : afficher un message
            System.out.println("Fichier inexistant : " + e);
            System.exit(0);
        }
        catch(IOException o) {
            System.out.println("Erreur readLine() : " + o);
            System.exit(0);
        }

        etendueX = maxX - minX + 1;										// calculer l'etendue entre minX et maxX
        etendueY = maxY - minY + 1;										// calculer l'etendue entre minY et maxY
        FactEchX = (double)getWidth() / etendueX;						// calculer le facteur de redimensionnement horizontal
        FactEchY = (double)getHeight() / etendueY;						// calculer le facteur de redimensionnement vertical

        Iterator<PointD2> itP = listeP.getListePoints().iterator();						// créer un itérateur pour l'array de points
        while(itP.hasNext()){															// tant qu'il y a un point dans la liste
            p = itP.next();																// point suivant
            facteur(p);																	// appliquer le facteur de redimensionnement au point
            NouveauY = getHeight()-NouveauY;											// inverser l'ordonnée
            g.drawRect(NouveauX.intValue(), NouveauY.intValue(), 1, 1);                 // tracer le point
        }

        List<String> cities = virtualMap.getCitiesList();
        for(String city : cities){															// tant qu'il y a une ville dans la liste
            facteur(new PointD2(Double.valueOf(virtualMap.getLongitude(city)),
                                Double.valueOf(virtualMap.getLatitude(city))
                                )
            );															// appliquer le facteur de redimensionnement au point de la ville
            NouveauY = getHeight()-NouveauY;											// inverser l'ordonnée
            g.drawRect(NouveauX.intValue()-5, NouveauY.intValue()-5, 10, 10);			// tracer le point
            g.drawString(city, NouveauX.intValue() - 20, NouveauY.intValue() - 12);	// écrire le nom de la ville
        }

        List<String []> connections = virtualMap.getConnectionsList();
            for (String[] connection : connections) {
                try {
                    Double[][] coordinates = virtualMap.getConnection(connection[0], connection[1]);
                    SegmentD2 seg = new SegmentD2(coordinates[0][0], coordinates[0][1], coordinates[1][0], coordinates[1][1]);
                    if (Double.isInfinite(seg.getPente()) == false) {                            // si la pente n'est pas infinie (!= vertical)
                        facteur(seg.getP1());                                                // ajuster à la taille de la map
                        x1 = NouveauX;
                        y1 = NouveauY;
                        facteur(seg.getP2());                                                    // appliquer le facteur de redimensionnement au point
                        x2 = NouveauX;
                        y2 = NouveauY;

                        y1 = getHeight() - y1;                                                // inverser les ordonnées pour que la carte soit à l'endroit
                        y2 = getHeight() - y2;
                        seg.setPente(x1, y1, x2, y2);                                        // calculer la nouvelle pente du segment
    
                        for (Double i = (x1 < x2 ? x1 : x2); i < (x1 < x2 ? x2 : x1); i++) {                                    // de x1 à x2
                            Double y = (seg.getPente() * i + seg.getOrdoneeOrigine());            // calculer y = ax + b
                            g.drawRect(i.intValue(), y.intValue(), 1, 1);                    // tracer les points du segment
                            g.drawString(String.valueOf(virtualMap.getWeight(connection)), (int) ((x1 + ((x2 - x1) / 2)) - 20), (int) ((y1 + ((y2 - y1) / 2)) - 12));    // écrire le nom de la ville
                        }
                        for (Double i = (y1 < y2 ? y1 : y2); i < (y1 < y2 ? y2 : y1); i++) {                                    // de x1 à x2
                            Double x = (i - seg.getOrdoneeOrigine()) / seg.getPente();            // calculer y = ax + b
                            g.drawRect(x.intValue(), i.intValue(), 1, 1);                    // tracer les points du segment
                            g.drawString(String.valueOf(virtualMap.getWeight(connection)), (int) ((x1 + ((x2 - x1) / 2)) - 20), (int) ((y1 + ((y2 - y1) / 2)) - 12));    // écrire le nom de la ville
                        }
                    } else {                                                                    // si la pente est infinie (segment vertical)
                        x1 = seg.getP1().getX() * FactEchX;                                    // ajuster les points sur la map
                        y1 = seg.getP1().getY() * FactEchY;
                        y2 = seg.getP2().getY() * FactEchY;
                        g.drawRect(x1.intValue(), y1.intValue(), 1, (int) (y2 - y1));            // tracer un rectangle sans longueur
                    }
                } catch (NullPointerException e) { // nothing to do}
            }
        }
    }
}
