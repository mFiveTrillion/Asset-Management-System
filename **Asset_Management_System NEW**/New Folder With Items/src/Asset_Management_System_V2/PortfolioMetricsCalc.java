/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Collections;

/**
 *
 * @author hayden
 */
public class PortfolioMetricsCalc {
    
   private Portfolio portfolio; 

    public PortfolioMetricsCalc(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
    
     public String retrieveWeighting(Portfolio portfolio){ //compute the weightings of each asset class and return a string representation

        int numOfAssets = 0; 
        double totalStock = 0.0;
        double totalRealEstate = 0.0;
        double totalETF = 0.0;
        double totalOther = 0.0;
        
        ArrayList<Asset> stockArray = new ArrayList<>(); 
        ArrayList<Asset> realEstateArray = new ArrayList<>(); 
        ArrayList<Asset> other = new ArrayList<>(); 
        ArrayList<Asset> ETF = new ArrayList<>();
        
        String str = " ";

        for (Asset asset : portfolio.getPortfolio()) {
          
            switch(asset.getAssetType()){
                
                case "Stock":   
                 
                stockArray.add(asset);
                numOfAssets++;
                break;
                
                case "RealEstate":
                    
                realEstateArray.add(asset);
                numOfAssets++;
                break;
                
                case "ETF":
                    
                ETF.add(asset);
                numOfAssets++;
                break;
                
                default:
                    
                other.add(asset);
                numOfAssets++;
                break;
                    
                
            }
            
           
    }
        
        for(Asset asset : stockArray){
            
         totalStock += asset.getMarketValue();
             
        }
        //compute the percent value stock comprises of the portfolio 
        double stockWeight = totalStock / portfolio.getTotalPortValue()   * 100; 
        
        for(Asset asset : realEstateArray){
            
            totalRealEstate += asset.getMarketValue();
        }
        //compute the percent value real Esate comprises of the portfolio
        double estateWeight = totalRealEstate / portfolio.getTotalPortValue()* 100; 
        
        
        for(Asset asset : ETF){
            
            totalETF += asset.getMarketValue();
        }
        //compute the percent value ETF comprises of the portfolio
        double etfWeight = totalETF / portfolio.getTotalPortValue()*100;
        for(Asset asset : other){
         
            totalOther += asset.getMarketValue();
            
        }
        //compute the percent value of other assets that comprises of the portfolio
        double otherWeight = totalOther / portfolio.getTotalPortValue() * 100;
        
        stockWeight = Math.round(stockWeight*10.0)/10.0;
        estateWeight = Math.round(estateWeight*10.0)/10.0;
        etfWeight = Math.round(etfWeight*10.0)/10.0;
        otherWeight = Math.round(otherWeight*10.0)/10.0;
        
        return "Weighting of Portfolio: " + 
                "\nStock: " + stockWeight + "%" +
                "\nReal Esate: " + estateWeight + "%" +
                "\nETF weight: " + etfWeight + "%" +
                "\nOther: " + otherWeight + "%"; 
        }
     
     public List<Asset> getPerformanceBasedList(Map<String,Double> netMap, Portfolio portfolio){//compute and return a sorted list based on returns 
            
            List<Asset> performanceBasedList = new ArrayList<>();
            
            List<Map.Entry<String, Double>> sortedNetReturns = new ArrayList<>(netMap.entrySet());
            

            Collections.sort(sortedNetReturns, new Comparator<Map.Entry<String, Double>>(){
         
            
            public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
            return entry2.getValue().compareTo(entry1.getValue());
            
        }
            
                 
     });
             for (Map.Entry<String, Double> entry : sortedNetReturns) {
                for (Asset asset : portfolio.getPortfolio()) {
                    if (asset.getAssetidentification().equals(entry.getKey())) {
                        performanceBasedList.add(asset);
                        break;
                    }
        }
    }
            
            return performanceBasedList;
        }
}
