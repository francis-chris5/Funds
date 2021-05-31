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
        Account revenue = new Account("revenue", false);
        Account expense = new Account("expense", true);
        
        cash.getEntries().add(new Entry(LocalDate.now(), 1, "paycheck", revenue, 222.22, 0.0));
        cash.getEntries().add(new Entry(LocalDate.now(), 2, "buy cigarettes", expense,0.0,  25.43));
        cash.getEntries().add(new Entry(LocalDate.now(), 3, "buy coffee", expense,0.0, 12.12));
        cash.getEntries().add(new Entry(LocalDate.now(), 4, "buy dinner", expense,0.0, 7.48));
        cash.getEntries().add(new Entry(LocalDate.now(), 5, "buy shoes", expense,0.0, 84.39));
        cash.findRunningBalance();
        
        AccountDialog temp = new AccountDialog(cash);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, just need it here
    }//end initialize()
    
}//end FundController
