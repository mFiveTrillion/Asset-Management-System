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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Transaction {
    
    private Portfolio portfolio; 
    private String assetID;
    private String acqDate; 
    private double acqCost; 
    private final DatabaseManager db;

    public Transaction(String assetID, String acqDate, double acqCost) throws SQLException {
        this.db = new DatabaseManager();
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

    public List<Transaction> getTransactionsFromDB()throws SQLException{
   
        List<Transaction> transactionList = new ArrayList<>();

        
    try {
        Connection connection = db.getConnection();
        String query = "SELECT ID, ACQ_DATE, ACQ_COST FROM TRANSACTIONLIST";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String assetId = resultSet.getString("ID");
                String acqDate = resultSet.getString("ACQ_DATE");
                double acqCost = resultSet.getDouble("ACQ_COST");
                transactionList.add(new Transaction(assetId, acqDate, acqCost));
            }
        }
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return transactionList;
}
    
    
    
}
