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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportOrderForm {
   
    private List<Asset> portfolio;
    boolean importedPerformed = false; 
    PopUpMessageWindow p = new PopUpMessageWindow();
    
    public void importPortfolioAndUpdateDatabase( Connection connection)throws SQLException { //method calls to import the data from file and popualates the portfolio DB
        
       try{ List<Asset> portfolio = readPortfolioFromFile("./resources/importedPortfolio.txt", connection);
        updateDatabase(portfolio, connection);
        
        importedPerformed = true; 
           System.out.println("File has been already been imported");
           p.displayPopUp("File has been already been imported");
           
       }catch(SQLException e){
           
          e.printStackTrace();
       }
    }
    
     public static List<Asset> readPortfolioFromFile(String file, Connection connection)throws SQLException{
         
      List<Asset> portfolio = new ArrayList<>();    
         
         try(BufferedReader br = new BufferedReader(new FileReader(file))){ //copy and paste order list into given file, for program to read
             
             String eachLine; 
             boolean firstLine = true;  
             while((eachLine = br.readLine()) != null){
                 
                 if(firstLine){
                  firstLine = false; 
                  continue;
                 }
                 
                String[] fields = eachLine.split(",");

                 String assetIdentification = fields[0];
                 String assetType = fields[1];
                 String acqDate = fields[2];
                 double acqCost = Double.parseDouble(fields[3]);
                 
                 double holdings = Double.parseDouble(fields[4]); 
                 
                 
                 Asset asset;
            switch (assetType.toLowerCase()) {
                case "stock":
                    asset = new Stock(assetIdentification, assetType, acqDate, acqCost,holdings);
                    break;
                case "realestate":
                    asset = new RealEstate(assetIdentification, assetType, acqDate, acqCost,holdings);
                    break;
                case "etf":
                    asset = new ETF(assetIdentification, assetType, acqDate, acqCost,holdings);
                    break;
                default:
                    asset = new Asset(assetIdentification, assetType, acqDate, acqCost, holdings);
                    break;
            }

            portfolio.add(asset);
           }
             
         }catch(IOException e){
             System.err.println("An error occured reading CSV file: Following are some diagnostics\n Check formatting of CSV order file ensuring variables are seperated by a ',' (Comma)");
          e.printStackTrace();
         }
         
        return portfolio;  
      
     
     }
     
     public void updateDatabase(List<Asset> importedPortfolio, Connection connection){ //update DB method, checks if asset already exists in DB
         
         try(connection){
             
             for(Asset asset : importedPortfolio){
                 
              String assetID = asset.getAssetidentification();
              
              if(assetFound(connection, assetID)){
               updateAssetInDatabase(connection, asset);   
              }else{
                  
               insertAssetIntoDatabase(connection, asset);
                  
              }
                 
             }
             
             System.out.println("Successfully updated database");
             
         }catch(SQLException ex){
          
             System.err.println("An Error occured while updating the database. ");
             ex.printStackTrace();
             
         }
         
     }
     
      
     
     private static boolean assetFound(Connection connection, String assetID) throws SQLException{
        
         String query = "SELECT COUNT(*) FROM PORTFOLIO WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, assetID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            
            int count = resultSet.getInt(1);

            return count > 0;
     }
     
    private static void updateAssetInDatabase(Connection connection, Asset asset) throws SQLException {
        
            String query = "UPDATE PORTFOLIO SET ACQ_DATE = ?, ACQ_COSTS = ?, HOLDINGS = ?, MARKET_VALUE = ?, TOTAL_VALUE = ? WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, asset.getAcqDate());
            statement.setDouble(2, asset.getAcqCost());
            statement.setDouble(3, asset.getHoldings());
            statement.setDouble(4, asset.getMarketValue());
            statement.setDouble(5, asset.getTotalValue());
            statement.setString(6, asset.getAssetidentification());

            statement.executeUpdate();
}
     
    private static void insertAssetIntoDatabase(Connection connection, Asset asset) throws SQLException {
        
            String query = "INSERT INTO PORTFOLIO (ID, TYPE, ACQ_DATE, ACQ_COSTS, HOLDINGS, MARKET_VALUE, TOTAL_VALUE) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, asset.getAssetIdentification());
            statement.setString(2, asset.getAssetType());
            statement.setString(3, asset.getAcqDate());
            statement.setDouble(4, asset.getAcqCost());
            statement.setDouble(5, asset.getHoldings());
            statement.setDouble(6, asset.getMarketValue());
            statement.setDouble(7, asset.getTotalValue());

            statement.executeUpdate();
    
    }
     
     
     
}
