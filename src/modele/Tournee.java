package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
     * @return la feuille de route editee
     */
    public String editerFeuilleRoute() {
        return "";
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
     * Méthode calculant le plus court chemin entre deux noeuds
     * @param noeudDepart
     * @param noeudDestination
     * @return la liste ordonnée de troncons à suivre représentant le plus court chemin
     */
    private ArrayList<Troncon> calculerDijkstra(Noeud noeudDepart, Noeud noeudDestination) {
		
    	ArrayList<Troncon> cheminAPrendre = new ArrayList<Troncon>();
    	
    	HashMap<Noeud, Double> graphe = new HashMap<Noeud, Double>();
    	
    	Set<Noeud> noeudNonVisite = planTournee.getToutNoeuds();
    	Iterator<Noeud> it = noeudNonVisite.iterator();
    	
    	while(it.hasNext()){
    		graphe.put(it.next(), Double.MAX_VALUE);
    	}
    	graphe.put(noeudDepart, 0.0);
    	Noeud noeudCourant = noeudDepart;
    	
    	for(Iterator<Troncon> itTroncon = noeudCourant.getTronconSortants().iterator(); it.hasNext(); ){
    		Troncon troncon = itTroncon.next();
    		Noeud noeud = troncon.getNoeudFin();
    		if(troncon.getTemps() < graphe.get(noeud)){
    			graphe.put(noeud,troncon.getTemps());
    		}
    	}
    	noeudNonVisite.remove(noeudCourant);
    	
    	return cheminAPrendre;
    }

    /**
     * 
     */
    public void calculerTournee() {
        // TODO implement here
    }

}