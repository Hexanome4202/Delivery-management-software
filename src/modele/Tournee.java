package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import controleur.Controleur;

/**
 * 
 */
public class Tournee {

	private Livraison entrepot;

	/**
	 * Constructeur vide de <code>Tournee</code>
	 */
	public Tournee() {

		this.plagesHoraires = new ArrayList<PlageHoraire>();
		this.itineraires = new ArrayList<Itineraire>();
		this.entrepot = null;
		this.planTournee = null;

	}

	/**
     * Liste ordonée des <code>PlageHoraire</code>
     */
	private ArrayList<PlageHoraire> plagesHoraires;

	/**
     * Liste ordonée des <code>Itineraire</code>s à prendre pour compléter la tournée
     */
	private List<Itineraire> itineraires;

	/**
     * Plan complet dans lequel sont les livraisons
     */
	private Plan planTournee;

	/**
     * 
     */
	public void creerFeuilleRoute() {
		// TODO implement here
	}

	/**
	 * @param livraison
	 */
	public void supprimerLivraison(Livraison livraison) {
		// TODO implement here
	}

	/**
	 * @return la feuille de route editee
	 */
	public String editerFeuilleRoute() {
		return "";
	}

	/**
	 * @param tourneeXML
	 *            TODO: Changer type
	 */
	public void chargerTourneeDOM(Document tourneeXML) {
		// TODO implement here
	}

	/**
	 * @param coordX
	 * @param coordY
	 */
	public Noeud recupererNoeud(int coordX, int coordY) {
		Set<Noeud> noeuds = this.planTournee.getToutNoeuds();
		Noeud n;
		Noeud result = null;
		Iterator<Noeud> it = noeuds.iterator();
		while(it.hasNext()) {
			n = it.next();
			if(n.getX() == coordX && n.getY() == coordY) {
				result = n;
				break;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param noeud
	 */
	public Noeud recupererNoeud(int idNoeud) {
		return this.planTournee.recupererNoeud(idNoeud);
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
	public boolean effacerItineraire(Noeud noeudPrecedent) {
		Iterator<Itineraire> it = this.itineraires.iterator();
		Itineraire itineraire;
		while(it.hasNext()) {
			itineraire = it.next();
			if(itineraire.getDepart().getDemandeDeLivraison().getNoeud().compareTo(noeudPrecedent) == 0) {
				it.remove();
				return true;
			}
		}
		return false;
	}

	/**
	 * Méthode permettant de tester la méthode privée
	 * <code>calculerDijkstra</code>
	 * 
	 * @param noeudDepart
	 * @param noeudDestination
	 * @param chemin
	 * @return
	 */
	// FIXME deprecated public method to test a private one ? Sooooo dirty...
	@Deprecated
	public double testCaculDijkstra(Noeud noeudDepart, Noeud noeudDestination,
			LinkedList<Troncon> chemin) {
		return calculerDijkstra(noeudDepart, noeudDestination, chemin);
	}

	/**
	 * Méthode calculant le plus court chemin entre deux noeuds
	 * 
	 * @param noeudDepart
	 * @param noeudDestination
	 * @param chemin
	 *            : la liste ordonnée de troncons à suivre représentant le plus
	 *            court chemin -> cette liste est remplie après l'appel de la
	 *            fonction
	 * @return la pondération du chemin total (soit le temps total pour ce
	 *         chemin)
	 */
	private double calculerDijkstra(Noeud noeudDepart, Noeud noeudDestination,
			LinkedList<Troncon> chemin) {

		HashMap<Noeud, Double> graphePonderation = new HashMap<Noeud, Double>();
		HashMap<Noeud, Noeud> grapheVoisinPrecedent = new HashMap<Noeud, Noeud>();

		Set<Noeud> noeuds = planTournee.getToutNoeuds();

		Iterator<Noeud> itNoeuds = noeuds.iterator();
		while (itNoeuds.hasNext()) {
			Noeud noeud = itNoeuds.next();
			graphePonderation.put(noeud, Double.MAX_VALUE);
			grapheVoisinPrecedent.put(noeud, null);
		}

		graphePonderation.put(noeudDepart, 0.0);

		ArrayList<Noeud> noeudsVisite = new ArrayList<Noeud>();
		ArrayList<Noeud> noeudsNonVisite = new ArrayList<Noeud>();
		noeudsNonVisite.add(noeudDepart);

		while (!noeudsNonVisite.isEmpty()) {
			Noeud noeudCourant = noeudDePlusPetitePonderation(
					graphePonderation, noeudsNonVisite);
			noeudsNonVisite.remove(noeudCourant);
			noeudsVisite.add(noeudCourant);
			evaluerVoisins(noeudCourant, graphePonderation,
					grapheVoisinPrecedent, noeudsNonVisite, noeudsVisite);
		}

		Noeud noeudDebut = null;
		Noeud noeudFin = noeudDestination;

		chemin.retainAll(null);

		while (noeudDebut != noeudDepart) {
			noeudDebut = grapheVoisinPrecedent.get(noeudFin);
			if (noeudDebut == null) {
				break;
			} else {
				Set<Troncon> troncons = noeudDebut.getTronconSortants();
				for (Iterator<Troncon> itTroncon = troncons.iterator(); itTroncon
						.hasNext();) {
					Troncon troncon = itTroncon.next();
					if (troncon.getNoeudFin() == noeudFin) {
						chemin.addFirst(troncon);
						noeudFin = noeudDebut;
						break;
					}
				}
			}
		}
		return graphePonderation.get(noeudDestination);
	}

	/**
	 * Méthode retournant le noeud de plus petite pondération contenu dans une
	 * liste passée en paramètre
	 * 
	 * @param graphe
	 *            : structure contenant chaque noeud et leur pondération
	 * @param noeuds
	 *            : la liste de noeuds concernée
	 * @return Le noeud de plus petite pondération
	 */
	private Noeud noeudDePlusPetitePonderation(HashMap<Noeud, Double> graphe,
			ArrayList<Noeud> noeuds) {

		Noeud noeudPlusPetitePonderation = null;
		double plusPetitePonderation = Double.MAX_VALUE;

		Iterator<Noeud> it = noeuds.iterator();
		while (it.hasNext()) {
			Noeud noeudTest = it.next();
			double ponderationTest = graphe.get(noeudTest);
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
	 * @param graphePonderation
	 *            : structure contenant chaque noeud ainsi que le temps mis pour
	 *            arriver à lui (sa pondération)
	 * @param grapheVoisinPrecedent
	 *            : structure contenant un noeud et, si il appartient au plus
	 *            court chemin, le noeud précédent
	 * @param noeudsNonVisites
	 * @param noeudsVisite
	 */
	private void evaluerVoisins(Noeud noeudCourant,
			HashMap<Noeud, Double> graphePonderation,
			HashMap<Noeud, Noeud> grapheVoisinPrecedent,
			ArrayList<Noeud> noeudsNonVisites, ArrayList<Noeud> noeudsVisite) {
		Iterator<Troncon> itTroncon = noeudCourant.getTronconSortants()
				.iterator();
		while (itTroncon.hasNext()) {
			Troncon troncon = itTroncon.next();
			Noeud noeudDestination = troncon.getNoeudFin();
			if (!noeudsVisite.contains(noeudDestination)) {
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

	/**
	 * Méthode qui calculera les chemins les plus courts de l'entrepôt aux
	 * différentes livraisons en essayant de respecter les plages horaires
	 */
	public void calculerTournee() {
		int[][] couts;
		ArrayList<ArrayList<Integer>> succ;
		int maxCoutArc;
		int minCoutArc;
		
		HashMap<Integer,Livraison> dicoIntNoeud = new HashMap<Integer,Livraison>();
		dicoIntNoeud.put(0, entrepot);
		for(int i=0; i<plagesHoraires.size(); i++){
			ArrayList<Integer> successeur = new ArrayList<Integer>();
			Set<DemandeDeLivraison> demandes = plagesHoraires.get(i).getDemandeLivraison();
			if(i==0){
				//succ.add(index, element);
			}
			else if(i == plagesHoraires.size()-1){
				
			}
			
		}
		
//		this.nbNoeuds = noeudsGraphe.size();
//		int maxCoutArcTemp = 0;
//		int minCoutArcTemp = Integer.MAX_VALUE;
//		
//		for(int i=0; i<noeudsGraphe.size(); i++){
//			ArrayList<int[]> succTemp = noeudsGraphe.get(i);
//			for(int j=0; j<succTemp.size(); j++){
//				int[] ponderation = succTemp.get(j);
//				if(ponderation[1] > maxCoutArcTemp){
//					maxCoutArcTemp = ponderation[1];
//				}
//				else if(ponderation[1] < minCoutArcTemp){
//					minCoutArcTemp = ponderation[1];
//				}
//				couts[i][ponderation[0]] = ponderation[1];
//				ArrayList<Integer> succTemp = new ArrayList
//			}
//		}
		
		// TODO implement here
	}

	public int construireLivraisonsAPartirDeDOMXML(Element noeudDOMRacine) {

		// TODO : gerer les erreurs de syntaxe dans le fichier XML
		// lecture des attributs

		NodeList liste = noeudDOMRacine.getElementsByTagName("Entrepot");
		if (liste.getLength() != 1) {
			return Controleur.PARSE_ERROR;
		}
		Element adresseElement = (Element) liste.item(0);
		int idAdresseEntrepot = Integer.parseInt(adresseElement
				.getAttribute("adresse"));

		Noeud noeudEntrepot = recupererNoeud(idAdresseEntrepot);

		this.entrepot = new Livraison(noeudEntrepot);

		// creation des Plages;
		String tag = "Plage";
		liste = noeudDOMRacine.getElementsByTagName(tag);
		plagesHoraires.clear();
		for (int i = 0; i < liste.getLength(); i++) {
			Element plageElement = (Element) liste.item(i);
			PlageHoraire nouvellePlage = new PlageHoraire(
					plageElement.getAttribute("heureDebut"),
					plageElement.getAttribute("heureFin"));
			if (nouvellePlage.construireLivraisonsAPartirDeDOMXML(plageElement) != Controleur.PARSE_OK) {
				System.out.println("error");
				return Controleur.PARSE_ERROR;
			}
			// ajout des elements crees dans la structure objet
			plagesHoraires.add(nouvellePlage);
		}
		return Controleur.PARSE_OK;
	}

	public void setPlanTournee(Plan planTournee) {
		this.planTournee = planTournee;
	}

	public List<Itineraire> getItineraires() {
		return this.itineraires;
	}
}