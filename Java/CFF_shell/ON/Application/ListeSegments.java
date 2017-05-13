package ON.Application;

import ON.Geometry.SegmentD2;

import java.util.ArrayList;

/**
 * Création d'une liste de type DroiteD2
 */
public class ListeSegments {                       // classe ListeDroites

    private ArrayList<SegmentD2> liste;            // liste de droites
   
    public ListeSegments(){                        // lors de l'instanciation
        this.liste = new ArrayList<SegmentD2>();   // créer une liste
    }
   
    public ArrayList<SegmentD2> getListeSegments(){ // récupérer une liste
        return this.liste;                        // retourne la liste
    }

    public void ajoutValeur(SegmentD2 d1){         // ajouter une droite à la liste
        this.liste.add(d1);                        // l'ajouter à la liste
    }
}
