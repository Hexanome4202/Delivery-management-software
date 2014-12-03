package commande;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui gére la liste des commandes faites
 * @author asarazin1
 *
 */
public class GestionnaireDeCommandes {
	/**
	 * Liste des commandes faites par l'utilisateur
	 */
	private List<Commande> commandes = new ArrayList<Commande>();
	
	/**
	 * Index de la dernière commande exécutée dans la liste
	 */
	private int indexCommandeActuelle = 0;
	
	/**
	 * Méthode pour exécuter une nouvelle <code>Commande</code>
	 * @param commande : la nouvelle commande
	 */
	public void executerNouvelleCommande(Commande commande){
		commandes = commandes.subList(0, indexCommandeActuelle);
		indexCommandeActuelle++;
		commandes.add(commande);
		commande.executer();
	}
	
	/**
	 * Méthode pour annuler la dernière <code>Commande</code> exécutée
	 * @return true si l'annulation est réussie, false sinon
	 */
	public boolean annulerDerniereCommande(){
		if(indexCommandeActuelle > 0){
			commandes.get(indexCommandeActuelle-1).annuler();
			indexCommandeActuelle--;
			return true;
		}
		return false;
	}
	
	/**
	 * Méthode pour refaire la dernière <code>Commande</code> annulée
	 * @return true si l'action est réussie, false sinon
	 */
	public boolean refaireCommandeAnnulee(){
		if(indexCommandeActuelle < commandes.size()){
			indexCommandeActuelle++;
			commandes.get(indexCommandeActuelle-1).executer();
			return true;
		}
		return false;
	}
}
