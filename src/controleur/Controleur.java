package controleur;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import modele.DemandeDeLivraison;
import modele.Noeud;
import modele.Plan;
import modele.Tournee;

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

	/**
     * 
     */
	public Controleur() {
		tournee = new Tournee();
		vueTournee = new VueTournee(null);
		plan = new Plan();
		 this.fen = new Fenetre(this);
		 this.fen.setVisible(true);
	}

	/**
	 * @param client
	 * @param noeud
	 * @param precedent
	 */
    public void ajouterLivraison(int client, Noeud courant, Noeud precedent) {
    	this.tournee.ajouterLivraison(precedent, courant, client);
    }
    
    public void calculerTournee(){
		this.tournee.calculerTournee();
		fen.dessinerTournee();
    }


	/**
	 * @param livraison
	 */
	public void supprimerLivraison(DemandeDeLivraison livraison) {
		// TODO implement here
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
	 * Methode responsable pour le traitement du fichier xml 
	 * 
	 * @param xml le fichier xml que l'on veut traiter
	 * @param typeFichier peut être "horaires" ou "plan"
	 */
	public void gererFichier(File xml, String typeFichier) {
    	try {
            // creation d'un constructeur de documents a l'aide d'une fabrique
           DocumentBuilder constructeur = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
           // lecture du contenu d'un fichier XML avec DOM
           Document document = constructeur.parse(xml);
           Element racine = document.getDocumentElement();
           
           int resultatConstruction = 0;
           if (typeFichier.equals("horaires")) {
				if (racine.getNodeName().equals("JourneeType")) {
					resultatConstruction = construireLivraisonsAPartirDeDOMXML(racine);
					// TODO: display
					System.out.println("fini");
					fen.creerMenuHoraires();
					fen.afficherDemandesLivraisonsSurPlan();
					fen.activerCalculItineraire();
				}
				// todo : traiter les erreurs
			}else if(typeFichier.equals("plan")){
				if (racine.getNodeName().equals("Reseau")) {
					resultatConstruction = construirePlanAPartirDeDOMXML(racine);
					fen.afficherPlan();
					fen.activerChargementHoraires();
				}
			}
           
           afficherErreurs(resultatConstruction);
           
       } catch (ParserConfigurationException pce) {
           System.out.println("Erreur de configuration du parseur DOM");
           JOptionPane.showMessageDialog(null,"Erreur de configuration du parseur DOM!","Erreur",JOptionPane.ERROR_MESSAGE);
           System.out.println("lors de l'appel a fabrique.newDocumentBuilder();");
       } catch (SAXException se) {
           System.out.println("Erreur lors du parsing du document");
           System.out.println("lors de l'appel a construteur.parse(xml)");
           JOptionPane.showMessageDialog(null,"PB de parsing du document xml!","Erreur",JOptionPane.ERROR_MESSAGE);
       } catch (IOException ioe) {
           System.out.println("Erreur d'entree/sortie");
           JOptionPane.showMessageDialog(null,"Erreur d'entree/sortie!","Erreur",JOptionPane.ERROR_MESSAGE);
           System.out.println("lors de l'appel a construteur.parse(xml)");
       }
    }

	

	/**
	 * Methode responsable pour la construction des livraisons à partir d'un element xml
	 * 
	 * @param livraisonsElement le element xml
	 * @return
	 */
	public int construireLivraisonsAPartirDeDOMXML(Element livraisonsElement) {
		tournee.setPlanTournee(plan);
		int code=tournee.construireLivraisonsAPartirDeDOMXML(livraisonsElement);
		if (code != Codes.PARSE_OK) {
			return code;
		}
		// vueTournee.dessiner();
		return Codes.PARSE_OK;
	}

	/**
	 * Methode responsable pour la construction du plan à partir d'un element xml
	 * 
	 * @param planElement
	 * @return
	 */
	public int construirePlanAPartirDeDOMXML(Element planElement) {
		plan = new Plan();
		int code=plan.construirePlanAPartirDeDOMXML(planElement);
		if(code!=Codes.PARSE_OK){
			return code;
		}
		this.tournee.setPlanTournee(this.plan);
		this.tournee.getPlagesHoraires().clear();
		return Codes.PARSE_OK;
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

	public static void main(String[] args) {
		new Controleur();
	}
	
	/**
	 * Methode responsable pour l'affichage des differents erreurs que l'on peut avoir dans le parsing des fichiers xml
	 * @param code le code de l'execution du parsing
	 */
	private void afficherErreurs(int code) {
		switch (code) {
			case Codes.ERREUR_301:
				JOptionPane.showMessageDialog(null,"Noeud destination du tronçon n'existe pas ou vide!","Erreur 301",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_302:
				JOptionPane.showMessageDialog(null,"Probleme dans les specifications d'un tronçon (vitesse, longueur..)!","Erreur 302",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_303:
				JOptionPane.showMessageDialog(null,"Probleme dans les specifications d'un noeud!","Erreur 303",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_304:
				JOptionPane.showMessageDialog(null,"Probleme dans les specifications d'une plage horaire!","Erreur 304",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_305:
				JOptionPane.showMessageDialog(null,"Noued correspondant a l'adresse de livraison specifiée inexistant!","Erreur 305",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_306:
				JOptionPane.showMessageDialog(null,"Noued correspondant a l'entrepot n'existe pas!","Erreur 306",JOptionPane.ERROR_MESSAGE);
				break;
			default:
				break;
			}
	}
}