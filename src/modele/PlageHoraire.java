package modele;

import java.util.Date;
import java.util.List;
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
	public PlageHoraire() {
	}

	/**
	 * @param heureDebut
	 * @param heureFin
	 */
	public PlageHoraire(String heureDebut, String heureFin) {
		// TODO implement here
	}

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
	private Set<DemandeDeLivraison> demandeLivraisonPlage;

	/**
	 * @param noeuds
	 */
	public void getNoeuds(List<Noeud> noeuds) {
		// TODO implement here
	}
	
	private Noeud recupererNoeud(Integer id){
		//TODO implement here
		return null;
	}

	public int construireAPartirDeDOMXML(Element plageElement) {
		// todo : gerer les erreurs de syntaxe dans le fichier XML !
		// lecture des attributs


		// creation des Boules;
		String tag = "Livraison";
		NodeList liste = plageElement.getElementsByTagName(tag);
		demandeLivraisonPlage.clear();
		for (int i = 0; i < liste.getLength(); i++) {
			Element livraisonElement = (Element) liste.item(i);
			Integer id = Integer.parseInt(livraisonElement.getAttribute("id"));
			Integer adresse = Integer.parseInt(livraisonElement.getAttribute("adresse"));
			Integer idClient = Integer.parseInt(livraisonElement.getAttribute("client"));
			DemandeDeLivraison nouvelleDemande = new DemandeDeLivraison(id,recupererNoeud(adresse), idClient);
			
			// ajout des elements crees dans la structure objet
			demandeLivraisonPlage.add(nouvelleDemande);
		}

		return Controleur.PARSE_OK;
	}

    /**
     * @return les noeuds correspondants aux demandes de livraisons de la plage horaire
     */
    public List<Noeud> getNoeuds() {
        return null;
    }

}