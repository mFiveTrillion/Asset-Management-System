/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class CUI {
    
    
    public void commandUserInterface(Portfolio portfolio){
     
        PortfolioMetricsCalc metrics = new PortfolioMetricsCalc(portfolio);
        Portfolio importedPortfolio = new Portfolio();
        ImportOrderForm up1 = new ImportOrderForm();
        
        Scanner scan = new Scanner(System.in);
        int input = 0;
        do{
        System.out.println("----------------------------------- ASSET MANAGEMENT SYSTEM -------------------------------------------------");
        System.out.println("<^> Menu <^> Enter corresponding number to control system || imported Portfolio controls");
        
        System.out.println("[1] Display Current Portfolio                               [8] Load Imported File");
        System.out.println("[2] Display Asset Class Weighting                           [9] Display Imported Portfolio");
        System.out.println("[3] Display Portf. Based on Highest Returns                 [10] Display Imported Portf. Asset Class Weighting");
        System.out.println("[4] Get Simple Return of Specific Asset                     [11] Display Imported Performance Based List");
        System.out.println("[5] Buy/Officiate a New Asset                               [12] Get Return of Asset");
        System.out.println("[6] Sell/Officiate an Asset                                 [13] Buy/Officiate a New Asset");
        System.out.println("[7] Save Updates of Portfolio to File                       [14] Sell/Officiate an Asset");
     
        System.out.println("<[99]> End System Management                                [15] Save Updates of Portfolio to Imported File");
        
            try{
                  input = Integer.parseInt(scan.nextLine());
                  
                  switch(input){
                     
                      case 1:
                                                                           
                         System.out.println(portfolio.toString());
                         break;
                       
                      case 2: 
                          System.out.println(metrics.retrieveWeighting(portfolio));
                          break;
                      
                      case 3:
                          System.out.println(metrics.getPerformanceBasedList(portfolio.getNetMap(portfolio.getPortfolio()), portfolio));
                          break;
                          
                      case 4: 
                          String ID;
                          System.out.println("Enter the Asset ID: ");
                          ID = scan.nextLine();  
                          System.out.println("\nASSET: " + ID + "RETURN: " + portfolio.getAssetReturn(ID, portfolio.getNetMap(portfolio.getPortfolio()))+ "%");
                          break;
                          
                      case 5:
                          String newAssetID;
                          System.out.println("Enter the new AssetID: ");
                          newAssetID = scan.nextLine();
                          portfolio.buyAsset(portfolio, newAssetID);
                          
                          break;
                          
                      case 6: 
                          String sellAssetID;
                          System.out.println("Enter the AssetID: ");
                          sellAssetID = scan.nextLine();
                          portfolio.sellAsset(portfolio.getAssetSet(portfolio.getPortfolio()), sellAssetID);
                          break;
                      
                      case 7: 
                         
                          up1.writePortfolioToFile(portfolio.getPortfolio(), "./resources/portfolioSave.txt"); //no longer writing to files, instead inputting to DataBase.
                          break;
                      case 8:
                        
                         for(Asset asset :ImportOrderForm.readPortfolioFromFile("./resources/importedPortfolio.txt")){
                          
                             importedPortfolio.addAssetToPort(asset);
                             
                         }
                        
                          break;  
                       case 9:
                                                                           
                         System.out.println(importedPortfolio.toString());
                         break;
                       
                      case 10: 
                          System.out.println(metrics.retrieveWeighting(importedPortfolio));
                          break;
                      
                      case 11:
                          System.out.println(metrics.getPerformanceBasedList(importedPortfolio.getNetMap(importedPortfolio.getPortfolio()), importedPortfolio));
                          break;
                          
                      case 12: 
                          String impID;
                          System.out.println("Enter the Asset ID: ");
                          impID = scan.nextLine();  
                          System.out.println("\nASSET: " + impID + "RETURN: " + importedPortfolio.getAssetReturn(impID, importedPortfolio.getNetMap(importedPortfolio.getPortfolio()))+ "%");
                          break;
                          
                      case 13:
                          String impAssetID;
                          System.out.println("Enter the new AssetID: ");
                          impAssetID = scan.nextLine();
                          importedPortfolio.buyAsset(importedPortfolio, impAssetID);
                          
                          break;
                          
                      case 14: 
                          String impSellAssetID;
                          System.out.println("Enter the AssetID: ");
                          impSellAssetID = scan.nextLine();
                          importedPortfolio.sellAsset(importedPortfolio.getAssetSet(importedPortfolio.getPortfolio()), impSellAssetID);
                          break;
                      
                      case 15: 
                          
                          ImportOrderForm.writePortfolioToFile(importedPortfolio.getPortfolio(), "./resources/importedPortfolio.txt");
                          break;
                          
                      case 99: 
                          System.out.println("Saving files");
                          up1.writePortfolioToFile(portfolio.getPortfolio(), "./resources/portfolioSave.txt");
                           ImportOrderForm.writePortfolioToFile(importedPortfolio.getPortfolio(), "./resources/importedPortfolio.txt");
                         break;
                         
                  default:
                  System.err.println("Invalid input, please input 0 - 7 or 99 to end program");
                  break;
                  
                  }
                  
                  
                  
            }catch(NumberFormatException e){
                
                System.err.println("Invalid input, Please enter a number.");
            }
                
        }while(input != 99);
        
        
        
        
        
    }
}
