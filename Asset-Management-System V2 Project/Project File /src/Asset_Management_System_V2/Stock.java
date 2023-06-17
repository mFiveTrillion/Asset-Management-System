/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */

import java.sql.SQLException;

public class Stock extends Asset{
    
    private double shares; 
    private String ticker;
    
    public Stock(String assetIdentification, String assetType, String acqDate, double acqCost, double holdings) throws SQLException {
        
        super(assetIdentification, assetType, acqDate, acqCost, holdings);
       
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
