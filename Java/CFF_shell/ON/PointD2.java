package ON.Geometry;

/**
 * Création et calculs relatifs aux points à afficher
 */
public class PointD2 {
   
    private Double x = null, y = null;
   
    public PointD2() {             // création d'un point générique
        setX(10.0);                  // x = 10
        setY(10.0);                  // y = 10
    }
   
    public PointD2(Double a, Double b) { // créer un point à partir de 2 valeurs
        setX(a);  // x
        setY(b);  // y
    }
   
    public boolean defini() {      // retourne vrai si le point existe
        return (getX() != null) && (getY() != null);
    }
   
    public PointD2(ON.Geometry.PointD2 p){     // à partir d'un point
        setX(p.getX());
        setY(p.getY());
    }
   
    public PointD2(Double[] p){   // à partir d'un tableau de valeurs
        setX(p[0]);
        setY(p[1]);
    }
   
    public Double getX(){ return x; }  // retourne x
    public Double getY(){ return y; }  // retourne y
    public void setX(Double a){ x = Double.valueOf(a); }  	// changer la valeur de x
    public void setY(Double a){ y = Double.valueOf(a); } 	// changer la valeur de y
   
    public double dist(ON.Geometry.PointD2 p){ // calculer la distance entre 2 points
        int a = p.getX().intValue(); // a = x
        int b = p.getY().intValue(); // b = y
        return Math.sqrt(Math.pow(a-getX().intValue(), 2) + Math.pow(b-getY().intValue(), 2)); // retourner racine de (a*a + b*b)
    }
   
    public String myToString(){    // retourne les coordonnees sous forme de chaine de caratères
        String coordonnees = getX().toString() + "," + getY().toString();
        return coordonnees;
    }
   
    public boolean myEquals(Object o){
        if(this.getClass() == o.getClass()) return true; // retourne vrai si les classes sont égales
        else return false;
    }
}
