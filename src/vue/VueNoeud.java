package vue;

import java.util.*;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import modele.Noeud;

/**
 * 
 */
public class VueNoeud {
	
	/**
	 * Constante contenant le rayon du point représentant le noeud
	 */
	private final double RAYON_NOEUD = 10;
	
	/**
	 * Le noeud à afficher
	 */
	private Noeud noeud;
	
	/**
	 * L'abscisse du point sur le plan
	 */
	private double x;
	
	/**
	 * L'ordonnée du point sur le plan
	 */
	private double y;
	
	/**
	 * Le point qui sera affiché sur le graph
	 */
	private Object point;
	
	/*
	 * La couleur de l'intérieur du point
	 */
	private String couleurRemplissage;
	
	/**
	 * La couleur de la bordure du point
	 */
	private String couleurBordure;

	
    
    /**
	 * @param noeud
	 * @param x
	 * @param y
	 * @param point
	 * @param couleurRemplissage
	 * @param couleurBordure
	 */
	public VueNoeud(Noeud noeud, double hX, double hY,
			String couleurRemplissage, String couleurBordure) {
		this.noeud = noeud;
		this.x = noeud.getX()*hX;
		this.y = noeud.getY()*hY;
		this.couleurRemplissage = couleurRemplissage;
		this.couleurBordure = couleurBordure;
	}

	/**
	 * Méthode permettant d'afficher le noeud
	 * @param graph
	 * @param style paramètres de style
	 */
	public void afficher(mxGraph graph, String style ){
		point = graph.insertVertex(graph.getDefaultParent(), "", "",
				 x, y, RAYON_NOEUD, RAYON_NOEUD, 
				style);
    }
	
	/**
	 * Méthode permettant d'afficher le noeud
	 * @param graph
	 */
	public void afficher(mxGraph graph){
		afficher(graph, 
				"fillColor=" + couleurRemplissage +
				";strokeColor=" + couleurBordure);
	}

    /**
     * 
     */
    public void surbrillance() {
        // TODO implement here
    }

	/**
	 * @return le point
	 */
	public Object getPoint() {
		return point;
	}
    
	/**
	 * Teste si le noeud est aux coordonnées données
	 * @param x 
	 * @param y
	 * @return boolean
	 */
	public boolean estLa(int x, int y){
		return ((x > this.x - RAYON_NOEUD && x < this.x + RAYON_NOEUD)
				&& (y > this.y - RAYON_NOEUD && y < this.y + RAYON_NOEUD));
	}

	/**
	 * @return the noeud
	 */
	public Noeud getNoeud() {
		return noeud;
	}
	
	
    

}