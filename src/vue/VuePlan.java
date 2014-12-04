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
	 * L'id des noeuds à livrer associé au numéro de plage horaire
	 */
	private HashMap<Integer, Integer> noeudsALivrer;
	
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
    	if (noeudsALivrer.containsKey(noeudSelectionne.getId())) {
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
		
		System.out.println("On affiche le plan!");
	
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
		//TODO rendre possible la sélection de nouveau
		/*
		// On commence par déselectionner l'ancienne sélection
		if (noeudSelectionne != null) {
			int idCouleur = (noeudsALivrer != null
					&& noeudsALivrer.containsKey(noeudSelectionne.getId()) ? noeudsALivrer
					.get(noeudSelectionne.getId()) : 0);
			Object[] cells = { vueNoeuds.get(noeudSelectionne.getId()).getPoint() };
			graph.setCellStyle("fillColor=" + COULEUR_REMPLISSAGE[idCouleur]
					+ ";strokeColor=" + COULEUR_BORDURE[idCouleur], cells);
		}
		noeudSelectionne = nouvelleSelection;

		if (noeudSelectionne != null) {
			int idCouleur = (noeudsALivrer != null
					&& noeudsALivrer.containsKey(nouvelleSelection.getId()) ? noeudsALivrer
					.get(nouvelleSelection.getId()) : 0);
			Object[] cells = { vueNoeuds.get(noeudSelectionne.getId()).getPoint() };
			graph.setCellStyle("strokeColor=red;strokeWidth=3;fillColor="
					+ COULEUR_REMPLISSAGE[idCouleur], cells);
		}
		*/
	}
	
	/**
	 * Méthode permettant d'afficher d'une couleur différente les demandes de
	 * livraison sur le plan
	 */
	public void afficherDemandesLivraisons(Tournee tournee) {
		noeudsALivrer = new HashMap<Integer, Integer>();

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
		return (noeudAAjouter != null && (noeudsALivrer.containsKey(noeud.getId()) || noeud.getId() == entrepot.getId()));
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
		return tourneeDessinee && entrepot!= null && noeud!= entrepot && !noeudsALivrer.containsKey(noeud.getId())  ;
	}
	
	/**
	 * Methode permettant de savoir si le bouton Supprimer doit être activé ou non
	 * @param noeud 
	 * 			le noeud actuellement sélectionné
	 * @return	l'état que doit avoir le bouton
	 */
	public boolean etatBtnSupprimer(Noeud noeud){
		return tourneeDessinee && noeudsALivrer.containsKey(noeud.getId());
	}

}