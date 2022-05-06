package examen;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.sql.*;
import java.util.*;

public class install extends JPanel{
	
	private JLabel IdInstallation, CodeSoft, CodeOS, RespReseau, 
	dateValidationPrevue;
	private JTextField CIdInstall;
	private JComboBox CCodeSoft, CCodeOS, CRespReseau;
	private JRadioButton Validation, Suppersion, APrevoir;
	private int styleCourant=Font.PLAIN;
	private String fontCourant="Times New Roman";
	private int IdInstall=1;
	String instructionSQL;
	Connection connexion;

	public install() {
		
		MonGestionnaireAction g = new MonGestionnaireAction();
		
		setBounds(200, 200, 1040, 800);
		IdInstallation = new JLabel("Id D'installation : ");
		this.add(IdInstallation);
		CIdInstall = new JTextField(30);
		this.add(CIdInstall);
		this.setLayout(new GridLayout(8, 2, 10, 20));
		IdInstallation.setHorizontalAlignment(JLabel.CENTER);
		CIdInstall.setHorizontalAlignment(JLabel.CENTER);
		CIdInstall.addActionListener(g);
//		CIdInstall.setSize(30, 30);
		
		}

	
	private class MonGestionnaireAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == CIdInstall) {
				try {
					String sql= "insert into Installation" +
							"values (4,'2016-08-11',true,null,100, null, 'En cours',null, 'Vs12','MarGob','W8ProfEn')";
					PreparedStatement prepStat = connexion.prepareStatement(sql);
					prepStat.executeUpdate(sql);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}

}
