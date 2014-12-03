package commande;

/**
 * Interface d'une commande
 * @author asarazin1
 *
 */
public interface Commande {
	/**
	 * Méthode pour exécuter une commande
	 */
	public void executer();
	
	/**
	 * Méthode pour annuler l'exécution d'une commande
	 */
	public void annuler();
}
