/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */
import java.sql.SQLException;

public class ETF extends Asset {
    
    private String ticker; 
    private String inceptionDate; 

    public ETF(String assetIdentification, String type, String date, double cost, double holdings) throws SQLException {
        
        super(assetIdentification, type, date, cost, holdings);
     
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
