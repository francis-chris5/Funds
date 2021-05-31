
package Funds;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AccountDialog extends Dialog implements Initializable {

    @FXML
    VBox vbxLedger;
    
    private Account account;
    private TableView<Entry> ledger = new TableView<>();
    
    public AccountDialog(Account account){
        this.account = account;
        this.setTitle("Funds: " + account.getName());
        Image icon = new Image(getClass().getResourceAsStream("Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountDialogGUI.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        loadAccountLedger();
        this.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == ButtonType.FINISH){
            //save changes and close dialog
        }
        else if(clicked.get() == ButtonType.CANCEL){
            //discard changes and close dialog
        }
        else{
            //there's probably something else
        }
    }//end constructor
    
    
    public void loadAccountLedger(){
        TableColumn<Entry, LocalDate> clmDate = new TableColumn<>("Date");
        clmDate.setPrefWidth(75);
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        TableColumn<Entry, Integer> clmNumber = new TableColumn("Num");
        clmNumber.setPrefWidth(40);
        clmNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        
        TableColumn<Entry, String> clmDescription = new TableColumn<>("Description");
        clmDescription.setPrefWidth(200);
        clmDescription.setCellValueFactory(new PropertyValueFactory("description"));
        
        TableColumn<Entry, Account> clmTransfer = new TableColumn("Transfer");
        clmTransfer.setPrefWidth(200);
        clmTransfer.setCellValueFactory(new PropertyValueFactory("transfer"));
        
        TableColumn<Entry, Boolean> clmReconcile = new TableColumn<>("R");
        clmReconcile.setPrefWidth(25);
        clmTransfer.setCellValueFactory(new PropertyValueFactory<>("reconcile"));
        
        TableColumn<Entry, Double> clmDebit = new TableColumn<>("Debit");
        clmDebit.setPrefWidth(75);
        clmDebit.setCellValueFactory(new PropertyValueFactory<>("debit"));
        
        TableColumn<Entry, Double> clmCredit = new TableColumn<>("Credit");
        clmCredit.setPrefWidth(75);
        clmCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        
        TableColumn<Entry, Double> clmBalance = new TableColumn<>("Balance");
        clmBalance.setPrefWidth(95);
        clmBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        
        ledger.setItems(account.getEntries());
        ledger.getColumns().addAll(clmDate, clmNumber, clmDescription, clmTransfer, clmReconcile, clmDebit, clmCredit, clmBalance);
        
        //hbox with inputs for the next entry that matches table
        
        vbxLedger.getChildren().addAll(ledger);
    }//end loadAccountLedger()
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, just in interfacing requirements
    }
    
}//end AccountDialog
