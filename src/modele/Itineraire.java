package modele;

import java.util.*;

/**
 * @author: hexanome 4202
 * Classe correspondant à un itinéraire d'une tournée
 */
public class Itineraire {

	/**
	 * Demande de livraison correspondant au départ de l'itinéraire
	 */
	private DemandeDeLivraison depart;

	/**
	 * Demande de livraison correspondant à l'arrivée de l'itinéraire
	 */
	private DemandeDeLivraison arrivee;

	/**
	 * Tronçons empruntés lors de l'itinéraire
	 */
	private List<Troncon> tronconsItineraire;

	// ----- Constructeur(s)
	/**
	 * Constructeur de la classe <code>Itineraire</code>
	 * 
	 * @param liv1
	 * @param liv2
	 * @param troncons
	 */
	public Itineraire(DemandeDeLivraison liv1, DemandeDeLivraison liv2,
			List<Troncon> troncons) {
		this.depart = liv1;
		this.arrivee = liv2;
		this.tronconsItineraire = troncons;
	}

	// ----- Getter(s)
	/**
	 * Permet de calculer et de retourner le temps mis pour parcourir un
	 * itinéraire.
	 * 
	 * @return le temps mis pour parcourir les troncons entre
	 *         <code>depart</code> et <code>arrivee</code>
	 */
	public double getTemps() {
		double temps = 0;
		Troncon t = null;
		Iterator<Troncon> it = this.tronconsItineraire.iterator();
		while (it.hasNext()) {
			t = it.next();
			temps += t.getTemps();
		}
		return temps;
	}

	/**
	 * Getter de l'attribut <code>depart</code>
	 * 
	 * @return La demande de livraison du départ de l'itinéraire
	 */
	public DemandeDeLivraison getDepart() {
		return this.depart;
	}

	/**
	 * Getter de l'attribut <code>arrivee</code>
	 * 
	 * @return La demande de livraison à l'arrivée de l'itinéraire
	 */
	public DemandeDeLivraison getArrivee() {
		return this.arrivee;
	}

	/**
	 * Getter de l'attribut <code>tronconsItineraire</code>
	 * 
	 * @return Les <code>Troncon</code>s correspondant à l'
	 *         <code>Itineraire</code>
	 */
	public List<Troncon> getTronconsItineraire() {
		return this.tronconsItineraire;
	}

	// ----- Setter(s)
	/**
	 * Setter de l'attribut <code>arrivee</code>
	 * 
	 * @param arrivee
	 *            la <code>DemandeDeLivraison</code> correspondant à la nouvelle
	 *            arrivée de l'<code>Itineraire</code>
	 */
	public void setArrivee(DemandeDeLivraison arrivee) {
		this.arrivee = arrivee;
	}

	/**
	 * Setter de l'attribut <code>troncons</code>
	 * 
	 * @param troncons
	 */
	public void setTroncons(List<Troncon> troncons) {
		this.tronconsItineraire = troncons;
	}
}
