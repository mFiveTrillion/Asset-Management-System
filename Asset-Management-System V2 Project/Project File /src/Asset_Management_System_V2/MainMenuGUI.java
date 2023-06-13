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


public class MainMenuGUI {
 
    public JFrame frame;
    private JButton importButton;
    private JFrame importMessage;
    private JLabel label;
    
   private JButton buyButton;
   private JButton sellButton;
   private JButton performanceButton;
   private JButton displayPortButton;
    public ImportOrderForm importer; 
    public DatabaseManager dbMan;
    public Portfolio portfolio;
    public PortfolioMetricsCalc metrics;
    private JButton transactionButton;
    
    private JPanel metricsPanel;
    private JLabel totalValueLabel;
    private JLabel netReturnLabel;
    private JLabel portAssetWeightingLabel;
    private ImportOrderForm imp;
     
    
   public MainMenuGUI() throws SQLException{
       
       this.portfolio = new Portfolio();
       this.dbMan = new DatabaseManager();
       this.metrics = new PortfolioMetricsCalc(portfolio);
       this.importer = new ImportOrderForm();
        this.label = new JLabel();
      
       //JFrame initializer
       frame = new JFrame("Asset Management System");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(400, 600);
       frame.setLayout(null);
       
       //button to import ASSETS from CSV file
       importButton();
               
        //button to buy an asset
        buyAssetButton();
        
        //button to sell an asset
        sellAssetButton();
        
        //button to display portfolio based on performance
        performanceBasedList();

        //display portfolio button
        displayPortfolioButton();
        
        //display transactions
        transactionButton();
        
        //metrics panel on JFrame 
         metricsPanel(); 
   }           
         
   private void importButton(){
    
       //button to import ASSETS from CSV file
        importButton = new JButton("Import (CSV) order form");
        importButton.setBounds(100, 50, 200, 30);
        importButton.addActionListener(e -> {
           
         int choice = JOptionPane.showConfirmDialog(frame, "Import Order form? //***// Form Location: ./resources/importedPortfolio.txt", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    importer.importPortfolioAndUpdateDatabase(dbMan.getConnection());
                    portfolio.addAssetToPort(dbMan.getConnection());
                 
                } catch (SQLException ex) {
                    Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                label.setText("Importing orders into Database");



                   try {
                       portfolio.addAssetToPort(dbMan.getConnection());
                   } catch (SQLException ex) {
                       Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
                   }
                  }
                });
      
        frame.add(importButton);
       
       
   }
   
   private void buyAssetButton(){
       
       buyButton = new JButton("Buy asset");
        buyButton.setBounds(100, 100, 200, 30);
        buyButton.addActionListener(e -> {
            
           try {
               portfolio.buyAsset(dbMan.getConnection());
           } catch (SQLException ex) {
               Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
           }
            
            
           try {
               portfolio.addAssetToPort(dbMan.getConnection());
           } catch (SQLException ex) {
               Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        });
        
        frame.add(buyButton);
       
   }
   
   private void sellAssetButton(){
       
     //button to sell an asset
        sellButton = new JButton("Sell asset");
        sellButton.setBounds(100, 150, 200, 30);
        sellButton.addActionListener(e -> {
            
           try {
               portfolio.sellAsset(dbMan.getConnection());
           } catch (SQLException ex) {
               Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
           }
            
            
           try {
               portfolio.addAssetToPort(dbMan.getConnection());
           } catch (SQLException ex) {
               Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
           }
            
        });
        
        
        frame.add(sellButton);   
       

   }
   
   private void performanceBasedList(){
    
         //performance based list button 
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
           
   }
   
   private void displayPortfolioButton(){
       
       //display portfolio button
        displayPortButton = new JButton("Display portfolio");
        displayPortButton.setBounds(100, 250, 200, 30);
        displayPortButton.addActionListener(e -> {
            
            JTextArea portfolioTextArea = new JTextArea();
       portfolioTextArea.setEditable(false);

       for (Asset asset : portfolio.getPortfolio()) {
           portfolioTextArea.append(asset.toString());
           portfolioTextArea.append("\n");
       }

       JDialog portfolioDialog = new JDialog(frame, "Portfolio", true);
       portfolioDialog.setSize(400, 300);
       portfolioDialog.setLayout(new BorderLayout());

       portfolioDialog.add(new JScrollPane(portfolioTextArea), BorderLayout.CENTER);

       portfolioDialog.setVisible(true);

      
      });
        
        frame.add(displayPortButton);
       
       
   }
   
   private void metricsPanel(){
       
       //displays portfolio metrics
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
        
        metricsPanel.setBounds(50, 350, 200, 100);
        frame.add(metricsPanel);
  
   }
   
   private void transactionButton(){
    
        transactionButton = new JButton("Transactions");
        transactionButton.setBounds(100, 300, 200, 30);
        transactionButton.addActionListener(e -> {
       
       
        });
        
        frame.add(transactionButton);
   }
}
