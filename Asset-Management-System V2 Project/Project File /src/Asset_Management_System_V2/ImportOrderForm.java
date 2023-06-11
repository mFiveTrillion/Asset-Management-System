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

public class ImportOrderForm {
   
    private List<Asset> portfolio;
    
    public void importPortfolioAndUpdateDatabase( Connection connection)throws SQLException {
       try{ List<Asset> portfolio = readPortfolioFromFile("./resources/importedPortfolio.txt", connection);
        updateDatabase(portfolio);
        
       }catch(SQLException e){
           
          e.printStackTrace();
       }
    }
    
     public static List<Asset> readPortfolioFromFile(String file, Connection connection)throws SQLException{
         
      List<Asset> portfolio = new ArrayList<>();    
         
         try(BufferedReader br = new BufferedReader(new FileReader("./resources/importedPortfolio.txt"))){ //copy and paste order list into given file, for program to read
             
             String eachLine; 
             boolean firstLine = true;  
             while((eachLine = br.readLine()) != null){
                 
                 if(firstLine){
                  firstLine = false; 
                  continue;
                 }
                 
                 String[] fields = eachLine.split(","); //format seperator
                 String assetIdentification = fields[0];
                 String assetType = fields[1];
                 String acqDate = fields[2];
                 double acqCost = Double.parseDouble(fields[3]);
                 double marketValue = Double.parseDouble(fields[4]);
                 
                 Asset asset; 
                 switch(assetType){
                     
                     case "Stock":
                         
                        portfolio.add(new Stock(assetIdentification, connection));
                         break;
                         
                     case "RealEstate": 
                         
                        portfolio.add( new RealEstate(assetIdentification, connection));
                         break;
                         
                     case "ETF":
                         
                        portfolio.add(new ETF(assetIdentification, connection));
                         break;
                         
                     default:
                         
                       portfolio.add(new Asset(assetIdentification, connection));
                         break;
                     
                     
                        
                 }
                 
                  System.out.println("Successfully imported your order form!");
                 
             }
             
         }catch(IOException e){
             System.err.println("An error occured reading CSV file: Following are some diagnostics\n Check formatting of CSV order file ensuring variables are seperated by a ',' (Comma)");
          e.printStackTrace();
         }
         
        return portfolio;  
      
     
     }
     
     public static void updateDatabase(List<Asset> portfolio){
         
         try(Connection connection = DriverManager.getConnection("URL")){
             
             for(Asset asset : portfolio){
                 
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
        
         String query = "Checking for asset within portfolio...";
         PreparedStatement statement = connection.prepareStatement(query);
         statement.setString(1, assetID);
         ResultSet resultSet = statement.executeQuery();
         resultSet.next();
         int count = resultSet.getInt(1);
         
         return count > 0;
     }
     
     private static void updateAssetInDatabase(Connection connection, Asset asset) throws SQLException{
         
         String query = "Update Asset setting Acquisition Costs + Holdings ---> Fetching market Value ---> computing new total value";
         PreparedStatement statement = connection.prepareStatement(query);
         statement.setDouble(4, asset.getAcqCost());
         statement.setDouble(6, asset.getHoldings());
         
         
     }
     
     private static void insertAssetIntoDatabase(Connection connection, Asset asset) throws SQLException {

    String query = "INSERT INTO assets (assetIdentification, assetType, acqDate, acqCost, marketValue)";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setString(1, asset.getAssetidentification());
    statement.setString(2, asset.getAssetType());
    statement.setString(3, asset.getAcqDate());
    statement.setDouble(4, asset.getAcqCost());
    statement.setDouble(5, asset.getMarketValue());
    
    statement.executeUpdate();
}
     
     
     
}
