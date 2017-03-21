package crush;

import java.util.Random;

class Replace implements Runnable {

    VueCrush vue;                                                          // VueCrush
    Random rnd;                                                            // nombre aléatoire
    
    int taille = 0;                                                        // nombre de lignes et/ou colonnes
    int numThread = 0;                                                     // numéro de la colonne traitée par le thread
    int tab[];                                                             // tableau contenant les valeurs des boutons
   
    public Replace(VueCrush v, int n) {
        vue = v;                                                           // récupérer les valeurs et méthodes de VueCrush
        numThread = n;                                                     // récupérer le numéro de la colonne traitée par le thread
    }
    
    /**
     * Remplassement des boutons 'vides'
     * @étape1 recopier le bouton du dessus.
     * @étape2 donner une valeur aléatoire aux boutons du sommet.
     * @étape3 associer aux boutons les images correspondantes
     */
    public synchronized void run() {                                       // pour chaque thread
    	taille = vue.getTaille();                                          // récupérer le nombre de lignes et/ou colonnes
    	while(true){                                                       // boucle infinie
            for(int i = (taille-1)*taille+numThread; i >= 0; i -= taille){ // pour chaque case de la colonne traitée par le thread
                if(vue.getNum(i) == vue.getTabLength()){                   // si la case doit être supprimée
                	if(i >= taille){                                       // si ce n'est pas la case tout en haut
                        vue.setNum(i, vue.getNum(i-taille));               // elle recopie la case du dessus
                        vue.setNum(i-taille, vue.getTabLength());          // case du dessus = valeur de modification
                        vue.setBtnIcon(i);                                 // mettre à jour l'image
    	                vue.getBtn(i).setBackground(null);
                    }
                	else{                                                  // si c'est la case tout en haut
    	                vue.setNum(i, vue.getRnd());                       // mettre une valeur aléatoire
    	                vue.setBtnIcon(i);                                 // attribuer l'image correspondante
    	                vue.getBtn(i).setBackground(null);                 // supprimer la couleur d'arrière-plan
                	}
            	}
            }
        }
    }
}
