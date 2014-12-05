package controleur;

import modele.DemandeDeLivraison;
import modele.Noeud;
import modele.Tournee;

/**
 * Classe de la commande qui supprime une livraison
 *
 */
public class CommandeSupprimerLivraison implements Commande {

	Tournee tournee;
	Noeud noeudASupprimer;
	Noeud noeudPrecedent;
	int client;

	/**
	 * Constructeur de la commande pour supprimer une livraison
	 * 
	 * @param tournee
	 * @param noeudASupprimer
	 */
	public CommandeSupprimerLivraison(Tournee tournee, Noeud noeudASupprimer) {
		this.tournee = tournee;
		this.noeudASupprimer = noeudASupprimer;
		DemandeDeLivraison demandeASupprimer = tournee
				.getDemandeDeLivraison(noeudASupprimer);
		this.client = demandeASupprimer.getIdClient();
		this.noeudPrecedent = tournee.getNoeudPrecedent(demandeASupprimer);
	}

	@Override
	public void executer() {
		tournee.supprimerLivraison(noeudASupprimer);
	}

	@Override
	public void annuler() {
		tournee.ajouterLivraison(noeudPrecedent, noeudASupprimer, client);
	}

}
