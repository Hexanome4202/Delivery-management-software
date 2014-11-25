package modele;

import java.util.*;

/**
 * 
 */
public class Livraison {
	/**
	 * Constructeur vide de <Livraison>.
	 */
    private Livraison() {
    	this.motifNonLivraison = "";
    	this.heure = null;
    	this.etat = Etat.EN_ATTENTE;
    }
	
    /**
     * @param noeudCourant 
     * @param client
     */
    public Livraison(DemandeDeLivraison demande) {
    	this();
        this.demandeLivraison = demande;
    }

    /**
     * @param entrepot
     */
    public Livraison(Noeud entrepot) {
    	this();
        this.demandeLivraison = new DemandeDeLivraison(entrepot);
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
        EN_ATTENTE,
        LIVREE,
        RETARD,
        NON_LIVREE
    }

}