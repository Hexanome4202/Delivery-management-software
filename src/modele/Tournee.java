package modele;

import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import controleur.Controleur;

/**
 * 
 */
public class Tournee {

	/**
     * 
     */
	public Tournee() {
	}

	/**
     * 
     */
	private List<PlageHoraire> plagesHoraires;

	/**
     * 
     */
	private Itineraire itineraires;

	/**
     * 
     */
	private Livraison[] entrepot;

	/**
     * 
     */
	private Plan[] planTournee;

	/**
     * 
     */
	public void creerFeuilleRoute() {
		// TODO implement here
	}

	/**
	 * @param client
	 * @param noeud
	 * @param precedent
	 */
	public void ajouterLivraison(int client, int noeud, Livraison precedent) {
		// TODO implement here
	}

	/**
	 * @param livraison
	 */
	public void supprimerLivraison(Livraison livraison) {
		// TODO implement here
	}

	/**
	 * @param feuille
	 */
	public void editerFeuilleRoute(String feuille) {
		// TODO implement here
	}

	// /**
	// * @param tourneeXML
	// * TODO: Changer type
	// */
	// public void chargerTourneeDOM(DOMXML tourneeXML) {
	// // TODO implement here
	// }

	/**
	 * @param coordX
	 * @param coordY
	 */
	public void recupererNoeud(int coordX, int coordY) {
		// TODO implement here
	}

	/**
	 * @param noeudPrecedent
	 * @param noeudCourant
	 * @param client
	 */
	public void ajouterLivraison(Noeud noeudPrecedent, Noeud noeudCourant,
			int client) {
		// TODO implement here
	}

	/**
	 * @param noeudPrecedent
	 */
	public void effacerItineraire(Noeud noeudPrecedent) {
		// TODO implement here
	}

	/**
	 * @param noeudPrecedent
	 * @param noeudCourant
	 * @param troncons
	 */
	private void calculerDijkstra(Noeud noeudPrecedent, Noeud noeudCourant,
			Set<Troncon> troncons) {
		// TODO implement here
	}

	/**
     * 
     */
	public void calculerTournee() {
		// TODO implement here
	}

	public int construireAPartirDeDOMXML(Element noeudDOMRacine) {

		// todo : gerer les erreurs de syntaxe dans le fichier XML
		// lecture des attributs
		// hauteur = noeudDOMRacine.getAttribute("");
		// largeur = noeudDOMRacine.getAttribute("");

		NodeList liste = noeudDOMRacine.getElementsByTagName("Entrepot");
		if (liste.getLength() != 1) {
			return Controleur.PARSE_ERROR;
		}
		Element adresseElement = (Element) liste.item(0);
		int idAdresse = Integer
				.parseInt(adresseElement.getAttribute("adresse"));

		// creation des Boules;
		String tag = "Plage";
		liste = noeudDOMRacine.getElementsByTagName(tag);
		plagesHoraires.clear();
		for (int i = 0; i < liste.getLength(); i++) {
			Element plageElement = (Element) liste.item(i);
			PlageHoraire nouvellePlage = new PlageHoraire(plageElement.getAttribute("heureDebut"),plageElement.getAttribute("heureFin"));
			if (nouvellePlage.construireAPartirDeDOMXML(plageElement) != Controleur.PARSE_OK) {
				return Controleur.PARSE_ERROR;
			}
			// ajout des elements crees dans la structure objet
			plagesHoraires.add(nouvellePlage);
		}
		return Controleur.PARSE_OK;
	}

}