package crush;

import java.awt.Color;

class Vertical implements Runnable { // quasiment identique à Horizontal
	
    VueCrush vue;                                                                              // VueCrush
    int count = 0;                                                                             // compteur
    int taille = 0;                                                                            // nombre de lignes et/ou colonnes
    int ref = 0;                                                                               // bouton de référence

    public Vertical(VueCrush v) {
        vue = v;                                                                               // récupérer les variables et fonctions de VueCrush
        taille = v.getTaille();                                                                // récupérer le nombre de lignes et/ou colonnes
    }
    
    /**
     * Comptage des points et suppression des boutons réalisés par le thread 'td2'
     * @étape1 déterminer le nombre de boutons identiques qui se suivent verticalement.
     * @étape2 leur donner une valeur ne correspondant à aucune image pour que les threads 'Replace' les remplacent.
     * @étape3 ajouter le score uniquement si les boutons à supprimer sont collés aux boutons cliqués par l'utilisateur.
     */
    public synchronized void run() {                                                           // exécution du thread vertical
    	while(true){                                                                           // boucle infinie
    	    for(int i = 0; i < taille; i++){                                                   // pour chaque ligne
    	        for(int j = 0; j < taille-2; j++){                                             // pour les 8 premières lignes
    	            ref = (j*taille) + i;                                                      // bouton de référence
    	            if(vue.getNum(ref) == vue.getNum(ref + taille) &&
    	            	    vue.getNum(ref + taille) == vue.getNum(ref + 2*taille)){           // si 3 cases qui se suivent sont identiques
    	                count = 3;                                                             // mettre le compteur à 3
    	                if(j < taille-3 && vue.getNum(ref) == vue.getNum(ref + 3*taille)){     // si 4e case identique
    	                    count++;                                                           // mettre le compteur à 4
    	                    if(j < taille-4 && vue.getNum(ref) == vue.getNum(ref + 4*taille)){ // si 5e case identique
    	                        count++;                                                       // mettre le compteur à 5
    	                    }
    	                }
                    
    	                for(int k = 0; k < count; k++){                          // pour les cases identiques repérées
    	                    vue.setNum(ref + k*taille, vue.getTabLength());      // leur donner la taille du tableau d'images comme valeur
    	                    vue.getBtn(ref + k*taille).setBackground(Color.red); // leur mettre un arrière-plan rouge pour les repérer
    	                    j += count;                                          // incrémenter j pour économiser des ressources et éviter certaines erreur
    	                    
    	                    if(vue.getBtnScoreV1() == ref + k*taille){      // si le bouton cliqué 1 fait partie des boutons à supprimer
    	                    	vue.resetBtnScoreV1();                      // reset pour éviter de comptabiliser plusieurs fois son score
   	                    	    vue.addScore(count);                        // ajouter des points au score
    	                    }
    	                    else if(vue.getBtnScoreV2() == ref + k*taille){ // si le bouton cliqué 2 fait partie des boutons à supprimer
    	                    	vue.resetBtnScoreV2();                      // reset pour éviter de comptabiliser plusieurs fois son score
   	                    	    vue.addScore(count);                        // ajouter des points au score
    	                    }
                        }
                    }
                }
            }
    	}
    }
}
