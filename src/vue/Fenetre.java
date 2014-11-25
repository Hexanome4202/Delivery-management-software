package vue;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import modele.Client;
import modele.DemandeDeLivraison;
import modele.Noeud;

/**
 * 
 */
public class Fenetre extends JFrame implements Observer {

    /**
     * 
     */
    public Fenetre() {
    }

    /**
     * 
     */
    private static int facteurCoordonnees;

    /**
     * 
     */
    private VueTournee vueTournee;

    /**
     * @param noeud
     */
    public void surbrillanceNoeud(Noeud noeud) {
        // TODO implement here
    }

    /**
     * @param client
     */
    public void afficherListeClients(List<Client> client) {
        // TODO implement here
    }

    /**
     * 
     */
    public void dessinerTournee() {
        // TODO implement here
    }

    /**
     * @param demandes
     */
    public void creerVuesDemandeDeLivraison(List<DemandeDeLivraison> demandes) {
        // TODO implement here
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}