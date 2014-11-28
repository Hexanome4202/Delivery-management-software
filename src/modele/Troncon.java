package modele;

/**
 * 
 */
public class Troncon implements Comparable<Troncon>{

	/**
	 * 
	 * @param vitesse	La vitesse de déplacement sur le tronçon.
	 * @param longueur	La longueur du tronçon.
	 * @param nomRue	Le nom de la rue correspondant au tronçon.
	 * @param noeudFin	Le noeud d'arrivée/de fin du tronçon.
	 */
    public Troncon(double vitesse, double longueur, String nomRue, Noeud noeudFin) {
    	this.vitesse = vitesse;
    	this.longueur = longueur;
    	this.nomRue = nomRue;
    	this.fin = noeudFin;
    }
    
    /**
     * 
     * @param vitesse	La vitesse de déplacement sur le tronçon.
	 * @param longueur	La longueur du tronçon.
	 * @param nomRue	Le nom de la rue correspondant au tronçon.
     */
    public Troncon(double vitesse, double longueur, String nomRue) {
    	this.vitesse = vitesse;
    	this.longueur = longueur;
    	this.nomRue = nomRue;
    	this.fin = null;
    }

    /**
     * 
     */
    private double vitesse;

    /**
     * 
     */
    private double longueur;

    /**
     * 
     */
    private String nomRue;

    /**
     * 
     */
    private Noeud fin;
    
    /**
     * 
     * @return le noeud de fin (d'arrivée) du tronçon
     */
    public Noeud getNoeudFin(){
    	return fin;
    }
    
    
    /**
     * Modifie le noeud d'arrive d'un tronçon
     * @return 
     */
    public void setNoeudFin(Noeud fin) {
		this.fin = fin;
	}

	/**
     * Calcule et retourne le temps estimé pour parcourir le tronçon.
     * @return le temps estimé de parcours.
     */
    public double getTemps(){
    	return longueur/vitesse;
    }

	@Override
	public int compareTo(Troncon troncon) {
		int result = 0;
		
		if(! (this.fin == troncon.fin 
				&& this.longueur == troncon.longueur 
				&& this.nomRue == troncon.nomRue 
				&& this.vitesse == troncon.vitesse)){
			result = this.fin.compareTo(troncon.fin);
		}
		
		return result;
	}

}