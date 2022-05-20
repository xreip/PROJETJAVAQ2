package examen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanneauAccueil extends JPanel{
	
	private JLabel texte;
	private JLabel texte2;
	private static final int styleCourant=Font.BOLD;
	private static final String fontCourant="Arial";
	
	public PanneauAccueil() {
		
      this.setLayout(new GridLayout(0,1));
      this.setBackground(Color.decode("#222222"));
		// setBounds(175, 175, 1000, 700); ne fait rien
		texte = new JLabel("Bienvenue sur l'application",JLabel.CENTER);
		texte.setFont(new Font(fontCourant, styleCourant, 46));
      texte.setForeground(Color.decode("#00B1E9"));
      
		texte2 = new JLabel("Réalisée par Mouffe Pierre et Noel Alexandre",JLabel.CENTER);
	   texte2.setFont(new Font(fontCourant, styleCourant, 25));
      texte2.setForeground(Color.decode("#00B1E9"));

		this.add(texte);
		this.add(texte2);
	}
}
