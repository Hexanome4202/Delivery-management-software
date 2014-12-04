package vue;

import java.awt.Graphics;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position.Bias;
import javax.swing.text.View;

import com.mxgraph.view.mxGraph;

import modele.DemandeDeLivraison;
import modele.Itineraire;
import modele.Noeud;
import modele.PlageHoraire;
import modele.Tournee;

/**
 * 
 */
public class VueTournee {

	/**
	 * Liste des vues DemandeDeLivraison associées à l'id du noeud correspondant
	 */
	private HashMap<Integer, VueDemandeDeLivraison> vuesDemandeDeLivraison;
	
	/**
	 * Liste des vuesItineraires
	 */
	private Set<VueItineraire> vuesItineraires;
	
	/**
	 * La vue de l'entrepot
	 */
	private VueEntrepot vueEntrepot;
	
	/**
	 * La tournée
	 */
	private Tournee tournee;
	
	/**
	 * La liste des vueNoeuds associées à l'id du noeud, 
	 * nécessaire pour tracer les tronçons
	 */
	HashMap<Integer, VueNoeud> vueNoeuds;
	
	/**
	 * Facteurs de mise à l'échelle pour l'affichage sur le plan
	 */
	private double hY;
	private double hX;
	

	/**
	 * @param tournee
	 */
	public VueTournee(Tournee tournee, double hX, double hY, HashMap<Integer, VueNoeud> vueNoeuds) {
		this.vueNoeuds = vueNoeuds;
		vuesDemandeDeLivraison = new HashMap<Integer, VueDemandeDeLivraison>();
		vuesItineraires = new HashSet<VueItineraire>();
		
		this.hX = hX;
		this.hY = hY;
		
		if(tournee != null){
			this.tournee = tournee;
			
			if(tournee.getEntrepot() != null){
				vueEntrepot = new VueEntrepot(tournee.getEntrepot(), hX, hY);
			}
		}
		
		chargerVuesDemandeDeLivraison(tournee);
	}
	
	public void setTournee(Tournee tournee){
		if(tournee != null){
			
			/*//On remet tous les noeuds en gris
			Set<Integer> cles = vuesDemandeDeLivraison.keySet();
			for (Integer cle : cles) {
				vueNoeuds.get(cle).setColors(0);
			}*/
			
			vuesDemandeDeLivraison.clear();
			
			chargerVuesDemandeDeLivraison(tournee);
			this.tournee = tournee;
			
			if(tournee.getEntrepot() != null){
				vueEntrepot = new VueEntrepot(tournee.getEntrepot(), hX, hY);
			}
			
			int idPremierNoeud = vueEntrepot.getNoeud().getId();
			
			for (Itineraire itineraire : tournee.getItineraires()) {
				String couleur = VueNoeud.COULEUR_BORDURE[1];
				if(vuesDemandeDeLivraison.containsKey(idPremierNoeud)){
					couleur = VueNoeud.COULEUR_BORDURE[vuesDemandeDeLivraison.get(idPremierNoeud).getNumPlage()];
				}
				vuesItineraires.add(
						new VueItineraire(itineraire, vueNoeuds, idPremierNoeud, couleur));
				idPremierNoeud = itineraire.getArrivee().getNoeud().getId();
			}
		}

			
		
	}
	
	private void chargerVuesDemandeDeLivraison(Tournee tournee){
		int numPlage = 1;
		for (PlageHoraire plage : tournee.getPlagesHoraires()) {
			
			for (DemandeDeLivraison demandeDeLivraison : plage.getDemandeLivraison()) {
				VueDemandeDeLivraison vueDemande = new VueDemandeDeLivraison(demandeDeLivraison, hX, hY, numPlage);
				if(tournee.getDemandesTempsDepasse()!= null && tournee.getDemandesTempsDepasse().contains(demandeDeLivraison)){
					vueDemande.setTempsDepasse(true);
				}
				vuesDemandeDeLivraison.put(demandeDeLivraison.getNoeud().getId(), 
						vueDemande);
				
				vueNoeuds.get(demandeDeLivraison.getNoeud().getId()).setColors(numPlage);
			}
			numPlage ++;
		}
	}
	
	public void afficher(mxGraph graph){
		if(tournee.getItineraires() != null){

			//On indique aux DemandesDeLivraison au Temps Dépassé qu'elles le sont
			if(tournee.getDemandesTempsDepasse()!= null){
				for (DemandeDeLivraison demande : tournee.getDemandesTempsDepasse()) {
					try{
						vuesDemandeDeLivraison.get(demande.getNoeud().getId()).setTempsDepasse(true);
					}catch(Exception e){ }
				}
			}
			
			//On affiche les demandes de livraison
			Set<Integer> cles = vuesDemandeDeLivraison.keySet();
			for (Integer cle : cles) {
				vuesDemandeDeLivraison.get(cle).afficher(graph, vueNoeuds.get(cle).getPoint());
			}
			
			if(vuesItineraires != null && vueEntrepot!=null){			
				int idPremierNoeud = vueEntrepot.getNoeud().getId();
				//puis on affiche l'itinéraire
				for (VueItineraire vueItineraire : vuesItineraires) {
					vueItineraire.afficher(graph);
				}
			}
		}else{
			//On affiche seulement les demandes de livraison si la tournée n'est pas calculée
			Set<Integer> cles = vuesDemandeDeLivraison.keySet();
			for (Integer cle : cles) {
				vuesDemandeDeLivraison.get(cle).afficher(graph);
			}
		}
 	}
	
	/**
	 * Methode indiquant si le noeud avec l'id passé est une demande de livraison
	 * @param idNoeud l'id du noeud à tester
	 * @return
	 */
	public boolean estDemandeDeLivraison(int idNoeud){
		return vuesDemandeDeLivraison.containsKey(idNoeud);
	}
	
	public void supprimerDemandeDeLivraison(int idNoeud){
		vueNoeuds.get(idNoeud).setColors(0);
	}

}