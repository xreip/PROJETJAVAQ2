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


public class PanneauUninstall extends JPanel {
   private JLabel osChoice, idChoice;
   private JComboBox<Object[]> osChoiceComboBox, idChoiceComboBox;
   // private Connection Connect2;
   private Singleton instance;
   private Connection DBconnect;
   private Object[] liste;
   private TableModelGen model;
   private JTable myTable;
   private JScrollPane defilant;
   private JButton supprimer, selectionner;
   private JPanel main1, main2;
   private JPanel tablePanel;

   private static final String couleurbg = "#F4F4F4";
   private static final String couleurTxt = "#F4F4F4";

   public PanneauUninstall() {
      // this.setLayout(new GridLayout(8, 2, 10, 20));
      this.setBackground(Color.decode(couleurbg));
      main1 = new JPanel(new FlowLayout());
      main2 = new JPanel(new GridLayout(1, 2));
      main1.setLayout(new GridLayout(2, 2));
      // main2.setLayout(new GridLayout(2, 2));
      main1.setBackground(Color.decode("#222222"));
      main2.setBackground(Color.decode("#222222"));

      main2.setMinimumSize(new Dimension(200, 200));

      // OSCHOICE
      osChoice = new JLabel("CHOIX DE L'OS : ");
      osChoice.setForeground(Color.white);
      osChoice.setHorizontalAlignment(JLabel.CENTER);
      main1.add(osChoice);

      try {
         instance=Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "select Libelle from OS";

         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
         liste = ConnectionBD.creerListe1Colonne(pst);
      } catch (SQLException e) {
         System.out.println("Is not possible for connexion");
      } finally {
         try {
            DBconnect.close();
            System.out.println("Connection fermée pour les OS");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      osChoiceComboBox = new JComboBox(liste);
      main1.add(osChoiceComboBox);

      this.add(main1);

      // TABLE
      tablePanel = new JPanel();
      try {
         instance=Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "select IdInstallation, DateInstallation, CodeSoftware, Matricule, CodeOS  from Installation;";

         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
         // pst.setString(1,String.valueOf(osChoiceComboBox));
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
            DBconnect.close();
            System.out.println("Connection fermée");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }

      this.add(tablePanel);

      // ID CHOICE
      idChoice = new JLabel("ID à supprimer : ");
      idChoice.setHorizontalAlignment(JLabel.CENTER);
      idChoice.setForeground(Color.white);
      main2.add(idChoice);

      try {
         instance=Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "select IdInstallation from Installation as i INNER JOIN os as os ON i.CodeOs=os.CodeOs WHERE Libelle= ? ;";
         // String requeteSQL = "select IdInstallation from Installation as i order by
         // IdInstallation";

         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
         pst.setString(1, String.valueOf(osChoiceComboBox.getSelectedItem()));
         liste = ConnectionBD.creerListe1Colonne(pst);
      } catch (SQLException e) {
         System.out.println("Is not possible for connexion");
      } finally {
         try {
            DBconnect.close();
            System.out.println("Connection fermée pour les OS");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      idChoiceComboBox = new JComboBox(liste);
      main2.add(idChoiceComboBox);

      this.add(main2);

      // BOUTONs
      selectionner = new JButton("Selectionner");
      main1.add(selectionner);

      supprimer = new JButton("Supprimer");
      this.add(supprimer);

      this.setBackground(Color.decode("#222222"));

      MonGestionnaireAction g = new MonGestionnaireAction();
      supprimer.addActionListener(g);
      selectionner.addActionListener(g);
      osChoiceComboBox.addActionListener(g);
   }

   private void selectLine() {
      tablePanel.removeAll();
      try {
         instance=Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "select IdInstallation, DateInstallation, CodeSoftware, Matricule, i.CodeOS  from Installation as i INNER JOIN os as os ON i.CodeOs=os.CodeOs WHERE Libelle= ? ;";

         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
         System.out.println(String.valueOf(osChoiceComboBox.getSelectedItem()));
         pst.setString(1, String.valueOf(osChoiceComboBox.getSelectedItem()));
         System.out.println(pst.toString());
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
         idCreation();
      } catch (SQLException e) {
         System.out.println("ça marche pas lol");
      } finally {
         try {
            DBconnect.close();
            System.out.println("Connection fermée");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }

   private void deleteLineConfirmation() {
      int result = JOptionPane.showConfirmDialog(this, "Souhaitez-vous vraiment supprimer ?");
      if (result == 0) {
         deleteLineDB();
      } else if (result == 1) {
         System.out.println("Annulation");
      }
   }

   private void deleteLineDB() {
      try {
         instance=Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "DELETE FROM installation WHERE IdInstallation=?";
         // String requeteSQL = "select IdInstallation from Installation as i order by
         // IdInstallation";

         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
         pst.setInt(1, Integer.parseInt(String.valueOf(idChoiceComboBox.getSelectedItem())));
         pst.executeUpdate();
      } catch (SQLException e) {
         System.out.println("impossible de supprimer");
      } finally{
         try {
            DBconnect.close();
            System.out.println("Connection fermée pour la deletion");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }

   private void idCreation() {
      // ID CHOICE
      main2.removeAll();
      idChoice = new JLabel("ID à supprimer : ");
      idChoice.setHorizontalAlignment(JLabel.CENTER);
      idChoice.setForeground(Color.white);
      main2.add(idChoice);

      try {
         instance=Singleton.getInstance();
         DBconnect = instance.getConnection();

         String requeteSQL = "select IdInstallation from Installation as i INNER JOIN os as os ON i.CodeOs=os.CodeOs WHERE Libelle= ? ;";

         PreparedStatement pst = DBconnect.prepareStatement(requeteSQL);
         pst.setString(1, String.valueOf(osChoiceComboBox.getSelectedItem()));
         liste = ConnectionBD.creerListe1Colonne(pst);
      } catch (SQLException e) {
         System.out.println("Is not possible for connexion");
      } finally {
         try {
            DBconnect.close();
            System.out.println("Connection fermée");
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      idChoiceComboBox = new JComboBox(liste);
      main2.add(idChoiceComboBox);
      main2.validate();
      main2.repaint();
   }

   private class MonGestionnaireAction implements ActionListener {

      @Override
      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == supprimer) {
            deleteLineConfirmation();
         } else if (e.getSource() == selectionner) {
            selectLine();
         } else if (e.getSource() == osChoiceComboBox) {
            selectLine();
         } 
      }
   }
}
