package modele;

import java.util.*;

/**
 * 
 */
public class Livraison {
	/**
	 * Constructeur vide de <code>Livraison</code>.
	 */
    private Livraison() {
    	this.motifNonLivraison = "";
    	this.heure = null;
    	this.etat = Etat.EN_ATTENTE;
    }
	
    /**
     * Constructeur de <code>Livraison</code>
     * @param demande La demande de livraison liée à la livraison.
     */
    public Livraison(DemandeDeLivraison demande) {
    	this();
        this.demandeLivraison = demande;
    }

    /**
     * Constructeur de <code>Livraison</code>
     * @param entrepot
     */
    public Livraison(Noeud entrepot) {
    	this();
        this.demandeLivraison = new DemandeDeLivraison(-1, entrepot);
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