package vue;

import modele.DemandeDeLivraison;

public class VueEntrepot extends VueDemandeDeLivraison {
	
	private final int TAILLE = 15;

	/**
	 * @param noeud
	 * @param hX
	 * @param hY
	 * @param couleurRemplissage
	 * @param couleurBordure
	 */
	public VueEntrepot(DemandeDeLivraison entrepot, double hX, double hY) {
		super(entrepot, hX, hY, "yellow", "black");
		modifierForme("shape=ellipse;"
				, TAILLE);
	}	
	
	/**
	 * Renvoie la taille de la VueEntrepot
	 * @return
	 */
	public int getTaille(){
		return TAILLE;
	}

}
