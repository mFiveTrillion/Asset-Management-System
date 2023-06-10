/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Stock extends Asset{
    
    private double shares; 
    private String ticker;
    
    public Stock(String assetIdentification, Connection connection)throws SQLException{
       
      super(assetIdentification, connection);  
     
        
    }

    public double getShares() {
        return shares;
    }

    public String getTicker() {
        return ticker;
    }

    public void setShares(double shares) {
        this.shares = shares;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    
    
    
    @Override
    public String toString(){
    
    return "\nTicker: " + ticker + " No. of Shares: " + shares + "\n" + 
    super.toString();
    
}
    
    
}
