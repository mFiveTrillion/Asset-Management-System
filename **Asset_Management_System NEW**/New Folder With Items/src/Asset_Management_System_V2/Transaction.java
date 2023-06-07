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

public class Transaction {
    
    private Portfolio portfolio; 
    private String assetID;
    private String acqDate; 
    private double acqCost; 

    public Transaction(String assetID, String acqDate, double acqCost) {
        this.assetID = assetID;
        this.acqDate = acqDate;
        this.acqCost = acqCost;
    }

    public String getAssetID() {
        return assetID;
    }

    public String getAcqDate() {
        return acqDate;
    }

    public double getAcqCost() {
        return acqCost;
    }

    public List<Transaction> transactionList(Portfolio portfolio){ 
        
       List<Transaction> transactionList = new ArrayList<>();
       
       for(Asset asset: portfolio.getPortfolio()){
           
           if(asset.getAssetidentification() == null || asset.getAcqDate() == null || asset.getAcqCost() <= 0){
            throw new IllegalArgumentException("Invalid asset data provided.");   
           }
           transactionList.add(new Transaction(asset.getAssetidentification(), asset.getAcqDate(), asset.getAcqCost()));
           
       }
        
        return transactionList;
        
    }
    
    
    
}
