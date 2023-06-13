/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Asset_Management_System_V2;

import java.sql.SQLException;

/**
 *
 * @author hayden
 */
public class App_Asset_Management_System_App {

    /**
     * 
     */
    public static void main(String[] args) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.getConnection();
        db.createPortfolioTable();
        db.createTransactionsTable();
        
        
        Portfolio p1 = new Portfolio();
        MainMenuGUI gui = new MainMenuGUI();
        gui.frame.setVisible(true);
      
    }
    
}
