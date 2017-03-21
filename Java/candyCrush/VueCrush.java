package crush;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class VueCrush extends JPanel implements MouseListener { // JPanel qui gère les actions sur les boutons de la souris
  
    private static final String[] Letter = { "bird.png", "cricket.png",
        "elephant.png", "penguin.png", "dolphin.png", "jelly_fish.png",
        "gnome_panel_fish.png","pig.png", "kbugbuster.png"}; // tableau contenant les noms des images du jeu
    
    private static int tmp = 0;                                // variable temporaire
    private static int div = 0;                                // résultat de division
    private static int score = 0;                // score du jeu + score temporaire
    private static int num[];                                  // valeurs des boutons (correspondant aux images)
    private static int nbClicks = 0;                           // 1er ou 2e click
	private static int ref = 0;                                // bouton clické devient la référence
    private static int numButton = 0;                          // numéro du bouton cliqué
    private static int dim = 0;                                // nombre de cases du jeu = taille * taille
    private static int valAbs = 0;                             // valeur absolue
    private static int btnScoreH1 = -1, btnScoreH2 = -1;       // boutons à traiter avant d'ajouter le score
    private static int btnScoreV1 = -1, btnScoreV2 = -1;       // boutons à traiter avant d'ajouter le score
    private static Icon iconTmp;                               // icône temporaire
    private static int taille;                             // nombre de boutons des lignes et/ou colonnes
    private static JButton[] btn;                              // tableau de boutons
    Random rnd;                                                // nombre aléatoire
    
    /**
     * Contenu du JPanel
     * @param1 déclaration de nombre aléatoire
     * @param2 nombre de lignes et/ou colonnes
     * @étape1 définir la grille du jeu.
     * @étape2 insérer les boutons dans la grille du JPanel.
     * @étape3 créer les threads de score et remplissage
     */
    public VueCrush(Random rnd, int t) {
        super(new GridLayout(t, t, 2, 2));                     // définition de la grille
        
        taille = t;                                            // récupérer le nombre de lignes et/ou colonnes
        dim = taille*taille;                                   // récupérer le nombre de boutons dans la grille
        num = new int[dim];                                    // définir la taille du tableau de valeurs des boutons
        btn = new JButton[dim];                                // définir la taille du tableau de boutons
        this.rnd = rnd;                                        // récupérer un nombre aléatoire
       
        for(int j = 0; j < dim; j++) {                         // pour chaque case de la grille de jeu
            num[j] = rnd.nextInt(Letter.length);               // mettre une valeur aléatoire dans le tableau de numéros
                                                               // instancier le bouton avec une image en paramètre
            btn[j] = new JButton(new ImageIcon(new ImageIcon(Letter[num[j]]).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT)));
            btn[j].setName(String.valueOf(j));                 // attribuer un nom au bouton correspondant à sa position dans la grille
            btn[j].addMouseListener(this);                     // écouteur sur le bouton
            this.add(btn[j]);                                  // ajouter le bouton dans le JPanel
        }
        
        Horizontal hori = new Horizontal(this);                // "classe exécutée" par le thread qui vérifie les points horizontaux
        Thread td1 = new Thread(hori);                         // instanciation du thread
        td1.start();                                           // démarrage du thread

        Vertical verti = new Vertical(this);                   // thread qui vérifie les points verticaux
        Thread td2 = new Thread(verti);
        td2.start();

        Replace[] rep = new Replace[taille];                   // créer autant de threads que de colonnes pour combler les boutons "vides"
        for(int j = 0; j < taille; j++){
            rep[j] = new Replace(this, j);
            Thread td3 = new Thread(rep[j]);
            td3.start();
        }
    }
    
    // Définitions obligatoires associées à 'MouseListener'
    public void mouseReleased(MouseEvent event) { }            // relâche le bouton souris
    public void mouseClicked(MouseEvent e) { }                 // clique un bouton
    public void mouseEntered(MouseEvent event) { }             // souris entre dans la zone du bouton
    public void mouseExited(MouseEvent event) { }              // souris sort de la zone du bouton
    
    public void mousePressed(MouseEvent event) {               // appui bouton gauche souris, NETTEMENT MIEUX que "mouseclicked"
        BtnColor(ref, null);                                   // aucune couleur en arrière-plan des boutons autour du bouton cliqué
        JButton test = (JButton)event.getSource();             // récupérer les infos du bouton
        numButton = Integer.parseInt(test.getName());          // parmi ces infos, isoler le nom du bouton

        if(nbClicks == 0){                                     // si c'est le 1er bouton cliqué par l'utilisateur ou si le bouton est trop loin
        	refBtn();                                          // définir le bouton de référence
        }
        
        else{                                                  // si c'est le 2ème bouton clické par l'utilisateur et qu'il est collé au 1er
        	if((numButton == ref-1 || numButton == ref+1 || numButton == ref-taille || numButton == ref+taille) &&
        		(testSwap(ref, numButton) == 1)){
        	
                iconTmp = btn[ref].getIcon();                  // inverser les images
                btn[ref].setIcon(btn[numButton].getIcon());
                btn[numButton].setIcon(iconTmp);

                tmp = num[ref];                                // inverser les numéros
                num[ref]= num[numButton];
                num[numButton] = tmp;

    		    btnScoreH1 = ref;                              // boutons cliqués = références pour le score
    		    btnScoreV1 = ref;
    			btnScoreH2 = numButton;
    			btnScoreV2 = numButton;
                
        		nbClicks = 0;                                  // initialiser le nombre de clicks
        	}
        	else{                                              // si c'est le premier bouton cliqué
        		refBtn();                                      // définir le bouton de référence
        	}
        }
    }
    
    // Getters
    public int getNum(int c){ return num[c]; }                 // retourne le numéro d'image associé au bouton
    public int getTaille(){ return taille; }                   // retourne la taille de l'image
    public int getScore(){ return score; }                     // retourne le score
    public int getTabLength(){ return Letter.length; }         // retourne la taille du tableau d'images
    public JButton getBtn(int b){ return btn[b]; }             // retourne le bouton en paramètre
    public int getRnd(){ return rnd.nextInt(Letter.length); }  // retourne une valeur de bouton aléatoire
    
    // Setters
    public synchronized void setNum(int a, int b){ num[a] = b; } // modifie le numéro d'image associé au bouton
    public void setBtnIcon(int i){                               // modifier l'image du bouton
    	try{ btn[i].setIcon(new ImageIcon(new ImageIcon(Letter[num[i]]).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT))); }
    	catch(ArrayIndexOutOfBoundsException ie){ }
	}
    
    public synchronized void addScore(int s){                  // modifier le score
    	switch(s){                                             // si le nombre de boutons identiques en paramètre :
	        case 3: score += 50;                               // - est 3, ajouter 50 au score temporaire
                    break;                                     // quitter la boucle 'switch'
	        case 4: score += 150;                              // - est 4, ajouter 150 points au score temporaire
                    break;                                     // quitter la boucle 'switch'
	        case 5: score += 400;                              // - est 5, ajouter 400 points au score temporaire
                    break;                                     // quitter la boucle 'switch'
    	    default: System.out.println("Incorrect score!");   // score différent de 3, 4 ou 5
    	}
    }
    
    public synchronized int testSwap(int a, int b){            // tester si les boutons peuvent être échangés
		if(a > b){                                             // si 'a' est supérieur à 'b'
			tmp = a;                                           // inverser 'a' et 'b' afin que 'b' soit toujours supérieur à 'a' pour les calculs
			a = b;
			b = tmp;
		}

        valAbs =  b - a;                                       // valeur absolue de 'b - a'
        div = taille / valAbs;                                 // taille / valeur absolue

        // cette condition retourne 1 si l'échange des boutons va rapporter des points
        if(((a / valAbs) % taille >= 2 && getNum(b) == getNum(a - 2 * valAbs) && getNum(b) == getNum(a - valAbs)) ||
        		((a / div) % taille >= 2 && getNum(b) == getNum(a - 2 * div) && getNum(b) == getNum(a - div)) ||
        		((a / div) % taille < taille - 2 && getNum(b) == getNum(a + 2 * div) && getNum(b) == getNum(a + div)) ||
    		 	((a / div) % taille >= 1 && (a / div) % taille < taille - 1 && getNum(b) == getNum(a - div) && getNum(b) == getNum(a + div)) ||
    		 	((b / valAbs) % taille < taille - 2 && getNum(a) == getNum(b + 2 * valAbs) && getNum(a) == getNum(b + valAbs)) ||
    		 	((b / div) % taille >= 2 && getNum(a) == getNum(b - 2 * div) && getNum(a) == getNum(b - div)) ||
    		 	((b / div) % taille < taille - 2 && getNum(a) == getNum(b + 2 * div) && getNum(a) == getNum(b + div)) ||
    		 	((b / div) % taille >= 1 && (b / div) % taille < taille - 1 && getNum(a) == getNum(b - div) && getNum(a) == getNum(b + div))){
    		if(valAbs == taille || b % taille != 0) return 1;   // ne pas échanger si le bouton 1 est sur un bord et le 2 sur l'autre bord de la grille
    	}
    	return 0;                                               // empêcher l'échange des boutons s'il n'y a aucun match
    }

    public int getBtnScoreH1(){ return btnScoreH1; }  // bouton 1 à vérifier horizontalement pour entrer le score
    public int getBtnScoreH2(){ return btnScoreH2; }  // bouton 2 à vérifier horizontalement pour entrer le score
    public int getBtnScoreV1(){ return btnScoreV1; }  // bouton 1 à vérifier verticalement pour entrer le score
    public int getBtnScoreV2(){ return btnScoreV2; }  // bouton 2 à vérifier verticalement pour entrer le score
    public void resetBtnScoreH1(){ btnScoreH1 = -1; } // initialiser les boutons à vérifier
    public void resetBtnScoreH2(){ btnScoreH2 = -1; }
    public void resetBtnScoreV1(){ btnScoreV1 = -1; }
    public void resetBtnScoreV2(){ btnScoreV2 = -1; }
    
    public void refBtn(){          // bouton de référence
        ref = numButton;           // 'ref' récupère le nom du bouton
        nbClicks = 1;              // incrémentation du nombre de clicks
        BtnColor(ref, Color.cyan); // colorier le bouton en bleu
    }
    
    public void BtnColor(int btn, Color col){                                       // modifier la couleur de fond du bouton
    	if(btn % taille > 0){ getBtn(btn - 1).setBackground(col); }                 // colorier le bouton de gauche s'il existe
    	if(btn % taille < taille - 1){ getBtn(btn + 1).setBackground(col); }        // colorier le bouton de droite s'il existe
    	if(btn >= taille){ getBtn(btn - taille).setBackground(col); }               // colorier le bouton au-dessus s'il existe
    	if(btn < (taille - 1) * taille){ getBtn(btn + taille).setBackground(col); } // colorier le bouton en-dessous s'il existe
    }
}
