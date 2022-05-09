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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PanneauInstall extends JPanel {

   private JLabel idInstallationLabel;
   private JLabel codeSoftJLabel, codeOSlJLabel, matriculeJLabel,
         dateValidationPrevueJLabel, commentsJLabel, dureeInstallJLabel, refProcedureJLabel, typeInstallJLabel;
   private JTextField dateValidationPrevueField, idInstallField, commentsField, dureeInstallJField, refProcedureJField;
   private JComboBox<Object[]> codeSoftBox, codeOSBox, matriculeBox;
   private JCheckBox typeInstallCheckBox;
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
      mainPanel.setLayout(new GridLayout(10, 2, 10, 20));
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

      // Duree FIELD
      dureeInstallJLabel = new JLabel("Durée installation (mins) :");
      dureeInstallJLabel.setForeground(Color.decode("#F4F4F4"));
      dureeInstallJLabel.setHorizontalAlignment(JLabel.CENTER);
      mainPanel.add(dureeInstallJLabel);

      dureeInstallJField = new JTextField(3);
      mainPanel.add(dureeInstallJField);

      // Type install CHECKBOX
      typeInstallJLabel = new JLabel("Personnalisée : ");
      typeInstallJLabel.setForeground(Color.decode("#F4F4F4"));
      typeInstallJLabel.setHorizontalAlignment(JLabel.CENTER);
      mainPanel.add(typeInstallJLabel);

      typeInstallCheckBox = new JCheckBox();
      typeInstallCheckBox.setBackground(Color.decode("#222222"));
      mainPanel.add(typeInstallCheckBox);

      // PROCEDURE FIELD
      refProcedureJLabel = new JLabel("Référence de la procédure d'installation (F) :");
      refProcedureJLabel.setForeground(Color.decode("#F4F4F4"));
      refProcedureJLabel.setHorizontalAlignment(JLabel.CENTER);
      mainPanel.add(refProcedureJLabel);

      refProcedureJField = new JTextField();
      mainPanel.add(refProcedureJField);

      // COMMENTAIRES FIELD
      commentsJLabel = new JLabel("Commentaires (F) :");
      commentsJLabel.setForeground(Color.decode("#F4F4F4"));
      mainPanel.add(commentsJLabel);
      commentsJLabel.setHorizontalAlignment(JLabel.CENTER);

      commentsField = new JTextField(100);
      mainPanel.add(commentsField);

      // RADIO BUTTONS VALIDATION
      aPrevoirRButton = new JRadioButton("A prevoir", true);
      enCoursRButton = new JRadioButton("En cours", false);
      termineeRButton = new JRadioButton("Terminee", false);

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
      System.out.println(aPrevoirRButton.getText());

      // DATEDEVALIDATIONPREVUE
      dateValidationPrevueJLabel = new JLabel("Date de validation prévue (YYYY-mm-dd) : ");
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

               // ID
               pst.setInt(1, Integer.parseInt(idInstallField.getText()));

               // DATE DE L'INSTALLATION
               pst.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));

               // PERSONNALISEE OU PAS
               Boolean perso = false;
               if (typeInstallCheckBox.isSelected()) {
                  perso = true;
               }
               pst.setBoolean(3, perso);

               // COMMENTAIRE (PEUT ETRE NULL)
               if (commentsField.getText().isEmpty()) {
                  pst.setNull(4, Types.VARCHAR);
               } else {
                  pst.setString(4, commentsField.getText());
               }

               // DUREE INSTALLATION (PAS DE NULL donc 0);
               if (dureeInstallJField.getText().isEmpty()) {
                  pst.setInt(5, 0);
               } else {
                  pst.setInt(5, Integer.parseInt(dureeInstallJField.getText()));
               }

               // PROCEDURE (PEUT ETRE NULL)
               if (refProcedureJField.getText().isEmpty()) {
                  pst.setNull(6, Types.VARCHAR);
               } else {
                  pst.setString(6, refProcedureJField.getText());
               }

               // CHOIX A COCHER
               String buttonText = "";
               if (aPrevoirRButton.isSelected()) {
                  buttonText = aPrevoirRButton.getText();
               } else if (enCoursRButton.isSelected()) {
                  buttonText = enCoursRButton.getText();
               } else if (termineeRButton.isSelected()) {
                  buttonText = termineeRButton.getText();
               }
               pst.setString(7, buttonText);

               // DATE DE L'INSTALLATION PREVUE QUE SI "à prévoir" de coché sinon NULL
               if (aPrevoirRButton.isSelected()) {
                  pst.setString(8, dateValidationPrevueField.getText());
               } else {
                  pst.setNull(8, Types.DATE);
               }
               
               // SOFTCODE
               String getCodeSoft = String.valueOf(codeSoftBox.getSelectedItem());
               System.out.println(getCodeSoft);
               String sqlCodeSoft = "SELECT codeSoftware FROM software WHERE Nom=" + "\"" + getCodeSoft + "\"" + ";";
               System.out.println(sqlCodeSoft);
               try {
                  connect2 = ConnectionBD.connect();

                  PreparedStatement pest = connect2.prepareStatement(sqlCodeSoft);
                  ResultSet rs = pest.executeQuery(sqlCodeSoft);
                  if (rs.next()) {
                     String resCode = rs.getString(1);
                     System.out.println(resCode);
                     pst.setString(9, resCode);
                  } else {
                     System.out.println("rien dans la requête");
                  }
               } catch (SQLException e1) {
                  System.out.println("Is not possible for connexion");
               } finally {
                  try {
                     connect2.close();
                     System.out.println("Connection fermée pour les CodeSoft");
                  } catch (SQLException e1) {
                     e1.printStackTrace();
                  }
               }

               // CODEMATRICULE
               String idmatricule = String.valueOf(matriculeBox.getSelectedItem());
               System.out.println(idmatricule);
               String sqlMatricule = "SELECT Matricule FROM responsablereseaux WHERE NomPrenom=" + "\"" + idmatricule
                     + "\"" + ";";
               System.out.println(sqlMatricule);
               try {
                  connect2 = ConnectionBD.connect();

                  PreparedStatement pest = connect2.prepareStatement(sqlMatricule);
                  ResultSet rs = pest.executeQuery(sqlMatricule);
                  if (rs.next()) {
                     String res = rs.getString(1);
                     pst.setString(10, res);
                  } else {
                     System.out.println("rien dans la requête");
                  }
               } catch (SQLException e1) {
                  System.out.println("Is not possible for connexion");
               } finally {
                  try {
                     connect2.close();
                     System.out.println("Connection fermée pour les Matricules");
                  } catch (SQLException e1) {
                     e1.printStackTrace();
                  }
               }

               // CODEOS
               String getCodeOs = String.valueOf(codeOSBox.getSelectedItem());
               System.out.println(getCodeOs);
               String sqlCodeOs = "SELECT CodeOs FROM OS WHERE Libelle=" + "\"" + getCodeOs + "\"" + ";";
               System.out.println(sqlCodeOs);
               try {
                  connect2 = ConnectionBD.connect();

                  PreparedStatement pest = connect2.prepareStatement(sqlCodeOs);
                  ResultSet rs = pest.executeQuery(sqlCodeOs);
                  if (rs.next()) {
                     String res = rs.getString(1);
                     pst.setString(11, res);
                  } else {
                     System.out.println("rien dans la requête");
                  }
               } catch (SQLException e1) {
                  System.out.println("Is not possible for connexion");
               } finally {
                  try {
                     connect2.close();
                     System.out.println("Connection fermée pour les CodeOS");
                  } catch (SQLException e1) {
                     e1.printStackTrace();
                  }
               }

               System.out.println(pst.toString());

               pst.executeUpdate();

               // ID +1 , PAS BESOIN DE RELOAD LE PANNEAU
               int id = Integer.valueOf(idInstallField.getText()) + 1;
               idInstallField.setText(String.valueOf(id));

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
