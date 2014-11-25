package modele;

import java.util.*;

/**
 * 
 */
public class PlageHoraire {

    /**
     * 
     */
    public PlageHoraire() {
    }
    
    /**
     * @param heureDebut 
     * @param heureFin
     */
    public PlageHoraire(int heureDebut, int heureFin) {
        // TODO implement here
    }

    /**
     * 
     */
    private Date heureDebut;

    /**
     * 
     */
    private Date heureFin;

    /**
     * 
     */
    private Set<DemandeDeLivraison> demandeLivraisonPlage;

    /**
     * @return les noeuds correspondants aux demandes de livraisons de la plage horaire
     */
    public List<Noeud> getNoeuds() {
        return null;
    }

}