package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
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
import modele.Itineraire;
import modele.Noeud;
import modele.PlageHoraire;
import modele.Plan;
import modele.Tournee;
import modele.Troncon;
import b4.advancedgui.menu.AccordionItem;
import b4.advancedgui.menu.AccordionLeafItem;
import b4.advancedgui.menu.AccordionMenu;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import controleur.Controleur;

/**
 * 
 */
public class Fenetre extends JFrame implements Observer {

	/**
	 * Facteur permettant de faire la conversion entre les coordonnées réelles
	 * et les coordonnées d'affichage
	 */
	private static int facteurCoordonnees;

	private VueTournee vueTournee;

	private static final long serialVersionUID = 1L;

	private JFileChooser jFileChooserXML;
	private Controleur controleur;

	private AccordionMenu menuHoraires;
	private javax.swing.JPanel horairesPannel;
	private mxGraph plan;
	private mxGraphComponent planComponent;

	private JButton btnCalculer;
	private JButton btnImprimer;
	private JButton btnAjouter;
	private JButton btnSupprimer;
	JMenuItem actionAnnuler;
	JMenuItem actionRetablir;

	private JMenuItem actionChargerHoraires;
	private JTextField message;

	private static final double RAYON_NOEUD = 10;
	private static final int TOLERANCE = 10;

	/**
	 * L'id des noeuds à livrer associé au numéro de plage horaire
	 */
	private HashMap<Integer, Integer> noeudsALivrer;

	/**
	 * L'id des demandes de livraison qui ne pouront pas être livrés dans la
	 * plage horaire demandée.
	 */
	private Set<Integer> demandesTempsDepasse;

	private final String[] couleurRemplissage = { "#a7a7a7", "#4407a6",
			"#07a60f", "#ff7300", "#84088c", "#08788c", "#792f2f" };
	private final String[] couleurBordure = { "#838383", "#2d0968", "#0d7412",
			"#b3560b", "#511155", "#0f5f6d", "#522828" };

	/**
	 * Facteurs de mise à l'échelle pour l'affichage sur le plan
	 */
	private double hY;
	private double hX;

	/**
	 * La liste des points affichés sur le plan
	 */
	private HashMap<Integer, Object> points;
	
	Set<Noeud> tousNoeuds = new HashSet<Noeud>();

	/**
	 * Le point actuellement selectionné
	 */
	private Noeud pointSelectionne;
	private Noeud entrepot;
	private Noeud noeudAAjouter = null;

	private boolean tourneeDessinee = false;

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
		//creerMenuHoraires();

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
		plan = new mxGraph();
		planComponent = new mxGraphComponent(plan);

		plan.setAllowDanglingEdges(false);
		plan.setCellsBendable(false);
		plan.setCellsDisconnectable(false);
		plan.setCellsMovable(false);
		plan.setCellsResizable(false);
		plan.setCellsEditable(false);
		planComponent.setConnectable(false);

		planComponent.getGraphControl().addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				Noeud n = getNoeudA(e.getX(), e.getY());
				if (n != null) {
					// Si on est dans l'ajout de point de livraison
					if (noeudAAjouter != null) {
						if (noeudsALivrer.containsKey(n.getId())) {
							controleur.ajouterLivraison(0, noeudAAjouter, n);
							noeudAAjouter = null;
						}
					} else {

						changerPointSelectionne(n);
						if (!tourneeDessinee || n == entrepot
								|| noeudsALivrer.containsKey(n.getId())) {
							btnAjouter.setEnabled(false);
						} else if (entrepot != null && n != entrepot
								&& !noeudsALivrer.containsKey(n.getId())) {
							btnAjouter.setEnabled(true);
						}

						btnSupprimer.setEnabled(tourneeDessinee
								&& noeudsALivrer.containsKey(n.getId()));

					}
				} else {
					changerPointSelectionne(null);
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
																planComponent,
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
																				planComponent,
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

		JMenu menuAide = new JMenu("Aide");

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
				setVisible(false); // you can't see me!
				dispose(); // Destroy the JFrame object
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
				noeudAAjouter = pointSelectionne;
				btnAjouter.setEnabled(false);
				message.setText("Veuillez sélectionner le noeud de livraison précédent");
			}

		});
		btnAjouter.setEnabled(false);
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (noeudsALivrer.containsKey(pointSelectionne.getId())) {
					controleur.supprimerLivraison(pointSelectionne);
				} else {
					// TODO remplacer ça par un affichage graphique
					System.out.println("Rien à supprimer");
				}
				btnSupprimer.setEnabled(false);
			}

		});
		btnSupprimer.setEnabled(false);
	}

	/**
	 * Met à jour le menu horaires
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
						+ livraison.getNoeud().getId(),
						String.valueOf(livraison.getIdClient()));
			}
			iteratorPlage++;
		}

		MouseAdapter menuMouseAdapter = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				AccordionItem item = (AccordionItem) e.getSource();
				changerPointSelectionne(controleur.getPlan().getNoeud(
						Integer.parseInt(item.getName())));
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
	 * Crée le menu contenant les demandes de livraisons classées par Plage
	 * Horaire
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
				changerPointSelectionne(controleur.getPlan().getNoeud(
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
	 * Méthode permettant de dessiner la tournée
	 */
	public void dessinerTournee(Tournee tournee) {
		noeudAAjouter = null;
		pointSelectionne = null;
		btnCalculer.setEnabled(false);
		
		List<DemandeDeLivraison> demandesTempsDepasse = tournee.getDemandesTempsDepasse();

		Object parent = plan.getDefaultParent();

		int noeudPrecedent = entrepot.getId();

		ArrayList<Itineraire> itineraires = tournee.getItineraires();
		Iterator<Itineraire> it = itineraires.iterator();

		HashMap<String, Integer> noeudsTraverses = new HashMap<String, Integer>();

		afficherDemandesTempsDepasse(demandesTempsDepasse);

		while (it.hasNext()) {
			Itineraire itineraire = it.next();
			int idHoraire = 1;
			try {
				idHoraire = noeudsALivrer.get(itineraire.getDepart().getNoeud()
						.getId());
			} catch (Exception e) {
			}
			String color = couleurBordure[idHoraire];

			Iterator<Troncon> troncons = itineraire.getTronconsItineraire()
					.iterator();
			while (troncons.hasNext()) {
				Troncon troncon = troncons.next();
				String key = ""
						+ Math.max(noeudPrecedent, troncon.getNoeudFin()
								.getId())
						+ "-"
						+ Math.min(troncon.getNoeudFin().getId(),
								noeudPrecedent);

				String edgeStyle = (noeudsTraverses.containsKey(key)) ? "edgeStyle=elbowEdgeStyle;elbow=horizontal;"
						+ "exitX=0.5;exitY=1;exitPerimeter=1;entryX=0;entryY=0;entryPerimeter=1;"
						+ mxConstants.STYLE_ROUNDED + "=1;"
						: "";

				plan.insertEdge(parent, null, "", points.get(noeudPrecedent),
						points.get(troncon.getNoeudFin().getId()), edgeStyle
								+ "strokeWidth=2;strokeColor=" + color);

				if (noeudsTraverses.containsKey(key)) {
					noeudsTraverses.put(key, noeudsTraverses.get(key) + 1);
				} else {
					noeudsTraverses.put(key, 1);
				}

				noeudPrecedent = troncon.getNoeudFin().getId();
			}
			tourneeDessinee = true;
		}

		btnImprimer.setEnabled(true);
	}

	/**
	 * Méthode permettant de mettre en évidence sur l'affichage les points de
	 * livraisons qui ne pourront pas être livrés dans la plage horaire demandée
	 */
	private void afficherDemandesTempsDepasse(List<DemandeDeLivraison> demandesTempsDepasse) {
		if (this.demandesTempsDepasse == null) {
			this.demandesTempsDepasse = new HashSet<Integer>();
			Iterator<DemandeDeLivraison> it = demandesTempsDepasse.iterator();
			while (it.hasNext()) {
				Noeud noeud = it.next().getNoeud();
				this.demandesTempsDepasse.add(noeud.getId());
			}
		}

		for (Integer idNoeud : this.demandesTempsDepasse) {
			int numPlage = noeudsALivrer.get(idNoeud);
			Object[] cells = { points.get(idNoeud) };
			plan.setCellStyle(
					"shape=triangle;strokeWidth=2;fillColor=red;strokeColor="
							+ couleurBordure[numPlage], cells);
		}
	}

	/**
	 * @param demandes
	 */
	public void creerVuesDemandeDeLivraison(List<DemandeDeLivraison> demandes) {
		// TODO implement here
	}

	public void lireDepuisFichierXML(String typeFichier) {
		File xml = ouvrirFichier('o');
		if (xml != null) {
			setMessage("Chargement du fichier en cours...");
			this.controleur.gererFichier(xml, typeFichier);
		}
	}

	private File ouvrirFichier(char mode) {
		jFileChooserXML = new JFileChooser();
		// Note: source for ExampleFileFilter can be found in FileChooserDemo,
		// under the demo/jfc directory in the JDK.
		ExampleFileFilter filter = new ExampleFileFilter();
		filter.addExtension("xml");
		filter.setDescription("Fichier XML");
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

	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * Affiche le plan à partir des données préalablement chargées depuis un XML
	 */
	public void afficherPlan(Set<Noeud> noeuds) {
		tousNoeuds = noeuds;
		
		demandesTempsDepasse = new HashSet<Integer>();
		noeudAAjouter = null;
		pointSelectionne = null;
		tourneeDessinee = false;

		points = new HashMap<Integer, Object>();
		Iterator<Noeud> it = noeuds.iterator();

		Object parent = plan.getDefaultParent();
		plan.getModel().beginUpdate();
		plan.removeCells(plan.getChildCells(parent));

		// On commence par placer les points
		while (it.hasNext()) {
			Noeud noeudCourant = it.next();
			double x = noeudCourant.getX();
			double y = noeudCourant.getY();
			points.put(noeudCourant.getId(), plan.insertVertex(parent, "", "",
					hX * x, hY * y, RAYON_NOEUD, RAYON_NOEUD, "fillColor="
							+ couleurRemplissage[0] + ";strokeColor="
							+ couleurBordure[0]));
		}

		// Puis on trace les tronçons
		it = noeuds.iterator();
		while (it.hasNext()) {
			Noeud noeudCourant = it.next();

			Iterator<Troncon> itTroncons = noeudCourant.getTronconSortants()
					.iterator();
			while (itTroncons.hasNext()) {
				plan.insertEdge(parent, null, "",
						points.get(noeudCourant.getId()),
						points.get(itTroncons.next().getNoeudFin().getId()),
						"strokeColor=" + couleurRemplissage[0]);
			}

		}

		plan.getModel().endUpdate();

	}
	
	public void afficherPlan(Plan plan){
		// facteur de mise à l'échelle
		hY = (planComponent.getSize().getHeight() - 20)
				/ plan.getMaxY();
		hX = (planComponent.getSize().getWidth() - 20)
				/ plan.getMaxY();
		
		afficherPlan(plan.getToutNoeuds());
	}
	
	

	/**
	 * Méthode renvoyant le noeud aux coordonnées passées en paramètre
	 * 
	 * @param x
	 *            abscisse du point à tester
	 * @param y
	 *            ordonnée du point à tester
	 * @return le noeud à ces coordonnées s'il existe
	 */
	private Noeud getNoeudA(int x, int y) {
		if (points == null)
			return null;
		else {
			Iterator<Noeud> it = tousNoeuds.iterator();
			while (it.hasNext()) {
				Noeud n = it.next();
				double nX = n.getX() * hX;
				double nY = n.getY() * hY;
				if ((x > nX - TOLERANCE && x < nX + TOLERANCE)
						&& (y > nY - TOLERANCE && y < nY + TOLERANCE)) {
					return n;
				}
			}
		}
		return null;

	}

	/**
	 * Change le point selectionné sur l'affichage : Déselectionne le point qui
	 * était selectionné jusque là, et sélectionne le nouveau
	 * 
	 * @param nouvelleSelection
	 */
	private void changerPointSelectionne(Noeud nouvelleSelection) {

		// On commence par déselectionner l'ancienne sélection
		if (pointSelectionne != null) {
			int idCouleur = (noeudsALivrer != null
					&& noeudsALivrer.containsKey(pointSelectionne.getId()) ? noeudsALivrer
					.get(pointSelectionne.getId()) : 0);
			Object[] cells = { points.get(pointSelectionne.getId()) };
			plan.setCellStyle("fillColor=" + couleurRemplissage[idCouleur]
					+ ";strokeColor=" + couleurBordure[idCouleur], cells);
		}
		pointSelectionne = nouvelleSelection;

		if (pointSelectionne != null) {
			int idCouleur = (noeudsALivrer != null
					&& noeudsALivrer.containsKey(nouvelleSelection.getId()) ? noeudsALivrer
					.get(nouvelleSelection.getId()) : 0);
			Object[] cells = { points.get(pointSelectionne.getId()) };
			plan.setCellStyle("strokeColor=red;strokeWidth=3;fillColor="
					+ couleurRemplissage[idCouleur], cells);
		}
	}

	/**
	 * Méthode permettant d'afficher d'une couleur différente les demandes de
	 * livraison sur le plan
	 */
	public void afficherDemandesLivraisonsSurPlan(Tournee tournee) {
		noeudsALivrer = new HashMap<Integer, Integer>();

		// On réaffiche le plan proprement, sans point de livraison
		afficherPlan(tousNoeuds);

		if (tournee.getEntrepot() != null) {
			entrepot = tournee.getEntrepot().getNoeud();

			plan.insertVertex(plan.getDefaultParent(), "", "",
					hX * entrepot.getX(), hY * entrepot.getY(),
					RAYON_NOEUD + 6, RAYON_NOEUD + 6,
					"shape=ellipse;perimeter=30;strokeColor=black;strokeWidth=3;fillColor=yellow");
		}

		int numPlage = 1;
		for (PlageHoraire plage : tournee.getPlagesHoraires()) {

			for (DemandeDeLivraison livraison : plage.getDemandeLivraison()) {
				int noeud = livraison.getNoeud().getId();
				if(points.containsKey(noeud)){
					Object[] cells = { points.get(noeud) };
					plan.setCellStyle("fillColor=" + couleurRemplissage[numPlage]
							+ ";strokeColor=" + couleurBordure[numPlage], cells);
					noeudsALivrer.put(noeud, numPlage);
				}
			}
			numPlage++;
		}
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
		controleur.genererFichierImpression(f);
	}
	
	public void setMessage(String message){
		this.message.setText(message);
		System.out.println(message);
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
	
	public void afficherPopupErreur(String message, String title){
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Méthode appelant toutes les méthodes permettant de 
	 * redessiner complètement le plan
	 */
	public void majTotale(Set<Noeud> noeuds, Tournee tournee){
		afficherPlan(noeuds);
		afficherDemandesLivraisonsSurPlan(tournee);
		dessinerTournee(tournee);
		majMenuHoraire(tournee.getPlagesHoraires());
	}


}