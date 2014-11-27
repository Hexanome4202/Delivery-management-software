package vue;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import libs.ExampleFileFilter;
import modele.DemandeDeLivraison;
import modele.Noeud;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import b4.advancedgui.menu.AccordionItem;
import b4.advancedgui.menu.AccordionMenu;
import controleur.Controleur;


/**
 * 
 */
public class Fenetre extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFileChooser jFileChooserXML;
	private Controleur controleur;
	
	private File fichierPlan;
	private File fichierHoraires;
	
    private AccordionMenu menuHoraires;
    private javax.swing.JPanel horairesPannel;
    /**
     * 
     */
    public Fenetre() {
    	
    	controleur = new Controleur();
    	
    	setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 900);
		
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
		
		JPanel plan = new JPanel();
		plan.setBackground(Color.RED);
		
		JLabel lblNewLabel = new JLabel("Plan");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 24));
		
		JLabel lblNewLabel_1 = new JLabel("Horaires");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 24));		
		
		horairesPannel = new javax.swing.JPanel();
		
		menuHoraires = new AccordionMenu();
		createSampleMenuStructure(menuHoraires);
		menuHoraires.setBackground(Color.white);
		menuHoraires.setForeground(Color.blue.darker().darker().darker());
		menuHoraires.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 15));
		menuHoraires.setSelectionColor(Color.lightGray);
		menuHoraires.setLeafHorizontalAlignment(AccordionItem.LEFT);

		horairesPannel.add(menuHoraires);

		horairesPannel.setBackground(new java.awt.Color(153, 153, 153));
		horairesPannel.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		horairesPannel.setLayout(new javax.swing.BoxLayout(horairesPannel,
				javax.swing.BoxLayout.LINE_AXIS));
		horairesPannel.add(menuHoraires);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
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
														678, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(horairesPannel,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										346, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addContainerGap()));

		pack();
		
		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addContainerGap(850, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(plan, GroupLayout.PREFERRED_SIZE, 540, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(119)
							.addComponent(lblNewLabel_1)
							.addGap(119))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(horairesPannel, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
							.addContainerGap())))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(246)
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.RELATED, 313, Short.MAX_VALUE)
					.addComponent(btnNewButton_2)
					.addGap(53)
					.addComponent(btnNewButton_3)
					.addGap(65))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(32, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(plan, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(horairesPannel, GroupLayout.PREFERRED_SIZE, 414, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNewButton_3)
							.addComponent(btnNewButton_2))
						.addComponent(btnNewButton_1))
					.addContainerGap())
		);
		
		



		getContentPane().setLayout(groupLayout);

	}
	
	
	
	
	/**
     * <code>Second method to create an AccordionMenu: add manually each menu with its leafs to AccordionMenu.</code>
     * It creates manually a structure like one created before with a description String. First method is better when
     * menu structure is static. Use this method instead if you want to create structure dinamically.
     * @param target Target AccordionMenu to modify.
     */
    public void createSampleMenuStructure(AccordionMenu target) {
        target.addNewMenu("menu1", "Menu One");
        target.addNewLeafTo("menu1", "submenu1.1", "Sub Menu 1");
        target.addNewLeafTo("menu1", "submenu1.2", "Sub Menu 2");
        target.addNewLeafTo("menu1", "submenu1.3", "Sub Menu 3");

        target.addNewMenu("menu2", "Menu Two");
        target.addNewLeafTo("menu2", "submenu2.1", "Sub Menu 1");
        target.addNewLeafTo("menu2", "submenu2.2", "Sub Menu 2");
        target.addNewLeafTo("menu2", "submenu2.3", "Sub Menu 3");

        target.addNewMenu("menu3", "Menu Three");
        target.addNewLeafTo("menu3", "submenu3.1", "Sub Menu 1");
        target.addNewLeafTo("menu3", "submenu3.2", "Sub Menu 2");
        target.addNewLeafTo("menu3", "submenu3.3", "Sub Menu 3");

        target.addNewMenu("menu4", "Menu Four");
        target.addNewLeafTo("menu4", "submenu4.1", "Sub Menu 1");
        target.addNewLeafTo("menu4", "submenu4.2", "Sub Menu 2");
        target.addNewLeafTo("menu4", "submenu4.3", "Sub Menu 3");
        target.calculateAvaiableSpace();
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
             try {
                 // creation d'un constructeur de documents a l'aide d'une fabrique
                DocumentBuilder constructeur = DocumentBuilderFactory.newInstance().newDocumentBuilder();	
                // lecture du contenu d'un fichier XML avec DOM
                Document document = constructeur.parse(xml);
                Element racine = document.getDocumentElement();
                if (typeFichier.equals("horaires")) {
					if (racine.getNodeName().equals("JourneeType")) {
						int resultatConstruction = controleur
								.construireLivraisonsAPartirDeDOMXML(racine);
						if (resultatConstruction != Controleur.PARSE_OK) {
							System.out.println("PB de lecture de fichier!");
						}
					}
					// todo : traiter les erreurs
				}else if(typeFichier.equals("plan")){
					if (racine.getNodeName().equals("Reseau")) {
						int resultatConstruction = controleur
								.construirePlanAPartirDeDOMXML(racine);
						if (resultatConstruction != Controleur.PARSE_OK) {
							System.out.println("PB de lecture de fichier!");
						}
					}
				}
            } catch (ParserConfigurationException pce) {
                System.out.println("Erreur de configuration du parseur DOM");
                System.out.println("lors de l'appel a fabrique.newDocumentBuilder();");
            } catch (SAXException se) {
                System.out.println("Erreur lors du parsing du document");
                System.out.println("lors de l'appel a construteur.parse(xml)");
            } catch (IOException ioe) {
                System.out.println("Erreur d'entree/sortie");
                System.out.println("lors de l'appel a construteur.parse(xml)");
            }
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
					Fenetre frame = new Fenetre();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	

}