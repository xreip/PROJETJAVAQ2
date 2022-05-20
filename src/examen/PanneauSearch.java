package examen;

import javax.swing.*;
import javax.swing.table.TableColumn;
import accessDB.TableModelGen;
import accessDB.ConnectionBD;
import accessDB.Singleton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PanneauSearch extends JPanel {
   private JLabel dateJLabel, softJLabel;
   private JTextField dateJTextField;
   private JButton searchButton;
   private JComboBox<Object[]> softJComboBox;
   private JPanel datePanel, softPanel;
   private Object[] liste;

   private Singleton instance;
   private Connection DBconnect;

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

         instance = Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "select CodeSoftware from Software";
         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
         liste = ConnectionBD.creerListe1Colonne(pst);

      } catch (SQLException e) {
         System.out.println("Impossible Connection");
      } finally {
         try {
            DBconnect.close();
            System.out.println("Connection closed for softwares");
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
         instance = Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "select * FROM installation;";

         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
         System.out.println(requeteSQL);

         model = ConnectionBD.creerTableModel(pst);
         myTable = new JTable(model);
         TableColumn col = myTable.getColumnModel().getColumn(1);
         col.setPreferredWidth(100);
         myTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
         JScrollPane defilant = new JScrollPane(myTable);
         tablePanel.add(defilant);
      } catch (SQLException e) {
         System.out.println("Impossible Connection");
      } finally {
         try {
            DBconnect.close();
            System.out.println("Closed Connection");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      this.add(tablePanel);

      MonGestionnaireAction g = new MonGestionnaireAction();
      searchButton.addActionListener(g);
      softJComboBox.addActionListener(g);
   }

   private void tableCreation() {
      tablePanel.removeAll();
      try {
         instance = Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "select * from installation where DateInstallation between ? and now() AND CodeSoftware=?;";

         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
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
         System.out.println("Impossible Connection");
      } finally {
         try {
            DBconnect.close();
            System.out.println("Closed Connection");
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
         } else if (e.getSource() == softJComboBox) {
            tableCreation();
         }
      }
   }
}
