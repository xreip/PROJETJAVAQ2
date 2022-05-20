package accessDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Singleton {
   private static Singleton instance = null;
   private Connection connection;
   String url = "jdbc:mysql://127.0.0.1:3306/dbinstallations";
   String user = "root";
   String password = "Tigrou007";
   private static int test = 0;

   private Singleton() throws SQLException {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         this.connection = DriverManager.getConnection(url, user, password);
      } catch (ClassNotFoundException e) {
         System.out.println("Database connection Creation Failed " + e.getMessage());
      }
   }
   
   private Singleton(String user, String password) throws SQLException {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         this.connection = DriverManager.getConnection(url, user, password);
      } catch (ClassNotFoundException e) {
         System.out.println("Database connection Creation Failed " + e.getMessage());
      }
   }

   public Connection getConnection() {
      return connection;
   }

   public static Singleton getInstance() throws SQLException {
      try {
         if (instance == null) {
            instance = new Singleton();
            test++;
            System.out.println(test);
         } else if (instance.getConnection().isClosed()) {
            instance = new Singleton();
            System.out.println("Connection was closed, reopening");
         }
      } catch (SQLException e) {
         System.out.println("SQL Error " + e.getMessage());
      }
      return instance;
   }
   public static Singleton getInstanceUP(String user,String password) throws SQLException {
      try {
         if (instance == null) {
            instance = new Singleton(user,password);
            test++;
            System.out.println(test);
         } else if (instance.getConnection().isClosed()) {
            instance = new Singleton();
            System.out.println("Connection was closed, reopening");
         }
      } catch (SQLException e) {
         System.out.println("SQL Error " + e.getMessage());
      }
      return instance;
   }
}
