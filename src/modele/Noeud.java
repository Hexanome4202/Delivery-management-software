package modele;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import controleur.Controleur;

/**
 * 
 */
public class Noeud implements Comparable<Noeud> {
    
    /**
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
     * 
     * @param id l'id du noeud
     * @param x la position géographique en x du noeud
     * @param y la position géographique en y du noeud
     * @param troncons les troncons sortants du noeud
     */
    public Noeud(int id, int x, int y, Set<Troncon> troncons) {
    	this(id, x, y);
    	this.sortants = troncons;
    }

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
    
    
    
    /**
     * Méthode pour ajouter un troncon sortant au noeud
     * @param tronconSortant
     */
    public void ajouterTronconSortant(Troncon tronconSortant){
    	sortants.add(tronconSortant);
    }
    
    /**
     * 
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
     * 
     * @return la coordonnée en x du noeud
     */
    public int getX() {
    	return this.x;
    }
    
    /**
     * 
     * @return la coordonnée en y du noeud
     */
    public int getY() {
    	return this.y;
    }
    
    
    /**
     * 
     * @param setTroncons
     */
	public void setSortants(Set<Troncon> setTroncons) {
		this.sortants = setTroncons;
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

	/**
	 * 
	 * @param planElement
	 * @return
	 */
	public int construireLivraisonsAPartirDeDOMXML(Element planElement) {
		// todo : gerer les erreurs de syntaxe dans le fichier XML !

				// creation des Tronçons;
				String tag = "Noeud";
				NodeList liste = planElement.getElementsByTagName(tag);
				sortants.clear();
				for (int i = 0; i < liste.getLength(); i++) {
					Element NoeudElement = (Element) liste.item(i);
					String nomRue =NoeudElement.getAttribute("nomRue");
					Double vitesse = Double.parseDouble(NoeudElement.getAttribute("vitesse"));
					Double longueur = Double.parseDouble(NoeudElement.getAttribute("longueur"));
					Troncon troncon=new Troncon(vitesse, longueur, nomRue);
					//Noeud fin = new Noeud();
					
					// ajout des elements crees dans la structure objet
					sortants.add(troncon);
				}

				return Controleur.PARSE_OK;
	}
}
