package modele;

import java.util.*;

/**
 * 
 */
public class DemandeDeLivraison {

    /**
     * 
     */
    public DemandeDeLivraison() {
    }
    
    /**
     * @param noeud 
     * @param client
     */
    public DemandeDeLivraison(Noeud noeud, int client) {
        // TODO implement here
    }
    
    /**
     * @param entrepot
     */
    public DemandeDeLivraison(int entrepot) {
        // TODO implement here
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private int idClient;

    /**
     * 
     */
    private Noeud adresseLivraison;

    /**
     * 
     */
    private PlageHoraire plageHoraire;

    /**
     * @return le noeud rattache a la demande de livraison
     */
    public Noeud getNoeud() {
        return null;
    }

    /**
     * @param noeud 
     * @param idClient
     */
    public void ajouterDemande(Noeud noeud, int idClient) {
        // TODO implement here
    }

}