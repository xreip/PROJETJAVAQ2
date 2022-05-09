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


public class PanneauUninstall extends JPanel {
   private JLabel osChoice;
   private JComboBox<Object[]> osChoiceComboBox;
   private Connection connect2;
   private Object[] liste;

   private static final String couleurbg="#F4F4F4";
   private static final String couleurTxt="#F4F4F4";

   public PanneauUninstall(){
      this.setLayout(new GridLayout(8, 2, 10, 20));
      // this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.setBackground(Color.decode(couleurbg));

      osChoice = new JLabel("CHOIX DE L'OS : ");
      this.add(osChoice);

      
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
      this.add(osChoiceComboBox);


   }
   
}
