package vue;

import java.util.*;

import com.mxgraph.view.mxGraph;

import modele.Itineraire;
import modele.Noeud;
import modele.Troncon;

/**
 * 
 */
public class VueItineraire {
	
	/**
	 * L'itinéraire
	 */
	private Itineraire itineraire;
	
	private Set<VueTroncon> vuesTroncons;
	
	/**
	 * La couleur de l'itinéraire
	 */
	private String couleur;
	
    /**
	 * @param itineraire
	 * @param couleur
	 */
	public VueItineraire(Itineraire itineraire, HashMap<Integer, VueNoeud> vueNoeuds, int idPremierNoeud, String couleur) {
		this.itineraire = itineraire;
		this.couleur = couleur;
		vuesTroncons = new HashSet<VueTroncon>();
		
		VueNoeud noeudPrecedent = vueNoeuds.get(idPremierNoeud);
		for (Troncon troncon : itineraire.getTronconsItineraire()) {
			vuesTroncons.add(new VueTroncon(noeudPrecedent,
						vueNoeuds.get(troncon.getNoeudFin().getId()), couleur, 3));
			noeudPrecedent = vueNoeuds.get(troncon.getNoeudFin().getId());
		}
	}




	/**
     * Méthode permettant d'afficher l'itinéraire
     */
    public void afficher(mxGraph graph) {
        for (VueTroncon vueTroncon : vuesTroncons) {
			vueTroncon.afficher(graph);
		}
    }

}