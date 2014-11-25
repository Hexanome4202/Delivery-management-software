package controleur;

import org.w3c.dom.Element;

import vue.VueTournee;
import modele.Livraison;
import modele.Tournee;

/**
 * 
 */
public class Controleur {
	
    static public int PARSE_ERROR = -1;
    static public int PARSE_OK = 1;
    
    private Tournee tournee;
    private VueTournee vueTournee;

    /**
     * 
     */
    public Controleur() {
    	tournee = new Tournee();
    	vueTournee = new VueTournee(null);
    }

    /**
     * @param fichierPlan
     */
    public void chargerPlanZone(String fichierPlan) {
        // TODO implement here
    }

    /**
     * @param fichierLivraisons
     */
    public void chargerLivraisons(String fichierLivraisons) {
        // TODO implement here
    }

    /**
     * 
     */
    public void calculerFeuilleRoute() {
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
     * @return un object de type <code>String</code> contenant la feuille editee
     */
    public String editerFeuilleRoute() {
        return "";
    }

    /**
     * @param x 
     * @param y
     */
    public void planClique(int x, int y) {
        // TODO implement here
    }
    
    
    
	public int ConstruireToutAPartirDeDOMXML(Element vueCadreDOMElement) {
//        if (tournee.construireAPartirDeDOMXML(vueCadreDOMElement) != Controleur.PARSE_OK) {
//            return Controleur.PARSE_ERROR;
//        }
//        vueTournee.dessiner();
		return Controleur.PARSE_OK;
    }


}