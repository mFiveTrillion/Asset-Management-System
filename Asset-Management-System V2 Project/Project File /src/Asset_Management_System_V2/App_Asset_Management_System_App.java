/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Asset_Management_System_V2;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hayden
 */
public class App_Asset_Management_System_App {

   
    public static void main(String[] args) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        db.getConnection();
        
       //uncomment to restart 
       
       //db.removePortTable();
       //db.removeTransTable();
        
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(App_Asset_Management_System_App.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        
        db.createPortfolioTable();
        db.createTransactionsTable();
        
        
        Portfolio p1 = new Portfolio();
        MainMenuGUI gui = new MainMenuGUI();
        gui.frame.setVisible(true);
      
        
    }
    
}
