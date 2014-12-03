package modele;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import tsp.SolutionState;
import tsp.TSP;
import errors.Codes;

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
	 * Liste ordonée des <code>Itineraire</code>s à prendre pour compléter la
	 * tournée
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
	 * Constructeur par copie de Tournee
	 * 
	 * @param t
	 */
	public Tournee(Tournee t) {
		this.plagesHoraires = new ArrayList<PlageHoraire>(t.getPlagesHoraires());
		this.itineraires = new ArrayList<Itineraire>(t.getItineraires());
		this.entrepot = new DemandeDeLivraison(t.getEntrepot().getNoeud());
		this.planTournee = new Plan(t.getPlanTournee());
	}

	/**
	 * Supprime une <code>DemandeDeLivraison</code>
	 * @param livraison
	 */
	public boolean supprimerLivraison(DemandeDeLivraison livraison) {
		// TODO : tester
		Iterator<Itineraire> it = this.itineraires.iterator();
		Itineraire itineraire;
		Itineraire avant = null;
		while (it.hasNext()) {
			itineraire = it.next();
			if (itineraire.getDepart().compareTo(livraison) == 0) {
				if (avant != null) {
					avant.setArrivee(itineraire.getArrivee());
					Dijkstra.calculerDijkstra(avant.getDepart().getNoeud(),
							avant.getArrivee().getNoeud(),
							this.planTournee.getToutNoeuds());
					avant.setTroncons(Dijkstra.chemin);
				}
				it.remove();
				livraison.getPlage().getDemandeLivraison().remove(livraison);
				return true;
			}
			avant = itineraire;
		}
		return false;
	}
	
	/**
	 * Supprimer une <code>DemandeDeLivraison</code> à partir d'un <code>Noeud n</code>
	 * @param n <code>Noeud</code> à supprimer
	 * @return vrai si la suppression a été faite, faux sinon
	 */
	public boolean supprimerLivraison(Noeud n) {
		DemandeDeLivraison demande;
		if((demande = getDemandeDeLivraison(n)) == null) {
			return false;
		}
		return supprimerLivraison(demande);
	}

	/**
	 * TODO: Not even sure of what it is doing...
	 * 
	 * @return la feuille de route editee
	 */
	public String editerFeuilleRoute() {
		String ret = "";
		for (Itineraire it : this.getItineraires()) {
			
			DemandeDeLivraison dDepart = it.getDepart();
			DemandeDeLivraison dArrive = it.getArrivee();
			Double tempsPourLivrer=0.0;
			Double tempsSortie=0.0;
			if (it.getDepart().getId()!=-1) {
				tempsSortie = (double) dDepart.getPlage()
						.getHeureDebut().getHours()*60;
			}
				tempsPourLivrer = it.getTemps()/60 + 15;
				int tempsArriveH = (int) ((tempsSortie + tempsPourLivrer)/60);
				int tempsArriveM = (int) ((tempsSortie + tempsPourLivrer)%60);
				NumberFormat formatter = new DecimalFormat("00");
			String descLivraison="Livraison: "+dArrive.getId();
			if(descLivraison.equals("Livraison: -1")){descLivraison="Retour Entrepot";}
			
			ret += descLivraison+"\n";
			ret += "\tCoordonées de l'adresse: (" + dArrive.getNoeud().getX()
					+ "," + dArrive.getNoeud().getY() + ")"+"\n";
			ret += "\tHeure d'arrivé prevue: " + formatter.format(tempsArriveH)+":"+formatter.format(tempsArriveM)+"\n";
			for(Troncon t : it.getTronconsItineraire()){
				ret += "\t\t" + t.getNomRue() + ": ("+t.getNoeudFin().getX()+","+t.getNoeudFin().getY()+")\n";
			}
			ret += "\tIdentifiant du client à contacter en cas de problème: " + dArrive.getIdClient();
			ret += "\n";
		}

		return ret;
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
		while (it.hasNext()) {
			n = it.next();
			if (n.getX() == coordX && n.getY() == coordY) {
				result = n;
				break;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param idNoeud
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
		int pos;
		DemandeDeLivraison precedent;
		if ((pos = effacerItineraire(noeudPrecedent)) == -1)
			return;
		if((precedent = getDemandeDeLivraison(noeudPrecedent)) == null) return;
		DemandeDeLivraison livraison = new DemandeDeLivraison(noeudCourant,
				client, precedent.getPlage());
		precedent.getPlage().getDemandeLivraison().add(livraison);
		Dijkstra.calculerDijkstra(noeudPrecedent, noeudCourant,
				this.planTournee.getToutNoeuds());
		List<Troncon> troncons = Dijkstra.chemin;
		Itineraire it1 = new Itineraire(this.itineraires.get(pos - 1)
				.getArrivee(), livraison, troncons);
		int entrepot = pos == this.itineraires.size() ? 0 : pos;
		Dijkstra.calculerDijkstra(noeudCourant, this.itineraires.get(entrepot)
				.getDepart().getNoeud(), this.planTournee.getToutNoeuds());
		troncons = Dijkstra.chemin;
		Itineraire it2 = new Itineraire(livraison, this.itineraires.get(entrepot)
				.getDepart(), troncons);
		this.itineraires.add(pos, it1);
		this.itineraires.add(pos + 1, it2);
	}

	/**
	 * @param noeudPrecedent
	 */
	public int effacerItineraire(Noeud noeudPrecedent) {
		// TODO: tester
		int cpt = 0;
		Iterator<Itineraire> it = this.itineraires.iterator();
		Itineraire itineraire;
		while (it.hasNext()) {
			itineraire = it.next();
			if (itineraire.getDepart().getNoeud().compareTo(noeudPrecedent) == 0) {
				it.remove();
				return cpt;
			}
			cpt++;
		}
		return -1;
	}

	/**
	 * Méthode qui calculera les chemins les plus courts de l'entrepôt aux
	 * différentes livraisons en essayant de respecter les plages horaires
	 */
	public void calculerTournee() {

		ArrayList<ArrayList<Integer>> succ = new ArrayList<ArrayList<Integer>>();
		int maxCoutArc = 0;
		int minCoutArc = Integer.MAX_VALUE;

		HashMap<Integer, DemandeDeLivraison> dicoIndexDemande = new HashMap<Integer, DemandeDeLivraison>();
		HashMap<DemandeDeLivraison, Integer> dicoDemandeIndex = new HashMap<DemandeDeLivraison, Integer>();
		int index = 0;

		dicoIndexDemande.put(index, entrepot);
		dicoDemandeIndex.put(entrepot, index);
		index++;

		ArrayList<DemandeDeLivraison> noeudsActuels = new ArrayList<DemandeDeLivraison>();
		noeudsActuels.add(entrepot);

		for (int i = 0; i < plagesHoraires.size() + 1; i++) {
			ArrayList<DemandeDeLivraison> noeudsSuivants = new ArrayList<DemandeDeLivraison>();
			if (i == plagesHoraires.size()) {
				noeudsSuivants.add(entrepot);
			} else {
				noeudsSuivants = new ArrayList<DemandeDeLivraison>(
						plagesHoraires.get(i).getDemandeLivraison());
			}

			Iterator<DemandeDeLivraison> iteratorNoeudSuivant = noeudsSuivants
					.iterator();
			while (iteratorNoeudSuivant.hasNext()) {
				DemandeDeLivraison demande = iteratorNoeudSuivant.next();
				if (demande != entrepot) {
					dicoIndexDemande.put(index, demande);
					dicoDemandeIndex.put(demande, index);
					index++;
				}
			}

			Iterator<DemandeDeLivraison> itNoeudActuel = noeudsActuels
					.iterator();
			while (itNoeudActuel.hasNext()) {
				DemandeDeLivraison noeudActuel = itNoeudActuel.next();
				ArrayList<Integer> indexNoeudsSuccesseur = new ArrayList<Integer>();
				for (int k = 0; k < noeudsActuels.size(); k++) {
					if (noeudsActuels.get(k) != noeudActuel) {
						indexNoeudsSuccesseur.add(dicoDemandeIndex
								.get(noeudsActuels.get(k)));
					}
				}

				for (int k = 0; k < noeudsSuivants.size(); k++) {
					DemandeDeLivraison demande = noeudsSuivants.get(k);
					indexNoeudsSuccesseur.add(dicoDemandeIndex.get(demande));
				}
				succ.add(dicoDemandeIndex.get(noeudActuel),
						indexNoeudsSuccesseur);
			}
			noeudsActuels = noeudsSuivants;
		}

		int[][] couts = new int[succ.size()][succ.size()];
		ArrayList<Itineraire> toutItineraires = new ArrayList<Itineraire>();
		for (int i = 0; i < succ.size(); i++) {
			for (int j = 0; j < succ.get(i).size(); j++) {
				DemandeDeLivraison demandeDepart = dicoIndexDemande.get(i);
				DemandeDeLivraison demandeArrivee = dicoIndexDemande.get(succ
						.get(i).get(j));
				Double coutDouble = Dijkstra.calculerDijkstra(
						demandeDepart.getNoeud(), demandeArrivee.getNoeud(),
						planTournee.getToutNoeuds());
				LinkedList<Troncon> chemin = Dijkstra.chemin;
				int cout = coutDouble.intValue();
				couts[i][succ.get(i).get(j)] = cout;

				if (cout > maxCoutArc) {
					maxCoutArc = cout;
				} else if (cout < minCoutArc) {
					minCoutArc = cout;
				}

				Itineraire itineraire = new Itineraire(demandeDepart,
						demandeArrivee, chemin);
				toutItineraires.add(itineraire);
			}
		}
		GraphTournee graphe = new GraphTournee(couts, succ, maxCoutArc,
				minCoutArc);

		TSP tsp = new TSP(graphe);
		// TODO : changer timeLimit -> peut changer après plusieurs tentatives
		tsp.solve(200000, graphe.getNbVertices() * graphe.getMaxArcCost() + 1);
		if (tsp.getSolutionState() == SolutionState.SOLUTION_FOUND
				|| tsp.getSolutionState() == SolutionState.OPTIMAL_SOLUTION_FOUND) {
			int[] solution = tsp.getNext();
			itineraires = new ArrayList<Itineraire>();
			int noeud = 0;
			for (int i = 0; i < solution.length; i++) {
				Iterator<Itineraire> itItineraire = toutItineraires.iterator();
				while (itItineraire.hasNext()) {
					Itineraire iti = itItineraire.next();
					if (iti.getDepart() == dicoIndexDemande.get(noeud)
							&& iti.getArrivee() == dicoIndexDemande
									.get(solution[noeud])) {
						itineraires.add(iti);
						noeud = solution[noeud];
						break;
					}
				}
			}
		} else {
			// TODO : rappeler avec plus de temps ? peut-être ?
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
			return Codes.ERREUR_306;
		}
		try{
		Element adresseElement = (Element) liste.item(0);
		int idAdresseEntrepot = Integer.parseInt(adresseElement
				.getAttribute("adresse"));

		Noeud noeudEntrepot = recupererNoeud(idAdresseEntrepot);

		if (noeudEntrepot != null) {
			this.entrepot = new DemandeDeLivraison(noeudEntrepot);
		} else {
			return Codes.ERREUR_306;
		}
		}catch(NumberFormatException e){
			return Codes.ERREUR_306;
		}

		// creation des Plages;
		String tag = "Plage";
		liste = noeudDOMRacine.getElementsByTagName(tag);
		plagesHoraires.clear();
		int code = Codes.PARSE_OK;
		for (int i = 0; i < liste.getLength(); i++) {
			Element plageElement = (Element) liste.item(i);
			PlageHoraire nouvellePlage = null;
			try {
				Date heureDebut = new SimpleDateFormat("H:m:s", Locale.ENGLISH)
						.parse(plageElement.getAttribute("heureDebut"));
				Date heureFin = new SimpleDateFormat("H:m:s", Locale.ENGLISH)
						.parse(plageElement.getAttribute("heureFin"));
				if (heureDebut.before(heureFin)) {
					nouvellePlage = new PlageHoraire(
							plageElement.getAttribute("heureDebut"),
							plageElement.getAttribute("heureFin"));
				} else {
					// erreur si heure fin < heure début
					code = Codes.ERREUR_307;
				}
				if (code == 1 && nouvellePlage != null) {
					code = nouvellePlage.construireLivraisonsAPartirDeDOMXML(
							plageElement, planTournee);
					plagesHoraires.add(nouvellePlage);
				} else if (nouvellePlage != null) {
					nouvellePlage.construireLivraisonsAPartirDeDOMXML(
							plageElement, planTournee);
					plagesHoraires.add(nouvellePlage);
				}
				// erreur si plages horaires se chevauchent

			} catch (ParseException e) {
				code = Codes.ERREUR_304;
			}

		}

		code = testerListePlagesHoraires();

		if (code != Codes.PARSE_OK) {
			// plagesHoraires.clear();
			// itineraires.clear();
			// this.entrepot = null;
			// this.planTournee = null;
		}

		return code;
	}

	public List<DemandeDeLivraison> getDemandesTempsDepasse() {
		ArrayList<DemandeDeLivraison> demandesDepassees = new ArrayList<DemandeDeLivraison>();

		GregorianCalendar dateLivreur = new GregorianCalendar();
		dateLivreur.setTime(itineraires.get(0).getArrivee().getPlage()
				.getHeureDebut());

		Iterator<Itineraire> it = itineraires.iterator();
		while (it.hasNext()) {
			Itineraire itineraire = it.next();

			DemandeDeLivraison demande = itineraire.getArrivee();
			if (demande.getId() != -1) {
				PlageHoraire plage = demande.getPlage();
				Date tempsFin = plage.getHeureFin();
				Date tempsDebut = plage.getHeureDebut();
				dateLivreur.add(Calendar.SECOND, (int) itineraire.getTemps());

				if (dateLivreur.getTime().compareTo(tempsFin) <= 0) {
					if (dateLivreur.getTime().compareTo(tempsDebut) < 0) {
						dateLivreur.setTime(tempsDebut);
					}
					dateLivreur.add(Calendar.MINUTE, 10);
				} else {

					dateLivreur.add(GregorianCalendar.SECOND,
							(int) itineraire.getTemps());

					if (dateLivreur.getTime().compareTo(tempsFin) <= 0) {
						if (dateLivreur.getTime().compareTo(tempsDebut) < 0) {
							dateLivreur.setTime(tempsDebut);
						}
						dateLivreur.add(GregorianCalendar.MINUTE, 10);
					} else {
						demandesDepassees.add(demande);
						dateLivreur.add(GregorianCalendar.MINUTE, 10);
					}
				}
			}
		}
		return demandesDepassees;
	}

	private int testerListePlagesHoraires() {

		int code = Codes.PARSE_OK;
		List<PlageHoraire> lp = new ArrayList<PlageHoraire>();

		for (PlageHoraire p1 : plagesHoraires) {
			for (PlageHoraire p2 : plagesHoraires) {
				if (!lp.contains(p1) && !lp.contains(p2)) {
					if ((p1.getHeureDebut().after(p2.getHeureDebut()) && p1
							.getHeureDebut().before(p2.getHeureFin()))
							|| (p1.getHeureFin().after(p2.getHeureDebut()) && p1
									.getHeureFin().before(p2.getHeureFin()))) {
						lp.add(p2);
						code = Codes.ERREUR_309;

					}
				}
			}
		}
		plagesHoraires.removeAll(lp);

		return code;
	}

	/**
	 * 
	 * @param planTournee
	 *            : la <code>Plan</code> de la tournée
	 */
	public void setPlanTournee(Plan planTournee) {
		this.planTournee = planTournee;
	}

	/**
	 * 
	 * @param entrepot
	 *            : la <code>DemandeDeLivraison</code> représentant l'entrepot
	 */
	public void setEntrepot(DemandeDeLivraison entrepot) {
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
	 * @param plagesHoraires
	 *            : liste <b>ordonnée</b> de plages horaires
	 */
	public void setPlagesHoraires(ArrayList<PlageHoraire> plagesHoraires) {
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
	 * @return une liste de <code>PlageHoraire</code>s appartenant à la
	 *         <code>Tournee</code>
	 */
	public ArrayList<PlageHoraire> getPlagesHoraires() {
		return this.plagesHoraires;
	}

	/**
	 * 
	 * @return le plan de la <code>Tournee</code>
	 */
	public Plan getPlanTournee() {
		return planTournee;
	}

	/**
	 * Cherche la demande de livraison associée au <code>Noeud</code>
	 * @param n Le <code>Noeud</code> dont on cherche la <code>DemandeDeLivraison</code>
	 * @return La <code>DemandeDeLivraison</code> associée au <code>Noeud n</code>
	 */
	public DemandeDeLivraison getDemandeDeLivraison(Noeud n) {
		DemandeDeLivraison demande = null;
		PlageHoraire plage;
		List<DemandeDeLivraison> demandes;
		for(int i = 0; i < this.plagesHoraires.size(); ++i) {
			plage = this.plagesHoraires.get(i);
			demandes = new ArrayList<DemandeDeLivraison>(plage.getDemandeLivraison());
			for(int j = 0; j < demandes.size(); ++j) {
				if(demandes.get(j).getNoeud().compareTo(n) == 0) return demandes.get(j);
			}
		}
		System.out.println("nope");
		return demande;
	}
}
