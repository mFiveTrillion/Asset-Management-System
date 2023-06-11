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

    public final class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:derby:assetman;create=true";
    private static final String USERNAME = "pdc";
    private static final String PASSWORD = "pdc";

    private Connection connection;

    public DatabaseManager() throws SQLException {
        
        getConnection();
        
    }
    
    public Connection getConnection()throws SQLException{
        
       try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        
     return connection;
       
    }
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
}