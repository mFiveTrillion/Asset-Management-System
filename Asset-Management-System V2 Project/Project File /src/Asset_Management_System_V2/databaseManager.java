/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;



    public final class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:derby:assetman";
    private static final String USERNAME = "pdc";
    private static final String PASSWORD = "pdc";

    private Connection connection;

    public DatabaseManager() throws SQLException {
        
        getConnection();
        
    }
    
    public Connection getConnection()throws SQLException{ //get connection to DB 
        
       try {
         
           System.out.println("Connecting to jdbc:derby:assetman;");
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            
                    
            try {
                   
                        Thread.sleep(300);
               } catch (InterruptedException e) {
                   
                   e.printStackTrace();
               }
            
            System.out.println("Connection Successful!");
            System.out.println("Connected to jdbc:derby:assetman;");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        
     return connection;
       
    }
    
    public void closeConnection() { //close connection 
        if (connection != null) {
            try {
                
                System.out.println("Closed");
                connection.close();
           
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createPortfolioTable() throws SQLException { // create PORTFOLIO table in assetman DB, stores assets with relevant information 

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            
            System.out.println("Initiating create PORTFOLIO table sequence (Checking table state)");
            try (Statement statement = conn.createStatement()) {
                 String createTableSQL = "CREATE TABLE PORTFOLIO("
                    + "ID VARCHAR(50) PRIMARY KEY, "
                    + "TYPE VARCHAR(50) NOT NULL, "
                    + "ACQ_DATE VARCHAR(50) NOT NULL, "
                    + "ACQ_COSTS DOUBLE, "
                    + "HOLDINGS DOUBLE, "
                    + "MARKET_VALUE DOUBLE, "
                    + "TOTAL_VALUE DOUBLE"
                    + ")";
                 
                statement.executeUpdate(createTableSQL);
                System.out.println("Portfolio Table created successfully In Database, checking for confirmation");
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                boolean exists = checkTableExists(conn, "PORTFOLIO");
                System.out.println("PORTFOLIO table exists: "+ exists);
                 if (exists) {
                System.out.println("PORTFOLIO table added to database.");
            } else {
                System.out.println("PORTFOLIO table does not exist in the database.");
            }
                 
                
            }
        } catch (SQLException e) {
            
            if (e.getSQLState().equals("X0Y32")) {
                try(Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)){
                // X0Y32 is the SQL state for table already exists exception in Apache Derby
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                boolean exists = checkTableExists(conn, "PORTFOLIO");
                System.out.println("PORTFOLIO table exists: "+ exists);
                 if (exists) {
                System.err.println("PORTFOLIO table exists in the database.");
                
                
            } else {
                e.printStackTrace();
            }
        }
      }
    }
  }
    
    
   public void createTransactionsTable() throws SQLException { //create TRANSACTION table in assetman DB 

        try (Connection conn2 = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            
            System.out.println("Initiating create TRANSACTIONLIST table sequence (Checking table state)");
            try (Statement statement = connection.createStatement()) {
                 String createTableSQL = "CREATE TABLE TRANSACTIONLIST("
                    + "ID VARCHAR(50) PRIMARY KEY, "
                    + "BORS VARCHAR(50) NOT NULL, "
                    + "AMOUNT DOUBLE"
                    + ")";
                statement.executeUpdate(createTableSQL);
                System.out.println("TRANSACTIONLIST Table created successfully in Database, checking for confirmation");
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                boolean exists = checkTableExists(conn2, "TRANSACTIONLIST");
                System.out.println("TRANSACTIONLIST table exists: "+ exists);
                 if (exists) {
                System.out.println("TRANSACTIONLIST table exists in the database.");
            } else {
                System.out.println("TRANSACTIONLIST table does not exist in the database.");
            }
                
            }
        } catch (SQLException e) {
            
            if (e.getSQLState().equals("X0Y32")) {
                
                try(Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)){
                // X0Y32 is the SQL state for table already exists exception in Apache Derby
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                    boolean exists = checkTableExists(conn, "TRANSACTIONLIST");
                    System.out.println("TRANSACTIONLIST table exists: "+ exists);
                     if (exists) {
                    System.err.println("TRANSACTIONLIST table exists in the database.");
                   
            
            
            } else {
                e.printStackTrace();
            }
        }
    }
  }
   }
   
 
   public void removePortTable() { //remove portfolio table method, called to restart and depopulate tables
       
        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {

            String dropTableSQL = "DROP TABLE PORTFOLIO";
            statement.executeUpdate(dropTableSQL);
            System.out.println("PORTFOLIO table deleted successfully from the database.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
   }
       
     public void removeTransTable() { //remove transactionlist table method, called to restart and depopulate tables
       
    try (Connection conn = getConnection();
         Statement statement = conn.createStatement()) {
        
        String dropTableSQL = "DROP TABLE TRANSACTIONLIST";
        statement.executeUpdate(dropTableSQL);
        System.out.println("TRANSACTIONLIST table deleted successfully from the database.");
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
   
   
   
    public static boolean checkTableExists(Connection connection, String tableName) throws SQLException { //method to check if table exists in DB
        
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);

        return resultSet.next();
        }
    
   }



