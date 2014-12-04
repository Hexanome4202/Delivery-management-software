package modele;

/**
 * Classe représentant un tronçon d'une <code>Tournee</code>
 */
public class Troncon implements Comparable<Troncon> {
	/**
	 * La vitesse de déplacement sur le <code>Troncon</code> en m/s
	 */
	private double vitesse;

	/**
	 * La longueur du <code>Troncon</code> en mètres
	 */
	private double longueur;

	/**
	 * Le nom de la rue correspondant au <code>Troncon</code>
	 */
	private String nomRue;

	/**
	 * Le <code>Noeud</code> de fin du <code>Troncon</code>
	 */
	private Noeud fin;

	// ----- Constructeur(s)
	/**
	 * Constructeur de <code>Troncon</code>
	 * 
	 * @param vitesse
	 *            La vitesse de déplacement sur le tronçon.
	 * @param longueur
	 *            La longueur du tronçon.
	 * @param nomRue
	 *            Le nom de la rue correspondant au tronçon.
	 * @param noeudFin
	 *            Le noeud d'arrivée/de fin du tronçon.
	 */
	public Troncon(double vitesse, double longueur, String nomRue,
			Noeud noeudFin) {
		this.vitesse = vitesse;
		this.longueur = longueur;
		this.nomRue = nomRue;
		this.fin = noeudFin;
	}

	/**
	 * Constructeur de <code>Troncon</code>
	 * 
	 * @param vitesse
	 *            La vitesse de déplacement sur le tronçon.
	 * @param longueur
	 *            La longueur du tronçon.
	 * @param nomRue
	 *            Le nom de la rue correspondant au tronçon.
	 */
	public Troncon(double vitesse, double longueur, String nomRue) {
		this.vitesse = vitesse;
		this.longueur = longueur;
		this.nomRue = nomRue;
		this.fin = null;
	}

	// ----- Getter(s)
	/**
	 * Getter de l'attribut <code>fin</code>
	 * 
	 * @return le noeud de fin (d'arrivée) du tronçon
	 */
	public Noeud getNoeudFin() {
		return fin;
	}

	/**
	 * Calcule et retourne le temps estimé pour parcourir le tronçon en
	 * secondes.
	 * 
	 * @return le temps estimé de parcours.
	 */
	public double getTemps() {
		return longueur / vitesse;
	}

	/**
	 * Getter de l'attribut <code>nomRue</code>
	 * 
	 * @return Le nom de la rue correspondant au <code>Troncon</code>
	 */
	public String getNomRue() {
		return this.nomRue;
	}

	/**
	 * Getter de l'attribut <code>longueur</code>
	 * 
	 * @return La longueur du <code>Troncon</code>
	 */
	public Double getLongueur() {
		return this.longueur;
	}

	/**
	 * Getter de l'attribut <code>vitesse</code>
	 * 
	 * @return La vitesse maximale sur le <code>Troncon</code>
	 */
	public Double getVitesse() {
		return this.vitesse;
	}

	// ----- Setter(s)
	/**
	 * Setter de l'attribut <code>fin</code>
	 * 
	 * @param le
	 *            nouveau <code>Noeud</code> de fin du <code>Troncon</code>
	 */
	public void setNoeudFin(Noeud fin) {
		this.fin = fin;
	}

	@Override
	public int compareTo(Troncon troncon) {
		int result = 0;

		if (!(this.fin == troncon.fin && this.longueur == troncon.longueur
				&& this.nomRue == troncon.nomRue && this.vitesse == troncon.vitesse)) {
			result = this.fin.compareTo(troncon.fin);
		}

		return result;
	}

}