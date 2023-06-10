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

public class databaseManager {
    private static final String DATABASE_URL = "jdbc:derby:PORTFOLIO";
    private static final String USERNAME = "pdc";
    private static final String PASSWORD = "pdc";

    private Connection connection;

    public databaseManager() {
        
        getConnection();
        
    }
    
    public void getConnection(){
        
       try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        
        
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