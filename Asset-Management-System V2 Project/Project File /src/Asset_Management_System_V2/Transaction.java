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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;


public class Transaction {
    
    private Portfolio portfolio; 
    private String assetID;
    private String buyOrSell; 
    private double amount; 
    private final DatabaseManager db;
    private double profit_Or_Loss;

    public Transaction(String assetID, String buyOrSell, double acqCost) throws SQLException {
        this.db = new DatabaseManager();
        this.assetID = assetID;
        this.buyOrSell = buyOrSell;
        this.amount = acqCost;
    }

    public String getAssetID() {
        return assetID;
    }

    public String buyOrSell() {
        return buyOrSell;
    }

    public double getAcqCost() {
        return amount;
    }

    public List<Transaction> getTransactionsFromDB()throws SQLException{ //retrieves transaction data from transactionList table in DB
   
        List<Transaction> transactionList = new ArrayList<>();

        
        try {
            Connection connection = db.getConnection();
            String query = "SELECT ID, ACQ_DATE, ACQ_COSTS FROM TRANSACTIONLIST";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String assetId = resultSet.getString("ID");
                    String acqDate = resultSet.getString("ACQ_DATE");
                    double acqCost = resultSet.getDouble("ACQ_COSTS");
                    transactionList.add(new Transaction(assetId, acqDate, acqCost));
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    return transactionList;
}
    
   public synchronized void insertTransaction(Connection conn) { //inserts a singular transaction into DB transactionList table
        
    try {
            String insertSQL = "INSERT INTO TRANSACTIONLIST (ID, BORS, AMOUNT) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(insertSQL);

            // Generate a new ID for each row using timestamp
            String uniqueID = this.assetID + "_" + System.currentTimeMillis();

            statement.setString(1, uniqueID);
            statement.setString(2, this.buyOrSell);
            statement.setDouble(3, this.getAcqCost());

            int rowsAffected = statement.executeUpdate();
            System.out.println("Transaction inserted: " + this.buyOrSell + " " +  this.assetID + " " + this.amount);

        } catch (SQLException e) {
            e.printStackTrace();
        }
}

  
         
}
