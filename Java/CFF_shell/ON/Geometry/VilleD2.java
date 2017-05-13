package ON.Geometry;

/**
 * Création et calculs relatifs aux points à afficher
 */
public class VilleD2 {
	
    private Double x = null, y = null;
    private String name = "";
    
    public VilleD2() {             	// création d'un point générique
        setX(10.0);                  // x = 10
        setY(10.0);                  // y = 10
        setName("OverNeyTown");
    }
    
    public VilleD2(Double a, Double b, String n) { // créer un point à partir de 2 valeurs
        setX(a);  // x
        setY(b);  // y
        setName(n);
    }
    
    public VilleD2(ON.Geometry.VilleD2 p, String n){     // à partir d'un point
        setX(p.getX());
        setY(p.getY());
        setName(n);
    }
    
    public VilleD2(Double[] p, String n){   // à partir d'un tableau de valeurs
        setX(p[0]);
        setY(p[1]);
        setName(n);
    }
    
    public Double getX(){ return x; }  // retourne x
    public Double getY(){ return y; }  // retourne y
    public String getName(){ return name; }	// retourne le nom de la ville
    public PointD2 getP(){ return new PointD2(x, y); }
    
    public void setX(Double a){ x = Double.valueOf(a); }  	// changer la valeur de x
    public void setY(Double a){ y = Double.valueOf(a); } 	// changer la valeur de y
    public void setName(String n){ name = n; } 	// changer la valeur de y
    
    public double dist(PointD2 p){ 		// calculer la distance entre 2 points
        int a = p.getX().intValue(); 	// a = x
        int b = p.getY().intValue(); 	// b = y
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