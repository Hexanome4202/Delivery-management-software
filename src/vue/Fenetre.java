package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;

import libs.ExampleFileFilter;
import modele.DemandeDeLivraison;
import modele.Noeud;
import b4.advancedgui.menu.AccordionItem;
import b4.advancedgui.menu.AccordionMenu;
import controleur.Controleur;


/**
 * 
 */
public class Fenetre extends JFrame implements Observer {

	/**
	   * Facteur permettant de faire la conversion entre les coordonnées 
     * réelles et les coordonnées d'affichage
     */
    private static int facteurCoordonnees;

    /**
     * 
     */
    private VueTournee vueTournee;
	
	/**
	*
	*/

	private static final long serialVersionUID = 1L;
	private JFileChooser jFileChooserXML;
	private Controleur controleur;
	
	
    private AccordionMenu menuHoraires;
    private javax.swing.JPanel horairesPannel;
    /**
     * 
     */
    public Fenetre(Controleur c) {
    	
    	controleur = c;
    	
    	setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*----------------------------------------------------*/
		/*--------------------- MENU BAR ---------------------*/
		/*----------------------------------------------------*/
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuFichier = new JMenu("Fichier");
		JMenuItem actionQuitter = new JMenuItem("Quitter");
		JMenu menuEdition = new JMenu("Edition");
		JMenuItem actionAnnuler = new JMenuItem("Annuler");
		JMenuItem actionRetablir = new JMenuItem("Rétablir");
		
		
		
		actionQuitter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false); //you can't see me!
				dispose(); //Destroy the JFrame object
			}
		});
		
		JMenuItem actionChargerPlan = new JMenuItem("Charger le plan");
		JMenuItem actionChargerHoraires = new JMenuItem("Charger les horaires");
		
		
		actionChargerPlan.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				lireDepuisFichierXML("plan");
				//TODO utiliser les méthodes de Felipe et Justine pour lire le xml
			}
			
		});
		
		actionChargerHoraires.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				lireDepuisFichierXML("horaires");
				//TODO utiliser les méthodes de Felipe et Justine pour lire le xml
			}
			
		});
		
		menuFichier.add(actionChargerPlan);
		menuFichier.add(actionChargerHoraires);
		menuFichier.add(actionQuitter);
		menuEdition.add(actionAnnuler);
		menuEdition.add(actionRetablir);
		
		JMenu menuAide = new JMenu("Aide");
		
		menuBar.add(menuFichier);
		menuBar.add(menuEdition);
		menuBar.add(menuAide);
		
		this.setJMenuBar(menuBar);
		
		JButton btnNewButton_1 = new JButton("Calculer");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton btnNewButton_2 = new JButton("Modifier");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton btnNewButton_3 = new JButton("Imprimer");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		/*----------------------------------------------------*/
		/*----------------------------------------------------*/
		
		
		
		
		JPanel plan = new JPanel();
		plan.setBackground(Color.RED);
		
		
		JLabel planLabel = new JLabel("Plan");
		planLabel.setFont(new Font("Arial", Font.BOLD, 24));
		
		
		/*---------------------HORAIRES-----------------------*/
		JLabel horairesLabel = new JLabel("Horaires");
		horairesLabel.setFont(new Font("Arial", Font.BOLD, 24));		
		
		horairesPannel = new javax.swing.JPanel();
		
		menuHoraires = new AccordionMenu();
		createSampleMenuStructure(menuHoraires);
		menuHoraires.setBackground(Color.white);
		menuHoraires.setFont(new Font("Arial", Font.PLAIN, 16));
		menuHoraires.setMenusSize(30);
		menuHoraires.setMenuBorders(new BevelBorder(BevelBorder.RAISED));
		menuHoraires.setSelectionColor(Color.lightGray);
		menuHoraires.setLeafHorizontalAlignment(AccordionItem.LEFT);

		horairesPannel.add(menuHoraires);

		horairesPannel.setBackground(new java.awt.Color(186, 186, 186));
		horairesPannel.setBorder(javax.swing.BorderFactory
				.createEtchedBorder(2, Color.black, Color.black));
				//.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		horairesPannel.setLayout(new javax.swing.BoxLayout(horairesPannel,
				javax.swing.BoxLayout.LINE_AXIS));
		horairesPannel.add(menuHoraires);
	

		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		
		//ContainerGap ajoute une marge
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														horairesPannel,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														150, Short.MAX_VALUE))
								.addContainerGap()));
		
		
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(horairesPannel,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										800, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addContainerGap()));

		pack();
		/*----------------------------------------------------*/
		/*----------------------------------------------------*/
		
	
		/*---------------------PLAN------------------------------*/
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(planLabel)
					.addContainerGap())//100, Short.MAX_VALUE))
					
					// addComponent => changer taille plan au niveau de la largeur(700)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(plan, GroupLayout.DEFAULT_SIZE, 700, GroupLayout.DEFAULT_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							//Placement du titre Horaires => 20x20
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(30)//119)
							.addComponent(horairesLabel)
							.addGap(30))//119))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							//addComponent => changer taille horaires au niveau de la largeur (200)
							.addComponent(horairesPannel, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
							.addContainerGap())))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(90)
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.RELATED, 400, Short.MAX_VALUE)
					.addComponent(btnNewButton_2)
					.addGap(5)
					.addComponent(btnNewButton_3)
					.addGap(80))
		);
		
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(32, Short.MAX_VALUE)
					
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(planLabel)
						.addComponent(btnNewButton_1)
						.addComponent(horairesLabel))
					
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						//addComponent => CA NE CHANGE PAS la taille du plan au niveau de la longueur (500)
						.addComponent(plan, GroupLayout.DEFAULT_SIZE, 600, GroupLayout.DEFAULT_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							//on peut changer la place du label Horaires
							//addComponent => change taille horaires au niveau de la longueur (650) et le plan !!!!
							.addComponent(horairesPannel, GroupLayout.DEFAULT_SIZE, 650, GroupLayout.DEFAULT_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNewButton_3)
							.addComponent(btnNewButton_2))
						)
					.addContainerGap())
		);
		
		
		pack();


		getContentPane().setLayout(groupLayout);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds (0, 0,screenSize.width,screenSize.height);
	}
	
	
	
	
	/**
     * <code>Second method to create an AccordionMenu: add manually each menu with its leafs to AccordionMenu.</code>
     * It creates manually a structure like one created before with a description String. First method is better when
     * menu structure is static. Use this method instead if you want to create structure dinamically.
     * @param target Target AccordionMenu to modify.
     */
    public void createSampleMenuStructure(AccordionMenu target) {
        target.addNewMenu("menu1", "8h - 9h30");
        target.addNewLeafTo("menu1", "submenu1.1", "Madame Fitzgerald ...");
        target.addNewLeafTo("menu1", "submenu1.2", "Monsieur Omard ...");
        target.addNewLeafTo("menu1", "submenu1.3", "Mademoiselle Martine : 11 rue de ....");

        target.addNewMenu("menu2", "9h30 - 11h");
        target.addNewLeafTo("menu2", "submenu2.1", "Madame Fitzgerald ...");
        target.addNewLeafTo("menu2", "submenu2.2", "Monsieur Omard ...");
        target.addNewLeafTo("menu2", "submenu2.3", "Mademoiselle Martine : 11 rue de ....");

        target.addNewMenu("menu3", "11h - 12h30");
        target.addNewLeafTo("menu3", "submenu3.1", "Madame Fitzgerald ...");
        target.addNewLeafTo("menu3", "submenu3.2", "Monsieur Omard ...");
        target.addNewLeafTo("menu3", "submenu3.3", "Mademoiselle Martine : 11 rue de ....");

        target.addNewMenu("menu4", "14h - 15h30");
        target.addNewLeafTo("menu4", "submenu4.1", "Madame Fitzgerald ...");
        target.addNewLeafTo("menu4", "submenu4.2", "Monsieur Omard ...");
        target.addNewLeafTo("menu4", "submenu4.3", "Mademoiselle Martine : 11 rue de ....");
        target.calculateAvaiableSpace();
    }

   

    /**
     * @param noeud
     */
    public void surbrillanceNoeud(Noeud noeud) {
        // TODO implement here
    }

    /**
     * @param client
     */
    public void afficherListeClients(List<Integer> client) {
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

	
	
	public void lireDepuisFichierXML(String typeFichier){
        File xml = ouvrirFichier('o');
        if (xml != null) {
             this.controleur.gererFichier(xml, typeFichier);
        }  
	}

	private File ouvrirFichier(char mode){
        jFileChooserXML = new JFileChooser();
        // Note: source for ExampleFileFilter can be found in FileChooserDemo,
        // under the demo/jfc directory in the JDK.
        ExampleFileFilter filter = new ExampleFileFilter();
        filter.addExtension("xml");
        filter.setDescription("Fichier XML");
        jFileChooserXML.setFileFilter(filter);
        jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal;
        if (mode == 'o')
        	returnVal = jFileChooserXML.showOpenDialog(null);
        else
        	returnVal = jFileChooserXML.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) 
                return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
        return null;
	}

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fenetre frame = new Fenetre(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	

}