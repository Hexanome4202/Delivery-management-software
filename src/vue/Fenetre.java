package vue;

import java.util.List;

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

}