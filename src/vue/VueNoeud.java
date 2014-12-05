package vue;

import com.mxgraph.view.mxGraph;

import modele.Noeud;

/**
 * 
 */
public class VueNoeud {
	/**
	 * Constantes contenant les couleurs de remplissage et de bordure des points
	 * de livraison en fonction de leur plage horaire
	 */
	public final static String[] COULEUR_REMPLISSAGE = { "#a7a7a7", "#4407a6",
			"#07a60f", "#ff7300", "#84088c", "#08788c", "#792f2f" };
	public final static String[] COULEUR_BORDURE = { "#838383", "#2d0968",
			"#0d7412", "#b3560b", "#511155", "#0f5f6d", "#522828" };
	
	/**
	 * Constante contenant le rayon d'un noeud du plan
	 */
	protected final int RAYON_POINT = 10;

	/**
	 * Le rayon du point représentant le noeud Vaut 10 par défaut, mais peut
	 * être modifié si besoin
	 */
	private int rayonNoeud = 10;

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
	 * Une String contenant un évenuel style pour le noeud
	 */
	private String style = null;

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
		this.x = noeud.getX() * hX;
		this.y = noeud.getY() * hY;
		this.couleurRemplissage = couleurRemplissage;
		this.couleurBordure = couleurBordure;
	}

	/**
	 * Constructeur de VueNoeud, ne prenant pas de couleur en paramètre Par
	 * défaut, la couleur sera alors le gris des points du plan
	 * 
	 * @param noeud
	 * @param x
	 * @param y
	 * @param point
	 */
	public VueNoeud(Noeud noeud, double x, double y) {
		this(noeud, x, y, COULEUR_REMPLISSAGE[0], COULEUR_REMPLISSAGE[0]);
	}

	/**
	 * Méthode permettant d'afficher le noeud
	 * 
	 * @param graph
	 * @param style
	 *            paramètres de style
	 */
	public void afficher(mxGraph graph, String style) {
		point = graph.insertVertex(graph.getDefaultParent(), "", "", x, y,
				rayonNoeud, rayonNoeud, style);
	}

	/**
	 * Méthode permettant d'afficher le noeud
	 * 
	 * @param graph
	 */
	public void afficher(mxGraph graph) {
		if (style == null || style != "") {
			afficher(graph, "fillColor=" + couleurRemplissage + ";strokeColor="
					+ getCouleurBordure());
		} else {
			afficher(graph, style);
		}
	}

	/**
	 * Met à jour l'affichage pour montrer que le noeud est sélectionné
	 * 
	 * @param graph
	 */
	public void selectionner(mxGraph graph) {

		graph.setCellStyle("strokeColor=red;strokeWidth=3;fillColor="
				+ couleurRemplissage, new Object[] { point });

	}

	/**
	 * Désélectionne le noeud
	 * 
	 * @param graph
	 */
	public void deselectionner(mxGraph graph) {
		graph.setCellStyle("fillColor=" + couleurRemplissage + ";strokeColor="
				+ couleurBordure, new Object[] { point });
	}

	/**
	 * Récupère le point
	 * 
	 * @return le point
	 */
	public Object getPoint() {
		return point;
	}

	/**
	 * Teste si le noeud est aux coordonnées données
	 * 
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean estLa(int x, int y) {
		return ((x > this.x - rayonNoeud && x < this.x + rayonNoeud) && (y > this.y
				- rayonNoeud && y < this.y + rayonNoeud));
	}

	/**
	 * Récupère le noeud
	 * 
	 * @return the noeud
	 */
	public Noeud getNoeud() {
		return noeud;
	}

	/**
	 * Récupère la couleur de la bordure
	 * 
	 * @return la couleur de bordure
	 */
	public String getCouleurBordure() {
		return couleurBordure;
	}

	/**
	 * Récupère la couleur de remplissage
	 * 
	 * @return la couleur de remplissage
	 */
	public String getCouleurRemplissage() {
		return couleurBordure;
	}

	/**
	 * Méthode permettant de changer les couleurs d'une vueNoeud
	 * 
	 * @param numPlage
	 *            le numéro de la plage horaire à laquelle il appartient
	 */
	public void setColors(int numPlage) {
		this.couleurRemplissage = COULEUR_REMPLISSAGE[numPlage];
		this.couleurBordure = COULEUR_BORDURE[numPlage];

	}

	/**
	 * Méthode permettant de modifier la forme d'une VueNoeud
	 * 
	 * @param style
	 *            le style à lui appliquer
	 * @param taille
	 *            la taille qu'on veut lui donner
	 */
	public void modifierForme(String style, int taille) {
		this.style = style;
		this.rayonNoeud = taille;
	}
	
	/**
	 * Méthode permettant d'assigner à la VueNoeud le style d'un point gris
	 * du plan
	 */
	public void resetStyle(){
		System.out.println("Reset style");
		modifierForme(null, RAYON_POINT);
	}

	/**
	 * Renvoie le style spécifique de la VueNoeud
	 * 
	 * @return le style
	 */
	public String getStyle() {
		return style;
	}

}