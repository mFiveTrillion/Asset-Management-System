/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Asset_Management_System_V2;

/**
 *
 * @author hayden
 */
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;


public class GUI {
 
    public JFrame frame;
    private JButton importButton;
    private JLabel label;
   private JButton buyButton;
   private JButton sellButton;
   private JButton performanceButton;
    public ImportOrderForm importer; 
    public DatabaseManager dbMan;
    public Portfolio portfolio;
    public PortfolioMetricsCalc metrics;
    
    private JPanel metricsPanel;
    private JLabel totalValueLabel;
    private JLabel netReturnLabel;
    private JLabel portAssetWeightingLabel;
     
    
   public GUI() throws SQLException{
       
       this.portfolio = new Portfolio();
       this.dbMan = new DatabaseManager();
       this.metrics = new PortfolioMetricsCalc(portfolio);
      
       
       frame = new JFrame("Asset Management System");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(500, 400);
       frame.setLayout(null);
       
       //button to import ASSETS from CSV file
        importButton = new JButton("Import (CSV) order form");
        importButton.setBounds(100, 50, 200, 30);
        importButton.addActionListener(e -> {
           
            try {
           
               importer.importPortfolioAndUpdateDatabase(dbMan.getConnection());
               
           } catch (SQLException ex) {
               Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
           }
        label.setText("Importing orders into Database");
       
           try {
               portfolio.addAssetToPort(dbMan.getConnection());
           } catch (SQLException ex) {
               Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
           }
        });
      
        frame.add(importButton);
        
         //metrics panel on JFrame //d
         metricsPanel = new JPanel();
         metricsPanel.setLayout(new GridLayout(3,1));
         
         totalValueLabel = new JLabel("Total Value: ");
         netReturnLabel = new JLabel("Net portfolio Return: ");
         portAssetWeightingLabel = new JLabel("Asset class weight: ");
         
        totalValueLabel.setText("Total Value: " + portfolio.getTotalPortValue());
        netReturnLabel.setText("Net portfolio return "+ portfolio.getPortfolioTotalNet());
        portAssetWeightingLabel.setText("Asset class weight: " + metrics.retrieveWeighting(portfolio));
        
        metricsPanel.add(totalValueLabel);
        
        metricsPanel.add(netReturnLabel);
        
        metricsPanel.add(portAssetWeightingLabel);
        
        metricsPanel.setBounds(50, 250, 200, 100);
        frame.add(metricsPanel);
        
        //performance based list
        
        performanceButton = new JButton("Top performing list");
        performanceButton.setBounds(100, 200, 200, 30);
        performanceButton.addActionListener(e -> {
            
             JDialog topAssetsDialog = new JDialog(frame, "Top Performing Assets", true);
             
                topAssetsDialog.setSize(400, 300);
                topAssetsDialog.setLayout(new BorderLayout());
                
                JTextArea topAssetsTextArea = new JTextArea();
                topAssetsTextArea.setEditable(false);
               
                for (Asset asset :  metrics.getPerformanceBasedList(portfolio.getNetMap(portfolio.getPortfolio()), portfolio)) {
                    topAssetsTextArea.append(asset.toString());
                    topAssetsTextArea.append("\n");
                }

         
                topAssetsDialog.add(new JScrollPane(topAssetsTextArea), BorderLayout.CENTER);

 
                topAssetsDialog.setVisible(true);

        
        
        });
        
        frame.add(performanceButton);
        
        //button to buy an asset
        buyButton = new JButton("Buy asset");
        buyButton.setBounds(100, 100, 200, 30);
        buyButton.addActionListener(e -> {
            
           try {
               portfolio.buyAsset(dbMan.getConnection());
           } catch (SQLException ex) {
               Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
           }
            
            
           try {
               portfolio.addAssetToPort(dbMan.getConnection());
           } catch (SQLException ex) {
               Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        });
        
        frame.add(buyButton);
        
        //button to sell an asset
        sellButton = new JButton("Sell asset");
        sellButton.setBounds(100, 150, 200, 30);
        sellButton.addActionListener(e -> {
            
           try {
               portfolio.sellAsset(dbMan.getConnection());
           } catch (SQLException ex) {
               Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
           }
            
            
           try {
               portfolio.addAssetToPort(dbMan.getConnection());
           } catch (SQLException ex) {
               Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        });
        
        
        frame.add(sellButton);

   }

    
    
    
    
    
    
    
}
