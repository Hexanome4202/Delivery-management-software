package modele;

import java.util.*;

/**
 * 
 */
public class Itineraire {

    /**
     * 
     */
    public Itineraire() {
    }
    
    /**
     * @param liv1 
     * @param liv2 
     * @param troncons
     */
    public Itineraire(Livraison liv1, Livraison liv2, List<Troncon> troncons) {
        // TODO implement here
    }

    /**
     * 
     */
    private Livraison depart;

    /**
     * 
     */
    private Livraison arrivee;

    /**
     * 
     */
    private List<Troncon> tronconsItineraire;

    /**
     * @return le temps mis pour parcourir les troncons entre <code>depart</code> et <code>arrivee</code>
     */
    public int getTemps() {
        return -1;
    }

}