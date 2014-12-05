package vue;

import java.util.HashMap;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

/**
 * @author: hexanome 4202
 * Vue représentant un troncon de l'application
 */
public class VueTroncon {

	/**
	 * Le point de départ
	 */
	private VueNoeud depart;

	/**
	 * Le point d'arrivée
	 */
	private VueNoeud arrivee;

	/**
	 * La couleur du tronçon
	 */
	private String couleur;

	/**
	 * L'épaisseur de la flèche
	 */
	private int epaisseur = 1;

	/**
	 * @param depart
	 * @param arrivee
	 * @param couleur
	 */
	public VueTroncon(VueNoeud depart, VueNoeud arrivee, String couleur) {
		super();
		this.depart = depart;
		this.arrivee = arrivee;
		this.couleur = couleur;
	}

	/**
	 * @param depart
	 * @param arrivee
	 * @param couleur
	 */
	public VueTroncon(VueNoeud depart, VueNoeud arrivee) {
		this(depart, arrivee, VueNoeud.COULEUR_REMPLISSAGE[0]);
	}

	/**
	 * @param depart
	 * @param arrivee
	 * @param couleur
	 * @param epaisseur
	 */
	public VueTroncon(VueNoeud depart, VueNoeud arrivee, String couleur,
			int epaisseur) {
		this(depart, arrivee, couleur);
		this.epaisseur = epaisseur;
	}

	/**
	 * Méthode permettant d'afficher le tronçon
	 * 
	 * @param graph
	 */
	public void afficher(mxGraph graph) {

		graph.insertEdge(graph.getDefaultParent(), null, "", depart.getPoint(),
				arrivee.getPoint(), "strokeColor=" + couleur + ";strokeWidth="
						+ epaisseur);
	}

	/**
	 * Méthode permettant d'afficher le tronçon
	 * 
	 * @param graph
	 * @param tronconsTraverses
	 *            la liste des tronçons déjà parcourus
	 */
	public void afficher(mxGraph graph,
			HashMap<String, Integer> tronconsTraverses) {

		String key = ""
				+ Math.max(depart.getNoeud().getId(), arrivee.getNoeud()
						.getId())
				+ "-"
				+ Math.max(depart.getNoeud().getId(), arrivee.getNoeud()
						.getId());

		String edgeStyle = "";

		if (tronconsTraverses.containsKey(key)) {
			tronconsTraverses.put(key, tronconsTraverses.get(key) + 1);

			edgeStyle = (tronconsTraverses.containsKey(key)) ? "edgeStyle=elbowEdgeStyle;elbow=horizontal;"
					+ "exitX=0.5;exitY=1;exitPerimeter=1;entryX=0;entryY=0;entryPerimeter=1;"
					+ mxConstants.STYLE_ROUNDED + "=1;"
					: "";
		} else {
			tronconsTraverses.put(key, 1);
		}

		graph.insertEdge(graph.getDefaultParent(), null, "", depart.getPoint(),
				arrivee.getPoint(), edgeStyle + "strokeColor=" + couleur
						+ ";strokeWidth=" + epaisseur);
	}

}