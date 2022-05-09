package accessDB;

import java.sql.*;

public class ConnectionBD {

   private static Connection connectionDB;

   public static Connection connect() throws SQLException {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         System.out.println("Pilotes chargés");
      } catch (ClassNotFoundException e) {
         System.out.println("Pilotes non chargés, aie lol");
      }

      String url = "jdbc:mysql://127.0.0.1:3306/dbinstallations";

      connectionDB = null;
      try {
         connectionDB = DriverManager.getConnection(url, "root", "Tigrou007");
         System.out.println("Connexion à la DB réussie");
      } catch (SQLException ex) {
         System.out.println("SQLException: " + ex.getMessage());
         System.out.println("SQLState: " + ex.getSQLState());
         System.out.println("VendorError: " + ex.getErrorCode());
      }
      return connectionDB;
   }

   public static Object[] creerListe1Colonne(PreparedStatement prepStat)
         throws SQLException {
      int max;
      Object[] uneColonne;
      int index = 0;

      String stringLu;
      int entierLu;
      float floatLu;
      double doubleLu;
      boolean booleenLu;
      java.util.Date dateLue;

      ResultSet donnees2 = prepStat.executeQuery();
      max = 0;
      while (donnees2.next()) {
         max++;
      }

      uneColonne = new Object[max];

      ResultSet donnees = prepStat.executeQuery();
      ResultSetMetaData meta = donnees.getMetaData();

      while (donnees.next()) {
         switch (meta.getColumnType(1)) {
            case Types.VARCHAR:
               stringLu = donnees.getString(1);
               if (donnees.wasNull() == false) {
                  uneColonne[index] = stringLu;
                  index++;
               }
               break;
            case Types.CHAR:
               stringLu = donnees.getString(1);
               if (donnees.wasNull() == false) {
                  uneColonne[index] = stringLu;
                  index++;
               }
               break;
            case Types.INTEGER:
               entierLu = donnees.getInt(1);
               if (donnees.wasNull() == false) {
                  uneColonne[index] = new Integer(entierLu);
                  index++;
               }
               break;
            case Types.SMALLINT:
               entierLu = donnees.getInt(1);
               if (donnees.wasNull() == false) {
                  uneColonne[index] = new Integer(entierLu);
                  index++;
               }
               break;
            case Types.TINYINT:
               entierLu = donnees.getInt(1);
               if (donnees.wasNull() == false) {
                  uneColonne[index] = new Integer(entierLu);
                  index++;
               }
               break;
            case Types.REAL:
               floatLu = donnees.getFloat(1);
               if (donnees.wasNull() == false) {
                  uneColonne[index] = new Float(floatLu);
                  index++;
               }
               break;
            case Types.DOUBLE:
               doubleLu = donnees.getDouble(1);
               if (donnees.wasNull() == false) {
                  uneColonne[index] = new Double(doubleLu);
                  index++;
               }
               break;
            case Types.TIMESTAMP:
               dateLue = donnees.getDate(1);
               if (donnees.wasNull() == false) {
                  uneColonne[index] = dateLue;
                  index++;
               }
               break;
            case Types.BIT:
               booleenLu = donnees.getBoolean(1);
               if (donnees.wasNull() == false) {
                  uneColonne[index] = new Boolean(booleenLu);
                  index++;
               }
               break;
         }
      }
      return uneColonne;
   }
}
