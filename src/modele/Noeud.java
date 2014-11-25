package modele;

import java.util.ArrayList;

/**
 * 
 */
public class Noeud {

    /**
     * 
     */
    public Noeud() {
    }
    
    /**
     * 
     */
    public Noeud(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    /**
     * 
     */
    public Noeud(int id, int x, int y) {
    	this.x = x;
    	this.y = y;
    	this.id = id;
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