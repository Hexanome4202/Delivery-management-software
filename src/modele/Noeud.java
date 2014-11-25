package modele;

import java.util.ArrayList;

/**
 * 
 */
public class Noeud {
    
    /**
     * 
     */
    public Noeud(int id, int x, int y) {
    	this.x = x;
    	this.y = y;
    	this.id = id;
    	this.sortants = new ArrayList<Troncon>();
    }
    
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
    private ZoneGeographique zone;

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

    public int getId() {
    	return this.id;
    }
}