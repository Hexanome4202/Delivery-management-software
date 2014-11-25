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
    
    public ArrayList<Troncon> getTronconSortants(){
    	return sortants;
    }

}