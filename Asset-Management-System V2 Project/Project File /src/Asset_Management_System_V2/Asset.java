/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */
public class Asset {
 
    private String assetIdentification; //Identification ID of asset (Unique to every asset in portfolio)
    private String assetType; //name (Input does not need to be unique 
    private String acqDate; //Acquisition date represented by DD-MM-YYYY (error handle the String length (11))
    private double acqCost; //Cost of acquisition of (object) asset at date (acqDate)
    private double marketValue; //curent determination of market price of asset (e.g. TSLA stock current market value) 
    private double netReturn;
    private double holdings;
  
        
    public Asset(String assetidentification, String assetType, String acqDate, double acqCost, double marketValue) {
            this.assetIdentification = assetidentification;
            this.assetType = assetType;
            this.acqDate = acqDate;
            this.acqCost = acqCost;
            this.marketValue = marketValue;
            this.netReturn = marketValue - acqCost; 
            this.holdings = acqCost + this.netReturn;
           
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
