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
import modele.Tournee;
import modele.Troncon;

/**
 * 
 */
public class VuePlan extends mxGraphComponent{
	
	/**
	 * Constantes contenant les couleurs de remplissage et de bordure 
	 * des points de livraison en fonction de leur plage horaire
	 */
	private final String[] COULEUR_REMPLISSAGE = { "#a7a7a7", "#4407a6",
			"#07a60f", "#ff7300", "#84088c", "#08788c", "#792f2f" };
	private final String[] COULEUR_BORDURE = { "#838383", "#2d0968", "#0d7412",
			"#b3560b", "#511155", "#0f5f6d", "#522828" };
	
	/**
	 * Constante contenant le rayon du point représentant le noeud
	 */
	private final double RAYON_NOEUD = 10;
	
	/**
	 * Facteurs de mise à l'échelle pour l'affichage sur le plan
	 */
	private double hY;
	private double hX;
	
	/**
	 * La liste des points affichés sur le plan
	 */
	private HashMap<Integer, Object> points;
	
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
	 * L'ensemble des <code>Noeud</code> du plan
	 */
	private Set<Noeud> tousNoeuds = new HashSet<Noeud>();
	
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
	private Controleur controleur;

    /**
     * 
     */
    public VuePlan() {
    	super(new mxGraph());
    	//this.controleur = controleur;
    	setParamsPlan();
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
    	System.out.println(noeudAAjouter + " == " + noeudSelectionne);
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
		
		List<DemandeDeLivraison> demandesTempsDepasse = tournee.getDemandesTempsDepasse();

		Object parent = graph.getDefaultParent();

		int noeudPrecedent = entrepot.getId();

		ArrayList<Itineraire> itineraires = tournee.getItineraires();
		Iterator<Itineraire> it = itineraires.iterator();

		HashMap<String, Integer> noeudsTraverses = new HashMap<String, Integer>();

		afficherDemandesTempsDepasse(demandesTempsDepasse);

		while (it.hasNext()) {
			Itineraire itineraire = it.next();
			int idHoraire = 1;
			try {
				idHoraire = noeudsALivrer.get(itineraire.getDepart().getNoeud()
						.getId());
			} catch (Exception e) {
			}
			String color = COULEUR_BORDURE[idHoraire];

			Iterator<Troncon> troncons = itineraire.getTronconsItineraire()
					.iterator();
			while (troncons.hasNext()) {
				Troncon troncon = troncons.next();
				String key = ""
						+ Math.max(noeudPrecedent, troncon.getNoeudFin().getId())
						+ "-"
						+ Math.min(troncon.getNoeudFin().getId(), noeudPrecedent);

				String edgeStyle = (noeudsTraverses.containsKey(key)) ? "edgeStyle=elbowEdgeStyle;elbow=horizontal;"
						+ "exitX=0.5;exitY=1;exitPerimeter=1;entryX=0;entryY=0;entryPerimeter=1;"
						+ mxConstants.STYLE_ROUNDED + "=1;"
						: "";

				graph.insertEdge(parent, null, "", points.get(noeudPrecedent),
						points.get(troncon.getNoeudFin().getId()), edgeStyle
								+ "strokeWidth=2;strokeColor=" + color);

				if (noeudsTraverses.containsKey(key)) {
					noeudsTraverses.put(key, noeudsTraverses.get(key) + 1);
				} else {
					noeudsTraverses.put(key, 1);
				}

				noeudPrecedent = troncon.getNoeudFin().getId();
			}
			tourneeDessinee = true;
		}
	}
	
	/**
	 * Méthode permettant de mettre en évidence sur l'affichage les points de
	 * livraisons qui ne pourront pas être livrés dans la plage horaire demandée
	 */
	public void afficherDemandesTempsDepasse(List<DemandeDeLivraison> demandesTempsDepasse) {
		if (this.demandesTempsDepasse == null) {
			this.demandesTempsDepasse = new HashSet<Integer>();
			Iterator<DemandeDeLivraison> it = demandesTempsDepasse.iterator();
			while (it.hasNext()) {
				Noeud noeud = it.next().getNoeud();
				this.demandesTempsDepasse.add(noeud.getId());
			}
		}

		for (Integer idNoeud : this.demandesTempsDepasse) {
			int numPlage = noeudsALivrer.get(idNoeud);
			graph.setCellStyle(
					"shape=triangle;strokeWidth=2;fillColor=red;strokeColor="
							+ COULEUR_BORDURE[numPlage], 
					new Object[] { points.get(idNoeud) });
		}
	}
	
	/**
	 * Affiche le plan à partir des données préalablement chargées depuis un XML
	 */
	public void afficherPlan(Set<Noeud> noeuds) {
		tousNoeuds = noeuds;
		
		demandesTempsDepasse = new HashSet<Integer>();
		noeudAAjouter = null;
		noeudSelectionne = null;
		tourneeDessinee = false;

		points = new HashMap<Integer, Object>();
		Iterator<Noeud> it = noeuds.iterator();

		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		graph.removeCells(graph.getChildCells(parent));

		// On commence par placer les points
		while (it.hasNext()) {
			Noeud noeudCourant = it.next();
			double x = noeudCourant.getX();
			double y = noeudCourant.getY();
			points.put(noeudCourant.getId(), graph.insertVertex(parent, "", "",
					hX * x, hY * y, RAYON_NOEUD, RAYON_NOEUD, "fillColor="
							+ COULEUR_REMPLISSAGE[0] + ";strokeColor="
							+ COULEUR_BORDURE[0]));
		}

		// Puis on trace les tronçons
		it = noeuds.iterator();
		while (it.hasNext()) {
			Noeud noeudCourant = it.next();

			Iterator<Troncon> itTroncons = noeudCourant.getTronconSortants()
					.iterator();
			while (itTroncons.hasNext()) {
				graph.insertEdge(parent, null, "",
						points.get(noeudCourant.getId()),
						points.get(itTroncons.next().getNoeudFin().getId()),
						"strokeColor=" + COULEUR_REMPLISSAGE[0]);
			}

		}

		graph.getModel().endUpdate();

	}
	
	/**
	 * Méthode permettant de calculer les facteurs de mise à l'échelle
	 * à partir du xMax et yMax réels des points à afficher
	 * @param xMax
	 * @param yMax
	 */
	public void calculeFacteurEchelle(int xMax, int yMax){
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
		if (points == null)
			return null;
		else {
			Iterator<Noeud> it = tousNoeuds.iterator();
			while (it.hasNext()) {
				Noeud n = it.next();
				double nX = n.getX() * hX;
				double nY = n.getY() * hY;
				if ((x > nX - RAYON_NOEUD && x < nX + RAYON_NOEUD)
						&& (y > nY - RAYON_NOEUD && y < nY + RAYON_NOEUD)) {
					return n;
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

		// On commence par déselectionner l'ancienne sélection
		if (noeudSelectionne != null) {
			int idCouleur = (noeudsALivrer != null
					&& noeudsALivrer.containsKey(noeudSelectionne.getId()) ? noeudsALivrer
					.get(noeudSelectionne.getId()) : 0);
			Object[] cells = { points.get(noeudSelectionne.getId()) };
			graph.setCellStyle("fillColor=" + COULEUR_REMPLISSAGE[idCouleur]
					+ ";strokeColor=" + COULEUR_BORDURE[idCouleur], cells);
		}
		noeudSelectionne = nouvelleSelection;

		if (noeudSelectionne != null) {
			int idCouleur = (noeudsALivrer != null
					&& noeudsALivrer.containsKey(nouvelleSelection.getId()) ? noeudsALivrer
					.get(nouvelleSelection.getId()) : 0);
			Object[] cells = { points.get(noeudSelectionne.getId()) };
			graph.setCellStyle("strokeColor=red;strokeWidth=3;fillColor="
					+ COULEUR_REMPLISSAGE[idCouleur], cells);
		}
	}
	
	/**
	 * Méthode permettant d'afficher d'une couleur différente les demandes de
	 * livraison sur le plan
	 */
	public void afficherDemandesLivraisons(Tournee tournee) {
		noeudsALivrer = new HashMap<Integer, Integer>();

		// On réaffiche le plan proprement, sans point de livraison
		afficherPlan(tousNoeuds);

		if (tournee.getEntrepot() != null) {
			entrepot = tournee.getEntrepot().getNoeud();

			graph.insertVertex(graph.getDefaultParent(), "", "",
					hX * entrepot.getX(), hY * entrepot.getY(),
					RAYON_NOEUD + 6, RAYON_NOEUD + 6,
					"shape=ellipse;perimeter=30;strokeColor=black;strokeWidth=3;fillColor=yellow");
		}

		int numPlage = 1;
		for (PlageHoraire plage : tournee.getPlagesHoraires()) {

			for (DemandeDeLivraison livraison : plage.getDemandeLivraison()) {
				int noeud = livraison.getNoeud().getId();
				if(points.containsKey(noeud)){
					graph.setCellStyle("fillColor=" + COULEUR_REMPLISSAGE[numPlage]
							+ ";strokeColor=" + COULEUR_BORDURE[numPlage],
							new Object[] { points.get(noeud) });
					noeudsALivrer.put(noeud, numPlage);
				}
			}
			numPlage++;
		}
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