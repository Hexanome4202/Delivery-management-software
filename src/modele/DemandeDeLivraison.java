package modele;


/**
 * Classe représentant la demande de livraison d'un client
 */
public class DemandeDeLivraison implements Comparable<DemandeDeLivraison> {
    
	/**
     * Id de la demande de livraison
     */
    private int id;

    /**
     * Id du client ayant effectué la demande
     */
    private int idClient;

    /**
     * Le noeud de livraison correspondant à l'adresse de la livraison
     */
    private Noeud adresseLivraison;

    /**
     * La plage horaire de la livraison
     */
    private PlageHoraire plageHoraire;
    
    /**
     * @param noeud 
     * @param client
     */
    public DemandeDeLivraison(int id, Noeud noeud, int client, PlageHoraire plage) {
        this.adresseLivraison = noeud;
        this.idClient = client;
        this.id = id;
        this.plageHoraire = plage;
    }
    
    /**
     * Utilisé pour créer le noeud spécial correspondant à l'entrepot
     * @param entrepot
     */
    public DemandeDeLivraison(Noeud entrepot) {
        this.adresseLivraison = entrepot;
        this.id = -1;
    }

    /**
     * @return le noeud rattache a la demande de livraison
     */
    public Noeud getNoeud() {
        return this.adresseLivraison;
    }
    
    /**
     * 
     * @return l'id de la demande de livraison
     */
    public int getId() {
    	return this.id;
    }

	@Override
	public int compareTo(DemandeDeLivraison demande) {
		return (demande != null && this.id == demande.getId()) ? 
				0 : this.id - demande.getId();
	}

}