package modele;

import java.util.*;

/**
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
	
	/**
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
	 * 
	 * @return La demande de livraison du départ de l'itinéraire
	 */
	public DemandeDeLivraison getDepart() {
		return this.depart;
	}
	
	/**
	 * 
	 * @return La demande de livraison à l'arrivée de l'itinéraire
	 */
	public DemandeDeLivraison getArrivee() {
		return this.arrivee;
	}
	
	/**
	 * 
	 * @return Les <code>Troncon</code>s correspondant à l'<code>Itineraire</code>
	 */
	public List<Troncon> getTronconsItineraire() {
		return this.tronconsItineraire;
	}
	
	/**
	 * 
	 * @param arrivee la <code>DemandeDeLivraison</code> correspondant à la nouvelle arrivée de l'<code>Itineraire</code>
	 */
	public void setArrivee(DemandeDeLivraison arrivee) {
		this.arrivee = arrivee;
	}
	
	public void setTroncons(List<Troncon> troncons) {
		this.tronconsItineraire = troncons;
	}
}
