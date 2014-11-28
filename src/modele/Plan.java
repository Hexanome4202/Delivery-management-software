package modele;

import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import controleur.Controleur;


/**
 * 
 */
public class Plan {
	/**
	 * Constructeur vide de <code>Plan</code>.
	 */
    public Plan() {
    }
    
    /**
     * @param racineXML
     */
    public Plan(Element racineXML) {

        // TODO implement here
    }
    
    /**
     * Constructeur de plan à partir de sets de troncons et de noeuds
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
    	Iterator<Noeud> it = this.toutNoeuds.iterator();
        while(it.hasNext()) {
        	noeudCourant = it.next();
        	if(noeudCourant.getId() == idNoeud) {
        		noeud = noeudCourant;
        		break;
        	}
        }
        return noeud;
    }
    
    /**
     * 
     * @return Les noeuds du plan
     */
    public Set<Noeud> getToutNoeuds(){
    	return this.toutNoeuds;
    }

	public int construireLivraisonsAPartirDeDOMXML(Element noeudDOMRacine) {
		// TODO : gerer les erreurs de syntaxe dans le fichier XML
				// lecture des attributs

				NodeList liste = noeudDOMRacine.getElementsByTagName("Reseau");
				if (liste.getLength() != 1) {
					return Controleur.PARSE_ERROR;
				}

				// creation des Noeuds;
				String tag = "Noeud";
				liste = noeudDOMRacine.getElementsByTagName(tag);
				toutNoeuds.clear();
				for (int i = 0; i < liste.getLength(); i++) {
					Element planElement = (Element) liste.item(i);
					Noeud nouveauNoeud = new Noeud(Integer.parseInt(planElement.getAttribute("id"))
							, Integer.parseInt(planElement.getAttribute("x")), 
							Integer.parseInt(planElement.getAttribute("y")));
					if (nouveauNoeud.construireLivraisonsAPartirDeDOMXML(planElement) != Controleur.PARSE_OK) {
						System.out.println("error");
						return Controleur.PARSE_ERROR;
					}
					// ajout des elements crees dans la structure objet
					toutNoeuds.add(nouveauNoeud);
				}
				return Controleur.PARSE_OK;
	}

}