package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import tsp.SolutionState;
import tsp.TSP;
import controleur.Controleur;

/**
 * 
 */
public class Tournee {

	private DemandeDeLivraison entrepot;

	/**
     * Liste ordonée des <code>PlageHoraire</code>
     */
	private ArrayList<PlageHoraire> plagesHoraires;

	/**
     * Liste ordonée des <code>Itineraire</code>s à prendre pour compléter la tournée
     */
	private ArrayList<Itineraire> itineraires;

	/**
     * Plan complet dans lequel sont les livraisons
     */
	private Plan planTournee;

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
     * 
     */
	public void creerFeuilleRoute() {
		// TODO implement here
	}

	/**
	 * @param livraison
	 */
	public void supprimerLivraison(DemandeDeLivraison livraison) {
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
			if(itineraire.getDepart().getNoeud().compareTo(noeudPrecedent) == 0) {
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
		//TODO : TEST !!!! and debug...
		//TODO : clean all this mess like making different methods or a Dijkstra class
		ArrayList<ArrayList<Integer>> succ = new ArrayList<ArrayList<Integer>>();
		int maxCoutArc = 0;
		int minCoutArc = Integer.MAX_VALUE;
		
		HashMap<Integer,DemandeDeLivraison> dicoIndexDemande = new HashMap<Integer, DemandeDeLivraison>();
		HashMap<DemandeDeLivraison, Integer> dicoDemandeIndex = new HashMap<DemandeDeLivraison, Integer>();
		int index = 0;
		
		dicoIndexDemande.put(index,entrepot);
		dicoDemandeIndex.put(entrepot,index);
		index++;
		
		ArrayList<DemandeDeLivraison> noeudsActuels = new ArrayList<DemandeDeLivraison>();
		noeudsActuels.add(entrepot);
		
		for(int i=0; i<plagesHoraires.size()+1; i++){
			ArrayList<DemandeDeLivraison> noeudsSuivants = new ArrayList<DemandeDeLivraison>();
			if(i == plagesHoraires.size()){
				noeudsSuivants.add(entrepot);
			}
			else{
				noeudsSuivants = new ArrayList<DemandeDeLivraison>(plagesHoraires.get(i).getDemandeLivraison());
			}
			
			Iterator<DemandeDeLivraison> iteratorNoeudSuivant = noeudsSuivants.iterator();
			while(iteratorNoeudSuivant.hasNext()){
				DemandeDeLivraison demande = iteratorNoeudSuivant.next();
				if(demande != entrepot){
					dicoIndexDemande.put(index,demande);
					dicoDemandeIndex.put(demande,index);
					index++;
				}
			}
			
			Iterator<DemandeDeLivraison> itNoeudActuel = noeudsActuels.iterator();
			while(itNoeudActuel.hasNext()){
				DemandeDeLivraison noeudActuel = itNoeudActuel.next();
				ArrayList<Integer> indexNoeudsSuccesseur = new ArrayList<Integer>();
				for(int k=0; k<noeudsActuels.size(); k++){
					if(noeudsActuels.get(k) != noeudActuel){
						indexNoeudsSuccesseur.add(dicoDemandeIndex.get(noeudsActuels.get(k)));
					}
				}

				for(int k=0; k<noeudsSuivants.size(); k++){
					DemandeDeLivraison demande = noeudsSuivants.get(k);
					indexNoeudsSuccesseur.add(dicoDemandeIndex.get(demande));
				}
				succ.add(dicoDemandeIndex.get(noeudActuel), indexNoeudsSuccesseur);
			}
			noeudsActuels = noeudsSuivants;
		}
		
		int[][] couts = new int[succ.size()][succ.size()];
		ArrayList<Itineraire> toutItineraires = new ArrayList<Itineraire>();
		for(int i=0; i<succ.size(); i++){
			for(int j=0; j<succ.get(i).size(); j++){
				LinkedList<Troncon> chemin = new LinkedList<Troncon>();
				DemandeDeLivraison demandeDepart = dicoIndexDemande.get(i);
				DemandeDeLivraison demandeArrivee = dicoIndexDemande.get(succ.get(i).get(j));
				Double coutDouble = calculerDijkstra(demandeDepart.getNoeud(), demandeArrivee.getNoeud(), chemin);
				int cout = coutDouble.intValue();
				couts[i][succ.get(i).get(j)] = cout;
				
				if(cout > maxCoutArc){
					maxCoutArc = cout;
				}
				else if(cout < minCoutArc){
					minCoutArc = cout;
				}
				
				Itineraire itineraire = new Itineraire(demandeDepart, demandeArrivee, chemin);
				toutItineraires.add(itineraire);
			}
		}
		GraphTournee graphe = new GraphTournee(couts, succ, maxCoutArc, minCoutArc);
		
		TSP tsp = new TSP(graphe);
		//TODO : changer timeLimit -> peut changer après plusieurs tentatives
		tsp.solve(200000,graphe.getNbVertices()*graphe.getMaxArcCost()+1);
		if(tsp.getSolutionState() == SolutionState.SOLUTION_FOUND || tsp.getSolutionState() == SolutionState.OPTIMAL_SOLUTION_FOUND){
			int[] solution = tsp.getNext();
			
			for(int i=0; i<solution.length; i++){
				Iterator<Itineraire> itItineraire = toutItineraires.iterator();
				while(itItineraire.hasNext()){
					Itineraire iti = itItineraire.next();
					int j = i-1;
					if(i == 0){
						 j = solution.length-1;
					}
					if(iti.getDepart() == dicoIndexDemande.get(solution[j]) 
							&& iti.getArrivee() == dicoIndexDemande.get(solution[i])){
						itineraires.add(iti);
						break;
					}
				}
			}
		}else{
			//TODO : rappeler avec plus de temps ? peut-être ?
		}
		
	}

	/**
	 * 
	 * @param noeudDOMRacine
	 * @return
	 */
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

		this.entrepot = new DemandeDeLivraison(noeudEntrepot);

		// creation des Plages;
		String tag = "Plage";
		liste = noeudDOMRacine.getElementsByTagName(tag);
		plagesHoraires.clear();
		for (int i = 0; i < liste.getLength(); i++) {
			Element plageElement = (Element) liste.item(i);
			PlageHoraire nouvellePlage = new PlageHoraire(
					plageElement.getAttribute("heureDebut"),
					plageElement.getAttribute("heureFin"));
			if (nouvellePlage.construireLivraisonsAPartirDeDOMXML(plageElement, planTournee) != Controleur.PARSE_OK) {
				System.out.println("error");
				return Controleur.PARSE_ERROR;
			}
			// ajout des elements crees dans la structure objet
			plagesHoraires.add(nouvellePlage);
		}
		return Controleur.PARSE_OK;
	}

	/**
	 * 
	 * @param planTournee : la <code>Plan</code> de la tournée
	 */
	public void setPlanTournee(Plan planTournee) {
		this.planTournee = planTournee;
	}
	
	/**
	 * 
	 * @param entrepot : la <code>DemandeDeLivraison</code> représentant l'entrepot
	 */
	public void setEntrepot(DemandeDeLivraison entrepot){
		this.entrepot = entrepot;
	}

	/**
	 * 
	 * @return la liste des <code>Itineraire</code> de la tournée
	 */
	public ArrayList<Itineraire> getItineraires() {
		return this.itineraires;
	}
	
	/**
	 * 
	 * @param plagesHoraires : liste <b>ordonnée</b> de plages horaires
	 */
	public void setPlagesHoraires(ArrayList<PlageHoraire> plagesHoraires){
		this.plagesHoraires = plagesHoraires;
	}
}
