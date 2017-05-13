package ON.Application;

import ON.Geometry.PointD2;

import java.util.ArrayList;

/**
 * Création d'une liste de type PointD2
 */
public class ListePoints {                       // classe ListePoints
   
    private ArrayList<PointD2> liste;            // liste de points
   
    public ListePoints(){                        // lors de l'instanciation
        this.liste = new ArrayList<PointD2>();   // créer une liste
    }
   
    public ArrayList<PointD2> getListePoints(){  // récupérer une liste
        return this.liste;                       // retourne la liste
    }

    public void ajoutValeur(PointD2 p1){         // ajouter un point à la liste
        this.liste.add(p1);                      // l'ajouter é la liste
    }
}
