/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class AssetsFileUpdatingTool {
    
    public static void writePortfolioToFile(List<Asset> portfolio, String file){
        
     try{
         
         FileWriter writer = new FileWriter("./resources/portfolioSave.txt");
         
         writer.write("Name,Type,Purchase Date,Purchase Price,Shares,Symbole,Market Value\n");
          for(Asset asset : portfolio){
           
              writer.write(String.format("%s,%s,%s,%.2f,%.2f\n",
                        asset.getAssetidentification(),
                        asset.getAssetType(),
                        asset.getAcqDate(),
                        asset.getAcqCost(),
                        asset.getMarketValue()));
              
                      
                }  
          writer.close();
          System.out.println("Successfully wrote portfolio to file");
                      
         
     }catch(IOException e){
         
         System.err.println("Error writing to file");
         e.printStackTrace();
         e.getMessage();
     }
    }
    
     public static List<Asset> readPortfolioFromFile(String file){
         
      List<Asset> portfolio = new ArrayList<>();    
         
         try(BufferedReader br = new BufferedReader(new FileReader("./resources/importedPortfolio.txt"))){
             
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
