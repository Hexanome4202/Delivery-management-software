package modele;

import java.util.*;


import org.w3c.dom.Element;


/**
 * 
 */
public class Plan {

    /**
     * 
     */
    public Plan() {
    }
    
    /**
     * @param racineXML
     * TODO: Type pas bon
     */
    public Plan(Element racineXML) {

        // TODO implement here
    }
    
    /**
     * 
     * @param troncons
     * @param noeuds
     */
    public Plan(Set<Troncon> troncons, Set<Noeud> noeuds) {
    	this.toutNoeuds = noeuds;
    	this.toutTroncons = troncons;
    }

    /**
     * 
     */
    private Set<Troncon> toutTroncons;

    /**
     * 
     */
    private Set<Noeud> toutNoeuds;

    /**
     * @param idNoeud 
     * @return le noeud ayant comme id <code>idNoeud</code> s'il existe, null sinon
     */
    public Noeud recupererNoeud(int idNoeud) {
    	Noeud noeud = null;
    	Noeud noeudCourant = null;
        for(Iterator<Noeud> it = this.toutNoeuds.iterator(); it.hasNext(); noeudCourant = it.next()) {
        	if(noeudCourant.getId() == idNoeud) {
        		noeud = noeudCourant;
        		break;
        	}
        }
        return noeud;
    }
    
    public Set<Noeud> getToutNoeuds(){
    	return this.toutNoeuds;
    }

}