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
    
    public void buy(Portfolio portfolio, String newAssetID){ //allows user to pass in a new asset, search the hashset if it already exists - if so, the asset is added to that already there, if not the new asset is added to set and list array
       
        Scanner scan = new Scanner(System.in);
        boolean found = false;
        for(Asset asset : portfolio.getPortfolio()){
            
                if(asset.getAssetidentification().equals(newAssetID)){

                    double acqCostOfNewPurchase = 0.0;
                    System.out.println("\nAsset found!");
                    System.out.println("Enter acquisition Cost: (Dollar Amount)");
                    acqCostOfNewPurchase = scan.nextDouble();

                   
                    System.out.println("Bought " + "$" + acqCostOfNewPurchase + " more of" + asset.getAssetidentification());
                    asset.updateHoldingsBuy(acqCostOfNewPurchase);
                    System.out.println("\nNow hold $" + asset.getHoldings() + " of " + asset.getAssetidentification());
                    found = true; 
                   
                            
                }
                
        }
        if(!found){

                 System.out.println("Enter the asset type:");
                 String assetType = scan.nextLine();
                 System.out.println("Enter the acquisition date (MM/DD/YYYY):");
                 String acqDate = scan.nextLine();
                 System.out.println("Enter the acquisition cost:");
                 double acqCost = scan.nextDouble();
                 System.out.println("Enter the market value:");
                 double marketValue = scan.nextDouble();

               Asset newAsset = new Asset(newAssetID, assetType, acqDate, acqCost, marketValue);

                 System.out.println("New asset: " + newAssetID + " added to portfolio");
              
                 portfolio.getPortfolio().add(newAsset);

                }
    }
     public void sell(Set<Asset> assets, String assetID){ //method to sell all or part of holdings for a specific asset defined by user input and passed in as parameters
         
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
}
  


    
    

