package modele;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import errors.Codes;

/**
 * 
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
	
	/**
	 * Constructeur vide de <code>Plan</code>.
	 */
	public Plan() {
		toutTroncons=new HashSet<Troncon>();
		toutNoeuds= new HashSet<Noeud>();
	}
	
	/**
	 * Constructeur de copie de Plan
	 * @param p
	 */
	public Plan(Plan p) {
		toutTroncons=p.getToutTroncons();
		toutNoeuds= p.getToutNoeuds();
	}

	/**
	 * @param racineXML
	 */
	public Plan(Element racineXML) {
		toutTroncons=new HashSet<Troncon>();
		toutNoeuds= new HashSet<Noeud>();
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
	 * @return Les noeuds du <code>Plan</code>
	 */
	public Set<Noeud> getToutNoeuds() {
		return this.toutNoeuds;
	}
	
	/**
	 * 
	 * @return Les troncons du <code>Plan</code>
	 */
	public Set<Troncon> getToutTroncons() {
		return this.toutTroncons;
	}

	/**
	 * Methode responsable pour construire les noeuds et tron�ons du plan
	 * 
	 * @param noeudDOMRacine element xml
	 * @return
	 */
	public int construirePlanAPartirDeDOMXML(Element noeudDOMRacine) {
		// TODO : gerer les erreurs de syntaxe dans le fichier XML
		// lecture des attributs

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
				Noeud nouveauNoeud = new Noeud(
						Integer.parseInt(planElement.getAttribute("id")), 
						x, y);
				
				majCoordonneesMax(x, y);
				
				// ajout des elements crees dans la structure objet
				toutNoeuds.add(nouveauNoeud);
				listeElements.add(planElement);
			} catch (NumberFormatException e) {
				toutNoeuds.clear();
				return Codes.ERREUR_303;
			}
		}
		for (Noeud n : toutNoeuds) {
			n.getTronconSortants().clear();		
		}
		
		int code=construireTronconAPartirDeDOMXML(liste);
		if(code!=Codes.PARSE_OK){
			toutNoeuds.clear();
			return code;
		}
		remplirTousTroncons();//Je sais pas si �a sert a grand chose

		return Codes.PARSE_OK;
	}
	
	/**
	 * Méthode mettant à jour les coordonnées maximales si celles
	 *  passées en paramètres sont plus grandes que les actuelles.
	 * @param x abcisse du noeud candidat
	 * @param y ordonnée du noeud candidat
	 */
	private void majCoordonneesMax(int x, int y){
		if(x > maxX) {
			maxX = x;
		}
		if(y > maxY){
			maxY = y;
		}
	}


	private void remplirTousTroncons() {

		for (Noeud t : toutNoeuds) {
			toutTroncons.addAll(t.getTronconSortants());
		}
		
	}

	/**
	 * 
	 * @param liste
	 * @return
	 */
	public int construireTronconAPartirDeDOMXML(NodeList liste) {
		// todo : gerer les erreurs de syntaxe dans le fichier XML !

		for (int i = 0; i < liste.getLength(); i++) {
			Element noeudElement = (Element) liste.item(i);
			String tag = "LeTronconSortant";
			NodeList listeNoeud = noeudElement.getElementsByTagName(tag);
			Set<Troncon> setTroncons = new HashSet<Troncon>();
			Boolean bool = true; // je sais pas si on a besoin
			for (int j = 0; j < listeNoeud.getLength(); j++) {
				Element tronconElement = (Element) listeNoeud.item(j);
				String nomRue = tronconElement.getAttribute("nomRue");
				try {
					Double vitesse = Double.parseDouble(tronconElement
							.getAttribute("vitesse").replace(",","."));
					Double longueur = Double.parseDouble(tronconElement
							.getAttribute("longueur").replace(",","."));
					Integer idNoeudFin = Integer.parseInt(tronconElement
							.getAttribute("idNoeudDestination"));
					Noeud noeudFin=null;
					if(idNoeudFin!=null){
						noeudFin=recupererNoeud(idNoeudFin);
					}
					if (vitesse>0 && longueur>0 && !nomRue.equals("") && nomRue!=null && noeudFin!=null) {
						
						Troncon troncon = new Troncon(vitesse, longueur, nomRue,noeudFin);
						bool = setTroncons.add(troncon);
						//TODO: afficher erreur si bool false (je crois que c'est pas necessaire)
					}else {
						return Codes.ERREUR_302;
					}
				} catch (NumberFormatException e) {
					return Codes.ERREUR_302;
				}

			}
			Noeud noeud = recupererNoeud(Integer.parseInt(noeudElement
					.getAttribute("id")));
			if (noeud!=null) {
				noeud.setSortants(setTroncons);
			}else {
				return Codes.ERREUR_303;
			}
		}

		return Codes.PARSE_OK;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}
	
	/**
	 * Retourne le Noeud avec l'id passé en paramètre s'il existe
	 * @param id
	 * @return noeud correspondant
	 */
	public Noeud getNoeud(int id){
		Iterator<Noeud> it = toutNoeuds.iterator();
		while(it.hasNext()){
			Noeud n = it.next();
			if(n.getId() == id){
				return n;
			}
		}
		return null;
	}
	
	

}