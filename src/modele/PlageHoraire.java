package modele;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import errors.Codes;

/**
 * Classe correspondant à une plage horaire de livraison
 */
public class PlageHoraire {

	/**
	 * Heure de début de la <code>PlageHoraire</code>
	 */
	private Date heureDebut;

	/**
	 * Heure de fin de la <code>PlageHoraire</code>
	 */
	private Date heureFin;

	/**
	 * Les <code>DemandeDeLivraison</code> de la <code>PlageHoraire</code>
	 */
	private Set<DemandeDeLivraison> demandesLivraisonPlage;

	// ----- Constructeur(s)
	/**
	 * Constructeur de la classe <code>PlageHoraire</code>
	 * 
	 * @param heureDebut
	 * @param heureFin
	 * @throws ParseException 
	 */
	public PlageHoraire(String heureDebut, String heureFin) throws ParseException {
		this.heureDebut = new SimpleDateFormat("H:m:s", Locale.ENGLISH)
				.parse(heureDebut);
		this.heureFin = new SimpleDateFormat("H:m:s", Locale.ENGLISH)
				.parse(heureFin);

		this.demandesLivraisonPlage = new HashSet<DemandeDeLivraison>();
	}

	/**
	 * Constructeur de la classe <code>PlageHoraire</code>
	 * 
	 * @param heureDebut
	 * @param heureFin
	 * @throws ParseException 
	 */
	public PlageHoraire(String heureDebut, String heureFin,
			Set<DemandeDeLivraison> demandes) throws ParseException {
		this(heureDebut, heureFin);
		this.demandesLivraisonPlage = demandes;
	}

	// ----- Getter(s)
	/**
	 * Getter de l'attribut <code>heureDebut</code>
	 * @return l'heure de début de la <code>PlageHoraire</code>
	 */
	public Date getHeureDebut() {
		return this.heureDebut;
	}

	/**
	 * Getter de l'attribut <code>heureFin</code>
	 * 
	 * @return l'heure de fin de la <code>PlageHoraire</code>
	 */
	public Date getHeureFin() {
		return this.heureFin;
	}
	
	/**
	 * Getter de l'attribut <code>demandesLivraisonPlage</code>
	 * @return
	 */
	public Set<DemandeDeLivraison> getDemandeLivraison() {
		return demandesLivraisonPlage;
	}

	// ----- Setter(s)
	/**
	 * Setter(s) de l'attribut <code>demandeLivraisonPlage</codes>
	 * @param demandes
	 */
	public void setDemandesDeLivraison(Set<DemandeDeLivraison> demandes) {
		this.demandesLivraisonPlage = demandes;
	}
	
	// ----- Méthode(s)
	/**
	 * Méthode permettant de récupérer tous les noeuds de la <code>PlageHoraire</code>
	 * @return les noeuds correspondants aux demandes de livraisons de la plage
	 *         horaire
	 */
	public ArrayList<Noeud> getNoeuds() {
		ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
		DemandeDeLivraison demande = null;
		Iterator<DemandeDeLivraison> it = this.demandesLivraisonPlage
				.iterator();
		while (it.hasNext()) {
			demande = it.next();
			noeuds.add(demande.getNoeud());
		}
		return noeuds;
	}
	
	/**
	 * Méthode permettant de récupérer un <code>Noeud</code> en fonction de son id
	 * @param id
	 *            Id du noeud que l'on souhaite trouver.
	 * @return Le noeud ayant comme <code>id<code> égal à <code>id</code>,
	 *         null sinon
	 */
	public Noeud recupererNoeud(Integer id) {
		Noeud noeud = null;
		DemandeDeLivraison demande = null;
		Iterator<DemandeDeLivraison> it = this.demandesLivraisonPlage
				.iterator();
		while (it.hasNext()) {
			demande = it.next();
			if (demande.getNoeud() != null && demande.getNoeud().getId() == id) {
				noeud = demande.getNoeud();
				break;
			}
		}
		return noeud;
	}

	/**
	 * Cette classe est responsable pour charger les livraisons d'une plage
	 * horaire à partir d'un Element plageElement, aprés elle additionne la
	 * nouvelle demande de livraison à la liste demandesLivraisonPlage
	 * 
	 * @param plageElement
	 * @param planTournee
	 * @return
	 */
	public int construireLivraisonsAPartirDeDOMXML(Element plageElement,
			Plan planTournee) {
		// todo : gerer les erreurs de syntaxe dans le fichier XML !

		// creation des Demandes Livraison;
		String tag = "Livraison";
		NodeList liste = plageElement.getElementsByTagName(tag);
		demandesLivraisonPlage.clear();
		int code=Codes.PARSE_OK;
		for (int i = 0; i < liste.getLength(); i++) {
			try{
				Element livraisonElement = (Element) liste.item(i);
				Integer id = Integer.parseInt(livraisonElement.getAttribute("id"));
				Integer adresse = Integer.parseInt(livraisonElement
						.getAttribute("adresse"));
				Integer idClient = Integer.parseInt(livraisonElement
						.getAttribute("client"));
				Noeud noeud = planTournee.recupererNoeud(adresse);
	
				if (noeud != null) {
					DemandeDeLivraison nouvelleDemande = new DemandeDeLivraison(id,
							noeud, idClient, this);
					demandesLivraisonPlage.add(nouvelleDemande);
				} else {
					//demandesLivraisonPlage.clear();
					code= Codes.ERREUR_305;
				}
			} catch (NumberFormatException e) {
				code=Codes.ERREUR_308;
			}

			// ajout des elements crees dans la structure objet
		}

		return code;
	}	
}