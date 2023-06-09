/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AssetManagementSystemDAO {
    private static final String DATABASE_URL = "";
    private static final String USERNAME = "pdc";
    private static final String PASSWORD = "pdc";

    private Connection connection;

    public AssetManagementSystemDAO() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addAsset(Asset asset) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO assets (name, category, quantity) VALUES (?, ?, ?)");
            statement.setString(1, asset.getName());
            statement.setString(2, asset.getCategory());
            statement.setInt(3, asset.getQuantity());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAsset(Asset asset) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE assets SET name = ?, category = ?, quantity = ? WHERE id = ?");
            statement.setString(1, asset.getName());
            statement.setString(2, asset.getCategory());
            statement.setInt(3, asset.getQuantity());
            statement.setInt(4, asset.getId());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAsset(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM assets WHERE id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Asset getAssetById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM assets WHERE id = ?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                int quantity = resultSet.getInt("quantity");

                return new Asset(id, name, category, quantity);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
        
    public void uploadAssets(Portfolio portfolio) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO assets (name, category, quantity) VALUES (?, ?, ?)");

            for (Asset asset : portfolio.getPortfolio()) {
                statement.setString(1, asset.getAssetidentification());
                statement.setString(2, asset.getAssetType());
                statement.setDouble(3,asset.getAcqCost() );
                statement.addBatch();
            }

            statement.executeBatch();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
     
        }
    
   
    }
        return null;
    }

}
