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
//*******CONVERT TO CSV reader for importing function.*****//
public class ImportAssetListFromCSVFile {
   
     public static List<Asset> readPortfolioFromFile(String file){
         
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
                 
                 
                 switch(assetType){
                     
                     case "Stock":
                         
                         portfolio.add(new Stock(assetIdentification, assetType, acqDate, acqCost, marketValue));
                         break;
                         
                     case "RealEstate": 
                         
                         portfolio.add(new RealEstate(assetIdentification, assetType, acqDate, acqCost, marketValue));
                         break;
                         
                     case "ETF":
                         
                         portfolio.add(new ETF(assetIdentification, assetType, acqDate, acqCost, marketValue));
                         break;
                         
                     default:
                         
                         portfolio.add(new Asset(assetIdentification, assetType, acqDate, acqCost, marketValue));
                         break;
                     
                     
                        
                 }
                 
                  System.out.println("Successfully imported file, your portfolio is ready to go Press [1] to view");
                 
             }
             
         }catch(IOException e){
             System.err.println("");
          e.printStackTrace();
         }
         
         
        return portfolio;  
      
     
     }
}
