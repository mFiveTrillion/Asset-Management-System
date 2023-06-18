/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */

import java.util.Map;
import java.util.HashMap;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Portfolio {
    
    private List<Asset> portfolio;
    public Asset asset; 
    private double totalValueOfAssetsUnderManagement;
    private double simpleReturn = 0.0;
    private PopUpMessageWindow p = new PopUpMessageWindow();
    
    public Portfolio( ){
       
        this.portfolio = new ArrayList<>();
        
    }

    public List<Asset> getPortfolioList() {
        return portfolio;
    }
    
    
    public void addAssetsToPortList(Asset asset){
     
        portfolio.add(asset);
        
    }
    
    public void addAssetToPortFromDB(Connection connection) throws SQLException {
        
        portfolio.clear();
        
        String query = "SELECT ID, TYPE, ACQ_DATE, ACQ_COSTS, MARKET_VALUE, HOLDINGS FROM PORTFOLIO";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String assetIdentification = resultSet.getString("ID");
            String assetType = resultSet.getString("TYPE");
            String acqDate = resultSet.getString("ACQ_DATE");
            double acqCost = resultSet.getDouble("ACQ_COSTS");
            double marketValue = resultSet.getDouble("MARKET_VALUE");
            double holdings = resultSet.getDouble("HOLDINGS");

            Asset asset = new Asset(assetIdentification, assetType, acqDate, acqCost, holdings);
            portfolio.add(asset);
        }
}
    
    public int numOfAssetsUnderManagement(){ //computes and returns an integer value representing the total number of assets under management
        
     return portfolio.size();
                   
    }

    public double getTotalPortValue(Connection connection) throws SQLException {
        
            double portTotal = 0.0;

            String query = "SELECT * FROM PORTFOLIO";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    double positionHoldings = resultSet.getDouble("HOLDINGS");
                    double marketValue = resultSet.getDouble("MARKET_VALUE");
                    double assetTotal = positionHoldings * marketValue;
                    portTotal += assetTotal;
                }
            } catch (SQLException e) {
                
                e.printStackTrace();
            }

            return portTotal;
}
    
    public double getPortfolioTotalNet(Connection connection) throws SQLException{ //computes the net gain/loss on each asset totalling to give the overall net gain/loss of the portfolio
        
        double portfolioNet = 0.0;
        
        addAssetToPortFromDB(connection);
        
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
             
            p.displayPopUp("Asset ID not found");
            throw new IllegalArgumentException("Asset ID not found");   
           
            
           }
          
     }
       
   public Set<Asset> getAssetSet(List<Asset> portfolio) {
    
       Set<Asset> assetSet = new LinkedHashSet<>(portfolio);
        return assetSet;
    
    }
    
   public void buyAsset(Connection connection, Asset newAsset, Portfolio portfolio)throws SQLException{
   
     boolean checkAssetExists = isAssetFound(newAsset.getAssetIdentification(), connection, portfolio);

     while(true){

       if (!newAsset.getAssetidentification().isEmpty() && newAsset.getAssetIdentification().matches("[a-zA-Z]+")) {
           
           break;
           
             } else {
           
                 System.out.println("Invalid asset ID. Please enter a valid string.");
                 p.displayPopUp("Invalid asset ID. Please enter a valid string.");
             }
     }
        if(checkAssetExists){

             System.out.println("\nAsset found! // buyMoreOfExistingAsset executed //");

             System.out.println("(purchase order recieved) Updating " + newAsset.getAssetIdentification() + " in database...");
             System.out.println("Bought " + "$" + newAsset.getAcqCost() + " more of " + newAsset.getAssetIdentification());
             p.displayPopUp("Bought " + "$" + newAsset.getAcqCost() + " more of " + newAsset.getAssetIdentification());


             buyMoreOfExistingAsset(newAsset.getAssetIdentification(), newAsset.getAcqCost(), newAsset.getHoldings(), connection, checkAssetExists, newAsset.getNetReturn());


        }else{

               boolean assetAlreadyExistsInPortfolio = false;
              for (Asset asset : portfolio.getPortfolioList()) {
                  if (newAsset.getAssetIdentification().equals(asset.getAssetIdentification())) {
                      assetAlreadyExistsInPortfolio = true;
                      break;
                  }
              }

                   if (assetAlreadyExistsInPortfolio) {
                       System.out.println("Asset already exists in the portfolio.");
                       buyMoreOfExistingAsset(newAsset.getAssetIdentification(), newAsset.getAcqCost(),
                               newAsset.getHoldings(), connection, checkAssetExists, newAsset.getNetReturn());

                       p.displayPopUp("Asset already exists in the portfolio.");

                   } else {

                       buyNewAsset(newAsset.getAssetIdentification(), newAsset.getAssetType(), newAsset.getAcqDate(),
                               newAsset.getAcqCost(), newAsset.getMarketValue(), newAsset.getHoldings(),
                               connection, checkAssetExists, newAsset.getNetReturn());

                       System.out.println("//buyNewAsset executed // Brand new asset added to the database successfully.");
                       p.displayPopUp("Bought " +newAsset.getAcqCost() + " of " + newAsset.getAssetIdentification());
                   }
        }
        
        Transaction buyTrans = new Transaction(newAsset.getAssetIdentification(), "BUY", newAsset.getAcqCost());
        buyTrans.insertTransaction(connection);
     } 
   
   public void sellAsset(Connection connection, Portfolio portfolio)throws SQLException{
       
        Scanner scan = new Scanner(System.in);
       
         String assetToSell;
         
         while(true){
         
     System.out.println("What asset would you like to sell: ");
     assetToSell = scan.nextLine();
       
     if (!assetToSell.isEmpty() && assetToSell.matches("[a-zA-Z]+")) {
              break;
          } else {
              System.out.println("Invalid asset ID. Please enter a valid string.");
              p.displayPopUp("Invalid asset ID. Please enter a valid string.");
          }
        System.out.println("What asset would you like to sell: ");
        assetToSell = scan.nextLine();
     
     boolean checkAssetExists = isAssetFound(assetToSell, connection, portfolio );
       
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
                    
                    p.displayPopUp("Invalid input Y or N");
                    System.out.println("Invalid input");   
                    
                    
                }
           
           
       }else{
        
           p.displayPopUp("Asset not found");
           System.out.println("Asset not found");
           return; 
            }
         }
   }
   
    
    public void buyMoreOfExistingAsset(String ID, double acq_costs, double tangibleAmount, Connection connection, boolean found, double netReturn)throws SQLException{ //method that is called if asset is found in database
        
         if(found == true){
                  
                 System.out.println("\nAsset found! // buyMoreOfExistingAsset executed //");
                  String query =  "SELECT HOLDINGS, ACQ_COSTS FROM PORTFOLIO WHERE ID = ?";
                  PreparedStatement statement = connection.prepareStatement(query);
                  
                  statement.setString(1, ID);
                  
                  ResultSet resultSet = statement.executeQuery();
                  
                  if(resultSet.next()){
                      
                    double holdings = resultSet.getDouble("HOLDINGS");
                    double newHoldings = holdings + tangibleAmount;
                    
                    double currentAcq_costs = resultSet.getDouble("ACQ_COSTS");
                    double newAcq_costs = currentAcq_costs + acq_costs;
                    
                    
                    String updateQuery = "UPDATE PORTFOLIO SET HOLDINGS = ?, ACQ_COSTS = ?, TOTAL_VALUE = ? WHERE ID = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                     
                   updateStatement.setDouble(1, newHoldings);
                   updateStatement.setDouble(2, newAcq_costs);
                   
                   updateStatement.setDouble(3, netReturn);
                   updateStatement.setString(4, ID);
                   
                   updateStatement.execute();
                   
                   System.out.println("Asset on database updated with purchase");
                   //p.displayPopUp("Asset on database updated with purchase"); unnecessary pop up 
                      
                   for (Asset asset : portfolio) {
                        if (ID.equals(asset.getAssetIdentification())) {
                            System.out.println(ID + " found in List");
                            double updatedAcq_Costs = asset.getAcqCost() + acq_costs;
                            asset.setAcqCost(updatedAcq_Costs);
                            double updatedAmount = asset.getHoldings() + tangibleAmount;
                        }
                    }
                  }
        
        
        
         }
         
       
    }
         
   public void buyNewAsset(String ID, String type, String date, double acq_costs, double marketValue, double holdings, Connection connection, boolean foundOrNot, double netReturn)throws SQLException{
       
        String insertQuery = "INSERT INTO PORTFOLIO (ID, TYPE, ACQ_DATE, ACQ_COSTS, MARKET_VALUE, HOLDINGS, TOTAL_VALUE) VALUES (?, ?, ?, ?, ?, ?, ?)";
                  PreparedStatement statement = connection.prepareStatement(insertQuery);
                        statement = connection.prepareStatement(insertQuery);

                        statement.setString(1, ID );
                        statement.setString(2, type);
                        statement.setString(3, date);
                        statement.setDouble(4, acq_costs);
                        statement.setDouble(5, marketValue);
                        statement.setDouble(6, holdings);
                        statement.setDouble(7, netReturn);

                        statement.executeUpdate();

       Asset asset = new Asset(ID, type, date, acq_costs, holdings);
       portfolio.add(asset);
       
       
   }
    
   public void sellPartOfAsset(String ID, double dollarSellAmount, Connection connection, boolean found) throws SQLException {
       
            if (found) {
                String query = "SELECT ACQ_COSTS FROM PORTFOLIO WHERE ID = ?";
                PreparedStatement statement = connection.prepareStatement(query);

                statement.setString(1, ID);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    double holdings = resultSet.getDouble("ACQ_COSTS");

                    if (dollarSellAmount < holdings) {
                        
                        System.out.println("Executing sellPartOfAsset");
                        double newHoldings = holdings - dollarSellAmount;

                        String updateQuery = "UPDATE PORTFOLIO SET ACQ_COSTS = ? WHERE ID = ?";
                        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);

                        updateStatement.setDouble(1, newHoldings); // Use setDouble()
                        updateStatement.setString(2, ID);

                        updateStatement.executeUpdate();
                        updateStatement.close();
                        
                        p.displayPopUp("Successfully sold, new holdings of " + ID + ": " + newHoldings );
                        
                    } else {
                        System.err.println("Invalid input, you only have " + holdings + " to sell");
                        p.displayPopUp("Invalid input, you only have " + holdings + " to sell");
                    }
                }

                resultSet.close();
                statement.close();
                
                Transaction sell = new Transaction(ID, "SELL", dollarSellAmount);
                sell.insertTransaction(connection);
    }
}

   
   public void sellAll(String ID, Connection connection) throws SQLException {
       
        String selectQuery = "SELECT HOLDINGS FROM PORTFOLIO WHERE ID = ?";
        double holdings = 0.0;
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, ID);
            
            ResultSet resultSet = selectStatement.executeQuery();
            
            if (resultSet.next()) {
               holdings = resultSet.getDouble("HOLDINGS");
                
                String deleteQuery = "DELETE FROM PORTFOLIO WHERE ID = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
                
                deleteStatement.setString(1, ID);
                
                int rowsAffected = deleteStatement.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Asset sold and removed from the database.");
                    p.displayPopUp("Asset sold and removed from the database.");
                } else {
                    System.out.println("Asset not found in the database.");
                    p.displayPopUp("Asset not found in the database.");
                }
                
                deleteStatement.close();
                
              
            }
            
             
            resultSet.close();
        }
         Transaction sell = new Transaction(ID, "SELL", holdings);
                sell.insertTransaction(connection);
    }

    
      
    public boolean isAssetFound(String ID, Connection connection, Portfolio portfolio) throws SQLException {
        
        String query = "SELECT COUNT(*) FROM PORTFOLIO WHERE ID = ?";
        
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, ID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            if (count > 0) {
                System.out.println(ID + " found in DB");
                return true;
            }
        }

        for (Asset asset : portfolio.getPortfolioList()) {
            if (ID.equals(asset.getAssetIdentification())) {
                System.out.println(ID + " found in List");
                return true;
            }
        }

        System.out.println(ID + " not found");
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
  


    
    

