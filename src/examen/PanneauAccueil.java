package examen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanneauAccueil extends JPanel{
	
	public JLabel texte;
	public JLabel texte2;
	private int styleCourant=Font.PLAIN;
	private String fontCourant="Times New Roman";
	
	
	public PanneauAccueil() {
		
		
		setBounds(175, 175, 1000, 700);
		texte = new JLabel("Bienvenue sur l'application", SwingConstants.CENTER);
//		texte.setVerticalAlignment(SwingConstants.CENTER); (ne marche pas)
		texte.setFont(new Font(fontCourant, styleCourant, 46));
		texte2 = new JLabel("Réalisé par Mouffe Pierre et Noel Alexandre", SwingConstants.CENTER);
	    texte2.setFont(new Font(fontCourant, styleCourant, 40));
		this.add(texte);
		this.add(texte2);
	}
	
	
}
