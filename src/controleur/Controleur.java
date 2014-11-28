package controleur;

import modele.DemandeDeLivraison;
import modele.Plan;
import modele.Tournee;

import org.w3c.dom.Element;

import vue.VueTournee;

/**
 * 
 */
public class Controleur {
	
    static public int PARSE_ERROR = -1;
    static public int PARSE_OK = 1;
    
    private Tournee tournee;
    private VueTournee vueTournee;
    private Plan plan;

    /**
     * 
     */
    public Controleur() {
    	tournee = new Tournee();
    	vueTournee = new VueTournee(null);
    	plan=new Plan();
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
        //this.tournee.construireLivraisonsAPartirDeDOMXML(noeudDOMRacine);
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

    public void ajouterLivraison(int client, int noeud, DemandeDeLivraison precedent) {
    }

    /**
     * @param livraison
     */
    public void supprimerLivraison(DemandeDeLivraison livraison) {
        // TODO implement here
    }

    /**
     * @return un object de type <code>String</code> contenant la feuille editee
     */
    public String editerFeuilleRoute() {
        return this.tournee.editerFeuilleRoute();
    }

    /**
     * @param x 
     * @param y
     */
    public void planClique(int x, int y) {
        // TODO implement here
    }
    
    
    
	public int construireLivraisonsAPartirDeDOMXML(Element vueCadreDOMElement) {
		tournee.setPlanTournee(plan);
        if (tournee.construireLivraisonsAPartirDeDOMXML(vueCadreDOMElement) != Controleur.PARSE_OK) {
            return Controleur.PARSE_ERROR;
        }
        //vueTournee.dessiner();
	return Controleur.PARSE_OK;
    }
	
	public int construirePlanAPartirDeDOMXML(Element racineXML) {
		plan=new Plan(racineXML);
        //vueTournee.dessiner();
        return Controleur.PARSE_OK;
    }


}