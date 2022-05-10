package examen;

import javax.swing.*;
import javax.swing.table.TableColumn;

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

public class PanneauUninstall extends JPanel {
   private JLabel osChoice, idChoice;
   private JComboBox<Object[]> osChoiceComboBox, idChoiceComboBox;
   private Connection connect2;
   private Object[] liste;
   private TableModelGen model;
   private JTable myTable;
   private JButton supprimer;
   private JPanel main1;

   private static final String couleurbg = "#F4F4F4";
   private static final String couleurTxt = "#F4F4F4";

   public PanneauUninstall() {
      // this.setLayout(new GridLayout(8, 2, 10, 20));
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.setBackground(Color.decode(couleurbg));
      main1 = new JPanel();
      main1.setLayout(new GridLayout(2, 2));
      main1.setBackground(Color.decode("#222222"));

      //OSCHOICE
      osChoice = new JLabel("CHOIX DE L'OS : ");
      osChoice.setForeground(Color.decode(couleurTxt));
      osChoice.setHorizontalAlignment(JLabel.CENTER);
      main1.add(osChoice);

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

      osChoiceComboBox = new JComboBox(liste);
      main1.add(osChoiceComboBox); 


      //ID CHOICE
      idChoice = new JLabel("CHOIX DE L'ID : ");
      idChoice.setHorizontalAlignment(JLabel.CENTER);
      idChoice.setForeground(Color.decode(couleurTxt));
      main1.add(idChoice);
  
      try {
         connect2 = ConnectionBD.connect();

         String requeteSQL = "select IdInstallation from Installation order by IdInstallation";

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
      idChoiceComboBox = new JComboBox(liste);
      main1.add(idChoiceComboBox);
      this.add(main1);
      //TABLE
      try {
         connect2 = ConnectionBD.connect();

         String requeteSQL = "select IdInstallation, DateInstallation, CodeSoftware, Matricule, CodeOS  from Installation";

         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         // ResultSet rs = pst.executeQuery(requeteSQL);
         model = ConnectionBD.creerTableModel(pst);
         myTable = new JTable(model);
         TableColumn col = myTable.getColumnModel().getColumn(1);
         col.setPreferredWidth(200);
         myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
         JScrollPane defilant = new JScrollPane(myTable);
         this.add(defilant);
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



      supprimer = new JButton("Supprimer");
      this.add(supprimer);
      this.setBackground(Color.decode("#00B1E9"));
   }

}
