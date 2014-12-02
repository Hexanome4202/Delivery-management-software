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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;

import libs.ExampleFileFilter;
import modele.DemandeDeLivraison;
import modele.Itineraire;
import modele.Noeud;
import modele.PlageHoraire;
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
	
	private String repertoireActuel;
	
    private AccordionMenu menuHoraires;
    private javax.swing.JPanel horairesPannel;
    private mxGraph plan;
    private mxGraphComponent planComponent;
    
    private JButton btnCalculer;
	private JButton btnModifier;
	private JButton btnImprimer;
	private JButton btnAjouter;
	private JButton btnSupprimer;
	JMenuItem actionChargerHoraires;
    
    private static final double RAYON_NOEUD = 10;
    private static final int TOLERANCE = 10;
    
    /**
     * L'id des noeuds à livrer associé au numéro de plage horaire
     */
    private HashMap<Integer, Integer> noeudsALivrer;
    
    String[] couleurRemplissage = {"#a7a7a7", "#4407a6", "#07a60f", "#ff7300", "#84088c", "#08788c", "#792f2f"};
    String[] couleurBordure = {"#838383", "#2d0968", "#0d7412", "#b3560b", "#511155", "#0f5f6d", "#522828"};
    
    /**
     * Facteurs de mise à l'échelle pour l'affichage sur le plan
     */
    private double hY;
    private double hX;
    
    /**
     * La liste des points affichés sur le plan
     */
    private HashMap<Integer, Object> points;
    
    /**
     * Le point actuellement selectionné
     */
    private Noeud pointSelectionne;
    
    private Noeud entrepot;
    private Noeud noeudAAjouter = null;
    
    /**
     * 
     */
    public Fenetre(Controleur c) {
    	this.controleur = c;
    	
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
		actionChargerHoraires = new JMenuItem("Charger les horaires");
		actionChargerHoraires.setEnabled(false);
		
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
		
		
		actionChargerPlan.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				lireDepuisFichierXML("plan");
			}
			
		});
		
		actionChargerHoraires.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				lireDepuisFichierXML("horaires");
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
		
		btnCalculer = new JButton("Calculer");
		btnCalculer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controleur.calculerTournee();
			}
		});
		btnCalculer.setEnabled(false);
		
		btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnModifier.setEnabled(false);

		btnImprimer = new JButton("Imprimer");
		btnImprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnImprimer.setEnabled(false);
		
		btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				noeudAAjouter = pointSelectionne;
				btnAjouter.setEnabled(false);
			}
			
		});
		btnAjouter.setEnabled(false);
		
		btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(noeudsALivrer.containsKey(pointSelectionne.getId())){
					controleur.supprimerLivraison(pointSelectionne);
				}else{
					//TODO remplacer ça par un affichage graphique
					System.out.println("Rien à supprimer");
				}
				btnSupprimer.setEnabled(false);
			}
			
		});
		btnSupprimer.setEnabled(false);
		
		/*----------------------------------------------------*/
		/*----------------------------------------------------*/
		
		
		
		JLabel planLabel = new JLabel("Plan");
		planLabel.setFont(new Font("Arial", Font.BOLD, 24));
		
		
		/*---------------------HORAIRES-----------------------*/
		JLabel horairesLabel = new JLabel("Horaires");
		horairesLabel.setFont(new Font("Arial", Font.BOLD, 24));		
		
		horairesPannel = new javax.swing.JPanel();
		
		menuHoraires = new AccordionMenu();
		menuHoraires.setBackground(Color.white);
		menuHoraires.setFont(new Font("Arial", Font.PLAIN, 16));
		menuHoraires.setMenusSize(30);
		menuHoraires.setMenuBorders(new BevelBorder(BevelBorder.RAISED));
		menuHoraires.setSelectionColor(Color.lightGray);
		menuHoraires.setLeafHorizontalAlignment(AccordionItem.LEFT);
		creerMenuHoraires();


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
		plan = new mxGraph();
		planComponent = new mxGraphComponent(plan);	

		plan.setAllowDanglingEdges(false);
		plan.setCellsBendable(false);
		plan.setCellsDisconnectable(false);
		plan.setCellsMovable(false);
		plan.setCellsResizable(false);
		plan.setCellsEditable(false);
		planComponent.setConnectable(false);
		
		planComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
				Noeud n = getNoeudA(e.getX(), e.getY());
				if(n != null){
					
					if(noeudAAjouter != null){
						if(noeudsALivrer.containsKey(n.getId())){
							controleur.ajouterLivraison(0, noeudAAjouter, n);
							noeudAAjouter = null;
						}
					}else{
					
						changerPointSelectionne(n);
						if(n==entrepot || noeudsALivrer.containsKey(n.getId())){
							btnAjouter.setEnabled(false);
						}else if(entrepot != null && n != entrepot && !noeudsALivrer.containsKey(n.getId())){
							btnAjouter.setEnabled(true);
						}
						
						btnSupprimer.setEnabled(noeudsALivrer.containsKey(n.getId()));

					}
				}
			}
		});
		
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
					.addComponent(planComponent, GroupLayout.DEFAULT_SIZE, 700, GroupLayout.DEFAULT_SIZE)
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
					.addComponent(btnCalculer)
					.addPreferredGap(ComponentPlacement.RELATED, 313, Short.MAX_VALUE)
					.addComponent(btnAjouter)
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(btnSupprimer)
					.addPreferredGap(ComponentPlacement.RELATED, 450, Short.MAX_VALUE)
					.addComponent(btnModifier)
					.addGap(5)
					.addComponent(btnImprimer)
					.addGap(80))
		);
		
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(32, Short.MAX_VALUE)
					
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(planLabel)
						.addComponent(btnCalculer)
						.addComponent(btnAjouter)
						.addComponent(btnSupprimer)
						.addComponent(horairesLabel))
					
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						//addComponent => CA NE CHANGE PAS la taille du plan au niveau de la longueur (500)
						.addComponent(planComponent, GroupLayout.DEFAULT_SIZE, 600, GroupLayout.DEFAULT_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							//on peut changer la place du label Horaires
							//addComponent => change taille horaires au niveau de la longueur (650) et le plan !!!!
							.addComponent(horairesPannel, GroupLayout.DEFAULT_SIZE, 650, GroupLayout.DEFAULT_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnImprimer)
							.addComponent(btnModifier))
						)
					.addContainerGap())
		);
		
		
		
		pack();


		getContentPane().setLayout(groupLayout);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds (0, 0,screenSize.width,screenSize.height);

	}
	
	/**
	 * Met à jour le menu horaires
	 */
	public void majMenuHoraire(){
		//TODO : Cécilia - trouver un moyen de mettre à jour le menu horaires
	}
	
    /**
     * Crée le menu contenant les demandes de livraisons classées par Plage Horaire
     */
	public void creerMenuHoraires() {
		int iteratorPlage = 1;
		for (PlageHoraire plage : controleur.getTournee().getPlagesHoraires()) {

			SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");

			SimpleDateFormat timeFormatFin = new SimpleDateFormat("H:mm");

			menuHoraires.addNewMenu("menu" + iteratorPlage,
					timeFormat.format(plage.getHeureDebut()) + "-"
							+ timeFormatFin.format(plage.getHeureFin()));

			int iteratorLiv = 1;
			for (DemandeDeLivraison livraison : plage.getDemandeLivraison()) {
				menuHoraires.addNewLeafTo("menu" + iteratorPlage, ""
						+ livraison.getNoeud().getId(),
						String.valueOf(livraison.getIdClient()));
				iteratorLiv++;
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
	public void dessinerTournee() {
		Tournee tournee = controleur.getTournee();
		Object parent = plan.getDefaultParent();
		
		entrepot = tournee.getEntrepot().getNoeud();
		
		int noeudPrecedent = entrepot.getId();		

		plan.insertVertex(parent, "", "", hX*entrepot.getX(), hY*entrepot.getY(), RAYON_NOEUD+6, RAYON_NOEUD+6, 
				"shape=ellipse;perimeter=30;strokeColor=black;strokeWidth=3;fillColor=yellow");

		ArrayList<Itineraire> itineraires = tournee.getItineraires();
		Iterator<Itineraire> it = itineraires.iterator();
		
		HashMap<String, Integer> noeudsTraverses = new HashMap<String, Integer>(); 

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
				String key = ""+Math.max(noeudPrecedent,troncon.getNoeudFin().getId())+"-"+ Math.min(troncon.getNoeudFin().getId(), noeudPrecedent);
				
				String edgeStyle = (noeudsTraverses.containsKey(key)) ? 
						"edgeStyle=elbowEdgeStyle;elbow=horizontal;"
						+ "exitX=0.5;exitY=1;exitPerimeter=1;entryX=0;entryY=0;entryPerimeter=1;"+mxConstants.STYLE_ROUNDED+"=1;" : "";

				plan.insertEdge(parent, null, "", 
						points.get(noeudPrecedent),
						points.get(troncon.getNoeudFin().getId()),
						edgeStyle +
						"strokeWidth=2;strokeColor=" + color);
				
				
				
				if(noeudsTraverses.containsKey(key)){
					noeudsTraverses.put(key, noeudsTraverses.get(key)+1);
					System.out.println(key+" : "+noeudsTraverses.get(key)+" fois");
				}else{
					noeudsTraverses.put(key, 1);
				}
				
				noeudPrecedent = troncon.getNoeudFin().getId();
			}
		}
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
        if (mode == 'o'){
        	if(repertoireActuel!=null)
        		jFileChooserXML.setCurrentDirectory(new File(repertoireActuel));
        	returnVal = jFileChooserXML.showOpenDialog(null);
        }else{
        	returnVal = jFileChooserXML.showSaveDialog(null);
        }
        if (returnVal == JFileChooser.APPROVE_OPTION){
        		repertoireActuel=jFileChooserXML.getSelectedFile().getAbsolutePath();
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
	public void afficherPlan(){

		
		Set<Noeud> noeuds = controleur.getPlan().getToutNoeuds();
		points= new HashMap<Integer, Object>();
		Iterator<Noeud> it = noeuds.iterator();
		
		Object parent = plan.getDefaultParent();
		plan.getModel().beginUpdate();
		plan.removeCells(plan.getChildCells(parent));
		
		//facteur de mise à l'échelle
		hY = (planComponent.getSize().getHeight()-20)/controleur.getPlan().getMaxY();
		hX = (planComponent.getSize().getWidth()-20)/controleur.getPlan().getMaxX();
		
		//On commence par placer les points
		while (it.hasNext()) {
			Noeud noeudCourant = it.next();
			double x = noeudCourant.getX();
			double y = noeudCourant.getY();
			points.put(noeudCourant.getId(), plan.insertVertex(parent, "", "", hX*x, hY*y, RAYON_NOEUD, RAYON_NOEUD, 
					"fillColor="+couleurRemplissage[0]+";strokeColor="+couleurBordure[0]));
		}
		
		//Puis on trace les tronçons
		it = noeuds.iterator();
		while (it.hasNext()) {
			Noeud noeudCourant = it.next();	
			
			Iterator<Troncon> itTroncons = noeudCourant.getTronconSortants().iterator(); 
			while(itTroncons.hasNext()){
				plan.insertEdge(parent, null, "", 
						points.get(noeudCourant.getId()), 
						points.get(itTroncons.next().getNoeudFin().getId()),
						"strokeColor="+couleurRemplissage[0]);
			}
			
		}
		
		plan.getModel().endUpdate();
		

	}
	
	/**
	 * Méthode renvoyant le noeud aux coordonnées passées en paramètre
	 * @param x abscisse du point à tester
	 * @param y ordonnée du point à tester
	 * @return le noeud à ces coordonnées s'il existe
	 */
	private Noeud getNoeudA(int x, int y){
		if(points == null)
			return null;
		else{
			Iterator<Noeud> it = controleur.getPlan().getToutNoeuds().iterator();
			while(it.hasNext()){
				Noeud n = it.next();
				double nX = n.getX()*hX;
				double nY = n.getY()*hY;
				if( (x > nX-TOLERANCE && x < nX+TOLERANCE) && (y > nY-TOLERANCE && y < nY+TOLERANCE)){
					return n;
				}
			}
		}
		return null;
		
	}
	
	/**
	 * Change le point selectionné sur l'affichage :
	 * Déselectionne le point qui était selectionné jusque là,
	 * et sélectionne le nouveau
	 * @param nouvelleSelection
	 */
	private void changerPointSelectionne(Noeud nouvelleSelection){		
		//TODO Changer la couleur des demandes de livraison en fonction de leur plage horaire
		if(pointSelectionne != null){
			int idCouleur = (noeudsALivrer != null && noeudsALivrer.containsKey(pointSelectionne.getId()) ? noeudsALivrer.get(pointSelectionne.getId()) : 0);
			Object[] cells = {points.get(pointSelectionne.getId())};
			plan.setCellStyle("fillColor="+couleurRemplissage[idCouleur]+";strokeColor="+couleurBordure[idCouleur], cells);
		}
		
		int idCouleur = (noeudsALivrer != null && noeudsALivrer.containsKey(nouvelleSelection.getId()) ? noeudsALivrer.get(nouvelleSelection.getId()) : 0);
		pointSelectionne = nouvelleSelection;
		Object[] cells = {points.get(pointSelectionne.getId())};
		plan.setCellStyle("strokeColor=red;strokeWidth=3;fillColor="+couleurRemplissage[idCouleur], cells);
	}
	
	/**
	 * Méthode permettant d'afficher d'une couleur différente les demandes de livraison sur le plan
	 */
	public void afficherDemandesLivraisonsSurPlan(){
		noeudsALivrer = new HashMap<Integer, Integer>();
		
		//On réaffiche le plan proprement, sans point de livraison
		afficherPlan();
		
		int numPlage = 1;
		for (PlageHoraire plage : controleur.getTournee().getPlagesHoraires()) {
			
			for (DemandeDeLivraison livraison : plage.getDemandeLivraison()) {
				int noeud = livraison.getNoeud().getId();
				System.out.println(""+noeud);
				noeudsALivrer.put(noeud, numPlage);
				Object[] cells = {points.get(noeud)};
				plan.setCellStyle("fillColor="+couleurRemplissage[numPlage]+";strokeColor="+couleurBordure[numPlage], cells);
				
			}
			numPlage++;
		}		
	}
	
	/**
	 * Methode permettant d'activer le bouton Charger les Horaires
	 */
	public void activerChargementHoraires(){
		actionChargerHoraires.setEnabled(true);
	}
	
	/**
	 * Methode permettant d'activer le bouton Calculer
	 */
	public void activerCalculItineraire(){
		btnCalculer.setEnabled(true);
	}
	
	/**
	 * Methode permettant d'activer le bouton Ajouter
	 */
	public void activerAjouter(){
		btnAjouter.setEnabled(true);
	}

}