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

import controleur.Controleur;

/**
 * 
 */
public class PlageHoraire {

	/**
     * 
     */
	private Date heureDebut;

	/**
     * 
     */
	private Date heureFin;

	/**
     * 
     */
	private Set<DemandeDeLivraison> demandesLivraisonPlage;
	
	/**
	 * Constructeur de la classe <code>PlageHoraire</code>
	 * @param heureDebut
	 * @param heureFin
	 */
	public PlageHoraire(String heureDebut, String heureFin) {
		try {
			this.heureDebut = new SimpleDateFormat("H:m:s", Locale.ENGLISH).parse(heureDebut);
			this.heureFin = new SimpleDateFormat("H:m:s", Locale.ENGLISH).parse(heureFin);
		} catch (ParseException e) {
			// TODO: do something clever...
			this.heureDebut = new Date();
			this.heureFin = new Date();
		}
		
		this.demandesLivraisonPlage = new HashSet<DemandeDeLivraison>();
	}
	
	/**
	 * Constructeur de la classe <code>PlageHoraire</code>
	 * @param heureDebut
	 * @param heureFin
	 */
	public PlageHoraire(String heureDebut, String heureFin, Set<DemandeDeLivraison> demandes) {
		this(heureDebut, heureFin);
		this.demandesLivraisonPlage = demandes;
	}
	
	/**
	 * 
	 * @param id Id du noeud que l'on souhaite trouver.
	 * @return Le noeud ayant comme <code>id<code> égal à <code>id</code>, null sinon
	 */
	public Noeud recupererNoeud(Integer id){
		Noeud noeud = null;
		DemandeDeLivraison demande = null;
		Iterator<DemandeDeLivraison> it = this.demandesLivraisonPlage.iterator();
		while(it.hasNext()) {
			demande = it.next();
			if(demande.getNoeud() != null && demande.getNoeud().getId() == id) {//Il faut traiter le cas ou le plan n'est pas encore charge

				noeud = demande.getNoeud();
				break;
			}
		}
		return noeud;
	}

	/**
	 * Cette classe est responsable pour charger les livraisons d'une plage horaire à partir
	 * d'un Element plageElement, aprés elle additionne la nouvelle demande de livraison à la liste
	 * demandesLivraisonPlage
	 * @param plageElement
	 * @return
	 */
	public int construireLivraisonsAPartirDeDOMXML(Element plageElement) {
		// todo : gerer les erreurs de syntaxe dans le fichier XML !

		// creation des Demandes Livraison;
		String tag = "Livraison";
		NodeList liste = plageElement.getElementsByTagName(tag);
		demandesLivraisonPlage.clear();
		for (int i = 0; i < liste.getLength(); i++) {
			Element livraisonElement = (Element) liste.item(i);
			Integer id = Integer.parseInt(livraisonElement.getAttribute("id"));
			Integer adresse = Integer.parseInt(livraisonElement.getAttribute("adresse"));
			Integer idClient = Integer.parseInt(livraisonElement.getAttribute("client"));
			DemandeDeLivraison nouvelleDemande = new DemandeDeLivraison(id,recupererNoeud(adresse), idClient, this);
			
			// ajout des elements crees dans la structure objet
			demandesLivraisonPlage.add(nouvelleDemande);
		}

		return Controleur.PARSE_OK;
	}

	/**
	 * @return les noeuds correspondants aux demandes de livraisons de la plage horaire
	 */
	public ArrayList<Noeud> getNoeuds() {
		ArrayList<Noeud> noeuds = new ArrayList<Noeud>();
		DemandeDeLivraison demande = null;
		Iterator<DemandeDeLivraison> it = this.demandesLivraisonPlage.iterator();
		while(it.hasNext()) {
			demande = it.next();
			noeuds.add(demande.getNoeud());
		}
		return noeuds;
	}
	
	public Set<DemandeDeLivraison> getDemandeLivraison() {
		return demandesLivraisonPlage;
	}

}