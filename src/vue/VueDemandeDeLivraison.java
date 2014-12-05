package vue;

import com.mxgraph.view.mxGraph;

import modele.DemandeDeLivraison;
import modele.Noeud;

/**
 * @author: hexanome 4202
 * Vue représentant une demande de livraison
 */
public class VueDemandeDeLivraison extends VueNoeud {

	/**
	 * Boolean indiquant si la demande de livraison peut être satisfaite dans la
	 * plage horaire demandée ou non
	 */
	private boolean tempsDepasse = false;

	/**
	 * Le numéro de la plage horaire dans laquelle est la demande de livraison
	 */
	private int numPlage = 0;

	/**
	 * @param demandeDeLivraison
	 * @param hX
	 * @param hY
	 * @param numPlage
	 */
	public VueDemandeDeLivraison(Noeud demandeDeLivraison,
			double hX, double hY, int numPlage) {
		super(demandeDeLivraison, hX, hY,
				COULEUR_REMPLISSAGE[numPlage], COULEUR_BORDURE[numPlage]);
		this.numPlage = numPlage;
		modifierForme("shape=triangle;strokeWidth=2;fillColor=red;strokeColor="
		+ getCouleurBordure(), RAYON_POINT); 
	}

	/**
	 * @param demandeDeLivraison
	 * @param hX
	 * @param hY
	 * @param couleurRemplissage
	 * @param couleurBordure
	 */
	protected VueDemandeDeLivraison(Noeud demandeDeLivraison,
			double hX, double hY, String couleurRemplissage,
			String couleurBordure) {
		super(demandeDeLivraison, hX, hY, couleurRemplissage,
				couleurBordure);
	}

	/**
	 * @param demandeDeLivraison
	 * @param hX
	 * @param hY
	 * @param numPlage
	 */
	public VueDemandeDeLivraison(Noeud demandeDeLivraison,
			double hX, double hY, int numPlage, boolean tempsDepasse) {
		this(demandeDeLivraison, hX, hY, COULEUR_REMPLISSAGE[numPlage],
				COULEUR_BORDURE[numPlage]);
		this.tempsDepasse = tempsDepasse;
		this.numPlage = numPlage;
	}

	/**
	 * Modifie le temps dépassé
	 * 
	 * @param tempsDepasse
	 *            (temps dépassé à modifier)
	 */
	public void setTempsDepasse(boolean tempsDepasse) {
		this.tempsDepasse = tempsDepasse;
	}

	/**
	 * Affiche le graph
	 * 
	 * @param graph
	 * @param point
	 */
	public void afficher(mxGraph graph, Object point) {
		if(getStyle() != null){
			super.afficher(graph);
		}
		if (!tempsDepasse) {
			// TODO Grossir le temps dépassé
			graph.setCellStyle("fillColor=" + getCouleurRemplissage()
					+ ";strokeColor=" + getCouleurBordure(),
					new Object[] { point });
		} else {
			graph.setCellStyle(
					"shape=triangle;strokeWidth=2;fillColor=red;strokeColor="
							+ getCouleurBordure(), new Object[] { point });
		}
	}

	/**
	 * Renvoie le numéro de la plage
	 * 
	 * @return numPlage
	 */
	public int getNumPlage() {
		return numPlage;
	}

}