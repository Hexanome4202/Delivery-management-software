package modele;

import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import controleur.Controleur;

/**
 * 
 */
public class Plan {
	
	/**
     * 
     */
	private Set<Troncon> toutTroncons;

	/**
     * 
     */
	private Set<Noeud> toutNoeuds;
	
	/**
	 * Constructeur vide de <code>Plan</code>.
	 */
	public Plan() {
	}

	/**
	 * @param racineXML
	 */
	public Plan(Element racineXML) {
		// TODO implement here
	}

	/**
	 * Constructeur de plan à partir de sets de troncons et de noeuds
	 * 
	 * @param troncons
	 * @param noeuds
	 */
	public Plan(Set<Troncon> troncons, Set<Noeud> noeuds) {
		this.toutNoeuds = noeuds;
		this.toutTroncons = troncons;
	}

	/**
	 * @param idNoeud
	 * @return le noeud ayant comme id <code>idNoeud</code> s'il existe, null
	 *         sinon
	 */
	public Noeud recupererNoeud(int idNoeud) {
		Noeud noeud = null;
		Noeud noeudCourant = null;
		Iterator<Noeud> it = this.toutNoeuds.iterator();
		while (it.hasNext()) {
			noeudCourant = it.next();
			if (noeudCourant.getId() == idNoeud) {
				noeud = noeudCourant;
				break;
			}
		}
		return noeud;
	}

	/**
	 * 
	 * @return Les noeuds du plan
	 */
	public Set<Noeud> getToutNoeuds() {
		return this.toutNoeuds;
	}

	public int construireLivraisonsAPartirDeDOMXML(Element noeudDOMRacine) {
		// TODO : gerer les erreurs de syntaxe dans le fichier XML
		// lecture des attributs

		NodeList liste = noeudDOMRacine.getElementsByTagName("Reseau");
		if (liste.getLength() != 1) {
			return Controleur.PARSE_ERROR;
		}

		// creation des Noeuds;
		String tag = "Noeud";
		liste = noeudDOMRacine.getElementsByTagName(tag);
		toutNoeuds.clear();
		List<Element> listeElements = new ArrayList<Element>();
		for (int i = 0; i < liste.getLength(); i++) {
			Element planElement = (Element) liste.item(i);
			Noeud nouveauNoeud = new Noeud(Integer.parseInt(planElement
					.getAttribute("id")), Integer.parseInt(planElement
					.getAttribute("x")), Integer.parseInt(planElement
					.getAttribute("y")));
			// ajout des elements crees dans la structure objet
			toutNoeuds.add(nouveauNoeud);
			listeElements.add(planElement);
		}
		for (Noeud n : toutNoeuds) {
			n.getTronconSortants().clear();
			construireTronconAPartirDeDOMXML(liste);
		}

		return Controleur.PARSE_OK;
	}

	// private void actualiserTroncons(){
	//
	// for(Noeud n:toutNoeuds){
	// for(Troncon t : n.getTronconSortants()){
	// t.setNoeudFin(recupererNoeud(idNoeud));
	// }
	// }
	//
	// }

	public int construireTronconAPartirDeDOMXML(NodeList liste) {
		// todo : gerer les erreurs de syntaxe dans le fichier XML !

		for (int i = 0; i < liste.getLength(); i++) {
			Element noeudElement = (Element) liste.item(i);
			String tag = "Noeud";
			NodeList listeNoeud = noeudElement.getElementsByTagName(tag);
			Set<Troncon> setTroncons = new HashSet<Troncon>();
			Boolean bool = true;
			for (int j = 0; j < listeNoeud.getLength(); j++) {
				Element tronconElement = (Element) listeNoeud.item(j);
				String nomRue = tronconElement.getAttribute("nomRue");
				Double vitesse = Double.parseDouble(tronconElement
						.getAttribute("vitesse"));
				Double longueur = Double.parseDouble(tronconElement
						.getAttribute("longueur"));
				Integer idNoeudFin = Integer.parseInt(tronconElement
						.getAttribute("idNoeudDestination"));
				Troncon troncon = new Troncon(vitesse, longueur, nomRue,
						recupererNoeud(idNoeudFin));
				bool=setTroncons.add(troncon);
				//afficher erreur si bool false

			}
			Noeud noeud = recupererNoeud(Integer.parseInt(noeudElement
					.getAttribute("id")));
			noeud.setSortants(setTroncons);
		}

		return Controleur.PARSE_OK;
	}

}