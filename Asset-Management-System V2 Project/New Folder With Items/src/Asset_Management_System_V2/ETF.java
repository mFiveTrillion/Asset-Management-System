/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */
public class ETF extends Asset {
    
    private String ticker; 
    private String inceptionDate; 

    public ETF(String assetidentification, String assetType, String acqDate, double acqCost, double marketValue) {
        super(assetidentification, assetType, acqDate, acqCost, marketValue);
        
    }

    public String getTicker() {
        return ticker;
    }

    public String getInceptionDate() {
        return inceptionDate;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setInceptionDate(String inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    @Override
    public String toString() {
        return "ETF{" + "ticker=" + ticker + ", inceptionDate=" + inceptionDate + '}' +
        super.toString();
    }
    
    
}
