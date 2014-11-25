package modele;

import java.util.*;

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
    public Plan(DOMXML racineXML) {
        // TODO implement here
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

}