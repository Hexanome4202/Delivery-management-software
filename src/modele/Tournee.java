package modele;

import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;

/**
 * 
 */
public class Tournee {

    /**
     * 
     */
    public Tournee() {
    }

    /**
     * 
     */
    private List<PlageHoraire> plagesHoraires;

    /**
     * 
     */
    private List<Itineraire> itineraires;

    /**
     * 
     */
    private Livraison entrepot;

    /**
     * 
     */
    private Plan planTournee;

    /**
     * 
     */
    public void creerFeuilleRoute() {
        // TODO implement here
    }

    /**
     * @param client 
     * @param noeud 
     * @param precedent
     */
    public void ajouterLivraison(int client, int noeud, Livraison precedent) {
        // TODO implement here
    }

    /**
     * @param livraison
     */
    public void supprimerLivraison(Livraison livraison) {
        // TODO implement here
    }

    /**
     * @param feuille
     */
    public void editerFeuilleRoute(String feuille) {
        // TODO implement here
    }

    /**
     * @param tourneeXML
     * TODO: Changer type
     */
    public void chargerTourneeDOM(Document tourneeXML) {
        // TODO implement here
    }

    /**
     * @param coordX 
     * @param coordY
     */
    public void recupererNoeud(int coordX, int coordY) {
        // TODO implement here
    }

    /**
     * @param noeudPrecedent 
     * @param noeudCourant 
     * @param client
     */
    public void ajouterLivraison(Noeud noeudPrecedent, Noeud noeudCourant, int client) {
        // TODO implement here
    }

    /**
     * @param noeudPrecedent
     */
    public void effacerItineraire(Noeud noeudPrecedent) {
        // TODO implement here
    }

    /**
     * @param noeudPrecedent 
     * @param noeudCourant 
     * @param troncons
     */
    private void calculerDijkstra(Noeud noeudPrecedent, Noeud noeudCourant, Set<Troncon> troncons) {
        // TODO implement here
    }

    /**
     * 
     */
    public void calculerTournee() {
        // TODO implement here
    }

}