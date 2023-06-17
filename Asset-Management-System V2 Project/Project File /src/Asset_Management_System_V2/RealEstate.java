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
public class RealEstate extends Asset{
    
    private String address; 

    public RealEstate(String assetIdentification, String assetType, String acqDate, double acqCost, double holdings) throws SQLException {
        
        super(assetIdentification, assetType, acqDate, acqCost, holdings);
      
    }
    
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
    @Override
    public String toString(){
        
     return  "\nAddress: " + address + "\n" + 
     super.toString();
            
        
    }
    
    
    
    
}
