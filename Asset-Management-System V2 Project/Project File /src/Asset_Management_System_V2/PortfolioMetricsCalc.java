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
import java.util.Comparator;
import java.util.Collections;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class PortfolioMetricsCalc {
    
   private Portfolio portfolio; 

    public PortfolioMetricsCalc(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
    
     public String retrieveWeighting(Connection connection) throws SQLException {
         
            int numOfAssets = 0;
            double totalStock = 0.0;
            double totalRealEstate = 0.0;
            double totalETF = 0.0;
            double totalOther = 0.0;

            String query = "SELECT * FROM PORTFOLIO";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String assetType = resultSet.getString("TYPE").toLowerCase();
                    double marketValue = resultSet.getDouble("MARKET_VALUE");
                    double holdings = resultSet.getDouble("HOLDINGS");

                    switch (assetType) {
                        case "stock":
                            totalStock += marketValue * holdings;
                            break;
                        case "real estate":
                            totalRealEstate += marketValue * holdings;
                            break;
                        case "etf":
                            totalETF += marketValue * holdings;
                            break;
                        case "realestate":
                            totalRealEstate += marketValue * holdings;
                            break;
                        default:
                            totalOther += marketValue * holdings;
                            break;
                    }

                    numOfAssets++;
                }
            }

            double totalPortValue = portfolio.getTotalPortValue(connection);

            double stockWeight = (totalStock / totalPortValue) * 100;
            double estateWeight = (totalRealEstate / totalPortValue) * 100;
            double etfWeight = (totalETF / totalPortValue) * 100;
            double otherWeight = (totalOther / totalPortValue) * 100;

            stockWeight = Math.round(stockWeight * 10.0) / 10.0;
            estateWeight = Math.round(estateWeight * 10.0) / 10.0;
            etfWeight = Math.round(etfWeight * 10.0) / 10.0;
            otherWeight = Math.round(otherWeight * 10.0) / 10.0;

            return 
                     "\nStock: " + stockWeight + "%"
                    + "\nReal Estate: " + estateWeight + "%"
                    + "\nETF weight: " + etfWeight + "%"
                    + "\nOther: " + otherWeight + "%";
}

     
     public List<Asset> getPerformanceBasedList(Map<String,Double> netMap, Portfolio portfolio, Connection connection)throws SQLException{//compute and return a sorted list based on returns 
            
         //call retrieval method
         portfolio.addAssetToPortFromDB(connection);
         
            List<Asset> performanceBasedList = new ArrayList<>();
            
            List<Map.Entry<String, Double>> sortedNetReturns = new ArrayList<>(netMap.entrySet());
            

            Collections.sort(sortedNetReturns, new Comparator<Map.Entry<String, Double>>(){
         
            
            public int compare(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
            return entry2.getValue().compareTo(entry1.getValue());
            
        }
            
                 
     });
             for (Map.Entry<String, Double> entry : sortedNetReturns) {
                for (Asset asset : portfolio.getPortfolioList()) {
                    if (asset.getAssetidentification().equals(entry.getKey())) {
                        performanceBasedList.add(asset);
                        break;
                    }
        }
    }
            
            return performanceBasedList;
        }
}
