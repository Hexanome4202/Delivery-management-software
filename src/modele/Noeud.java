package modele;

import java.util.*;

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
    private ZoneGeographique[] zone;

    /**
     * 
     */
    private Set<Troncon> sortants;

}