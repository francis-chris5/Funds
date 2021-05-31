
package Funds;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AccountDialog extends Dialog implements Initializable {

    @FXML
    VBox vbxLedger;
    @FXML
    DatePicker dtDate;
    @FXML
    TextField txtNumber;
    @FXML
    TextField txtDescription;
    @FXML
    ComboBox cmbTransfer;
    @FXML
    CheckBox chkReconcile;
    @FXML
    TextField txtAmount;
    @FXML
    Button btnAdd;
    @FXML
    Button btnClear;
    @FXML
    Button btnRemove;
    
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
        setAccountChoices();
        clearTransaction();
        vbxLedger.getChildren().addAll(ledger);
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
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        ledger.getColumns().clear();
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
        clmReconcile.setCellValueFactory(new PropertyValueFactory<>("reconcile"));
        clmReconcile.setCellFactory(chk -> new CheckBoxTableCell());
        
        TableColumn<Entry, Double> clmDebit = new TableColumn<>("Debit");
        clmDebit.setPrefWidth(75);
        clmDebit.setCellValueFactory(new PropertyValueFactory<>("debit"));
        clmDebit.setCellFactory(cell -> new TableCell<Entry, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == 0.0 ? "" : currencyFormat.format(item));
            }
        });
        
        TableColumn<Entry, Double> clmCredit = new TableColumn<>("Credit");
        clmCredit.setPrefWidth(75);
        clmCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        clmCredit.setCellFactory(cell -> new TableCell<Entry, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == 0.0 ? "" : currencyFormat.format(item));
            }
        });
        
        TableColumn<Entry, Double> clmBalance = new TableColumn<>("Balance");
        clmBalance.setPrefWidth(95);
        clmBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        clmBalance.setCellFactory(cell -> new TableCell<Entry, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == 0.0 ? "" : currencyFormat.format(item));
            }
        });
        
        ledger.setItems(account.getEntries());
        ledger.getColumns().addAll(clmDate, clmNumber, clmDescription, clmTransfer, clmReconcile, clmDebit, clmCredit, clmBalance);

        
    }//end loadAccountLedger()
    
    
    
    public void setAccountChoices(){
        cmbTransfer.getItems().clear();
        cmbTransfer.getItems().addAll(new Account("revenue", false), new Account("expense", true));
    }//end setAccountChoices()
    
    
    
    @FXML
    public void clearTransaction(){
        dtDate.setValue(LocalDate.now());
        txtNumber.setText("" + (account.getEntries().size() + 1));
        txtDescription.setText("");
        txtDescription.setPromptText("Enter a description");
        cmbTransfer.setValue(null);
        chkReconcile.setSelected(false);
        txtAmount.setText("");
        txtAmount.setPromptText("Enter + or - amount");
        dtDate.requestFocus();
    }//end clearTransaction()
    
    
    @FXML
    public void addTransaction(){
        try{
            Entry entry = new Entry();
            entry.setDate(dtDate.getValue());
            entry.setNumber(Integer.parseInt(txtNumber.getText()));
            entry.setDescription(txtDescription.getText());
            entry.setTransfer((Account)cmbTransfer.getValue());
            entry.setReconcile(chkReconcile.isSelected());
            if(account.isNormalDebit()){
                if(Double.parseDouble(txtAmount.getText()) < 0){
                    entry.setCredit(-Double.parseDouble(txtAmount.getText()));
                }
                else{
                    entry.setDebit(Double.parseDouble(txtAmount.getText()));
                }
            }
            else{
                if(Double.parseDouble(txtAmount.getText()) < 0){
                    entry.setDebit(-Double.parseDouble(txtAmount.getText()));
                }
                else{
                    entry.setCredit(Double.parseDouble(txtAmount.getText()));
                }
            }
            account.getEntries().add(entry);
            account.findRunningBalance();
            loadAccountLedger();
            clearTransaction();
        }
        catch(Exception e){
            //probbaly nothing to add: blank amount, rest are fine
        }
    }//end addTransaction()
    
    
    
    
    @FXML
    public void removeTransactions(){
        ObservableList<Entry> remove = ledger.getSelectionModel().getSelectedItems();
        remove.forEach(r -> ledger.getItems().remove(r));
        account.setEntries(ledger.getItems());
        account.findRunningBalance();
        ledger.getSelectionModel().clearSelection();
        loadAccountLedger();
    }//end removeTransactions()
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, just in interfacing requirements
    }
    
}//end AccountDialog
