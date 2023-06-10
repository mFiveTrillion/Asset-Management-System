/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.*;

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

public class Portfolio {
    
    private List<Asset> portfolio;
    private double totalValueOfAssetsUnderManagement;
    private double simpleReturn = 0.0;
    
    public Portfolio( ){
       
        this.portfolio = new ArrayList<>();
        
    }

    public List<Asset> getPortfolio() {
        return portfolio;
    }
    
    
    public void addAssetToPort(Asset asset){
     
        portfolio.add(asset);
    }
    
    public int numOfAssetsUnderManagement(){ //computes and returns an integer value representing the total number of assets under management
     
    int numberOfAssetsUnderManagement = 0;

        for(Asset asset: portfolio){

         numberOfAssetsUnderManagement += 1;    

        
            }
        
        return numberOfAssetsUnderManagement;
        }

    public double getTotalPortValue(){ //computes the total value of portfolio at market value of each asset
     
        double portTotal = 0.0;
        
        for(Asset asset : portfolio){
            
         portTotal += asset.getMarketValue();
            
        }
       
        
        return portTotal; 
    }
    
    public double getPortfolioTotalNet(){ //computes the net gain/loss on each asset totalling to give the overall net gain/loss of the portfolio
        
        double portfolioNet = 0.0;
        
            for(Asset asset : portfolio){
             
                portfolioNet += asset.getNetReturn();
            }
            
        return portfolioNet;
       }
    
    public static String getSimpleReturn(List<Asset> portfolio){ //computes percent value of net gain/loss
        
        double simpleReturn = 0.0;
        double portfolioNet = 0.0; 
        
        for(Asset asset : portfolio){
         
            portfolioNet += asset.getNetReturn();
            
        }
        
        
        return "Simple Return: " + simpleReturn + "%"; 
        
    }
    
    public Map<String, Double> getNetMap(List<Asset> portfolio){ //create a map with keys of the asset ID and the double netValue(the loss/gain) of each asset within the array list
        
        Map<String, Double> netMap = new HashMap<>();
        
        for(Asset asset: portfolio){
            double percentGain = asset.getNetReturn() / asset.getAcqCost() * 100;
            netMap.put(asset.getAssetidentification(),percentGain);
            
        }
      
        return netMap;
    }
    
       public double getAssetReturn(String assetIdentification, Map<String, Double> netMap){   
         
           if(netMap.containsKey(assetIdentification)){
            
               return netMap.get(assetIdentification);
           }else{
               
            throw new IllegalArgumentException("Asset ID not found");   
           }
          
     }
       
   public Set<Asset> getAssetSet(List<Asset> portfolio) {
    
       Set<Asset> assetSet = new LinkedHashSet<>(portfolio);
        return assetSet;
    
    }
    
    public void buyAsset(Portfolio portfolio, String newAssetID){ //allows user to pass in a new asset, search the hashset if it already exists - if so, the asset is added to that already there, if not the new asset is added to set and list array
       
        Scanner scan = new Scanner(System.in);
        boolean found = false;
        for(Asset asset : portfolio.getPortfolio()){
            
                if(asset.getAssetidentification().equals(newAssetID)){

                    double acqCostOfNewPurchase = 0.0;
                    System.out.println("\nAsset found!");
                    System.out.println("Enter acquisition Cost: (Dollar Amount)");
                    acqCostOfNewPurchase = scan.nextDouble();

                    found = true; 
                    System.out.println("Bought " + "$" + acqCostOfNewPurchase + " more of" + asset.getAssetidentification());
                    
                    
                    
                    System.out.println("\nNow hold $" + asset.getHoldings() + " of " + asset.getAssetidentification());
                   
                   
                            
                }
                
        }
        if(!found){

                 System.out.println("Enter the asset type:");
                 String assetType = scan.nextLine();
                 System.out.println("Enter the acquisition date (MM/DD/YYYY):");
                 String acqDate = scan.nextLine();
                 System.out.println("Enter the acquisition cost:");
                 double acqCost = scan.nextDouble();
                 System.out.println("Enter amount (e.g. # of sahres ");
                 double holdings = scan.nextDouble();
                 System.out.println("Enter the market value:");
                 double marketValue = scan.nextDouble();

               Asset newAsset = new Asset(newAssetID, assetType, acqDate, acqCost, marketValue);

                 System.out.println("New asset: " + newAssetID + " added to portfolio");
              
                 portfolio.getPortfolio().add(newAsset)z

                }

    }
     public void sellAsset(Set<Asset> assets, String assetID){ //method to sell all or part of holdings for a specific asset defined by user input and passed in as parameters
         
        Scanner scan = new Scanner(System.in);
        boolean found = false;
        for(Asset asset : assets){
            
            if(asset.getAssetidentification().equals(assetID)){
                
                found = true;
                double sellDollarAmount = 0.0;
                char sellAllQuery;
                
                System.out.println("Asset found! \n");
                System.out.println("Sell All? (Y for Yes, N for No)");
                sellAllQuery = scan.next().charAt(0);
                
                if(sellAllQuery == 'Y' || sellAllQuery == 'y'){
                    
                    assets.remove(asset);
                    
                }
                
                if(sellAllQuery == 'N' || sellAllQuery == 'n'){
                
                    double newHoldingsAmount = 0.0;    
                    System.out.println("Enter sell amount: (Dollar amount)");
                    sellDollarAmount = scan.nextDouble();

                    boolean validInput = false; 
                    if(sellDollarAmount == asset.getHoldings()){
                        assets.remove(asset);
                        validInput = true;

                    }else if(sellDollarAmount < asset.getHoldings()){

                        asset.updateHoldingsSell(sellDollarAmount);
                        System.out.println("Sold " + "$" + sellDollarAmount + " of " + asset.getAssetidentification());
                        System.out.println("\nNow hold $" + asset.getHoldings() + " of " + asset.getAssetidentification());
                        validInput = true;

                    }
                    if(!validInput){
                        System.err.println("Cannot sell more than you hold");   
                    }
                
            }
            
            
            }
            if(!found){
            System.err.println("ERROR: Asset not found");
           }
        }
     }
    
    @Override
    public String toString(){
     
        String str = "ASSETS: ";
        
        for(Asset asset : portfolio){
          
            str +=  "\n" + asset.toString();
            
        }
        
        return str; 
        
        
    }
    
    public void updateDB  (String ID, String type, String date, double acq_costs, double marketValue, double amountBought, Connection connection, String buyOrSell, String foundOrNot)throws SQLException{
        
      switch(buyOrSell){
          
          case "BUY":
              
              if(foundOrNot == "F"){
                  
                  String query = "SELECT HOLDINGS FROM PORTFOLIO WHERE ID = ?";
                  PreparedStatement statement = connection.prepareStatement(query);
                  
                  statement.setString(1, ID);
                  
                  ResultSet resultSet = statement.executeQuery();
                  
                  if(resultSet.next()){
                      
                    double holdings = resultSet.getDouble("HOLDINGS");
                    double newHoldings = holdings + amountBought;
                    double currentAcq_costs = resultSet.getDouble("ACQ_COSTS");
                    double newAcq_costs = currentAcq_costs + acq_costs;
                    
                    
                    String updateQuery = "UPDATE PORTFOLIO SET HOLDINGS = ? WHERE ID = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(query);
                     
                   updateStatement.setDouble(6, newHoldings);
                   updateStatement.setDouble(4, newAcq_costs);
                   updateStatement.setString(1, ID);
                   
                   updateStatement.execute();
                   
                   System.out.println("Database updated with purchase");
                      
                      
                  }else{
                      
                       String insertQuery = "INSERT INTO PORTFOLIO (ID, ASSET_TYPE, ACQUISITION_DATE, ACQUISITION_COST, MARKET_VALUE) VALUES (?, ?, ?, ?, ?)";
                        statement = connection.prepareStatement(insertQuery);

                        statement.setString(1, ID );
                        statement.setString(2, type);
                        statement.setString(3, date);
                        statement.setDouble(4, acq_costs);
                        statement.setDouble(5, marketValue);

                        statement.executeUpdate();

                        System.out.println("New asset added to the database successfully.");
                      
                      
                  }
              }else{
                  
                  
                  
                  
              }
              
              
              
              break;
          
          case "SELL":
              
              if(foundOrNot == "NF"){
                  
                  
                  
                  
              }else{
                  
                  
                  
                  
              }
              
              
              
              break;
              
              
              
          default: 
              System.err.println("ERROR EXECUTING BUY/SELL FUNCTION");
          
          
          
          
          
          
          
      }
        
        
        
        
    }
    
    
    
}
  


    
    

