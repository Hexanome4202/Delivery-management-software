package controleur;

import modele.Noeud;
import modele.Tournee;

/**
 * Classe de la commande qui ajoute une livraison
 *
 */
public class CommandeAjouterLivraison implements Commande {

	Tournee tournee;
	int client;
	Noeud precedent;
	Noeud courant;

	/**
	 * Constructeur de la classe CommandeAjouterLivraison
	 *
	 */
	public CommandeAjouterLivraison(Tournee tournee, int client, Noeud courant,
			Noeud precedent) {
		this.tournee = tournee;
		this.client = client;
		this.precedent = precedent;
		this.courant = courant;
	}

	@Override
	public void executer() {
		tournee.ajouterLivraison(precedent, courant, client);
	}

	@Override
	public void annuler() {
		tournee.supprimerLivraison(courant);
	}

}
