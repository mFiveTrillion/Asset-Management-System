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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.Timer;
import java.text.DecimalFormat;

public class MainMenuGUI {

    public JFrame frame;
    private JButton importButton;
    private JLabel label;

    private JButton buyButton;
    private JButton sellButton;
    private JButton performanceButton;
    private JButton displayPortButton;
    public ImportOrderForm importer;
    
    public Portfolio portfolio;
    public PortfolioMetricsCalc metrics;
    private JButton transactionButton;
    private Timer metricsTimer;
    private JPanel metricsPanel;
    private JLabel totalValueLabel;
    private JLabel netReturnLabel;
    private JLabel portAssetWeightingLabel;
    private JLabel title; 
    private ImportOrderForm imp;
    
    public DatabaseManager dbMan = new DatabaseManager();
    Connection con = dbMan.getConnection();
    private PopUpMessageWindow p = new PopUpMessageWindow();
    
    public MainMenuGUI() throws SQLException {

        this.portfolio = new Portfolio();
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
        metricsTimer();
    }

    private void importButton() throws SQLException {

        //button to import ASSETS from CSV file
        Connection con1 = dbMan.getConnection();
        
        importButton = new JButton("Import (CSV) order form");
        importButton.setBounds(100, 50, 200, 30);
        importButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(frame, "Import Order form form Location: ./resources/importedPortfolio.txt", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                try {

                    importer.importPortfolioAndUpdateDatabase(con1);
                
                    label.setText("Importing orders into Database");
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        frame.add(importButton);

    }

    private void buyAssetButton() {

        buyButton = new JButton("Buy asset");
        buyButton.setBounds(100, 100, 200, 30);
        buyButton.addActionListener(e -> {

            showBuyForm();

        });

        frame.add(buyButton);

    }
    
    private void showSellForm() throws SQLException{
        //sell form that is shown when sell button is pressed
       
     String assetID = JOptionPane.showInputDialog("Enter asset ID:");

   
     if (portfolio.isAssetFound(assetID, con, portfolio)) {
        
         int sellAll = JOptionPane.showConfirmDialog(null, "Sell all units?", "Sell Asset", JOptionPane.YES_NO_OPTION);

         if (sellAll == JOptionPane.YES_OPTION) {
         
             portfolio.sellAll(assetID, con);
             JOptionPane.showMessageDialog(null, "Asset sold successfully!");
             System.out.println("Asset completely sold succesfully");
         } else {
            
             double sellAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter dollar amount to sell:"));

        
            portfolio.sellPartOfAsset(assetID, sellAmount, con, true);
             
         }
        
     }else{
        p.displayPopUp("Asset not found");
     }
    }

    private void showBuyForm() {
        //buy form shown when buy button is pressed
        JFrame buyFormFrame = new JFrame("Buy Asset Form");
        buyFormFrame.setSize(400, 300);
        buyFormFrame.setLayout(new GridLayout(7, 2));

        JLabel idLabel = new JLabel(" Asset ID:");
        JTextField idField = new JTextField();
        JLabel typeLabel = new JLabel(" Asset Type:");
        JTextField typeField = new JTextField();
        JLabel dateLabel = new JLabel(" Acq. Date dd/mm/yyyy:");
        JTextField dateField = new JTextField();
        JLabel costLabel = new JLabel(" Acq. Cost:");
        JTextField costField = new JTextField();
        JLabel sharesLabel = new JLabel(" Number of Shares:");
        JTextField sharesField = new JTextField();
     

        JButton submitButton = new JButton("Submit");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));

        submitButton.addActionListener(e -> {

            String id = idField.getText();
            String type = typeField.getText();
            String date = dateField.getText();
            double cost = Double.parseDouble(costField.getText());
            double shares = Double.parseDouble(sharesField.getText());
           

            Asset asset = new Asset(id, type, date, cost, shares);

            try {
                
                portfolio.buyAsset(con, asset, portfolio);
                buyFormFrame.dispose();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(e -> {

            buyFormFrame.dispose();
        });

        buyFormFrame.add(idLabel);
        buyFormFrame.add(idField);
        buyFormFrame.add(typeLabel);
        buyFormFrame.add(typeField);
        buyFormFrame.add(dateLabel);
        buyFormFrame.add(dateField);
        buyFormFrame.add(costLabel);
        buyFormFrame.add(costField);
        buyFormFrame.add(sharesLabel);
        buyFormFrame.add(sharesField);
 
        buyFormFrame.add(submitButton);

        buttonPanel.add(cancelButton);

        buttonPanel.add(submitButton);

        buyFormFrame.add(inputPanel, BorderLayout.CENTER);
        buyFormFrame.add(buttonPanel, BorderLayout.SOUTH);

        buyFormFrame.setVisible(true);
    }

    private void sellAssetButton() {

        //button to sell an asset
         sellButton = new JButton("Sell asset");
        sellButton.setBounds(100, 130, 200, 30);
        sellButton.addActionListener(e -> {

             try {
                 showSellForm();
             } catch (SQLException ex) {
                 Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
             }

        });

        frame.add(sellButton);


    }

    private void performanceBasedList() {

        //performance based list button 
        performanceButton = new JButton("Top performing list");
        performanceButton.setBounds(100, 200, 200, 30);
        performanceButton.addActionListener(e -> {

            JDialog topAssetsDialog = new JDialog(frame, "Top Performing Assets", true);

            topAssetsDialog.setSize(400, 300);
            topAssetsDialog.setLayout(new BorderLayout());

            JTextArea topAssetsTextArea = new JTextArea();
            topAssetsTextArea.setEditable(false);

            try {
                for (Asset asset : metrics.getPerformanceBasedList(portfolio.getNetMap(portfolio.getPortfolioList()), portfolio, con)) {
                    topAssetsTextArea.append(asset.toString());
                    topAssetsTextArea.append("\n");
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainMenuGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            topAssetsDialog.add(new JScrollPane(topAssetsTextArea), BorderLayout.CENTER);

            topAssetsDialog.setVisible(true);

        });

        frame.add(performanceButton);

    }

    private void displayPortfolioButton() {

        //display portfolio button
 
        displayPortButton = new JButton("Display portfolio");
        displayPortButton.setBounds(100, 250, 200, 30);
        displayPortButton.addActionListener(e -> {
           
            System.out.println("Display portfolio executed");
            

           JFrame portfolioFrame = new JFrame("Portfolio");
            portfolioFrame.setSize(1500, 400);

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("TYPE");
            tableModel.addColumn("DATE OF ACQUISITION");
            tableModel.addColumn("ACQUISITION COST");
            tableModel.addColumn("PURCHASE PRICE");
            tableModel.addColumn("PURCHASED POSITION VOLUME");
            tableModel.addColumn("NET P/L");

                    try (
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM PORTFOLIO")) {
                       
                         DecimalFormat decimalFormat = new DecimalFormat("#.00");
                         
               while (resultSet.next()) {
                   
                   String id = resultSet.getString("ID");
                   String type = resultSet.getString("TYPE");
                   String date = resultSet.getString("ACQ_DATE");
                   double cost = resultSet.getDouble("ACQ_COSTS");
                   double marketValue = resultSet.getDouble("MARKET_VALUE");
                   double holdings = resultSet.getDouble("HOLDINGS");
                   double totalValue = resultSet.getDouble("TOTAL_VALUE");

                   tableModel.addRow(new Object[]{
                    id, type, date,
                    decimalFormat.format(cost),
                    decimalFormat.format(marketValue),
                    decimalFormat.format(holdings),
                    decimalFormat.format(totalValue)
                });
               }
           } catch (SQLException ex) {
               
               ex.printStackTrace();
           }

           JTable portfolioTable = new JTable(tableModel);
           JScrollPane scrollPane = new JScrollPane(portfolioTable);

           portfolioFrame.add(scrollPane);
           portfolioFrame.setVisible(true);
           
        });

        frame.add(displayPortButton);

    }

    private void metricsTimer() { //timer to keep the updating methods 
        
            int refreshInterval = 1000; // milliseconds
            Timer timer = new Timer(refreshInterval, e -> {
                try {
                    updateMetrics();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            timer.start();
}

    private void updateMetrics() throws SQLException {
        
         DecimalFormat decimalFormat = new DecimalFormat("#.00");
        
            double totalPortValue = portfolio.getTotalPortValue(con);
            double portfolioNetReturn = portfolio.getPortfolioTotalNet(con) - portfolio.getTotalPortValue(con);
            String assetWeighting = metrics.retrieveWeighting(con);

           String formattedTotalPortValue = decimalFormat.format(totalPortValue);
           String formattedPortfolioNetReturn = decimalFormat.format(portfolioNetReturn);
            
            totalValueLabel.setText("Portfolio position: $" + formattedTotalPortValue);
            netReturnLabel.setText("Portfolio Net P/L: $" + formattedPortfolioNetReturn);
            portAssetWeightingLabel.setText("ASSET CLASS WEIGHTINGS: " + assetWeighting);

            // Refresh the metrics panel so it stays live 
            metricsPanel.revalidate();
            metricsPanel.repaint();
        }
    
    private void metricsPanel() throws SQLException {
        
            metricsPanel = new JPanel();
            metricsPanel.setLayout(new BoxLayout(metricsPanel, BoxLayout.Y_AXIS));

            title = new JLabel("LIVE PORTFOLIO SUMMARY");
            totalValueLabel = new JLabel("Portfolio position: ");
            netReturnLabel = new JLabel("Portfolio Net P/L: $");
            portAssetWeightingLabel = new JLabel("ASSET CLASS WEIGHTINGS: ");

            metricsPanel.add(title);
            metricsPanel.add(totalValueLabel);
            metricsPanel.add(netReturnLabel);
            metricsPanel.add(Box.createVerticalStrut(10)); // Add some vertical spacing
            metricsPanel.add(new JLabel("Asset Class Weightings:"));

            // Retrieve and split the asset weightings string
            String assetWeighting = metrics.retrieveWeighting(con);
            String[] weightings = assetWeighting.split("\n");
            for (String weighting : weightings) {
                JLabel label = new JLabel(weighting);
                metricsPanel.add(label);
            }

            metricsPanel.setBounds(100, 350, 1000, 200);
            frame.add(metricsPanel);

            metricsTimer(); // Start the timer for updating the metrics
        }


    private void transactionButton() {

        transactionButton = new JButton("Transactions");

        transactionButton.setBounds(100, 300, 200, 30);
        transactionButton.addActionListener(e -> {

            System.out.println("Transaction display executed");
            JFrame transactionFrame = new JFrame("Past Transactions");
            transactionFrame.setSize(600, 400);

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("BUY or SELL");
            tableModel.addColumn("AMOUNT ($)");

            try ( Statement statement = con.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM TRANSACTIONLIST")) {

                while (resultSet.next()) {

                    String id = resultSet.getString("ID");
                    String bors = resultSet.getString("BORS");
                    double amount = resultSet.getDouble("AMOUNT");

                    tableModel.addRow(new Object[]{id, bors, amount});
                }
            } catch (SQLException ex) {

                ex.printStackTrace();
            }
            JTable transactionTable = new JTable(tableModel);

            JScrollPane scrollPane = new JScrollPane(transactionTable);

            transactionFrame.add(scrollPane);
            transactionFrame.setVisible(true);

        });

        frame.add(transactionButton);
    }
}
