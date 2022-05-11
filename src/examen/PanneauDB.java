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

public class PanneauDB extends JPanel {
   private JLabel tableChoicJLabel;
   private JComboBox<Object[]> tableComboBox;
   private JButton showButton;
   private TableModelGen model;
   private JTable myTable;
   private Object[] liste;
   private Connection connect2;
   private JScrollPane defilant;
   private JPanel tablePanel;

   public PanneauDB() {
      // this.setLayout(new GridLayout(1, 3, 10, 20));

      tableChoicJLabel = new JLabel("Table à afficher");

      try {
         connect2 = ConnectionBD.connect();

         String requeteSQL = "show tables";

         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         liste = ConnectionBD.creerListe1Colonne(pst);
      } catch (SQLException e) {
         System.out.println("Is not possible for connexion");
      } finally {
         try {
            connect2.close();
            System.out.println("Connection fermée pour les tables");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      tableComboBox = new JComboBox(liste);
      
      showButton = new JButton("Afficher");

      this.add(tableChoicJLabel);
      this.add(tableComboBox);
      this.add(showButton);

      tablePanel = new JPanel();

      try {
         connect2 = ConnectionBD.connect();

         String requeteSQL = "select * FROM"+" "+String.valueOf(tableComboBox.getSelectedItem())+";";

         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         System.out.println(requeteSQL);
         
         // ResultSet rs = pst.executeQuery(requeteSQL);
         model = ConnectionBD.creerTableModel(pst);
         myTable = new JTable(model);
         TableColumn col = myTable.getColumnModel().getColumn(1);
         col.setPreferredWidth(200);
         myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
         JScrollPane defilant = new JScrollPane(myTable);
         tablePanel.add(defilant);
      } catch (SQLException e) {
         System.out.println("Is not possible for connexion");
      } finally {
         try {
            connect2.close();
            System.out.println("Connection fermée");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      this.add(tablePanel);
      MonGestionnaireAction g = new MonGestionnaireAction();
      showButton.addActionListener(g);
   }

   private void tableCreation(){
      tablePanel.removeAll();
      try {
         connect2 = ConnectionBD.connect();

         String requeteSQL = "select * FROM"+" "+String.valueOf(tableComboBox.getSelectedItem())+";";

         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         System.out.println(requeteSQL);
         
         // ResultSet rs = pst.executeQuery(requeteSQL);
         model = ConnectionBD.creerTableModel(pst);
         myTable = new JTable(model);
         TableColumn col = myTable.getColumnModel().getColumn(1);
         col.setPreferredWidth(200);
         myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
         defilant = new JScrollPane(myTable);
         tablePanel.add(defilant);
         tablePanel.validate();
         this.validate();
         tablePanel.repaint();
         this.repaint();
      } catch (SQLException e) {
         System.out.println("Is not possible for connexion");
      } finally {
         try {
            connect2.close();
            System.out.println("Connection fermée");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }

   private class MonGestionnaireAction implements ActionListener{

      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == showButton){
            tableCreation();
         }
         
      }
      
   }
}