package modele;

import java.util.*;


import org.w3c.dom.Element;
import org.w3c.dom.Document;


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
        return null;
    }
    
    public Set<Noeud> getToutNoeuds(){
    	return toutNoeuds;
    }

}