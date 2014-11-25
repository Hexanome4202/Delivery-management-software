package modele;

import java.util.ArrayList;

/**
 * 
 */
public class Noeud {
    
    /**
     * @param id l'id du noeud
     * @param x la position géographique en x du noeud
     * @param y la position géographique en y du noeud
     */
    public Noeud(int id, int x, int y) {
    	this.x = x;
    	this.y = y;
    	this.id = id;
    	this.sortants = new ArrayList<Troncon>();
    }
    
    /**
     * 
     * @param id l'id du noeud
     * @param x la position géographique en x du noeud
     * @param y la position géographique en y du noeud
     * @param troncons les troncons sortants du noeud
     */
    public Noeud(int id, int x, int y, ArrayList<Troncon> troncons) {
    	this(id, x, y);
    	this.sortants = troncons;
    }

    /**
     * 
     */
    private int x;

    /**
     * 
     */
    private int y;

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private ArrayList<Troncon> sortants;
    
    /**
     * 
     * @return les troncons sortants (<code>Troncon</code>) du noeud
     */
    public ArrayList<Troncon> getTronconSortants(){
    	return sortants;
    }

    /**
     * Getter de l'attribut <code>id</code>.
     * @return l'id du noeud courant.
     */
    public int getId() {
    	return this.id;
    }
}