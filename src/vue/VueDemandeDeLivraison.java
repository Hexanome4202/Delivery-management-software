package vue;

import com.mxgraph.view.mxGraph;

import modele.DemandeDeLivraison;
import modele.Noeud;

/**
 * 
 */
public class VueDemandeDeLivraison extends VueNoeud{	
	/**
	 * La demande de livraison
	 */
	private DemandeDeLivraison demandeDeLivraison;
	
	/**
	 * Boolean indiquant si la demande de livraison peut être satisfaite 
	 * dans la plage horaire demandée ou non
	 */
	private boolean tempsDepasse = false;
	
	/**
	 * Le numéro de la plage horaire dans laquelle est la demande de livraison
	 */
	private int numPlage=0;

	/**
	 * @param demandeDeLivraison
	 * @param hX
	 * @param hY
	 * @param numPlage
	 */
	public VueDemandeDeLivraison(DemandeDeLivraison demandeDeLivraison, double hX, double hY, int numPlage) {
		super(demandeDeLivraison.getNoeud(), hX, hY, COULEUR_REMPLISSAGE[numPlage], COULEUR_BORDURE[numPlage]);
		this.demandeDeLivraison = demandeDeLivraison;
		this.numPlage = numPlage;
	}

	/**
	 * @param demandeDeLivraison
	 * @param hX
	 * @param hY
	 * @param couleurRemplissage
	 * @param couleurBordure
	 */
	protected VueDemandeDeLivraison(DemandeDeLivraison demandeDeLivraison, double hX, double hY,
			String couleurRemplissage, String couleurBordure) {
		super(demandeDeLivraison.getNoeud(), hX, hY, couleurRemplissage, couleurBordure);
		this.demandeDeLivraison = demandeDeLivraison;
	}
	
	/**
	 * @param demandeDeLivraison
	 * @param hX
	 * @param hY
	 * @param numPlage
	 */
	public VueDemandeDeLivraison(DemandeDeLivraison demandeDeLivraison, double hX, double hY, int numPlage, boolean tempsDepasse) {
		this(demandeDeLivraison, hX, hY, COULEUR_REMPLISSAGE[numPlage], COULEUR_BORDURE[numPlage]);
		this.tempsDepasse = tempsDepasse;
		this.numPlage = numPlage;
	}
	
	

	/**
	 * @param tempsDepasse the tempsDepasse to set
	 */
	public void setTempsDepasse(boolean tempsDepasse) {
		this.tempsDepasse = tempsDepasse;
	}
	
	public void afficher(mxGraph graph, Object point){
		if(!tempsDepasse){
			//TODO Grossir le temps dépassé
			graph.setCellStyle("fillColor=" + getCouleurRemplissage() + 
				";strokeColor=" + getCouleurBordure(), 
					new Object[] {point});
		}else{
			graph.setCellStyle("shape=triangle;strokeWidth=2;fillColor=red;strokeColor="+getCouleurBordure(), 
					new Object[] {point});
		}
	}

	/**
	 * @return the numPlage
	 */
	public int getNumPlage() {
		return numPlage;
	}	
	
	

}