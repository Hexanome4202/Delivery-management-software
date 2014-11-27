package modele;

import java.util.*;

/**
 * 
 */
public class Itineraire {

	/**
	 * @param liv1
	 * @param liv2
	 * @param troncons
	 */
	public Itineraire(Livraison liv1, Livraison liv2,
			ArrayList<Troncon> troncons) {
		this.depart = liv1;
		this.arrivee = liv2;
		this.tronconsItineraire = troncons;
	}

	/**
     * 
     */
	private Livraison depart;

	/**
     * 
     */
	private Livraison arrivee;

	/**
     * 
     */
	private List<Troncon> tronconsItineraire;

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
}
