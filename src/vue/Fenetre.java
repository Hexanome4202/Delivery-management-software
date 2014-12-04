package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;

import libs.ExampleFileFilter;
import modele.DemandeDeLivraison;
import modele.Noeud;
import modele.PlageHoraire;
import modele.Plan;
import modele.Tournee;
import b4.advancedgui.menu.AccordionItem;
import b4.advancedgui.menu.AccordionLeafItem;
import b4.advancedgui.menu.AccordionMenu;

import controleur.Controleur;

/**
 * 
 */
public class Fenetre extends JFrame {
	
	
	private VuePlan vuePlan;

	private static final long serialVersionUID = 1L;

	private Controleur controleur;

	private JButton btnCalculer;
	private JButton btnImprimer;
	private JButton btnAjouter;
	private JButton btnSupprimer;
	private JMenuItem actionAnnuler;
	private JMenuItem actionRetablir;
	private JMenuItem actionChargerHoraires;
	private JMenuItem actionHelp;
	
	private javax.swing.JPanel horairesPannel;
	private AccordionMenu menuHoraires;
	
	private JFileChooser jFileChooserXML;

	private JTextField message;

	private String repertoireActuel;

	/**
     * 
     */
	public Fenetre(Controleur c) {
		this.controleur = c;
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initMenuBar();
		initButtons();

		/*---------------------HORAIRES-----------------------*/
		JLabel horairesLabel = new JLabel("Horaires");
		message = new javax.swing.JTextField();
		message.setEditable(false);
		horairesLabel.setFont(new Font("Arial", Font.BOLD, 24));

		horairesPannel = new javax.swing.JPanel();

		menuHoraires = new AccordionMenu();
		menuHoraires.setBackground(Color.white);
		menuHoraires.setFont(new Font("Arial", Font.PLAIN, 16));
		menuHoraires.setMenusSize(30);
		menuHoraires.setMenuBorders(new BevelBorder(BevelBorder.RAISED));
		menuHoraires.setSelectionColor(Color.lightGray);
		menuHoraires.setLeafHorizontalAlignment(AccordionItem.LEFT);

		horairesPannel.add(menuHoraires);

		horairesPannel.setBackground(new java.awt.Color(186, 186, 186));
		horairesPannel.setBorder(javax.swing.BorderFactory.createEtchedBorder(
				2, Color.black, Color.black));
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
		JLabel planLabel = new JLabel("Plan");
		planLabel.setFont(new Font("Arial", Font.BOLD, 24));
		
		vuePlan = new VuePlan();

		vuePlan.getGraphControl().addMouseListener(new MouseAdapter() {

					public void mouseReleased(MouseEvent e) {
						Noeud n = vuePlan.getNoeudA(e.getX(), e.getY());
						if (n != null) {
							// Si on est dans l'ajout de point de livraison
							if(vuePlan.doitAjouterPoint(n)){
								try {
									int idClient=Integer.parseInt(
											JOptionPane.showInputDialog(Fenetre.this,"Veuillez saisir le numero du client:", null));
									
									if (idClient>=0) {
										controleur.ajouterLivraison(idClient,
												vuePlan.getNoeudAAjouter(), n);
									}else{
										JOptionPane.showMessageDialog(Fenetre.this,"L'identifiant doit être positif!","Erreur",JOptionPane.ERROR_MESSAGE);
									}
								} catch (NumberFormatException e1) {
									JOptionPane.showMessageDialog(Fenetre.this,"Erreur de saisie!","Erreur",JOptionPane.ERROR_MESSAGE);
								}
								
							}else {

								vuePlan.changerPointSelectionne(n);

								btnAjouter.setEnabled(vuePlan.etatBtnAjouter(n));

								btnSupprimer.setEnabled(vuePlan.etatBtnSupprimer(n));

							}
						} else {
							vuePlan.changerPointSelectionne(null);
							btnAjouter.setEnabled(false);
							btnSupprimer.setEnabled(false);
						}
					}
				});
		

		GroupLayout groupLayout = new GroupLayout(getContentPane());

		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(planLabel)
										.addContainerGap())

						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(20)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																vuePlan,
																GroupLayout.DEFAULT_SIZE,
																700,
																GroupLayout.DEFAULT_SIZE)
														.addGap(10)
														.addComponent(
																message,
																GroupLayout.DEFAULT_SIZE,
																700, 1040))

										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(30)
																		.addComponent(
																				horairesLabel)
																		.addGap(30))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				horairesPannel,
																				GroupLayout.DEFAULT_SIZE,
																				200,
																				Short.MAX_VALUE)
																		.addContainerGap())))

						.addGroup(
								Alignment.TRAILING,
								groupLayout
										.createSequentialGroup()
										.addGap(90)
										.addComponent(btnCalculer)
										.addPreferredGap(
												ComponentPlacement.RELATED,
												313, Short.MAX_VALUE)
										.addComponent(btnAjouter)
										.addPreferredGap(
												ComponentPlacement.RELATED, 10,
												Short.MAX_VALUE)
										.addComponent(btnSupprimer)
										.addPreferredGap(
												ComponentPlacement.RELATED,
												450, Short.MAX_VALUE)
										.addComponent(btnImprimer).addGap(40)));

		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap(32, Short.MAX_VALUE)

										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(planLabel)
														.addComponent(
																btnCalculer)
														.addComponent(
																btnAjouter)
														.addComponent(
																btnSupprimer)
														.addComponent(
																horairesLabel))

										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				vuePlan,
																				GroupLayout.DEFAULT_SIZE,
																				600,
																				GroupLayout.DEFAULT_SIZE)
																		)

														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				horairesPannel,
																				GroupLayout.DEFAULT_SIZE,
																				600,
																				GroupLayout.DEFAULT_SIZE)
																		))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																btnImprimer)
														.addComponent(
															message,
															GroupLayout.PREFERRED_SIZE,
															GroupLayout.DEFAULT_SIZE,
															GroupLayout.PREFERRED_SIZE)
														.addGap(20)				
												)
										.addContainerGap()));

		pack();

		getContentPane().setLayout(groupLayout);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width, screenSize.height);

	}

	/*----------------------------------------------------*/
	/*----------------------------------------------------*/

	/**
	 * Methode permettant d'initialiser la barre de menu
	 */
	public void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menuFichier = new JMenu("Fichier");
		JMenuItem actionChargerPlan = new JMenuItem("Charger le plan");
		actionChargerHoraires = new JMenuItem("Charger les horaires");
		actionChargerHoraires.setEnabled(false);
		JMenuItem actionQuitter = new JMenuItem("Quitter");

		JMenu menuEdition = new JMenu("Edition");
		actionAnnuler = new JMenuItem("Annuler");
		actionRetablir = new JMenuItem("Rétablir");
		actionAnnuler.setEnabled(false);
		actionRetablir.setEnabled(false);

		JMenu menuAide = new JMenu("?");
		actionHelp = new JMenuItem("Aide");
		actionHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "";
				try(BufferedReader br = new BufferedReader(new FileReader("ressources/aide.txt"))) {
			        StringBuilder sb = new StringBuilder();
			        String line = br.readLine();

			        while (line != null) {
			            sb.append(line);
			            sb.append(System.lineSeparator());
			            line = br.readLine();
			        }
			        message = sb.toString();
			        JOptionPane.showMessageDialog(null, message, "Aide", JOptionPane.INFORMATION_MESSAGE);
			    } catch (IOException e1) {
				}
			}
		});

		actionChargerPlan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lireDepuisFichierXML("plan");
			}
		});

		actionChargerHoraires.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lireDepuisFichierXML("horaires");
			}
		});

		actionQuitter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int dialogueConfirmation = JOptionPane.showConfirmDialog (null, "Voulez-vous vraiment quitter?","Quitter",JOptionPane.YES_NO_OPTION);
				if(dialogueConfirmation == JOptionPane.YES_OPTION){
					setVisible(false); // you can't see me!
					dispose(); // Destroy the JFrame object
				}
			}
		});

		actionAnnuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controleur.undo();
			}
		});

		actionRetablir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controleur.redo();
			}
		});

		menuFichier.add(actionChargerPlan);
		menuFichier.add(actionChargerHoraires);
		menuFichier.add(actionQuitter);
		menuEdition.add(actionAnnuler);
		menuEdition.add(actionRetablir);
		menuAide.add(actionHelp);

		menuBar.add(menuFichier);
		menuBar.add(menuEdition);
		menuBar.add(menuAide);

		this.setJMenuBar(menuBar);
	}

	/**
	 * Methode permettant d'initialiser les boutons
	 */
	public void initButtons() {
		btnCalculer = new JButton("Calculer");
		btnCalculer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vuePlan.changerPointSelectionne(null);
				controleur.calculerTournee();
			}
		});
		btnCalculer.setEnabled(false);

		btnImprimer = new JButton("Imprimer");
		btnImprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				genererFichierImpression();
			}
		});
		btnImprimer.setEnabled(false);

		btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				vuePlan.setNoeudAAjouter();
				btnAjouter.setEnabled(false);
				message.setText("Veuillez sélectionner le noeud de livraison précédent");
			}

		});
		btnAjouter.setEnabled(false);
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Noeud noeudASupprimer = vuePlan.getNoeudLivraisonSelectionne();
				if (noeudASupprimer != null) {
					controleur.supprimerLivraison(noeudASupprimer);
				}
				btnSupprimer.setEnabled(false);
			}

		});
		btnSupprimer.setEnabled(false);
	}

	/**
	 * Met à jour le menu horaires
	 * @param plagesHoraires
	 */
	public void majMenuHoraire(ArrayList<PlageHoraire> plagesHoraires) {

		horairesPannel.removeAll();
		horairesPannel.revalidate();
		horairesPannel.repaint();

		AccordionMenu menuHorairesMaj;
		menuHorairesMaj = new AccordionMenu();
		menuHorairesMaj.setBackground(Color.white);
		menuHorairesMaj.setFont(new Font("Arial", Font.PLAIN, 16));
		menuHorairesMaj.setMenusSize(30);
		menuHorairesMaj.setMenuBorders(new BevelBorder(BevelBorder.RAISED));
		menuHorairesMaj.setSelectionColor(Color.lightGray);
		menuHorairesMaj.setLeafHorizontalAlignment(AccordionItem.LEFT);

		int iteratorPlage = 1;
		for (PlageHoraire plage : plagesHoraires) {

			SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");

			SimpleDateFormat timeFormatFin = new SimpleDateFormat("H:mm");

			menuHorairesMaj.addNewMenu("menu" + iteratorPlage,
					timeFormat.format(plage.getHeureDebut()) + "-"
							+ timeFormatFin.format(plage.getHeureFin()));

			for (DemandeDeLivraison livraison : plage.getDemandeLivraison()) {
				menuHorairesMaj.addNewLeafTo("menu" + iteratorPlage, ""
						+ livraison.getNoeud().getId(),"Client "+
						String.valueOf(livraison.getIdClient()) +
						" -> Livraison n°" + String.valueOf(livraison.getId()));
			}
			iteratorPlage++;
		}

		MouseAdapter menuMouseAdapter = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				AccordionItem item = (AccordionItem) e.getSource();
				try{
					int idNoeudSelectionne = Integer.parseInt(item.getName());
					vuePlan.changerPointSelectionne(idNoeudSelectionne);
					btnSupprimer.setEnabled(true);
				}catch(Exception exception){
				}

			}
		};

		for (AccordionLeafItem leaf : menuHorairesMaj.getAllLeafs()) {
			leaf.addMouseListener(menuMouseAdapter);
		}

		menuHorairesMaj.calculateAvaiableSpace();
		menuHorairesMaj.repaint();

		horairesPannel.add(menuHorairesMaj);
		horairesPannel.revalidate();
		horairesPannel.repaint();
	}

	/**
	 * Crée le menu contenant les demandes de livraisons classées par Plage Horaire
	 * @param plagesHoraires
	 */
	public void creerMenuHoraires(ArrayList<PlageHoraire> plagesHoraires) {
		int iteratorPlage = 1;
		for (PlageHoraire plage : plagesHoraires) {

			SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");

			SimpleDateFormat timeFormatFin = new SimpleDateFormat("H:mm");

			menuHoraires.addNewMenu("menu" + iteratorPlage,
					timeFormat.format(plage.getHeureDebut()) + "-"
							+ timeFormatFin.format(plage.getHeureFin()));

			for (DemandeDeLivraison livraison : plage.getDemandeLivraison()) {
				menuHoraires.addNewLeafTo("menu" + iteratorPlage, ""
						+ livraison.getNoeud().getId(),
						String.valueOf(livraison.getIdClient()));
			}
			iteratorPlage++;
		}

		MouseAdapter menuMouseAdapter = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				AccordionItem item = (AccordionItem) e.getSource();
				vuePlan.changerPointSelectionne(controleur.getPlan().getNoeud(
						Integer.parseInt(item.getName())));
			}
		};

		for (AccordionLeafItem leaf : menuHoraires.getAllLeafs()) {
			leaf.addMouseListener(menuMouseAdapter);
		}

		menuHoraires.calculateAvaiableSpace();
		menuHoraires.repaint();
	}

	/**
	 * Méthode permettant de dessiner la tournée
	 * @param tournee
	 */
	public void dessinerTournee(Tournee tournee) {
		btnCalculer.setEnabled(false);
		btnImprimer.setEnabled(true);
		vuePlan.setTournee(tournee);
		vuePlan.dessinerTournee();
	}


	/**
	 * Methode permettant de lire un fichier XML pour l'importer
	 * @param typeFichier
	 */
	public void lireDepuisFichierXML(String typeFichier) {
		File xml = ouvrirFichier('o');
		if (xml != null) {
			setMessage("Chargement du fichier en cours...");
			this.controleur.gererFichier(xml, typeFichier);
		}
	}
	
	/**
	 * Méthode permettant d'ouvrir un fichier grâce à la fenêtre dédiée
	 * @param mode
	 * 			lecture ou écriture
	 * @return le fichier ouvert
	 */
	private File ouvrirFichier(char mode) {
		jFileChooserXML = new JFileChooser();
		// Note: source for ExampleFileFilter can be found in FileChooserDemo,
		// under the demo/jfc directory in the JDK.
		ExampleFileFilter filter = new ExampleFileFilter();
		if(mode == 'o') {
			filter.addExtension("xml");
			filter.setDescription("Fichier XML");
		} else if(mode == 'w') {
			filter.addExtension("txt");
			filter.setDescription("Fichier texte");
		}
		jFileChooserXML.setFileFilter(filter);
		jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal;
		if (mode == 'o') {
			if (repertoireActuel != null)
				jFileChooserXML.setCurrentDirectory(new File(repertoireActuel));
			returnVal = jFileChooserXML.showOpenDialog(null);
		} else
			returnVal = jFileChooserXML.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			repertoireActuel = jFileChooserXML.getSelectedFile()
					.getAbsolutePath();
			return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
		}
		return null;
	}

	/**
	 * Affiche le plan à partir des données préalablement chargées depuis un XML
	 * @param noeuds
	 */
	public void afficherPlan(Set<Noeud> noeuds) {
		vuePlan.afficherPlan();
		btnAjouter.setEnabled(false);
		btnSupprimer.setEnabled(false);
		btnCalculer.setEnabled(false);
		btnImprimer.setEnabled(false);
	}
	
	/**
	 * Affiche le plan à partir des données préalablement chargées depuis un XML
	 * @param plan
	 */
	public void afficherPlan(Plan plan){
		vuePlan.setPlan(plan);		
		vuePlan.afficherPlan();
		btnAjouter.setEnabled(false);
		btnSupprimer.setEnabled(false);
		btnCalculer.setEnabled(false);
	}
	
	/**
	 * Méthode permettant d'afficher les demandes de livraison sur le plan
	 * @param tournee
	 */
	public void afficherDemandesLivraisons(Tournee tournee){
		vuePlan.setTournee(tournee);
		vuePlan.afficherDemandesLivraisons(tournee);
	}
	
	/**
	 * Méthode appelant toutes les méthodes permettant de 
	 * redessiner complètement le plan
	 * @param noeuds
	 * @param tournee
	 */
	public void majTotale(Plan plan, Tournee tournee){
		vuePlan.setPlan(plan);
		vuePlan.setTournee(tournee);
		vuePlan.afficherDemandesLivraisons(tournee);
		dessinerTournee(tournee);
		majMenuHoraire(tournee.getPlagesHoraires());
	}
	


	/**
	 * Methode permettant d'activer le bouton Charger les Horaires
	 */
	public void activerChargementHoraires() {
		actionChargerHoraires.setEnabled(true);
	}

	/**
	 * Methode permettant d'activer le bouton Calculer
	 */
	public void activerCalculItineraire() {
		btnCalculer.setEnabled(true);
	}

	/**
	 * Methode permettant d'activer le bouton Ajouter
	 */
	public void activerAjouter() {
		btnAjouter.setEnabled(true);
	}

	/**
	 * Methode permettant de générer le fichier d'impression
	 */
	public void genererFichierImpression() {
		File f = ouvrirFichier('w');
		if(f != null) {
			controleur.genererFichierImpression(f);
		}
	}
	
	/**
	 * Setter de l'attribut <code>Message</code>
	 * @param message Message à afficher
	 */
	public void setMessage(String message){
		this.message.setText(message);
	}
	
	/**
	 * Permet d'activer ou désactiver le bouton Annuler
	 * @param valeur
	 */
	public void setBtnAnnulerEnabled(boolean valeur){
		actionAnnuler.setEnabled(valeur);
	}
	
	/**
	 * Permet d'activer ou désactiver le bouton Annuler
	 * @param valeur
	 */
	public void setBtnRetablirEnabled(boolean valeur){
		actionRetablir.setEnabled(valeur);
	}
	
	/**
	 * Méthode permettant d'afficher une popup d'erreur
	 * @param message
	 * @param titre
	 */
	public void afficherPopupErreur(String message, String titre){
		JOptionPane.showMessageDialog(this, message, titre, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Méthode permettant de prévenir la VuePlan que la tournée n'est plus dessinée
	 */
	public void resetDessinTournee(){
		vuePlan.resetDessinTournee();
		btnAjouter.setEnabled(false);
		btnSupprimer.setEnabled(false);
		btnImprimer.setEnabled(false);
	}
}