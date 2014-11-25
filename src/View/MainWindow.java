package View;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import libs.ExampleFileFilter;


public class MainWindow extends JFrame {
	
	private JFileChooser jFileChooserXML;
	private File fichierPlan;
	private File fichierHoraires;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuFichier = new JMenu("Fichier");
		JMenuItem actionQuitter = new JMenuItem("Quitter");
		
		
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
				fichierPlan = ouvrirFichier('o');
				//TODO utiliser les méthodes de Felipe et Justine pour lire le xml
			}
			
		});
		
		actionChargerHoraires.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				fichierHoraires = ouvrirFichier('o');
				//TODO utiliser les méthodes de Felipe et Justine pour lire le xml
			}
			
		});
		
		menuFichier.add(actionChargerPlan);
		menuFichier.add(actionChargerHoraires);
		menuFichier.add(actionQuitter);
		
		JMenu menuAide = new JMenu("Aide");
		
		menuBar.add(menuFichier);
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
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		
		JLabel lblNewLabel = new JLabel("Plan");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel lblNewLabel_1 = new JLabel("Horaires");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 24));
		
		JScrollPane scrollPane = new JScrollPane();
		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addContainerGap(850, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 540, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(119)
							.addComponent(lblNewLabel_1)
							.addGap(119))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
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
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 414, GroupLayout.PREFERRED_SIZE)))
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
}
