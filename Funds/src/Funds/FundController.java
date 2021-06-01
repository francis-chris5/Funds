package Funds;


import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class FundController implements Initializable{

    @FXML
    Button btnClicker;

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
    
    private Book book = new Book("untitled");
    
    
    
    
    
    
        //////////////////////////////////////////////  BOOK METHODS  /////////
    
    public void displayDetails(){
        btnBookDetails.setText(book.toString());
    }//end displayDetails()
    
    
    
    @FXML
    public void launchBookDetails(){
        DetailsDialog temp = new DetailsDialog(book);
        temp.editDetails();
        displayDetails();
    }//end launch details
    
    
    
    @FXML
    public void showBalanceSheet(){
        BalanceTree asset = new BalanceTree(book, AccountType.ASSET);
        BalanceTree liability = new BalanceTree(book, AccountType.LIABILITY);
        BalanceTree equity = new BalanceTree(book, AccountType.EQUITY);
        vbxAsset.getChildren().add(asset);
        vbxLiability.getChildren().add(liability);
        vbxEquity.getChildren().add(equity);
    }//end showBalanceSheet()
    
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////  OTHER  ////////////////
    
    public void showAccount(){
        Account cash = new Account("cash", true);

        
        AccountDialog temp = new AccountDialog(cash);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, just need it here
        Account cash = new Account("Cash", true);
        book.getAssets().add(cash);
        showBalanceSheet(); //holy cow... I finally did someting at startup...
    }//end initialize()
    
}//end FundController
