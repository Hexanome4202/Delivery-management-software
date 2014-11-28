package modele;

import java.util.ArrayList;
import tsp.Graph;

/**
 * Classe définissant le <code>Graph</code> d'une <code>Tournee</code>
 * @author asarazin1
 *
 */
public class GraphTournee implements Graph{

	private int nbNoeuds;
	private int maxCoutArc;
	private int minCoutArc;
	private int[][] couts; 
	private ArrayList<ArrayList<Integer>> succ;
	
	/**
	 * Constucteur d'un graphe de tournée
	 */
	public GraphTournee(int[][] couts, ArrayList<ArrayList<Integer>> succ, int maxCoutArc, int minCoutArc){
		this.couts = couts;
		this.succ = succ;
		this.maxCoutArc = maxCoutArc;
		this.minCoutArc = minCoutArc;
		this.nbNoeuds = couts.length;
	}
	
	@Override
	public int getMaxArcCost() {
		return maxCoutArc;
	}

	@Override
	public int getMinArcCost() {
		return minCoutArc;
	}

	@Override
	public int getNbVertices() {
		return nbNoeuds;
	}

	@Override
	public int[][] getCost() {
		return couts;
	}

	@Override
	public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
		if(i<0 || i>=nbNoeuds){
			throw new ArrayIndexOutOfBoundsException();
		}
		int[] res = new int[succ.get(i).size()];
		for(int j=0; j<res.length; j++){
			res[j] = succ.get(i).get(j);
		}
		return res;
	}

	@Override
	public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
		if(i<0 || i>=nbNoeuds){
			throw new ArrayIndexOutOfBoundsException();
		}
		return succ.get(i).size();
	}

}
