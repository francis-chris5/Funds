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


public class FundController implements Initializable{

    @FXML
    Button btnClicker;
    @FXML
    TextField txtName;
    @FXML
    Label lblOutput;
    @FXML
    AnchorPane apnMain;
    
    
    public void showAccount(){
        Account cash = new Account("cash", true);
        Account expense = new Account("expense", false);
        
        cash.getEntries().add(new Entry(LocalDate.now(), 1, "buy cigarettes", expense, 25.43, 0));
        cash.getEntries().add(new Entry(LocalDate.now(), 2, "buy coffee", expense, 12.12, 0));
        cash.getEntries().add(new Entry(LocalDate.now(), 3, "buy dinner", expense, 7.48, 0));
        cash.getEntries().add(new Entry(LocalDate.now(), 4, "buy shoes", expense, 84.39, 0));
        cash.findRunningBalance();
        
        AccountDialog temp = new AccountDialog(cash);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, just need it here
    }//end initialize()
    
}//end FundController
