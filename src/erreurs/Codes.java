package erreurs;

import javax.swing.JOptionPane;

/**
 * Classe contenant les codes d'erreur de l'application ainsi qu'une fonction gérant l'affichage des erreurs
 */
public class Codes {
	/**
	 * Erreur non specifié du parsage
	 */
	static public final int PARSE_ERROR = -1;
	/**
	 * Le parsage était bien passé
	 */
	static public final int PARSE_OK = 1;
	/**
	 * Noeud destination du tronçon n'existe pas ou vide
	 */
	static public final int ERREUR_301 = 301;
	/**
	 * Probleme dans les specifications d'un tronçon (vitesse, longueur..)
	 */
	static public final int ERREUR_302 = 302;
	/**
	 * Probleme dans les specifications d'un noeud
	 */
	static public final int ERREUR_303 = 303;
	/**
	 * Probleme dans les specifications d'une plage horaire
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
	 * L'heure de debut de la plage horaire est plus grande que la fin
	 */
	static public final int ERREUR_307 = 307;
	/**
	 * erreur dans la construction des livraisons
	 */
	static public final int ERREUR_308 = 308;
	/**
	 * Le fichier contient des plages horaires qui se chevauchent
	 */
	static public final int ERREUR_309 = 309;
	/**
	 * Methode responsable pour l'affichage des differents erreurs que l'on peut avoir dans le parsing des fichiers xml
	 * @param code le code de l'execution du parsing
	 */
	public static void afficherErreurs(int code) {
		switch (code) {
			case Codes.PARSE_ERROR:
				JOptionPane.showMessageDialog(null,"Le fichier n'est pas valide.","Erreur de parsage",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_301:
				JOptionPane.showMessageDialog(null,"Noeud destination du tronçon n'existe pas ou vide!","Erreur 301",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_302:
				JOptionPane.showMessageDialog(null,"Probleme dans les specifications d'un tronçon (vitesse, longueur..)!","Erreur 302",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_303:
				JOptionPane.showMessageDialog(null,"Probleme dans les specifications d'un noeud!","Erreur 303",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_304:
				JOptionPane.showMessageDialog(null,"Probleme dans les specifications d'une plage horaire!","Erreur 304",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_305:
				JOptionPane.showMessageDialog(null,"Noeud correspondant a l'adresse de livraison specifiée inexistant!","Erreur 305",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_306:
				JOptionPane.showMessageDialog(null,"Noued correspondant a l'entrepot n'existe pas!","Erreur 306",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_307:
				JOptionPane.showMessageDialog(null,"L'heure de debut de la plage horaire est plus grande que la fin!","Erreur 307",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_308:
				JOptionPane.showMessageDialog(null,"erreur dans la construction des livraisons!","Erreur 308",JOptionPane.ERROR_MESSAGE);
				break;
			case Codes.ERREUR_309:
				JOptionPane.showMessageDialog(null,"xml contient des plages horaires qui se chevauchent!","Erreur 309",JOptionPane.ERROR_MESSAGE);
				break;
			default:
				break;
			}
	}

}
