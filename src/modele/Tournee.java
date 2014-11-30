package modele;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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

	/**
	 * <code>DemandeDeLivraison</code> correspondant à l'entrepot
	 */
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
	 * @param livraison
	 */
	public void supprimerLivraison(DemandeDeLivraison livraison) {
		// TODO implement here
	}

	/**
	 * TODO: Not even sure of what it is doing...
	 * @return la feuille de route editee
	 */
	public String editerFeuilleRoute() {
		String ret = "";
		Itineraire itineraire;
		List<Troncon> troncons;
		Noeud n = this.entrepot.getNoeud();
		
		for(int i = 0; i < this.itineraires.size(); ++i) {
			itineraire = this.itineraires.get(i);
			ret += i +  " : " + itineraire.getDepart() + " --> " + itineraire.getArrivee() + " ("+itineraire.getTemps()+"min)\n";
			troncons = itineraire.getTronconsItineraire();
			for(Troncon t : troncons) {
				ret += "\tDe" + n.getId() + " vers " + t.getNoeudFin().getId();
				n = t.getNoeudFin();
			}
		}
		
		return ret;
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
		for(Itineraire iti : this.itineraires) {
			// TODO: finish...
		}
	}

	/**
	 * @param noeudPrecedent
	 */
	public boolean effacerItineraire(Noeud noeudPrecedent) {
		//TODO : tester
		Iterator<Itineraire> it = this.itineraires.iterator();
		Itineraire itineraire;
		Itineraire avant = null;
		while(it.hasNext()) {
			itineraire = it.next();
			if(itineraire.getDepart().getNoeud().compareTo(noeudPrecedent) == 0) {
				if(avant != null) {
					avant.setArrivee(itineraire.getArrivee());
				}
				it.remove();
				return true;
			}
			avant = itineraire;
		}
		return false;
	}

	/**
	 * Méthode qui calculera les chemins les plus courts de l'entrepôt aux
	 * différentes livraisons en essayant de respecter les plages horaires
	 */
	public void calculerTournee() {

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
				DemandeDeLivraison demandeDepart = dicoIndexDemande.get(i);
				DemandeDeLivraison demandeArrivee = dicoIndexDemande.get(succ.get(i).get(j));
				Double coutDouble = Dijkstra.calculerDijkstra(demandeDepart.getNoeud(), demandeArrivee.getNoeud(), planTournee.getToutNoeuds());
				LinkedList<Troncon> chemin = Dijkstra.chemin;
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
			return Controleur.ERREUR_306;
		}
		Element adresseElement = (Element) liste.item(0);
		int idAdresseEntrepot = Integer.parseInt(adresseElement
				.getAttribute("adresse"));

		Noeud noeudEntrepot = recupererNoeud(idAdresseEntrepot);
		
		if(noeudEntrepot!=null){
			this.entrepot = new DemandeDeLivraison(noeudEntrepot);
		}else{
			return Controleur.ERREUR_306;
		}

		// creation des Plages;
		String tag = "Plage";
		liste = noeudDOMRacine.getElementsByTagName(tag);
		plagesHoraires.clear();
		for (int i = 0; i < liste.getLength(); i++) {
			Element plageElement = (Element) liste.item(i);
			PlageHoraire nouvellePlage;
			try {
				nouvellePlage = new PlageHoraire(
						plageElement.getAttribute("heureDebut"),
						plageElement.getAttribute("heureFin"));
				int code=nouvellePlage.construireLivraisonsAPartirDeDOMXML(plageElement, planTournee);
				if (code != Controleur.PARSE_OK) {
					System.out.println("error");
					return code;
				}
				// ajout des elements crees dans la structure objet
				plagesHoraires.add(nouvellePlage);
			} catch (ParseException e) {
				return Controleur.ERREUR_304;
			}
			
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
	 * @param plagesHoraires : liste <b>ordonnée</b> de plages horaires
	 */
	public void setPlagesHoraires(ArrayList<PlageHoraire> plagesHoraires){
		this.plagesHoraires = plagesHoraires;
	}
		
	/**
	 * @return la <code>DemandeDeLivraison</code> correspondant à l'entrepôt
	 */
	public DemandeDeLivraison getEntrepot() {
		return this.entrepot;
	}
	
	/**
	 * 
	 * @return une liste de <code>PlageHoraire</code>s appartenant à la <code>Tournee</code>
	 */
	public ArrayList<PlageHoraire> getPlagesHoraires() {
		return this.plagesHoraires;
	}
}
