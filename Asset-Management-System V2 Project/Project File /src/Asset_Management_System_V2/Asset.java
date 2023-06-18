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
    private double totalValue; 
  
        
    public Asset(String assetIdentification, String type, String date, double cost, double holdings) {
        
            this.assetIdentification = assetIdentification;
            this.assetType = type;
            this.acqDate = date;
            this.acqCost = cost;
            this.holdings = holdings;
            this.marketValue = cost / holdings; // Calculate the market value correctly

            // Calculate the net return
            // Generate a random number within the range of 10% plus or minus of the market value to give a current market price 
            double randomRange = 0.05 * marketValue; // 10% of the market value
            double randomOffset = (Math.random() * 2 - 1) * randomRange; // Random value within the range [-randomRange, randomRange]
            
            this.netReturn = marketValue * holdings - randomOffset * holdings; //purchased market value * holdings to give position, minus random current price to give net return
}

    
    private void loadFromDatabase(Connection connection) throws SQLException{ //method to retrieve a specific asset from DB.
        
        String query = "SELECT TYPE, ACQ_DATE, ACQ_COSTS, MARKET_VALUE, HOLDINGS, TOTAL_VALUE FROM PORTFOLIO WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, assetIdentification);
        
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            this.assetType = resultSet.getString("TYPE");
            this.acqDate = resultSet.getString("ACQ_DATE");
            this.acqCost = resultSet.getDouble("ACQ_COSTS");
            this.marketValue = resultSet.getDouble("MARKET_VALUE");
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

        public String getAssetIdentification() {
            return assetIdentification;
        }

        public double getTotalValue() {
            return totalValue;
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
