package modele;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe correspondant à un noeud géographique de l'application
 */
public class Noeud implements Comparable<Noeud> {
	/**
     * Coordonnée en abscisse du noeud
     */
    private int x;

    /**
     * Coordonnée en ordonnée du noeud
     */
    private int y;

    /**
     * Id du noeud
     */
    private int id;

    /**
     * Ensemble de <code>Troncon</code> sortants du noeud courrant
     */
    private Set<Troncon> sortants;
    
    // ----- Constructeur(s)
    /**
     * Constructeur de la classe <code>Noeud</code>
     * @param id l'id du noeud
     * @param x la position géographique en x du noeud
     * @param y la position géographique en y du noeud
     */
    public Noeud(int id, int x, int y) {
    	this.x = x;
    	this.y = y;
    	this.id = id;
    	this.sortants = new HashSet<Troncon>();
    }
    
    /**
     * Constructeur de la classe <code>Noeud</code>
     * @param id l'id du noeud
     * @param x la position géographique en x du noeud
     * @param y la position géographique en y du noeud
     * @param troncons les troncons sortants du noeud
     */
    public Noeud(int id, int x, int y, Set<Troncon> troncons) {
    	this(id, x, y);
    	this.sortants = troncons;
    }
    
    // ----- Getter(s)
    /**
     * Getter de l'attribut <code>sortants</code>
     * @return les troncons sortants (<code>Troncon</code>) du noeud
     */
    public Set<Troncon> getTronconSortants(){
    	return sortants;
    }

    /**
     * Getter de l'attribut <code>id</code>.
     * @return l'id du noeud courant.
     */
    public int getId() {
    	return this.id;
    }
    
    /**
     * Getter de l'attribut <code>x</code>
     * @return la coordonnée en x du noeud
     */
    public int getX() {
    	return this.x;
    }
    
    /**
     * Getter de l'attribut <code>y</code>
     * @return la coordonnée en y du noeud
     */
    public int getY() {
    	return this.y;
    }
    
    // ----- Setter(s)
    /**
     * Setter de l'attribut <code>setTroncons</code>
     * @param setTroncons le nouveau <code>Set</code>
     */
	public void setSortants(Set<Troncon> setTroncons) {
		this.sortants = setTroncons;
	}
    
	// ----- Méthode(s)
    /**
     * Méthode pour ajouter un troncon sortant au noeud
     * @param tronconSortant le nouveau <code>Troncon</code> à ajouter au <code>Set</code> de <code>Troncon</code>
     */
    public void ajouterTronconSortant(Troncon tronconSortant){
    	sortants.add(tronconSortant);
    }

	@Override
	public int compareTo(Noeud noeud) {
		int result = 0;
		
		if(noeud != null && this.id != noeud.id){
			result = this.id - noeud.id;
		}
		
		return result;
	}
	
	@Override
	public boolean equals(Object noeud) {
		return this.compareTo((Noeud)noeud) == 0;
	}

}
