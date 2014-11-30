package controleur;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import modele.DemandeDeLivraison;
import modele.Plan;
import modele.Tournee;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import vue.Fenetre;
import vue.VueTournee;

/**
 * 
 */
public class Controleur {
	
    static public int PARSE_ERROR = -1;
    static public int PARSE_OK = 1;
    
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
    	plan=new Plan();
    	//this.fen = new Fenetre(this);
    	//this.fen.setVisible(true);
    }

    /**
     * @param fichierPlan
     */
    public void chargerPlanZone(String fichierPlan) {
        // TODO implement here
    }

    /**
     * @param fichierLivraisons
     */
    public void chargerLivraisons(String fichierLivraisons) {
        //this.tournee.construireLivraisonsAPartirDeDOMXML(noeudDOMRacine);
    }

    /**
     * @param client 
     * @param noeud 
     * @param precedent
     */

    public void ajouterLivraison(int client, int noeud, DemandeDeLivraison precedent) {
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
    
    public void gererFichier(File xml, String typeFichier) {
    	try {
            // creation d'un constructeur de documents a l'aide d'une fabrique
           DocumentBuilder constructeur = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
           // lecture du contenu d'un fichier XML avec DOM
           Document document = constructeur.parse(xml);
           Element racine = document.getDocumentElement();
           if (typeFichier.equals("horaires")) {
				if (racine.getNodeName().equals("JourneeType")) {
					int resultatConstruction = construireLivraisonsAPartirDeDOMXML(racine);
					if (resultatConstruction != Controleur.PARSE_OK) {
						System.out.println("PB de lecture de fichier!");
						JOptionPane.showMessageDialog(null,"PB de lecture de fichier!","Erreur",JOptionPane.ERROR_MESSAGE);
					}
				}
				// todo : traiter les erreurs
			}else if(typeFichier.equals("plan")){
				if (racine.getNodeName().equals("Reseau")) {
					int resultatConstruction = construirePlanAPartirDeDOMXML(racine);
					if (resultatConstruction != Controleur.PARSE_OK) {
						JOptionPane.showMessageDialog(null,"PB de lecture de fichier!","Erreur",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
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
     * 
     * @param vueCadreDOMElement
     * @return
     */
	public int construireLivraisonsAPartirDeDOMXML(Element vueCadreDOMElement) {
		tournee.setPlanTournee(plan);
        if (tournee.construireLivraisonsAPartirDeDOMXML(vueCadreDOMElement) != Controleur.PARSE_OK) {
            return Controleur.PARSE_ERROR;
        }
        //vueTournee.dessiner();
	return Controleur.PARSE_OK;
    }
	
	/**
	 * 
	 * @param racineXML
	 * @return
	 */
	public int construirePlanAPartirDeDOMXML(Element racineXML) {
		plan=new Plan(racineXML);
        //vueTournee.dessiner();
		this.tournee.setPlanTournee(this.plan);
        return Controleur.PARSE_OK;
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
}