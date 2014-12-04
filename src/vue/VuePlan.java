package vue;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import controleur.Controleur;
import modele.DemandeDeLivraison;
import modele.Itineraire;
import modele.Noeud;
import modele.PlageHoraire;
import modele.Plan;
import modele.Tournee;
import modele.Troncon;

/**
 * 
 */
public class VuePlan extends mxGraphComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3941514355042064714L;
	/**
	 * Constantes contenant les couleurs de remplissage et de bordure 
	 * des points de livraison en fonction de leur plage horaire
	 */
	public static final String[] COULEUR_REMPLISSAGE = { "#a7a7a7", "#4407a6",
			"#07a60f", "#ff7300", "#84088c", "#08788c", "#792f2f" };
	public static final String[] COULEUR_BORDURE = { "#838383", "#2d0968", "#0d7412",
			"#b3560b", "#511155", "#0f5f6d", "#522828" };
	

	
	/**
	 * Facteurs de mise à l'échelle pour l'affichage sur le plan
	 */
	private double hY;
	private double hX;
	
	/**
	 * Le noeud actuellement selectionné
	 */
	private Noeud noeudSelectionne;
	
	/**
	 * Le noeud correspondant à l'entrepot
	 */
	private Noeud entrepot;
	
	/**
	 * Le noeud sur lequel l'utilisateur veut ajout une demande de livraison
	 * Est null tant qu'il n'a pas sélectionné un point puis cliqué sur "Ajouter"
	 */
	private Noeud noeudAAjouter = null;

	/**
	 * Booleen permettant de savoir si la tournée a déjà été dessinée ou non
	 */
	private boolean tourneeDessinee = false;
	
	/**
	 * L'ensemble des <code>VueNoeud</code> du plan
	 */
	private HashMap<Integer, VueNoeud> vueNoeuds;
	
	/**
	 * L'ensemble des <code>VueTroncon</code> du plan
	 */
	private Set<VueTroncon> vueTroncons;
	
	private VueTournee vueTournee;

	/**
	 * L'id des demandes de livraison qui ne pouront pas être livrés dans la
	 * plage horaire demandée.
	 */
	private Set<Integer> demandesTempsDepasse;	

    /**
     * 
     */
    public VuePlan() {
    	super(new mxGraph());
    	//this.controleur = controleur;
    	setParamsPlan();
    	
		noeudAAjouter = null;
		noeudSelectionne = null;
		tourneeDessinee = false;
		
		vueNoeuds = new HashMap<Integer, VueNoeud>();
		vueTroncons = new HashSet<VueTroncon>();
		
		demandesTempsDepasse = new HashSet<Integer>();
    }
    
    public void setPlan(Plan plan){
    	calculeFacteurEchelle(plan.getMaxX(), plan.getMaxY());
    	vueNoeuds.clear();
    	vueTroncons.clear();
    	vueNoeuds.clear();

		for (Noeud noeud : plan.getToutNoeuds()) {
			vueNoeuds.put(noeud.getId(), 
					new VueNoeud(noeud, hX, hY));
		}
		
		for (Noeud noeud : plan.getToutNoeuds()) {
			for (Troncon troncon : noeud.getTronconSortants()) {
				vueTroncons.add(new VueTroncon(
						vueNoeuds.get(noeud.getId()), 
						vueNoeuds.get(troncon.getNoeudFin().getId())));
			}
			
		}
    }
    
    /**
     * Méthode se chargeant de mettre en place tous les paramètres du plan
     */
    private void setParamsPlan(){
    	graph.setAllowDanglingEdges(false);
		graph.setCellsBendable(false);
		graph.setCellsDisconnectable(false);
		graph.setCellsMovable(false);
		graph.setCellsResizable(false);
		graph.setCellsEditable(false);
		this.setConnectable(false);
    }
    
    
    /**
     * Méthode permettant d'indiquer que le noeud sélectionné va être ajouté
     * comme Point de Livraison
     */
    public void setNoeudAAjouter(){
    	noeudAAjouter = noeudSelectionne;
    }


    /**
     * Méthode permettant de vider l'attribut gardant en mémoire le noeud à ajouter
     */
    public void unsetNoeudAAjouter(){
    	noeudAAjouter = null;
    }
    
    public Noeud getNoeudLivraisonSelectionne(){
    	if (vueTournee.estDemandeDeLivraison(noeudSelectionne.getId())) {
			return noeudSelectionne;
		}
    	return null;
    }
    
    /**
	 * Méthode permettant de dessiner la tournée
	 */
	public void dessinerTournee(Tournee tournee) {
		noeudAAjouter = null;
		noeudSelectionne = null;
		
		tourneeDessinee = true;		
		vueTournee.setTournee(tournee);
		vueTournee.afficher(graph);
		
		System.out.println("On dessine la tournée");
	}
	
	/**
	 * Affiche le plan à partir des données préalablement chargées depuis un XML
	 */
	public void afficherPlan() {
		
		graph.getModel().beginUpdate();
		//On commence par supprimer tout ce qui était affiché précédemment
		graph.removeCells(graph.getChildCells(graph.getDefaultParent()));

		//On affiche d'abord tous les points
		Set<Integer> idNoeuds = vueNoeuds.keySet();
		for (Integer idNoeud : idNoeuds) {
			vueNoeuds.get(idNoeud).afficher(graph);
		}
		
		for (VueTroncon vueTroncon : vueTroncons) {
			vueTroncon.afficher(graph);
		}

		graph.getModel().endUpdate();

	}
	
	/**
	 * Méthode permettant de calculer les facteurs de mise à l'échelle
	 * à partir du xMax et yMax réels des points à afficher
	 * @param xMax
	 * @param yMax
	 */
	private void calculeFacteurEchelle(int xMax, int yMax){
		hY = (getSize().getHeight() - 20)
				/ yMax;
		hX = (getSize().getWidth() - 20)
				/ xMax;
	}
	
	/**
	 * Méthode renvoyant le noeud aux coordonnées passées en paramètre
	 * 
	 * @param x
	 *            abscisse du point à tester
	 * @param y
	 *            ordonnée du point à tester
	 * @return le noeud à ces coordonnées s'il existe
	 */
	public Noeud getNoeudA(int x, int y) {
		if (vueNoeuds == null)
			return null;
		else {
			Set<Integer> cles = vueNoeuds.keySet();
			for (Integer cle : cles) {
				if(vueNoeuds.get(cle).estLa(x, y)){
					return  vueNoeuds.get(cle).getNoeud();
				}
			}
		}
		return null;
	}
	
	/**
	 * Change le point selectionné sur l'affichage : Déselectionne le point qui
	 * était selectionné jusque là, et sélectionne le nouveau
	 * 
	 * @param nouvelleSelection
	 */
	public void changerPointSelectionne(Noeud nouvelleSelection) {
		if (noeudSelectionne != null) {
			vueNoeuds.get(noeudSelectionne.getId()).deselectionner(graph);
		}
		
		noeudSelectionne = nouvelleSelection;
		if (noeudSelectionne != null) {
			vueNoeuds.get(nouvelleSelection.getId()).selectionner(graph);
		}
		
	}
	
	/**
	 * Méthode permettant d'afficher d'une couleur différente les demandes de
	 * livraison sur le plan
	 */
	public void afficherDemandesLivraisons(Tournee tournee) {

		// On réaffiche le plan proprement, sans point de livraison
		afficherPlan();
		vueTournee.afficher(graph);
		
		System.out.println("On affiche les demandes de livraison");
		
	}
	
	
	/**
	 * @param vueTournee
	 */
	public void setTournee(Tournee tournee) {
		this.vueTournee = new VueTournee(tournee, hX, hY, vueNoeuds);
	}

	/**
	 * Méthode permettant de savoir si on doit ajouter un point ou non.
	 * 
	 * @param noeud
	 * 			Le noeud qui vient d'être sélectionné
	 * @return 
	 * 			true si on est en phase d'ajout et le point sélectionné est une demande de livraison
	 * 			false sinon
	 */
	public boolean doitAjouterPoint(Noeud noeud){
		return (noeudAAjouter != null && (vueTournee.estDemandeDeLivraison(noeud.getId()) || noeud.getId() == entrepot.getId()));
	}
	
    /**
	 * @return le noeudAAjouter
	 */
	public Noeud getNoeudAAjouter() {
		return noeudAAjouter;
	}
	
	/**
	 * Methode permettant de savoir si le bouton Ajouter doit être activé ou non
	 * @param noeud 
	 * 			le noeud actuellement sélectionné
	 * @return	l'état que doit avoir le bouton
	 */
	public boolean etatBtnAjouter(Noeud noeud){
		return tourneeDessinee && entrepot!= null && noeud!= entrepot && !vueTournee.estDemandeDeLivraison(noeud.getId())  ;
	}
	
	/**
	 * Methode permettant de savoir si le bouton Supprimer doit être activé ou non
	 * @param noeud 
	 * 			le noeud actuellement sélectionné
	 * @return	l'état que doit avoir le bouton
	 */
	public boolean etatBtnSupprimer(Noeud noeud){
		return tourneeDessinee && vueTournee.estDemandeDeLivraison(noeud.getId());
	}

}