package crush;

import java.awt.Color;

class Horizontal implements Runnable {
	
    VueCrush vue;                                                                       // VueCrush
    int count = 0;                                                                      // compteur
    int taille = 0;                                                                     // nombre de lignes/colonne
    int ref = 0;                                                                        // bouton de référence

    public Horizontal(VueCrush v) {
        vue = v;                                                                        // récupérer les variables et fonctions de VueCrush
        taille = v.getTaille();                                                         // récupérer le nombre de lignes et/ou colonnes
    }
    
    /**
     * Comptage des points et suppression des boutons réalisés par le thread 'td1'
     * @étape1 déterminer le nombre de cases identiques qui se suivent horizontalement.
     * @étape2 leur donner une valeur ne correspondant à aucune image pour que les threads 'Replace' les remplacent.
     * @étape3 ajouter le score uniquement si les cases à supprimer sont collées aux cases cliquées par l'utilisateur.
     */
    public synchronized void run() {                                                    // exécution du thread horizontal
    	while(true){                                                                    // boucle infinie
    	    for(int i = 0; i < taille; i++){                                            // pour chaque ligne
    	        for(int j = 0; j < taille-2; j++){                                      // pour les 8 premières colonnes
    	            ref = (i*taille) + j;                                               // ref = bouton de référence
    	            if(vue.getNum(ref) == vue.getNum(ref + 1) &&
    	            	    vue.getNum(ref + 1) == vue.getNum(ref + 2)){                // si 3 cases qui se suivent sont identiques
    	                count = 3;                                                      // mettre le compteur à 3
    	                if(j < taille-3 && vue.getNum(ref) == vue.getNum(ref + 3)){     // si 4e case identique
    	                    count++;                                                    // mettre le compteur à 4
    	                    if(j < taille-4 && vue.getNum(ref) == vue.getNum(ref + 4)){ // si 5e case identique
    	                        count++;                                                // mettre le compteur à 5
    	                    }
    	                }
                    
    	                for(int k = 0; k < count; k++){                   // pour les cases identiques repérées
    	                    vue.setNum(ref + k, vue.getTabLength());      // leur donner la taille du tableau d'images comme valeur
    	                    vue.getBtn(ref + k).setBackground(Color.red); // leur mettre brièvement un arrière-plan rouge pour les repérer
    	                    j += count;                                   // incrémenter j pour économiser des ressources et éviter certaines erreurs
    	                    
    	                    if(vue.getBtnScoreH1() == ref + k){           // si le bouton cliqué 1 fait partie des boutons à supprimer
    	                    	vue.resetBtnScoreH1();                    // reset pour éviter de comptabiliser plusieurs fois son score
   	                    	    vue.addScore(count);                      // ajouter des points au score
    	                    }
    	                    else if(vue.getBtnScoreH2() == ref + k){      // si le bouton cliqué 2 fait partie des boutons à supprimer
    	                    	vue.resetBtnScoreH2();                    // reset pour éviter de comptabiliser plusieurs fois son score
    	                    	vue.addScore(count);                      // ajouter des points au score
    	                    }
                        }
                    }
                }
            }
    	}
    }
}
