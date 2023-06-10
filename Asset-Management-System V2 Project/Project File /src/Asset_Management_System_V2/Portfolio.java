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
    public Asset asset; 
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
    
    public void addAssetToPort(Connection connection) throws SQLException {
        
        String query = "SELECT ID FROM PORTFOLIO";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String assetIdentification = resultSet.getString("ID");
            Asset asset = new Asset(assetIdentification, connection);
            portfolio.add(asset);
        }
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
    
   public void buyAsset(Connection connection)throws SQLException{
       
     Scanner scan = new Scanner(System.in);
     String newAsset;
     System.out.println("What asset would you like to buy: ");
     newAsset = scan.nextLine();
     
     boolean checkAssetExists = isAssetFound(newAsset, connection);
   
     if(checkAssetExists == true){
      
          System.out.println("\nAsset found!");
                    System.out.println("Enter acquisition Cost: (Dollar Amount)");
                    double acqCostOfNewPurchase = scan.nextDouble();
                   

                  
                    System.out.println("Bought " + "$" + acqCostOfNewPurchase + " more of" + newAsset);
                    
                    System.out.println("Updating database...");
                    
                    buyMoreOfExistingAsset(newAsset, acqCostOfNewPurchase, connection, checkAssetExists);
                    
                       
     }else if(checkAssetExists == false){
         
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
         
                 buyNewAsset(newAsset,assetType,acqDate,acqCost,marketValue,holdings,connection,checkAssetExists);        
         
         
     }
   
   }
   
   
   public void sellAsset(Connection connection)throws SQLException{
       
        Scanner scan = new Scanner(System.in);
       
         String assetToSell;
        System.out.println("What asset would you like to sell: ");
        assetToSell = scan.nextLine();
     
     boolean checkAssetExists = isAssetFound(assetToSell, connection);
       
       if(checkAssetExists == true){
           

                double sellDollarAmount = 0.0;
                char sellAllQuery;
                
                System.out.println("Asset found! \n");
                System.out.println("Sell All? (Y for Yes, N for No)");
                sellAllQuery = scan.next().charAt(0);
                
                if(sellAllQuery == 'Y' || sellAllQuery == 'y'){
                    
                    sellAll(assetToSell,connection);
                    
                }else if(sellAllQuery == 'N' || sellAllQuery == 'n'){
                
                    double sellAmount = 0.0;    
                    System.out.println("Enter sell amount: (Dollar amount)");
                    sellAmount = scan.nextDouble();
                
                    sellPartOfAsset(assetToSell, sellAmount, connection, checkAssetExists);
                
                
                    }else{
                    
                    System.out.println("Invalid input");   
                    
                    
                }
           
           
       }
       
   }
   
    
    public void buyMoreOfExistingAsset(String ID, double acq_costs, Connection connection, boolean found)throws SQLException{ //method that is called if asset is found in database
        
         if(found == true){
                  
                  String query = "SELECT HOLDINGS FROM PORTFOLIO WHERE ID = ?";
                  PreparedStatement statement = connection.prepareStatement(query);
                  
                  statement.setString(1, ID);
                  
                  ResultSet resultSet = statement.executeQuery();
                  
                  if(resultSet.next()){
                      
                    double holdings = resultSet.getDouble("HOLDINGS");
                    double newHoldings = holdings + acq_costs;
                    double currentAcq_costs = resultSet.getDouble("ACQ_COSTS");
                    double newAcq_costs = currentAcq_costs + acq_costs;
                    
                    
                    String updateQuery = "UPDATE PORTFOLIO SET HOLDINGS = ? WHERE ID = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(query);
                     
                   updateStatement.setDouble(6, newHoldings);
                   updateStatement.setDouble(4, newAcq_costs);
                   updateStatement.setString(1, ID);
                   
                   updateStatement.execute();
                   
                   System.out.println("Asset on database updated with purchase");
                      
                      
                  }
        
        
        
         }
    }
         
   public void buyNewAsset(String ID, String type, String date, double acq_costs, double marketValue, double holdings, Connection connection, boolean foundOrNot)throws SQLException{
       
        String insertQuery = "INSERT INTO PORTFOLIO (ID, ASSET_TYPE, ACQ_DATE, ACQ_COST, MARKET_VAL, HOLDINGS) VALUES (?, ?, ?, ?, ?, ?)";
                  PreparedStatement statement = connection.prepareStatement(insertQuery);
                        statement = connection.prepareStatement(insertQuery);

                        statement.setString(1, ID );
                        statement.setString(2, type);
                        statement.setString(3, date);
                        statement.setDouble(4, acq_costs);
                        statement.setDouble(5, marketValue);
                        statement.setDouble(6, holdings);

                        statement.executeUpdate();

                        System.out.println("Brand new asset added to the database successfully.");
       
   }
    
   public void sellPartOfAsset(String ID, double dollarSellAmount, Connection connection, boolean found)throws SQLException{
       
       if(found == true){
                  
                  String query = "SELECT HOLDINGS FROM PORTFOLIO WHERE ID = ?";
                  PreparedStatement statement = connection.prepareStatement(query);
                  
                  statement.setString(1, ID);
                  
                  ResultSet resultSet = statement.executeQuery();
                  
                  if(resultSet.next()){
                    
                   
                    double holdings = resultSet.getDouble("HOLDINGS");
                    if(dollarSellAmount < holdings){
                    double newHoldings = holdings - dollarSellAmount;
                    String updateQuery = "UPDATE PROTFOLIO SET HOLDINGS = ? WHERE ID = ?";
                   
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

                    updateStatement.setString(1, ID);
                    updateStatement.setDouble(6, newHoldings);
                     }else{
                        
                        System.err.println("Invalid input, you only have " + holdings + "to sell");   
                        
                    }
       
       
       
            }
       }
   }
   
   public void sellAll(String ID, Connection connection)throws SQLException{
       
        String deleteQuery = "DELETE FROM PORTFOLIO WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(deleteQuery);

        statement.setString(1, ID);

        int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {

                System.out.println("Asset sold and removed from the database.");

            } else {

                   System.out.println("Asset not found in the database.");
              }
       
       
   }
    
      
    public boolean isAssetFound(String ID, Connection connection) throws SQLException {
        
        String query = "SELECT COUNT(*) FROM PORTFOLIO WHERE ID = ?";
       
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, ID);

        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }

        return false;
    }  
    
     @Override
    public String toString(){
     
        String str = "ASSETS: ";
        
        for(Asset asset : portfolio){
          
            str +=  "\n" + asset.toString();
            
        }
        
        return str; 
        
        
    }
    
}
  


    
    

