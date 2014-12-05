package modele;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import erreurs.Codes;

/**
 * @author: hexanome 4202
 */
public class Plan {

	/**
	 * L'ensemble des <code>Troncon</code> du plan
	 */
	private Set<Troncon> toutTroncons;

	/**
	 * L'ensemble des <code>Noeud</code> du plan
	 */
	private Set<Noeud> toutNoeuds;

	/**
	 * L'abscisse maximale atteinte par un point du plan chargé
	 */
	private int maxX;

	/**
	 * L'ordonnée maximale atteinte par un point du plan chargé
	 */
	private int maxY;

	// ----- Constructeur(s)
	/**
	 * Constructeur vide de <code>Plan</code>.
	 */
	public Plan() {
		toutTroncons = new HashSet<Troncon>();
		toutNoeuds = new HashSet<Noeud>();
	}

	/**
	 * Constructeur de copie de Plan
	 * 
	 * @param p
	 */
	public Plan(Plan p) {
		toutTroncons = p.getToutTroncons();
		toutNoeuds = p.getToutNoeuds();
	}

	/**
	 * @param racineXML
	 */
	public Plan(Element racineXML) {
		toutTroncons = new HashSet<Troncon>();
		toutNoeuds = new HashSet<Noeud>();
		construirePlanAPartirDeDOMXML(racineXML);

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

	// ----- Getter(s)
	/**
	 * Getter de l'attribut <code>toutNoeuds</code>
	 * 
	 * @return Les noeuds du <code>Plan</code>
	 */
	public Set<Noeud> getToutNoeuds() {
		return this.toutNoeuds;
	}

	/**
	 * Getter de l'attribut <code>toutTroncons</code>
	 * 
	 * @return Les troncons du <code>Plan</code>
	 */
	public Set<Troncon> getToutTroncons() {
		return this.toutTroncons;
	}

	/**
	 * Getter de l'attribut <code>maxX</code>
	 * 
	 * @return la valeur maximale de <code>x</code>
	 */
	public int getMaxX() {
		return maxX;
	}

	/**
	 * Getter de l'attribut <code>maxY</code>
	 * 
	 * @return la valeur maximale de <code>y</code>
	 */
	public int getMaxY() {
		return maxY;
	}

	// ----- Méthode(s)
	/**
	 * Méthode permettant de récupérer un <code>Noeud</code> par rapport à son
	 * id
	 * 
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
	 * Méthode qui construit un <code>Plan</code> à partir d'un élément XML
	 * 
	 * @param noeudDOMRacine
	 *            element xml
	 * @return un code d'erreur spécifique ou PARSE_OK si aucune erreur n'est
	 *         détectée
	 */
	public int construirePlanAPartirDeDOMXML(Element noeudDOMRacine) {
		// creation des Noeuds;
		String tag = "Noeud";
		NodeList liste = noeudDOMRacine.getElementsByTagName(tag);
		toutNoeuds.clear();
		List<Element> listeElements = new ArrayList<Element>();
		for (int i = 0; i < liste.getLength(); i++) {
			Element planElement = (Element) liste.item(i);

			try {
				int x = Integer.parseInt(planElement.getAttribute("x"));
				int y = Integer.parseInt(planElement.getAttribute("y"));
				Noeud nouveauNoeud = new Noeud(Integer.parseInt(planElement
						.getAttribute("id")), x, y);

				majCoordonneesMax(x, y);

				if (nouveauNoeud.getId() >= 0) {
					toutNoeuds.add(nouveauNoeud);
					listeElements.add(planElement);
				} else {
					return Codes.ERREUR_303;
				}
			} catch (NumberFormatException e) {
				toutNoeuds.clear();
				toutTroncons.clear();
				return Codes.ERREUR_303;
			}
		}
		for (Noeud n : toutNoeuds) {
			n.getTronconSortants().clear();
		}

		int code = construireTronconAPartirDeDOMXML(liste);
		if (code != Codes.PARSE_OK) {
			toutNoeuds.clear();
			toutTroncons.clear();
			return code;
		}
		remplirTousTroncons();// Je sais pas si ça sert a grand chose

		return Codes.PARSE_OK;
	}

	/**
	 * Méthode mettant à jour les coordonnées maximales si celles passées en
	 * paramètres sont plus grandes que les actuelles.
	 * 
	 * @param x
	 *            abcisse du noeud candidat
	 * @param y
	 *            ordonnée du noeud candidat
	 */
	private void majCoordonneesMax(int x, int y) {
		if (x > maxX) {
			maxX = x;
		}
		if (y > maxY) {
			maxY = y;
		}
	}

	/**
	 * Méthode permettant de remplir l'attribut <code>toutTroncons</code>
	 */
	private void remplirTousTroncons() {

		for (Noeud t : toutNoeuds) {
			toutTroncons.addAll(t.getTronconSortants());
		}

	}

	/**
	 * Méthode permettant de construire les <code>Troncon</code> grâce à un
	 * fichier XML
	 * 
	 * @param liste
	 *            la liste des troncons à créer
	 * @return le code d'erreur rencontré, s'il y en a un
	 */
	public int construireTronconAPartirDeDOMXML(NodeList liste) {

		for (int i = 0; i < liste.getLength(); i++) {
			Element noeudElement = (Element) liste.item(i);
			String tag = "LeTronconSortant";
			NodeList listeNoeud = noeudElement.getElementsByTagName(tag);
			Set<Troncon> setTroncons = new HashSet<Troncon>();
			for (int j = 0; j < listeNoeud.getLength(); j++) {
				Element tronconElement = (Element) listeNoeud.item(j);
				String nomRue = tronconElement.getAttribute("nomRue");
				try {
					Double vitesse = Double.parseDouble(tronconElement
							.getAttribute("vitesse").replace(",", "."));
					Double longueur = Double.parseDouble(tronconElement
							.getAttribute("longueur").replace(",", "."));
					Integer idNoeudFin = Integer.parseInt(tronconElement
							.getAttribute("idNoeudDestination"));

					Noeud noeudFin = null;
					if (idNoeudFin != null) {
						noeudFin = recupererNoeud(idNoeudFin);
					}
					if (noeudFin == null) {
						return Codes.ERREUR_301;
					}
					if (vitesse > 0 && longueur > 0 && !nomRue.equals("")
							&& nomRue != null && noeudFin != null) {

						Troncon troncon = new Troncon(vitesse, longueur,
								nomRue, noeudFin);
						setTroncons.add(troncon);
					} else {
						return Codes.ERREUR_302;
					}
				} catch (NumberFormatException e) {
					toutTroncons.clear();
					toutNoeuds.clear();
					return Codes.ERREUR_302;
				}

			}
			Noeud noeud = recupererNoeud(Integer.parseInt(noeudElement
					.getAttribute("id")));
			if (noeud != null) {
				noeud.setSortants(setTroncons);
			} else {

				toutTroncons.clear();
				toutNoeuds.clear();
				return Codes.ERREUR_303;
			}
		}

		return Codes.PARSE_OK;
	}

	/**
	 * Retourne le Noeud avec l'id passé en paramètre s'il existe
	 * 
	 * @param id
	 *            l'id du <code>Noeud</code> à récupérer
	 * @return noeud le <code>Noeud</code> correspondant à l'<code>id</code>
	 */
	public Noeud getNoeud(int id) {
		Iterator<Noeud> it = toutNoeuds.iterator();
		while (it.hasNext()) {
			Noeud n = it.next();
			if (n.getId() == id) {
				return n;
			}
		}
		return null;
	}

}
