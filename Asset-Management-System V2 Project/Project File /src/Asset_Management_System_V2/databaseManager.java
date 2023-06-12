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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



    public final class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:derby:assetman;";
    private static final String USERNAME = "pdc";
    private static final String PASSWORD = "pdc";

    private Connection connection;

    public DatabaseManager() throws SQLException {
        
        getConnection();
        
    }
    
    public Connection getConnection()throws SQLException{
        
       try {
         
           System.out.println("Connecting to jdbc:derby:assetman;");
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            
                    
            try {
                   
                        Thread.sleep(800);
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
    public void closeConnection() {
        if (connection != null) {
            try {
                
                System.out.println("Closed");
                connection.close();
           
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createAccountsTable() {

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            try (Statement statement = conn.createStatement()) {
                 String createTableSQL = "CREATE TABLE PORTFOLIO("
                    + "ID VARCHAR(50) PRIMARY KEY, "
                    + "TYPE VARCHAR(50) NOT NULL, "
                    + "ACQ_DATE VARCHAR(50) NOT NULL, "
                    + "COSTS DOUBLE, "
                    + "HOLDINGS DOUBLE, "
                    + "MARKET_VALUE DOUBLE, "
                    + "TOTAL_VALUE DOUBLE"
                    + ")";
                statement.executeUpdate(createTableSQL);
                System.out.println("Table created successfully.");
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                // X0Y32 is the SQL state for table already exists exception in Apache Derby
                System.err.println("Table already exists.");
                System.out.println("Table in use");
            } else {
                e.printStackTrace();
            }
        }
    }
    
}