/**
 * @file JavaCrush.java
 * @description Jeu Candy Crush : alignement de boutons.
 * @auteur Stéphane Overlen.
 * @date 29 janvier 2016.
 */
package crush;

import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Point d'entrée du programme.
 * @param args taille de la grille.
 * @étape1 créer la grille du jeu.
 * @étape2 afficher la fenêtre de score après un temps défini.
 * @amélioration1 diminuer le nombre d'images disponibles si la taille de la grille entrée en paramètre est trop petite
 * @amélioration2 augmenter/diminuer la taille des images selon le nombre de boutons par rapport à la taille de la grille
 */
public class JavaCrush {
  
    public static void main(String[] args){
    	
    	String string = null;                                       // chaine de caractères pour le score
    	int taille = 0;                                             // taille de chaque côté de la grille
    	
    	try { taille = 10; }                 // taille = paramètre
    	catch(NumberFormatException e){                             // en cas d'erreur
    		System.out.println("Parametre doit etre un entier.");   // afficher un message
        }
    	
    	if(taille < 8){                                             // si la grille est trop petite (injouable)
    		taille = 8;                                             // mettre une taille par défaut
    		System.out.println("Taille trop petite. Taille par défaut utilisée.");
    	}
    	
    	final Random rnd = new Random(System.currentTimeMillis());  // créer un nombre aléatoire
    	VueCrush vue = new VueCrush(rnd, taille);                   // instancier la grille contenant les boutons
        JFrame f = new JFrame();                                    // créer un JFrame 1
        f.getContentPane().add(vue);                                // ajouter la vue au JFrame 1
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // fermer la fenêtre 1 quand on clique sur la croix
        f.setSize(800, 800);                                        // taille de la fenêtre 1
        f.setVisible(true);                                         // affichage de la fenêtre 1
        
        JFrame f2 = new JFrame();                                    // créer un JFrame 2
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // fermer la fenêtre 2 quand on clique sur la croix
        f2.setSize(200, 140);                                        // taille de la fenêtre 2
        
        try {
            Thread.sleep(20000);                                    // Laisser l'utilisateur jouer 20 secondes
        } catch(InterruptedException ex) {                          // en cas d'échec du sleep
            Thread.currentThread().interrupt();                     // interruption du thread
        }
        
        f.setVisible(false);                                        // masquer la fenêtre 1
        string = "Score : " + Integer.toString(vue.getScore());     // score dans une chaine de caractères
        JLabel l = new JLabel(string, SwingConstants.CENTER);       // créer un label pour afficher le score
        f2.add(l);                                                  // ajouter le label à la fenêtre 2
        f2.setVisible(true);                                        // afficher la fenêtre 2 contenant le score
    }
}
