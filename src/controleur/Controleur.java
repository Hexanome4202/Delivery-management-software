package controleur;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import modele.DemandeDeLivraison;
import modele.Itineraire;
import modele.Noeud;
import modele.Plan;
import modele.Tournee;
import modele.Troncon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import vue.Fenetre;
import vue.VueTournee;
import errors.Codes;

/**
 * 
 */
public class Controleur {

	private Tournee tournee;
	private VueTournee vueTournee;
	private Plan plan;
	private Fenetre fen;
	private ArrayList<Tournee> listeTournees;
	private Integer indexTourneeAct;
	private boolean modeTests;

	/**
	 * Constructeur par défaut de la classe <code>Controleur</code>
	 */
	public Controleur() {
		tournee = new Tournee();
		vueTournee = new VueTournee(null);
		plan = new Plan();
		listeTournees = new ArrayList<Tournee>();
		indexTourneeAct = -1;
		this.fen = new Fenetre(this);
		this.fen.setVisible(true);
		this.modeTests = false;
	}

	/**
	 * Ajouter une nouvelle livraison
	 * @param client l'id du client
	 * @param noeud le <code>Noeud</code> pour lequel on souhaite ajouter une <code>DemandeDeLivraison</code>
	 * @param precedent le <code>Noeud</code> après lequel on souhaite ajouter une <code>DemandeDeLivraison</code>
	 */
	public void ajouterLivraison(int client, Noeud courant, Noeud precedent) {
		this.tournee.ajouterLivraison(precedent, courant, client);
		ajouterAListeTournees(tournee);
		fen.afficherPlan();
		fen.afficherDemandesLivraisonsSurPlan();
		fen.dessinerTournee();
		fen.majMenuHoraire();
		fen.setBtnAnnulerEnabled(true);
		fen.setMessage("");
	}

	/**
	 * Calcule la tournée
	 */
	public void calculerTournee() {
		this.tournee.calculerTournee();
		ajouterAListeTournees(tournee);
		fen.dessinerTournee();
	}

	/**
	 * @param livraison
	 */
	public void supprimerLivraison(Noeud noeudASupprimer) {
		fen.setMessage("Suppression du point de livraison en cours...");
		this.tournee.supprimerLivraison(noeudASupprimer);
		fen.afficherPlan();
		fen.afficherDemandesLivraisonsSurPlan();
		fen.dessinerTournee();
		fen.majMenuHoraire();
		fen.setBtnAnnulerEnabled(true);
	}

	/**
	 * @return un object de type <code>String</code> contenant la feuille editee
	 */
	public String editerFeuilleRoute() {
		return this.tournee.editerFeuilleRoute();
	}

	/**
	 * @param x
	 * @param y
	 */
	public void planClique(int x, int y) {
		// TODO implement here
	}

	/**
	 * Methode responsable du traitement du fichier xml
	 * 
	 * @param xml
	 *            le fichier xml que l'on veut traiter
	 * @param typeFichier
	 *            peut être "horaires" ou "plan"
	 */
	public int gererFichier(File xml, String typeFichier) {
		try {
			// creation d'un constructeur de documents a l'aide d'une fabrique
			DocumentBuilder constructeur = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			// lecture du contenu d'un fichier XML avec DOM
			constructeur.setErrorHandler(null);
			Document document = constructeur.parse(xml);
			Element racine = document.getDocumentElement();

			int resultatConstruction = 0;
			if (typeFichier.equals("horaires")) {
				if (racine.getNodeName().equals("JourneeType")) {
					resultatConstruction = construireLivraisonsAPartirDeDOMXML(racine);
					// TODO: display
					System.out.println("fini");
					fen.majMenuHoraire();
					fen.afficherDemandesLivraisonsSurPlan();
					fen.activerCalculItineraire();
					fen.setMessage("");
				}
				// todo : traiter les erreurs
			} else if (typeFichier.equals("plan")) {
				if (racine.getNodeName().equals("Reseau")) {
					resultatConstruction = construirePlanAPartirDeDOMXML(racine);
					if (!this.modeTests) {
						fen.afficherPlan();
						fen.activerChargementHoraires();
						fen.setMessage("");
					}
				}
			}

			if (!this.modeTests)
				Codes.afficherErreurs(resultatConstruction);
			return resultatConstruction;

		} catch (ParserConfigurationException pce) {
			System.out.println("Erreur de configuration du parseur DOM");
			if (!this.modeTests)
				fen.afficherPopupErreur("Erreur de configuration du parseur DOM!", "Erreur");
			System.out.println("lors de l'appel a fabrique.newDocumentBuilder();");
		} catch (SAXException se) {
			System.out.println("Erreur lors du parsing du document");
			System.out.println("lors de l'appel a construteur.parse(xml)");
			if (!this.modeTests)
				fen.afficherPopupErreur("Problème de parsing du document xml!", "Erreur");
		} catch (IOException ioe) {
			System.out.println("Erreur d'entree/sortie");
			if (!this.modeTests)
				fen.afficherPopupErreur("Erreur d'entree/sortie!","Erreur");
			System.out.println("lors de l'appel a construteur.parse(xml)");
		}
		return Codes.PARSE_ERROR;
	}

	/**
	 * Methode responsable de la construction des livraisons à partir d'un
	 * element xml
	 * 
	 * @param livraisonsElement
	 *            le element xml
	 * @return
	 */
	public int construireLivraisonsAPartirDeDOMXML(Element livraisonsElement) {
		tournee.setPlanTournee(plan);
		int code = tournee
				.construireLivraisonsAPartirDeDOMXML(livraisonsElement);
		if (code != Codes.PARSE_OK) {
			return code;
		}
		// vueTournee.dessiner();
		return Codes.PARSE_OK;
	}

	/**
	 * Methode responsable de la construction du plan à partir d'un element
	 * xml
	 * 
	 * @param planElement
	 * @return
	 */
	public int construirePlanAPartirDeDOMXML(Element planElement) {
		plan = new Plan();
		int code = plan.construirePlanAPartirDeDOMXML(planElement);
		if (code != Codes.PARSE_OK) {
			return code;
		}
		this.tournee.setPlanTournee(this.plan);
		this.tournee.getPlagesHoraires().clear();
		return Codes.PARSE_OK;
	}

	public void undo() {

		int index = indexTourneeAct - 1;

		if (index >= 0) {
			this.tournee = listeTournees.get(index);
			indexTourneeAct = index;
			fen.afficherPlan();
			fen.afficherDemandesLivraisonsSurPlan();
			fen.dessinerTournee();
			fen.setBtnRetablirEnabled(true);
		} else {
			fen.afficherPopupErreur("Undo n'est pas possible!","Erreur 151");
		}
	}

	public void redo() {

		int index = indexTourneeAct + 1;

		if (index < listeTournees.size()) {
			this.tournee = listeTournees.get(index);
			indexTourneeAct = index;
			fen.afficherPlan();
			fen.afficherDemandesLivraisonsSurPlan();
			fen.dessinerTournee();
		} else {
			fen.afficherPopupErreur("Redo n'est pas possible!","Erreur 152");
		}
	}

	public void ajouterAListeTournees(Tournee tournee) {
		for (int i = indexTourneeAct + 1; i < listeTournees.size(); i++) {
			listeTournees.remove(i);
		}
		listeTournees.add(new Tournee(tournee));
		indexTourneeAct++;
	}

	/**
	 * 
	 * @return le <code>Plan</code>
	 */
	public Plan getPlan() {
		return this.plan;
	}

	/**
	 * 
	 * @return la <code>Tournee</code>
	 */
	public Tournee getTournee() {
		return this.tournee;
	}

	/**
	 * Permet de lancer les tests sans obtenir de pop-up qui doit être fermée à
	 * la main
	 * 
	 * @param val
	 */
	public void setModeTest(boolean val) {
		this.modeTests = val;
		if (val)
			this.fen.setVisible(false);
		else
			this.fen.setVisible(false);
	}

	public static void main(String[] args) {
		new Controleur();
	}
	

	/**
	 * 
	 * @param fichier dont on va realiser la sauvegarde de la tournee
	 */
	public void genererFichierImpression(File fichier) {
		try {

			FileOutputStream is = new FileOutputStream(fichier);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			Writer w = new BufferedWriter(osw);
			w.write(this.editerFeuilleRoute());
			w.close();
		} catch (IOException e) {
			System.err.println("Probleme de creation du fichier d'impression");
		}
	}

}