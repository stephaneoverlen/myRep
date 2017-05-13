package ON.Application;

import ON.Geometry.VilleD2;

import java.util.ArrayList;

/**
 * Création d'une liste de type PointD2
 */
public class ListeVilles {                       // classe ListePoints
   
    private ArrayList<VilleD2> liste;            // liste de points
   
    public ListeVilles(){                        // lors de l'instanciation
        this.liste = new ArrayList<VilleD2>();   // créer une liste
    }
   
    public ArrayList<VilleD2> getListeVilles(){  // récupérer une liste
        return this.liste;                       // retourne la liste
    }

    public void ajoutValeur(VilleD2 p1){         // ajouter un point à la liste
        this.liste.add(p1);                      // l'ajouter é la liste
    }
}
