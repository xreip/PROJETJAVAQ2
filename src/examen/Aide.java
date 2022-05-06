package examen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Aide extends JFrame {
	private Container cont;
	private JLabel text;
	private JButton bouton;
	private int styleCourant=Font.PLAIN;
	private String fontCourant="Times New Roman";
	
	public Aide() {
		super("Aide");
		cont = getContentPane();
		cont.setLayout(new BorderLayout( ));
		
		setBounds(200, 200, 1500, 455);
		
		text = new JLabel("Pour trouver de l'aide veiller appeler le numéro : 081/212696", SwingConstants.CENTER);
		text.setFont(new Font(fontCourant,styleCourant,46));
		
		bouton = new JButton("Quitter");
	
		cont.add(text, BorderLayout.CENTER);
		cont.add(bouton, BorderLayout.SOUTH);
		
		
			
		MonGestionnaireAction gb = new MonGestionnaireAction();
		bouton.addActionListener(gb);
			
			
			
		setVisible(true);
	}
		
	private class MonGestionnaireAction implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == bouton) {
				dispose();	
			}
			
		}
			
			
	}
		
		
}
