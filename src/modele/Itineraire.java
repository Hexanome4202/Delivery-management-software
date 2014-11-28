package modele;

import java.util.*;

/**
 * 
 */
public class Itineraire {

	/**
     * 
     */
	private DemandeDeLivraison depart;

	/**
     * 
     */
	private DemandeDeLivraison arrivee;

	/**
     * 
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
	
	public DemandeDeLivraison getDepart() {
		return this.depart;
	}
	
	public DemandeDeLivraison getArrivee() {
		return this.arrivee;
	}
}
