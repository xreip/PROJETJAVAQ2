package examen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Fenetre extends JFrame{

	private Container cont;
	private JMenuBar barre;
	private JMenu info, accueil, install;
	private JMenuItem itemInstall, itemUninstall, itemAccueil, itemQuitter, itemInfos;
	private Aide aide;
	private PanneauInstall in;
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
		
		info = new JMenu("Information");
		barre.add(info);
		
		itemAccueil = new JMenuItem("Accueil");
		accueil.add(itemAccueil);
		
		itemQuitter = new JMenuItem("Quitter");
		accueil.add(itemQuitter);
		
		itemInstall = new JMenuItem("Nouvelle installation");
		install.add(itemInstall);
		
		itemInfos = new JMenuItem("Aide");
		info.add(itemInfos);
		
		MonGestionnaireAction ga = new MonGestionnaireAction();
		itemQuitter.addActionListener(ga);
		itemInstall.addActionListener(ga);
		itemAccueil.addActionListener(ga);
		itemInfos.addActionListener(ga);
		
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

