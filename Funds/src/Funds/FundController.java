package Funds;


import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;



/**
 * The controller class for the software's main GUI
 * @author Chris Francis
 */
public class FundController implements Initializable{

        /////////////////////////////////////////////  GUI  /////////////////
    
        //right pane
    @FXML
    Button btnBookDetails;
    
        //center pane
    @FXML
    VBox vbxAsset;
    @FXML
    VBox vbxLiability;
    @FXML
    VBox vbxEquity;
    @FXML
    TextField txtTotalAssets;
    @FXML
    TextField txtTotalLiabilities;
    @FXML
    TextField txtTotalEquity;
    
    
    
    
    
    
    
    
    
        ////////////////////////////////////////////  DATAFIELDS  ///////////
    
    private Book book = new Book("untitled");
    
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    
    
    
        //////////////////////////////////////////////  BOOK METHODS  /////////
    
    /**
     * This is basically the refresh method for what's showing on the GUI
     * loads book details in button text, refreshes the balance sheet trees, updates the account totals in the balancing equation listing
     */
    public void displayDetails(){
        btnBookDetails.setText(book.toString());
        showBalanceSheet();
        displayTotals();
    }//end displayDetails()
    
    
    
    
    /**
     * Opens the details dialog with the current Book object for editing
     */
    @FXML
    public void launchBookDetails(){
        DetailsDialog temp = new DetailsDialog(book);
        temp.editDetails();
        displayDetails();
    }//end launch details
    
    
    
    /**
     * clears the balance sheet, creates a new one, and loads it in
     */
    @FXML
    public void showBalanceSheet(){
        vbxAsset.getChildren().clear();
        vbxLiability.getChildren().clear();
        vbxEquity.getChildren().clear();
        BalanceTree asset = new BalanceTree(book, AccountType.ASSET);
        BalanceTree liability = new BalanceTree(book, AccountType.LIABILITY);
        BalanceTree equity = new BalanceTree(book, AccountType.EQUITY);
        vbxAsset.getChildren().add(asset);
        vbxLiability.getChildren().add(liability);
        vbxEquity.getChildren().add(equity);
        displayTotals();
    }//end showBalanceSheet()
    
    
    
    /**
     * calculates and displays the total values from all of the current Book's accounts into the balancing equation listing
     */
    public void displayTotals(){
        double total = 0.0;
        for(int i = 0; i < book.getAssets().size(); i++){
            Account acc = book.getAssets().get(i);
            for(int j = 0; j < acc.getEntries().size(); j++){
                total += acc.getEntries().get(j).getDebit() - acc.getEntries().get(j).getCredit();
            }
        }
        txtTotalAssets.setText(Double.toString(total));
        total = 0.0;
        for(int i = 0; i < book.getLiabilities().size(); i++){
            Account acc = book.getLiabilities().get(i);
            for(int j = 0; j < acc.getEntries().size(); j++){
                total += acc.getEntries().get(j).getCredit() - acc.getEntries().get(j).getDebit();
            }
        }
        txtTotalLiabilities.setText(Double.toString(total));
        total = 0.0;
        for(int i = 0; i < book.getEquities().size(); i++){
            Account acc = book.getEquities().get(i);
            for(int j = 0; j < acc.getEntries().size(); j++){
                total += acc.getEntries().get(j).getCredit() - acc.getEntries().get(j).getDebit();
            }
        }
        txtTotalEquity.setText(Double.toString(total));
        total = 0.0;
    }//end displayTotals()
    
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////  OTHER  ////////////////
    
    
    /**
     * holy cow... I finally did something at startup... I created some default accounts (cash, credit card, revenue, and expense), this may not last past early development stages
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, just need it here
        Account cash = new Account("Cash", true);
        Account card = new Account("Credit Card", false);
        Account rev = new Account("Revenue", false);
        Account exp = new Account("Expense", true);
        book.getAssets().add(cash);
        book.getLiabilities().add(card);
        book.getEquities().add(rev);
        book.getEquities().add(exp);
        showBalanceSheet(); //holy cow... I finally did someting at startup...
    }//end initialize()
    
}//end FundController
