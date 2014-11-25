package vue;

import java.awt.Graphics;
import java.awt.Shape;
import java.util.List;
import java.util.Set;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position.Bias;
import javax.swing.text.View;

import modele.DemandeDeLivraison;
import modele.Noeud;

/**
 * 
 */
public class VueTournee extends View {


    public VueTournee(Element elem) {
		super(elem);
		// TODO Auto-generated constructor stub
	}

	/**
     * 
     */
    private Set<VueItineraire> vuesItineraire;

    /**
     * 
     */
    private Set<VueDemandeDeLivraison> vuesDemandesDeLivraison;

    /**
     * 
     */
    private VuePlan vuePlan;

    /**
     * @param noeud
     */
    public void surbrillanceNoeud(Noeud noeud) {
        // TODO implement here
    }

    /**
     * 
     */
    public void dessiner() {
        // TODO implement here
    }

    /**
     * @param demandes
     */
    public void creerVuesDemandeDeLivraison(List<DemandeDeLivraison> demandes) {
        // TODO implement here
    }

	@Override
	public float getPreferredSpan(int axis) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void paint(Graphics g, Shape allocation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Shape modelToView(int pos, Shape a, Bias b)
			throws BadLocationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int viewToModel(float x, float y, Shape a, Bias[] biasReturn) {
		// TODO Auto-generated method stub
		return 0;
	}

}