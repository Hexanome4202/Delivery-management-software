package modele;

/**
 * 
 */
public class Troncon {

    /**
     * 
     */
    public Troncon(double vitesse, double longueur, String nomRue, Noeud noeudFin) {
    	this.vitesse = vitesse;
    	this.longueur = longueur;
    	this.nomRue = nomRue;
    	this.fin = noeudFin;
    }
    
    /**
     * 
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

}