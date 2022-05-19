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


public class PanneauPC extends JPanel{
   private JLabel softJLabel;
   private JButton searchButton;
   private JComboBox<Object[]> softJComboBox;
   private JPanel datePanel, softPanel;
   private Object[] liste;

   // private Connection connect2;
   private Singleton instance;
   private Connection DBconnect;

   private JTable myTable;
   private JScrollPane defilant;
   private TableModelGen model;
   private JPanel tablePanel;

   public PanneauPC() {
      // this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      // this.setLayout(new FlowLayout());
      this.setLayout(new GridLayout(5, 2));
      datePanel = new JPanel(new FlowLayout());
      datePanel.setMaximumSize(new Dimension(200, 200));
      softPanel = new JPanel(new FlowLayout());

      searchButton = new JButton("Chercher");
      searchButton.setPreferredSize(new Dimension(100, 24));


      this.add(datePanel);

      softJLabel = new JLabel("Type de PC : ");
      datePanel.add(softJLabel);

      // COMBOBOX TYPEPC
      try {
         instance=Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "select Description from typepc;";
         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
         liste = ConnectionBD.creerListe1Colonne(pst);

      } catch (SQLException e) {
         System.out.println("Impossible connection");
      } finally {
         try {
            DBconnect.close();
            System.out.println("ComboBox TypePC End Connection");
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

      this.add(tablePanel);

      MonGestionnaireAction g = new MonGestionnaireAction();
      searchButton.addActionListener(g);
      softJComboBox.addActionListener(g);
   }

   private void tableCreation() {
      tablePanel.removeAll();
      try {
         instance=Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "SELECT Description, Nom FROM software as s INNER JOIN softwarepreinstalle as sp ON s.CodeSoftware = sp.CodeSoftware INNER JOIN typepc as tp ON sp.IdTypePC=tp.IdTypePC WHERE tp.Description = ? AND MemCarteVideoMin=4;";

         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
         pst.setString(1, String.valueOf(softJComboBox.getSelectedItem()));
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
            DBconnect.close();
            System.out.println("Closed connection");
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
         else if(e.getSource() ==softJComboBox){
            tableCreation();
         }
      }
   }
}

