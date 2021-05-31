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

        
        AccountDialog temp = new AccountDialog(cash);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, just need it here
    }//end initialize()
    
}//end FundController
