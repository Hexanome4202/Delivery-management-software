package modele;

import java.util.*;

/**
 * 
 */
public class Livraison {

    /**
     * 
     */
    public Livraison() {
    }
    
    /**
     * @param noeudCourant 
     * @param client
     */
    public Livraison(Noeud noeudCourant, int client) {
        // TODO implement here
    }

    /**
     * @param entrepot
     */
    public Livraison(int entrepot) {
        // TODO implement here
    }

    /**
     * 
     */
    private Date heure;

    /**
     * 
     */
    private String motifNonLivraison;

    /**
     * 
     */
    private DemandeDeLivraison demandeLivraison;

    /**
     * 
     */
    private Etat etat;

    /**
     * 
     */
    public enum Etat {
        EnAttente,
        Livree,
        Retard,
        NonLivre
    }

}