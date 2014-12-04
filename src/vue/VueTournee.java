package vue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.mxgraph.view.mxGraph;

import modele.DemandeDeLivraison;
import modele.Itineraire;
import modele.PlageHoraire;
import modele.Tournee;

/**
 * 
 */
public class VueTournee {

	/**
	 * Liste des vues DemandeDeLivraison associées à l'id du noeud correspondant
	 */
	private HashMap<Integer, VueDemandeDeLivraison> vuesDemandeDeLivraison;

	/**
	 * Liste des vuesItineraires
	 */
	private Set<VueItineraire> vuesItineraires;

	/**
	 * La vue de l'entrepot
	 */
	private VueEntrepot vueEntrepot;

	/**
	 * La tournée
	 */
	private Tournee tournee;

	/**
	 * La liste des vueNoeuds associées à l'id du noeud, nécessaire pour tracer
	 * les tronçons
	 */
	HashMap<Integer, VueNoeud> vueNoeuds;

	/**
	 * Facteurs de mise à l'échelle pour l'affichage sur le plan
	 */
	private double hY;
	private double hX;

	/**
	 * Affiche la vue de la tournée
	 * 
	 * @param tournee
	 */
	public VueTournee(Tournee tournee, double hX, double hY,
			HashMap<Integer, VueNoeud> vueNoeuds) {
		this.vueNoeuds = vueNoeuds;
		vuesDemandeDeLivraison = new HashMap<Integer, VueDemandeDeLivraison>();
		vuesItineraires = new HashSet<VueItineraire>();

		this.hX = hX;
		this.hY = hY;

		if (tournee != null) {
			this.tournee = tournee;

			if (tournee.getEntrepot() != null) {
				vueEntrepot = new VueEntrepot(tournee.getEntrepot(), hX, hY);
				vuesDemandeDeLivraison.put(vueEntrepot.getNoeud().getId(),
						vueEntrepot);
				vueNoeuds.get(vueEntrepot.getNoeud().getId()).modifierForme(
						vueEntrepot.getStyle(), vueEntrepot.getTaille());
				;
			}
		}

		chargerVuesDemandeDeLivraison(tournee);
	}

	/**
	 * Modifie la tournée
	 * 
	 * @param tournee
	 */
	public void setTournee(Tournee tournee) {
		if (tournee != null) {
			vuesDemandeDeLivraison.clear();

			chargerVuesDemandeDeLivraison(tournee);
			this.tournee = tournee;

			if (tournee.getEntrepot() != null) {
				vueEntrepot = new VueEntrepot(tournee.getEntrepot(), hX, hY);
			}

			int idPremierNoeud = vueEntrepot.getNoeud().getId();

			for (Itineraire itineraire : tournee.getItineraires()) {
				String couleur = VueNoeud.COULEUR_BORDURE[1];
				if (vuesDemandeDeLivraison.containsKey(idPremierNoeud)) {
					couleur = VueNoeud.COULEUR_BORDURE[vuesDemandeDeLivraison
							.get(idPremierNoeud).getNumPlage()];
				}
				vuesItineraires.add(new VueItineraire(itineraire, vueNoeuds,
						idPremierNoeud, couleur));
				idPremierNoeud = itineraire.getArrivee().getNoeud().getId();
			}
		}

	}

	/**
	 * Charge les vues de chaque demande de livraison
	 * 
	 * @param tournee
	 */
	private void chargerVuesDemandeDeLivraison(Tournee tournee) {
		int numPlage = 1;
		for (PlageHoraire plage : tournee.getPlagesHoraires()) {

			for (DemandeDeLivraison demandeDeLivraison : plage
					.getDemandeLivraison()) {
				VueDemandeDeLivraison vueDemande = new VueDemandeDeLivraison(
						demandeDeLivraison, hX, hY, numPlage);
				if (tournee.getDemandesTempsDepasse() != null
						&& tournee.getDemandesTempsDepasse().contains(
								demandeDeLivraison)) {
					vueDemande.setTempsDepasse(true);
				}
				vuesDemandeDeLivraison.put(demandeDeLivraison.getNoeud()
						.getId(), vueDemande);

				vueNoeuds.get(demandeDeLivraison.getNoeud().getId()).setColors(
						numPlage);
			}
			numPlage++;
		}
	}

	/**
	 * Afficher le graphe
	 * 
	 * @param graph
	 *            le graphe à afficher
	 */
	public void afficher(mxGraph graph) {
		if (tournee.getItineraires() != null) {

			// On indique aux DemandesDeLivraison au Temps Dépassé qu'elles le
			// sont
			if (tournee.getDemandesTempsDepasse() != null) {
				for (DemandeDeLivraison demande : tournee
						.getDemandesTempsDepasse()) {
					try {
						vuesDemandeDeLivraison.get(demande.getNoeud().getId())
								.setTempsDepasse(true);
					} catch (Exception e) {
					}
				}
			}

			// On affiche les demandes de livraison
			Set<Integer> cles = vuesDemandeDeLivraison.keySet();
			for (Integer cle : cles) {
				vuesDemandeDeLivraison.get(cle).afficher(graph,
						vueNoeuds.get(cle).getPoint());
			}

			if (vuesItineraires != null && vueEntrepot != null) {
				// puis on affiche l'itinéraire
				HashMap<String, Integer> tronconsTraverses = new HashMap<String, Integer>();

				for (VueItineraire vueItineraire : vuesItineraires) {
					vueItineraire.afficher(graph, tronconsTraverses);
				}
			}
		} else {
			// On affiche seulement les demandes de livraison si la tournée
			// n'est pas calculée
			Set<Integer> cles = vuesDemandeDeLivraison.keySet();
			for (Integer cle : cles) {
				vuesDemandeDeLivraison.get(cle).afficher(graph);
			}
		}
	}

	/**
	 * Methode indiquant si le noeud avec l'id passé est une demande de
	 * livraison
	 * 
	 * @param idNoeud
	 *            l'id du noeud à tester
	 * @return boolean : true or false
	 */
	public boolean estDemandeDeLivraison(int idNoeud) {
		return vuesDemandeDeLivraison.containsKey(idNoeud);
	}

	/**
	 * Supprime une demande de livraison
	 * 
	 * @param idNoeud
	 */
	public void supprimerDemandeDeLivraison(int idNoeud) {
		vueNoeuds.get(idNoeud).setColors(0);
	}

	/**
	 * Renvoie la tournée
	 * 
	 * @return tournee
	 */
	public Tournee getTournee() {
		return this.tournee;
	}

	/**
	 * Méthode permettant de supprimer toutes les demandes livraison et
	 * compagnie
	 * 
	 * @param graph
	 */
	public void reset(mxGraph graph) {
		// On remet tous les noeuds en gris
		Set<Integer> cles = vuesDemandeDeLivraison.keySet();
		for (Integer cle : cles) {
			vueNoeuds.get(cle).setColors(0);
			vueNoeuds.get(cle).afficher(graph);
		}

		vuesDemandeDeLivraison.clear();
	}
}