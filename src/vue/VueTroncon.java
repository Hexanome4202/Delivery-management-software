package vue;

import java.util.*;

import com.mxgraph.view.mxGraph;

/**
 * 
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
	 * @param graph
	 */
	public void afficher(mxGraph graph){
		graph.insertEdge(graph.getDefaultParent(), null, "",
				depart.getPoint(),
				arrivee.getPoint(),
				"strokeColor=" + couleur+";strokeWidth="+epaisseur);
	}


}