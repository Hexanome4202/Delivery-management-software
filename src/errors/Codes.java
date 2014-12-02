package errors;

import javax.swing.JOptionPane;

public class Codes {
	/**
	 * 
	 */
	static public final int PARSE_ERROR = -1;
	/**
	 * 
	 */
	static public final int PARSE_OK = 1;
	/**
	 * Noeud destination du tronçon n’existe pas ou vide
	 */
	static public final int ERREUR_301 = 301;
	/**
	 * Probleme dans les specifications d’un tronçon (vitesse, longueur..)
	 */
	static public final int ERREUR_302 = 302;
	/**
	 * Probleme dans les specifications d’un noeud
	 */
	static public final int ERREUR_303 = 303;
	/**
	 * Probleme dans les specifications d’une plage horaire
	 */
	static public final int ERREUR_304 = 304;
	/**
	 * Noued correspondant a l'adresse de livraison specifié inexistant
	 */
	static public final int ERREUR_305 = 305;
	/**
	 * Noued correspondant a l'entrepot n'existe pas
	 */
	static public final int ERREUR_306 = 306;
	
	/**
	 * Methode responsable pour l'affichage des differents erreurs que l'on peut avoir dans le parsing des fichiers xml
	 * @param code le code de l'execution du parsing
	 */
	public static void afficherErreurs(int code) {
		switch (code) {
			case Codes.ERREUR_301:
				JOptionPane.showMessageDialog(null,"Noeud destination du tronÃ§on n'existe pas ou vide!","Erreur 301",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_302:
				JOptionPane.showMessageDialog(null,"Probleme dans les specifications d'un tronÃ§on (vitesse, longueur..)!","Erreur 302",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_303:
				JOptionPane.showMessageDialog(null,"Probleme dans les specifications d'un noeud!","Erreur 303",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_304:
				JOptionPane.showMessageDialog(null,"Probleme dans les specifications d'une plage horaire!","Erreur 304",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_305:
				JOptionPane.showMessageDialog(null,"Noued correspondant a l'adresse de livraison specifiÃ©e inexistant!","Erreur 305",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_306:
				JOptionPane.showMessageDialog(null,"Noued correspondant a l'entrepot n'existe pas!","Erreur 306",JOptionPane.ERROR_MESSAGE);
				break;
			default:
				break;
			}
	}

}
