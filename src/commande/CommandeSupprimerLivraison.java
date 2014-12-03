package commande;

import modele.DemandeDeLivraison;
import modele.Noeud;
import modele.Tournee;

/**
 * Classe de la commande qui supprime une livraison
 * @author asarazin1
 *
 */
public class CommandeSupprimerLivraison implements Commande {

	Tournee tournee;
	Noeud noeudASupprimer;
	Noeud noeudPrecedent;
	int client;
	
	public CommandeSupprimerLivraison(Tournee tournee, Noeud noeudASupprimer) {
		this.tournee = tournee;
		this.noeudASupprimer = noeudASupprimer;
		DemandeDeLivraison demandeASupprimer = tournee.getDemandeDeLivraison(noeudASupprimer);
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
