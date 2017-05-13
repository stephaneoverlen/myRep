package ON.Geometry;

import ON.application.Panneau;

public class SegmentD2 extends Panneau {

    private PointD2 p1 = null, p2 = null, pTmp = null;	// points du segment
    private Double a = null;                      		// Pente de la droite
    private Double b = null;

    public SegmentD2(Double x1, Double y1, Double x2, Double y2){	// à partir des coordonnées de 2 points
        p1 = new PointD2(x1, y1);                                               // point de référence de la droite
        p2 = new PointD2(x2, y2);                                               // point de référence de la droite

        setPente(Double.valueOf((y2 - y1) / (x2 - x1)));  // calcul de la pente
        setOrdoneeOrigine(x1, y1);
    }
 
    public Double getPente(){ return a.doubleValue(); }
    public PointD2 getP1(){ return p1; }
    public PointD2 getP2(){ return p2; }
    public Double getOrdoneeOrigine(){ return b; }
    
    public void setP1(Double x, Double y){
    	p1.setX(x);
    	p1.setY(y);
	}
    
    public void setP2(Double x, Double y){
    	p2.setX(x);
    	p2.setY(y);
	}
	
    public void setPoints(PointD2 pA, PointD2 pB){
        p1 = pA;
        p2 = pB;
    }
 
    public void setPente(Double at){	// modifier la pente
        a = at;
        setOrdoneeOrigine(p1.getX(), p1.getY());
    }
 
    public void setPente(PointD2 p1, PointD2 p2){	// modifier la pente
        a = (double)(p2.getY()-p1.getY()) / (p2.getX()-p1.getX());
        setOrdoneeOrigine(p1.getX(), p1.getY());
    }
 
    public void setPente(Double x1, Double y1, Double x2, Double y2){	// modifier la pente
        a = (double)(y2-y1) / (x2-x1);
        setOrdoneeOrigine(x1, y1);
    }
 
    public void setOrdoneeOrigine(Double x, Double y){	// modifier la pente
        b = y-(a*x);
    }
}