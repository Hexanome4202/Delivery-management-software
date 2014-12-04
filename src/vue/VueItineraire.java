package vue;

import java.util.*;

import com.mxgraph.view.mxGraph;

import modele.Itineraire;
import modele.Troncon;

/**
 * 
 */
public class VueItineraire {
	
	/**
	 * <code>Set</code> de <code>Troncon</code>
	 */
	private Set<VueTroncon> vuesTroncons;
	
	/**
	 * Constante contenant l'épaisseur des tronçons d'itinéraire
	 */
	private final int EPAISSEUR = 2;
	
    /**
	 * @param itineraire
	 * @param couleur
	 */
	public VueItineraire(Itineraire itineraire, HashMap<Integer, VueNoeud> vueNoeuds, int idPremierNoeud, String couleur) {
		vuesTroncons = new HashSet<VueTroncon>();
		
		VueNoeud noeudPrecedent = vueNoeuds.get(idPremierNoeud);
		for (Troncon troncon : itineraire.getTronconsItineraire()) {
			vuesTroncons.add(new VueTroncon(noeudPrecedent,
						vueNoeuds.get(troncon.getNoeudFin().getId()), couleur, EPAISSEUR));
			noeudPrecedent = vueNoeuds.get(troncon.getNoeudFin().getId());
		}
	}




	/**
     * Méthode permettant d'afficher l'itinéraire
     */
    public void afficher(mxGraph graph, HashMap<String, Integer> tronconsTraverses) {
        for (VueTroncon vueTroncon : vuesTroncons) {
			vueTroncon.afficher(graph, tronconsTraverses);
		}
    }

}