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

public class PanneauSearch extends JPanel {
   private JLabel dateJLabel, softJLabel;
   private JTextField dateJTextField;
   private JButton searchButton;
   private JComboBox<Object[]> softJComboBox;
   private JPanel datePanel, softPanel;
   private Object[] liste;
   private Connection connect2;
   private JTable myTable;
   private JScrollPane defilant;
   private TableModelGen model;
   private JPanel tablePanel;

   public PanneauSearch() {
      // this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      // this.setLayout(new FlowLayout());
      this.setLayout(new GridLayout(5, 2));
      datePanel = new JPanel(new FlowLayout());
      datePanel.setMaximumSize(new Dimension(200, 200));
      softPanel = new JPanel(new FlowLayout());

      dateJLabel = new JLabel("Date à rechercher (yyyy-MM-dd) : ");
      dateJTextField = new JTextField();
      dateJTextField.setPreferredSize(new Dimension(200, 24));
      searchButton = new JButton("Chercher");
      searchButton.setPreferredSize(new Dimension(100, 24));

      datePanel.add(dateJLabel);
      datePanel.add(dateJTextField);

      this.add(datePanel);

      softJLabel = new JLabel("Logiciel : ");
      datePanel.add(softJLabel);

      // COMBOBOX LOGICIEL
      try {

         connect2 = ConnectionBD.connect();

         String requeteSQL = "select CodeSoftware from Software";
         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         liste = ConnectionBD.creerListe1Colonne(pst);

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
      softJComboBox = new JComboBox(liste);
      softJComboBox.setPreferredSize(new Dimension(200, 24));
      datePanel.add(softJComboBox);

      // Search Button
      datePanel.add(searchButton);

      // Tableau
      tablePanel = new JPanel();
      try {
         connect2 = ConnectionBD.connect();

         String requeteSQL = "select * FROM installation;";

         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         System.out.println(requeteSQL);

         // ResultSet rs = pst.executeQuery(requeteSQL);
         model = ConnectionBD.creerTableModel(pst);
         myTable = new JTable(model);
         TableColumn col = myTable.getColumnModel().getColumn(1);
         col.setPreferredWidth(100);
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
      searchButton.addActionListener(g);
   }

   private void tableCreation() {
      tablePanel.removeAll();
      try {
         connect2 = ConnectionBD.connect();

         String requeteSQL = "select * from installation where DateInstallation between ? and now() AND CodeSoftware=?;";

         PreparedStatement pst = connect2.prepareStatement(requeteSQL);
         pst.setDate(1, java.sql.Date.valueOf(dateJTextField.getText()));
         pst.setString(2, String.valueOf(softJComboBox.getSelectedItem()));
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

   private class MonGestionnaireAction implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == searchButton) {
            tableCreation();
         }
      }
   }
}
