package vue;

import com.mxgraph.view.mxGraph;

import modele.DemandeDeLivraison;
import modele.Noeud;

public class VueEntrepot extends VueDemandeDeLivraison {

	/**
	 * @param noeud
	 * @param hX
	 * @param hY
	 * @param couleurRemplissage
	 * @param couleurBordure
	 */
	public VueEntrepot(DemandeDeLivraison entrepot, double hX, double hY) {
		super(entrepot, hX, hY, "yellow", "black");
	}

	/* (non-Javadoc)
	 * @see vue.VueNoeud#afficher(com.mxgraph.view.mxGraph)
	 */
	@Override
	public void afficher(mxGraph graph) {
		// TODO Auto-generated method stub
		super.afficher(graph,
				"shape=ellipse;perimeter=30;strokeColor=black;strokeWidth=3;fillColor=yellow");
	}
	
	

}
