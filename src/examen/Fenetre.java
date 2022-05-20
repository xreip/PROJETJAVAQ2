package examen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fenetre extends JFrame{

	private Container cont;

   private JPanel panel;
   private JLabel userLabel, passwordLabel;
   private JTextField userTextField, passTextField;
   private JButton connectButton;

   private static String user;
   private static String password;

	private JMenuBar barre;
	private JMenu info, accueil, install, search;
	private JMenuItem itemInstall, itemUninstall, itemAccueil, itemQuitter, itemInfos, itemDB, searchItem,pcItem;
	private Aide aide;
	private PanneauInstall in;
   private PanneauUninstall un;
   private PanneauDB pdb;
   private PanneauPC ppc;
   private PanneauSearch spn;
	private PanneauAccueil PanAcc;
	
	
	public Fenetre() {
		super("Install");
		this.setBounds(200, 200, 1040, 900); //X,Y, width, height

		this.addWindowListener(new Listener()); //FERMETURE AVEC LA CROIX

		PanAcc = new PanneauAccueil();
		
		barre = new JMenuBar();
		setJMenuBar(barre);
		
		accueil = new JMenu("Accueil");
		barre.add(accueil);
		
		install = new JMenu("Installation");
		barre.add(install);
      
      search = new JMenu("Recherche");
      barre.add(search);

		info = new JMenu("Information");
		barre.add(info);
		
		itemAccueil = new JMenuItem("Accueil");
		accueil.add(itemAccueil);
		
		itemQuitter = new JMenuItem("Quitter");
		accueil.add(itemQuitter);
		
		itemInstall = new JMenuItem("Nouvelle installation");
		install.add(itemInstall);

      itemUninstall = new JMenuItem ("Supprimer une installation");
		install.add(itemUninstall);

      itemDB = new JMenuItem ("Tables DB");
		install.add(itemDB);

      searchItem = new JMenuItem("Date");
      search.add(searchItem);

      pcItem = new JMenuItem("Soft.Préinst.");
      search.add(pcItem);

		itemInfos = new JMenuItem("Aide");
		info.add(itemInfos);
		
		MonGestionnaireAction ga = new MonGestionnaireAction();
		itemQuitter.addActionListener(ga);
		itemInstall.addActionListener(ga);
      itemUninstall.addActionListener(ga);
      itemDB.addActionListener(ga);
		itemAccueil.addActionListener(ga);
		itemInfos.addActionListener(ga);
      searchItem.addActionListener(ga);
      pcItem.addActionListener(ga);
		
		cont = getContentPane();
		cont.setLayout(new BorderLayout());
		cont.add(PanAcc);
		
		setVisible(true);
	}
	
	private class MonGestionnaireAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == itemQuitter) {
				System.exit(0);
			}
			else if (e.getSource() == itemInstall) {
				cont.removeAll();
				in = new PanneauInstall();
				cont.add(in);
				setVisible(true);
			}
         else if (e.getSource() == itemUninstall) {
				cont.removeAll();
				un = new PanneauUninstall();
				cont.add(un);
				setVisible(true);
			}
         else if (e.getSource() == itemDB) {
				cont.removeAll();
				pdb = new PanneauDB();
				cont.add(pdb);
				setVisible(true);
			}
         else if (e.getSource() == searchItem) {
				cont.removeAll();
				spn = new PanneauSearch();
				cont.add(spn);
				setVisible(true);
			}
         else if (e.getSource() == pcItem) {
				cont.removeAll();
				ppc = new PanneauPC();
				cont.add(ppc);
				setVisible(true);
			}
			else if (e.getSource() == itemAccueil) {
				cont.removeAll();
				PanAcc = new PanneauAccueil();
				cont.add(PanAcc);
				setVisible(true);
			}
			else if (e.getSource() == itemInfos) {
				aide = new Aide();
			}
		
		}
	}
}

