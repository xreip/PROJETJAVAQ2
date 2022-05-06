package examen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.sql.*;
import java.util.*;

public class PanneauInstall extends JPanel {

   private JLabel idInstallationLabel, codeSoftJLabel, codeOSlJLabel, matriculeJLabel,
         dateValidationPrevueJLabel;
   private JTextField idInstallField, dateValidationPrevueField;
   private JComboBox codeSoftBox, codeOSBox, matriculeBox;
   private JRadioButton termineeRButton, enCoursRButton, aPrevoirRButton;
   private ButtonGroup groupeRButton;
   private JPanel mainPanel, buttonPanel;
   private JButton confirm;
   private static final int styleCourant = Font.PLAIN;
   private static final String fontCourant = "Times New Roman";
   private int idInstall = 1;
   String instructionSQL;
   Connection connexion;

   public PanneauInstall() {

      // this.setBounds(200, 200, 800, 800); NE FAIT RIEN
      // this.setLayout(new GridLayout(8, 2, 10, 20)); OLD
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

      mainPanel = new JPanel();
      mainPanel.setLayout(new GridLayout(5, 2, 10, 20));
      mainPanel.setBackground(Color.decode("#222222"));
      this.setBackground(Color.decode("#222222"));

      // ID INSTALLATION
      idInstallationLabel = new JLabel("Id D'installation : ");
      idInstallationLabel.setForeground(Color.decode("#F4F4F4"));
      mainPanel.add(idInstallationLabel);

      idInstallField = new JTextField("1", 30);
      mainPanel.add(idInstallField);
      idInstallField.setEditable(false);

      idInstallationLabel.setHorizontalAlignment(JLabel.CENTER);
      idInstallField.setHorizontalAlignment(JLabel.CENTER);

      // COMBOBOX SOFTWARES
      codeSoftJLabel = new JLabel("Nom du Logiciel : ");
      codeSoftJLabel.setForeground(Color.decode("#F4F4F4"));
      mainPanel.add(codeSoftJLabel);
      codeSoftJLabel.setHorizontalAlignment(JLabel.CENTER);

      String[] softwares = { "Office 2013", "NetBeas", "Bob50", "Visual Studio", "Oracle 11g" };
      // IL FAUT RECUP LA VALEUR DE LA BDD
      codeSoftBox = new JComboBox(softwares);
      codeSoftBox.setBackground(Color.decode("#FFFFFF"));
      codeSoftBox.setSelectedItem("Office 2013");
      codeSoftBox.setMaximumRowCount(5);
      // codeSoftBox.setEditable(true);
      mainPanel.add(codeSoftBox);

      // COMBOBOX OS
      codeOSlJLabel = new JLabel("Nom de l'OS : ");
      codeOSlJLabel.setForeground(Color.decode("#F4F4F4"));
      mainPanel.add(codeOSlJLabel);
      codeOSlJLabel.setHorizontalAlignment(JLabel.CENTER);

      String[] opSystems = { "Debian", "Windows", "Windows19", "Linux2", "Solaris34" };
      // IL FAUT RECUP LA VALEUR DE LA BDD
      codeOSBox = new JComboBox(opSystems);
      codeOSBox.setBackground(Color.decode("#FFFFFF"));
      codeOSBox.setSelectedItem("Debian");
      codeOSBox.setMaximumRowCount(5);
      // codeOSBox.setEditable(true);
      mainPanel.add(codeOSBox);

      // MATRICULE - RESPONSABLE RESEAU
      matriculeJLabel = new JLabel("Nom du responsable : ");
      matriculeJLabel.setForeground(Color.decode("#F4F4F4"));
      mainPanel.add(matriculeJLabel);
      matriculeJLabel.setHorizontalAlignment(JLabel.CENTER);

      String[] respReseaux = { "Marvin Gobin", "André Van Kerrebroeck", "Alexandre Baligant" };
      // IL FAUT RECUP LA VALEUR DE LA BDD
      matriculeBox = new JComboBox(respReseaux);
      matriculeBox.setBackground(Color.decode("#FFFFFF"));
      matriculeBox.setSelectedItem("Marvin Gobin");
      matriculeBox.setMaximumRowCount(5);
      // matriculeBox.setEditable(true);
      mainPanel.add(matriculeBox);

      // RADIO BUTTONS VALIDATION
      aPrevoirRButton = new JRadioButton("à prévoir", true);
      enCoursRButton = new JRadioButton("en cours", false);
      termineeRButton = new JRadioButton("terminée", false);

      aPrevoirRButton.setHorizontalAlignment(JRadioButton.CENTER);
      enCoursRButton.setHorizontalAlignment(JRadioButton.CENTER);
      termineeRButton.setHorizontalAlignment(JRadioButton.CENTER);
      aPrevoirRButton.setBackground(Color.decode("#00B1E9"));
      enCoursRButton.setBackground(Color.decode("#00B1E9"));
      termineeRButton.setBackground(Color.decode("#00B1E9"));

      buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(2, 3, 10, 20));

      buttonPanel.add(aPrevoirRButton);
      buttonPanel.add(enCoursRButton);
      buttonPanel.add(termineeRButton);

      groupeRButton = new ButtonGroup();
      groupeRButton.add(aPrevoirRButton);
      groupeRButton.add(enCoursRButton);
      groupeRButton.add(termineeRButton);

      // DATEDEVALIDATIONPREVUE
      dateValidationPrevueJLabel = new JLabel("Date de validation prévue (d-m-Y) : ");
      buttonPanel.add(dateValidationPrevueJLabel);
      dateValidationPrevueJLabel.setHorizontalAlignment(JLabel.CENTER);

      dateValidationPrevueField = new JTextField(30);
      buttonPanel.add(dateValidationPrevueField);
      buttonPanel.setBackground(Color.decode("#00B1E9"));

      // CONFIRM BUTTON
      confirm = new JButton("Confirmer");
      // confirm.setMinimumSize(new Dimension(400,400));
      confirm.setBackground(Color.decode("#00B1E9"));
      // MAIN LAYOUT
      this.add(mainPanel);
      this.add(buttonPanel);
      this.add(confirm);
      // this.add(mainPanel, BorderLayout.CENTER);
      // this.add(buttonPanel, BorderLayout.SOUTH);

      // GESTIONNAIRE D'ACTION
      MonGestionnaireAction g = new MonGestionnaireAction();
      idInstallField.addActionListener(g);

      // GESTIONNAIRE ITEM
      MonGestionnaireItem gi = new MonGestionnaireItem();
      aPrevoirRButton.addItemListener(gi);
      enCoursRButton.addItemListener(gi);
      termineeRButton.addItemListener(gi);
   }

   private class MonGestionnaireAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == idInstallField) {
            try {
               String sql = "insert into Installation" +
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

   private class MonGestionnaireItem implements ItemListener {

      @Override
      public void itemStateChanged(ItemEvent ie) {
         if (ie.getSource() == aPrevoirRButton && ie.getStateChange() == ItemEvent.SELECTED) {
            dateValidationPrevueField.setEnabled(true);
         } else if (ie.getSource() == termineeRButton && ie.getStateChange() == ItemEvent.SELECTED) {
            dateValidationPrevueField.setEnabled(false);
            dateValidationPrevueField.setText("");
         } else if (ie.getSource() == enCoursRButton && ie.getStateChange() == ItemEvent.SELECTED) {
            dateValidationPrevueField.setEnabled(false);
            dateValidationPrevueField.setText("");
         }
      }
   }
}