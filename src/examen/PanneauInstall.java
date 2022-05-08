package examen;

import javax.swing.*;

import accessDB.AccessBDGen;
import accessDB.TableModelGen;
import accessDB.ConnectionBD;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

public class PanneauInstall extends JPanel {

   private JLabel idInstallationLabel;
   private JLabel codeSoftJLabel, codeOSlJLabel, matriculeJLabel,
         dateValidationPrevueJLabel;
   private JTextField dateValidationPrevueField, idInstallField;
   private JComboBox<Object[]> codeSoftBox, codeOSBox, matriculeBox;
   private JRadioButton termineeRButton, enCoursRButton, aPrevoirRButton;
   private ButtonGroup groupeRButton;
   private JPanel mainPanel, buttonPanel;
   private JButton confirm;
   private Connection connect;
   private Connection connect2;
   private Object[] model;
   private Object[] liste;
   private TableModelGen model2;
   private String nomDB;
   private String userName;
   private String password;
   private int IdInst;

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
      idInstallationLabel.setHorizontalAlignment(JLabel.CENTER);
      mainPanel.add(idInstallationLabel);

      idInstallField = new JTextField();
      idInstallField.setHorizontalAlignment(JLabel.CENTER);
      mainPanel.add(idInstallField);
      setIdInstallField(idInstallField);

      idInstallField.setEditable(false);

      // Essayer de recuperer les données pour le mettre dans JTextField (on obtient
      // un emplacement)
      try {
         connect2 = ConnectionBD.connect();

         String requeteSQL = "select MAX(IdInstallation) from installation";

         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         ResultSet rs = pst.executeQuery(requeteSQL);
         if (rs.next()) {
            // System.out.println(rs.getInt(1)+1);
            int rsInt = rs.getInt(1) + 1;
            String nombre = String.valueOf(rsInt);
            idInstallField.setText(nombre);
         } else {
            System.out.println("rien dans la requête");
         }

      } catch (SQLException e) {
         System.out.println("Is not possible for connexion");
      } finally {
         try {
            connect2.close();
            System.out.println("Connection fermée pour l'idInstallation");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      // COMBOBOX SOFTWARES
      codeSoftJLabel = new JLabel("Nom du Logiciel : ");
      codeSoftJLabel.setForeground(Color.decode("#F4F4F4"));
      codeSoftJLabel.setHorizontalAlignment(JLabel.CENTER);
      mainPanel.add(codeSoftJLabel);

      try {

         connect2 = ConnectionBD.connect();

         String requeteSQL = "select Nom from Software";

         // PreparedStatement prepStat = connect2.prepareStatement(requeteSQL);

         // model = AccessBDGen.creerListe1Colonne(prepStat);

         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         liste = ConnectionBD.creerListe1Colonne(pst);
         // ResultSet rs = pst.executeQuery();
         // ResultSetMetaData meta = rs.getMetaData();

         // int max = 0;
         // int index = 0;
         // String stringLu;

         // while (rs.next()) {
         // max++;
         // }

         // liste = new Object[max];

         // while (rs.next()){
         // stringLu = rs.getString(1);
         // liste[index] = stringLu;
         // System.out.println(stringLu);
         // index++;
         // }
         // // rs.getString("name");
         // // System.out.println(rs.getString("name"));

      } catch (SQLException e) {
         System.out.println("Is not possible for connexion");
      } finally {
         try {
            connect2.close();
            System.out.println("Connection fermée pour les softwares");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      // String[] softwares = { "Office 2013", "NetBeas", "Bob50", "Visual Studio",
      // "Oracle 11g" };

      codeSoftBox = new JComboBox(liste);
      // codeSoftBox = new JComboBox(softwares);
      // codeSoftBox.setEditable(true);
      codeSoftBox.setBackground(Color.decode("#FFFFFF"));
      codeSoftBox.setSelectedItem("Office 2013");
      codeSoftBox.setMaximumRowCount(5);
      mainPanel.add(codeSoftBox);

      // COMBOBOX OS
      codeOSlJLabel = new JLabel("Nom de l'OS : ");
      codeOSlJLabel.setForeground(Color.decode("#F4F4F4"));
      codeOSlJLabel.setHorizontalAlignment(JLabel.CENTER);
      mainPanel.add(codeOSlJLabel);

      try {
         connect2 = ConnectionBD.connect();

         String requeteSQL = "select Libelle from OS";

         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         liste = ConnectionBD.creerListe1Colonne(pst);
      } catch (SQLException e) {
         System.out.println("Is not possible for connexion");
      } finally {
         try {
            connect2.close();
            System.out.println("Connection fermée pour les OS");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      // String[] opSystems = { "Debian", "Windows", "Windows19", "Linux2",
      // "Solaris34" };
      // IL FAUT RECUP LA VALEUR DE LA BDD
      codeOSBox = new JComboBox(liste);
      codeOSBox.setBackground(Color.decode("#FFFFFF"));
      // codeOSBox.setSelectedItem("Debian"); // PAS OBLIGE en vrai car si existe plus
      // aie
      codeOSBox.setMaximumRowCount(5);
      // codeOSBox.setEditable(true);
      mainPanel.add(codeOSBox);

      // MATRICULE - RESPONSABLE RESEAU
      matriculeJLabel = new JLabel("Nom du responsable : ");
      matriculeJLabel.setForeground(Color.decode("#F4F4F4"));
      matriculeJLabel.setHorizontalAlignment(JLabel.CENTER);
      mainPanel.add(matriculeJLabel);

      // String[] respReseaux = { "Marvin Gobin", "André Van Kerrebroeck", "LOIC
      // Baligant" };
      // IL FAUT RECUP LA VALEUR DE LA BDD

      try {
         connect2 = ConnectionBD.connect();

         String requeteSQL = "select NomPrenom from responsablereseaux";

         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         liste = ConnectionBD.creerListe1Colonne(pst);
      } catch (SQLException e) {
         System.out.println("Is not possible for connexion");
      } finally {
         try {
            connect2.close();
            System.out.println("Connection fermée pour les Respréseaux");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      matriculeBox = new JComboBox(liste);
      matriculeBox.setBackground(Color.decode("#FFFFFF"));
      // matriculeBox.setSelectedItem("Marvin Gobin"); // PAS OBLIGE en vrai car si
      // existe plus aie
      matriculeBox.setMaximumRowCount(5);
      // matriculeBox.setEditable(true);
      mainPanel.add(matriculeBox);
      String grosboule = String.valueOf(matriculeBox.getSelectedItem());
      System.out.println(grosboule);


      String sqlMatricule = "SELECT Matricule FROM responsablereseaux WHERE NomPrenom="+"\""+grosboule+"\""+";";
      System.out.println(sqlMatricule);

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
      confirm.addActionListener(g);

      // GESTIONNAIRE ITEM
      MonGestionnaireItem gi = new MonGestionnaireItem();
      aPrevoirRButton.addItemListener(gi);
      enCoursRButton.addItemListener(gi);
      termineeRButton.addItemListener(gi);
   }

   public JTextField getDateValidationPrevueField() {
      return dateValidationPrevueField;
   }

   public void setDateValidationPrevueField(JTextField dateValidationPrevueField) {
      this.dateValidationPrevueField = dateValidationPrevueField;
   }

   public JTextField getIdInstallField() {
      return idInstallField;
   }

   public void setIdInstallField(JTextField idInstallField) {
      this.idInstallField = idInstallField;
   }

   public JComboBox<Object[]> getCodeSoftBox() {
      return codeSoftBox;
   }

   public void setCodeSoftBox(JComboBox<Object[]> codeSoftBox) {
      this.codeSoftBox = codeSoftBox;
   }

   public JComboBox<Object[]> getCodeOSBox() {
      return codeOSBox;
   }

   public void setCodeOSBox(JComboBox<Object[]> codeOSBox) {
      this.codeOSBox = codeOSBox;
   }

   public JComboBox<Object[]> getMatriculeBox() {
      return matriculeBox;
   }

   public void setMatriculeBox(JComboBox<Object[]> matriculeBox) {
      this.matriculeBox = matriculeBox;
   }

   private class MonGestionnaireAction implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {

         if (e.getSource() == confirm) {

            try {
               connect2 = ConnectionBD.connect();

               String requeteSQL = "INSERT INTO Installation(IdInstallation,DateInstallation,TypeInstallation,Commentaires,DureeInstallation,RefProcedureInstallation,Validation,DateValidation,CodeSoftware,Matricule,CodeOS) VALUES(?,?,?,?,?,?,?,?,?,?,?);";


               PreparedStatement pst = connect2.prepareStatement(requeteSQL);
               pst.setInt(1, Integer.parseInt(idInstallField.getText()));
               pst.setString(2,dateValidationPrevueField.getText());
               pst.setBoolean(3,true);
               pst.setString(4,"OUfti woaw");
               pst.setInt(5,143);
               pst.setString(6,"Procedure 666");
               pst.setString(7,"Terminee");
               pst.setNull(8, Types.DATE);
               pst.setString(9,"Or11");
               pst.setString(10,"Alba");
               pst.setString(11,"W8ProfFr");

               System.out.println(pst.toString());
               pst.executeUpdate();


            } catch (SQLException s) {
               System.out.println("Is not possible for connexion");
            } finally {
               try {
                  connect2.close();
                  System.out.println("Connection fermée pour l'idInstallation");
               } catch (SQLException s) {
                  s.printStackTrace();
               }
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
