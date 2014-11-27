package modele;


/**
 * 
 */
public class DemandeDeLivraison implements Comparable {
    
    /**
     * Constructeur permettant de simplifier les méthodes de tests.
     * @param noeud 
     * @param client
     * @deprecated Utilisé pour les tests, ne devrait pas être utilisé en production
     */
    public DemandeDeLivraison(Noeud noeud, int client) {
        this.adresseLivraison = noeud;
        this.idClient = client;
    }
    
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
    public DemandeDeLivraison(int id, Noeud entrepot) {
        this.adresseLivraison = entrepot;
        this.id = id;
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private int idClient;

    /**
     * 
     */
    private Noeud adresseLivraison;

    /**
     * 
     */
    private PlageHoraire plageHoraire;

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
	public int compareTo(Object o) {
		DemandeDeLivraison demande = (DemandeDeLivraison) o;
		return (o != null && this.id == ((DemandeDeLivraison) o).getId()) ? 
				0 : this.id - demande.getId();
	}

}