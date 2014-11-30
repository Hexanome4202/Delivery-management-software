package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Classe regroupant les méthodes utilisées pour trouver des plus courts chemins
 * @author Alexandre
 *
 */
public class Dijkstra {

	private static Noeud noeudDepart = null;
	private static Noeud noeudDestination = null;
	public static LinkedList<Troncon> chemin = new LinkedList<Troncon>();
	
	private static HashMap<Noeud, Double> graphePonderation = new HashMap<Noeud, Double>();
	private static HashMap<Noeud, Noeud> grapheVoisinPrecedent = new HashMap<Noeud, Noeud>();
	
	private static ArrayList<Noeud> noeudsVisites = new ArrayList<Noeud>();
	private static ArrayList<Noeud> noeudsNonVisites = new ArrayList<Noeud>();
	

	/**
	 * Méthode calculant le plus court chemin entre deux noeuds
	 * 
	 * @param noeudDepart
	 * @param noeudDestination
	 * @param noeuds : ensemble de tous les noeuds du plan
	 * @return la pondération du chemin total (soit le temps total pour ce
	 *         chemin)
	 */
	public static double calculerDijkstra(Noeud noeudDepart, Noeud noeudDestination, Set<Noeud> noeuds) {

		reinitialiserVariables();
		
		Dijkstra.noeudDepart = noeudDepart;
		Dijkstra.noeudDestination = noeudDestination;
		
		Iterator<Noeud> itNoeuds = noeuds.iterator();
		while (itNoeuds.hasNext()) {
			Noeud noeud = itNoeuds.next();
			graphePonderation.put(noeud, Double.MAX_VALUE);
			grapheVoisinPrecedent.put(noeud, null);
		}

		graphePonderation.put(Dijkstra.noeudDepart, 0.0);

		noeudsNonVisites.add(Dijkstra.noeudDepart);

		while (!noeudsNonVisites.isEmpty()) {
			Noeud noeudCourant = noeudDePlusPetitePonderation();
			noeudsNonVisites.remove(noeudCourant);
			noeudsVisites.add(noeudCourant);
			evaluerVoisins(noeudCourant);
		}

		Noeud noeudDebut = null;
		Noeud noeudFin = Dijkstra.noeudDestination;

		Dijkstra.chemin = new LinkedList<Troncon>();
		
		while (noeudDebut != Dijkstra.noeudDepart) {
			noeudDebut = grapheVoisinPrecedent.get(noeudFin);
			if (noeudDebut == null) {
				break;
			} else {
				Set<Troncon> troncons = noeudDebut.getTronconSortants();
				for (Iterator<Troncon> itTroncon = troncons.iterator(); itTroncon
						.hasNext();) {
					Troncon troncon = itTroncon.next();
					if (troncon.getNoeudFin() == noeudFin) {
						Dijkstra.chemin.addFirst(troncon);
						noeudFin = noeudDebut;
						break;
					}
				}
			}
		}
		return graphePonderation.get(Dijkstra.noeudDestination);
	}

	/**
	 * Méthode qui réinitialise les variables statiques de la classe Dijkstra
	 */
	private static void reinitialiserVariables() {
		graphePonderation = new HashMap<Noeud, Double>();
		grapheVoisinPrecedent = new HashMap<Noeud, Noeud>();
		noeudsVisites = new ArrayList<Noeud>();
		noeudsNonVisites = new ArrayList<Noeud>();
		
	}

	/**
	 * Méthode retournant le noeud de plus petite pondération contenu dans une
	 * liste passée en paramètre
	 * 
	 * @return Le noeud de plus petite pondération
	 */
	private static Noeud noeudDePlusPetitePonderation() {

		Noeud noeudPlusPetitePonderation = null;
		double plusPetitePonderation = Double.MAX_VALUE;

		Iterator<Noeud> it = noeudsNonVisites.iterator();
		while (it.hasNext()) {
			Noeud noeudTest = it.next();
			double ponderationTest = graphePonderation.get(noeudTest);
			if (ponderationTest < plusPetitePonderation) {
				plusPetitePonderation = ponderationTest;
				noeudPlusPetitePonderation = noeudTest;
			}
		}

		return noeudPlusPetitePonderation;
	}

	/**
	 * Méthode évaluant les voisins d'un noeud courant afin de modifier les
	 * pondérations des arcs du graphe. </br>
	 * 
	 * Si une pondération du graphe est modifiée, le noeud voisin du noeud
	 * courant est rajoutée à la liste des noeuds non visités.
	 * 
	 * @param noeudCourant
	 */
	private static void evaluerVoisins(Noeud noeudCourant) {
		Iterator<Troncon> itTroncon = noeudCourant.getTronconSortants()
				.iterator();
		while (itTroncon.hasNext()) {
			Troncon troncon = itTroncon.next();
			Noeud noeudDestination = troncon.getNoeudFin();
			if (!noeudsVisites.contains(noeudDestination)) {
				double ponderation = troncon.getTemps()
						+ graphePonderation.get(noeudCourant);
				if (ponderation < graphePonderation.get(noeudDestination)) {
					graphePonderation.put(noeudDestination, ponderation);
					grapheVoisinPrecedent.put(noeudDestination, noeudCourant);
					noeudsNonVisites.add(noeudDestination);
				}
			}
		}
	}
}
