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

public class Asset {
 
    private String assetIdentification; //Identification ID of asset (Unique to every asset in portfolio)
    private String assetType; //name (Input does not need to be unique 
    private String acqDate; //Acquisition date represented by DD-MM-YYYY (error handle the String length (11))
    private double acqCost; //Cost of acquisition of (object) asset at date (acqDate)
    private double marketValue; //curent determination of market price of asset (e.g. TSLA stock current market value) 
    private double netReturn;
    private double holdings;
  
        
    public Asset(String assetidentification, Connection connection) throws SQLException{
            this.assetIdentification = assetIdentification;
            loadFromDatabase(connection);
                   
           
        }
    
    private void loadFromDatabase(Connection connection) throws SQLException{
        
        String query = "SELECT TYPE, ACQ_DATE, ACQ_COSTS, MARKET_VAL, HOLDINGS, TOTAL_ASSET_VALUE FROM PORTFOLIO WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, assetIdentification);
        
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            assetType = resultSet.getString("TYPE");
            acqDate = resultSet.getString("ACQ_DATE");
            acqCost = resultSet.getDouble("ACQ)_COST");
            marketValue = resultSet.getDouble("MARKET_VAL");
        } else {
            throw new IllegalArgumentException("Asset with identification " + assetIdentification + " not found in the database.");
        }
    
        
    }
            public String getAssetidentification() {
                return assetIdentification;
            }

            public String getAssetType() {
                return assetType;
            }

            public String getAcqDate() {
                return acqDate;
            }

            public double getAcqCost() {
                return acqCost;
            }

            public double getMarketValue() {
                return marketValue;
            }

            public double getNetReturn() {
                return netReturn;
            }

            public double getHoldings() {
                return holdings;
            }

    public void setAssetIdentification(String assetIdentification) {
        this.assetIdentification = assetIdentification;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public void setAcqDate(String acqDate) {
        this.acqDate = acqDate;
    }

    public void setAcqCost(double acqCost) {
        this.acqCost = acqCost;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }
            
            
            
            
    public void setHoldings(double holdings) {
        this.holdings = holdings;
    }

            
   
     public void updateHoldingsBuy(double buyCostAmount){          
         
      this.marketValue += buyCostAmount;
      
                   
     }

     public void updateHoldingsSell(double sellCostAmount){          
         
      double newHoldings = 0.0;
      newHoldings += holdings;
      newHoldings -= sellCostAmount; 
      
      newHoldings = this.holdings;
                   
     }

  
    public String toString(){
        
     return "Asset ID: " + assetIdentification +
            "\nAsset Type: " + assetType + 
            "\nAcquisition Cost: $" + acqCost + " || Acquired on: " + acqDate + 
            "\nMarket value: $" + marketValue +  "|| Total return: $" + netReturn + "\n"; 
          
        
    }
    
    
    
}
