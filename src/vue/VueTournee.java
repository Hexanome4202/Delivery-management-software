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
    }

    /**
     * 
     */
    public void dessiner() {
    }

    /**
     * @param demandes
     */
    public void creerVuesDemandeDeLivraison(List<DemandeDeLivraison> demandes) {
    }

	@Override
	public float getPreferredSpan(int axis) {
		return 0;
	}

	@Override
	public void paint(Graphics g, Shape allocation) {
	}

	@Override
	public Shape modelToView(int pos, Shape a, Bias b)
			throws BadLocationException {
		return null;
	}

	@Override
	public int viewToModel(float x, float y, Shape a, Bias[] biasReturn) {
		return 0;
	}

}