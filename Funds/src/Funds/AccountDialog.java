
package Funds;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AccountDialog extends Dialog implements Initializable {

    @FXML
    VBox vbxLedger;
    @FXML
    DatePicker dtDate;
    @FXML
    TextField txtTransactionID;
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
    private Book book;
    private TableView<Entry> ledger = new TableView<>();
    
    public AccountDialog(Book book, Account account){
        this.book = book;
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
        cmbTransfer.setVisibleRowCount(5);
        clearTransaction();
        vbxLedger.getChildren().addAll(ledger);
        ButtonType btnFinished = new ButtonType("Finish", ButtonData.OTHER);
        this.getDialogPane().getButtonTypes().addAll(btnFinished, ButtonType.CANCEL);
        dtDate.requestFocus();
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == btnFinished){
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
        
        TableColumn<Entry, String> clmNumber = new TableColumn("ID");
        clmNumber.setPrefWidth(40);
        clmNumber.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        
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
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  ASSET  -----", true), true));
        for(int i = 0; i < book.getAssets().size(); i++){
            cmbTransfer.getItems().add(new ComboBoxItem(book.getAssets().get(i), false));
        }
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  LIABILITY  -----", false), true));
        for(int i = 0; i < book.getLiabilities().size(); i++){
            cmbTransfer.getItems().add(new ComboBoxItem(book.getLiabilities().get(i), false));
        }
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  EQUITY  -----", false), true));
        for(int i = 0; i < book.getEquities().size(); i++){
            cmbTransfer.getItems().add(new ComboBoxItem(book.getEquities().get(i), false));
        }
        cmbTransfer.setCellFactory(cell -> new ListCell<ComboBoxItem>(){
            @Override
            public void updateItem(ComboBoxItem account, boolean empty){
                super.updateItem(account, empty);
                if(!empty){
                    setText(account.getName());
                    setDisable(account.isLabel());
                    setStyle(account.isLabel ? "-fx-text-fill: #FF0000a2;" : "-fx-text-fill: #b5a264;");
                }
            }
        });
    }//end setAccountChoices()
    
    
    
    @FXML
    public void clearTransaction(){
        dtDate.setValue(LocalDate.now());
        txtTransactionID.setText("");
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
            entry.setTransactionID(txtTransactionID.getText());
            entry.setDescription(txtDescription.getText());
            entry.setTransfer(cmbTransfer.getValue() != null ? ((ComboBoxItem)cmbTransfer.getValue()).toAccount() : null);
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
            if(entry.getTransfer() != null){
                addTransfer(entry.getTransfer(), account.isNormalDebit());
            }
            else{
                Account imbalance = new Account();
                boolean exists = false;
                for(int i = 0; i < book.getLiabilities().size(); i++){
                    if(book.getLiabilities().get(i).getName().equals("IMBALANCE")){
                        exists = true;
                        imbalance = book.getLiabilities().get(i);
                    }
                }
                if(!exists){
                    imbalance = new Account("IMBALANCE", false);
                    book.getLiabilities().add(imbalance);
                }
                addTransfer(imbalance, account.isNormalDebit());
            }
            loadAccountLedger();
            clearTransaction();
        }
        catch(Exception e){
            e.printStackTrace();
            //probbaly nothing to add: blank amount, rest are fine
        }
    }//end addTransaction()
    
    
    
    
    public void addTransfer(Account transfer, boolean notNormal){
        try{
            Entry entry = new Entry();
            entry.setDate(dtDate.getValue());
            entry.setTransactionID(txtTransactionID.getText());
            entry.setDescription(txtDescription.getText());
            entry.setTransfer(account);
            entry.setReconcile(chkReconcile.isSelected());
            if(!notNormal){
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
            transfer.getEntries().add(entry);
        }
        catch(Exception e){
            //probbaly nothing to add: blank amount, rest are fine
        }
    }//end addTransfer()
    
    
    
    
    @FXML
    public void removeTransactions(){
        ObservableList<Entry> remove = ledger.getSelectionModel().getSelectedItems();
        remove.forEach(r -> ledger.getItems().remove(r));
        account.setEntries(ledger.getItems());
        account.findRunningBalance();
        ledger.getSelectionModel().clearSelection();
        loadAccountLedger();
    }//end removeTransactions()
    
    
    
    @FXML
    public void editTransaction(){
        ObservableList<Entry> choice = ledger.getSelectionModel().getSelectedItems();
        try{
            choice.forEach(c -> {
                EditTransaction editor = new EditTransaction(c);
                Entry revised = editor.edit();
                if(revised != null){
                    for(int i = 0; i < account.getEntries().size(); i++){
                        if(account.getEntries().get(i) == c){
                            account.getEntries().set(i, revised);
                            account.findRunningBalance();
                            break;
                        }
                    }
                }
            });
            loadAccountLedger();
        }
        catch(Exception e){
            //above changes the selected value but then throws error in javafx objects --my part seems to work fine like this
        }
    }//end editTransaction()
    
    
    
    @FXML
    public void checkKeyboardEvents(KeyEvent event){
        switch(event.getCode()){
            case ENTER:
                addTransaction();
                break;
            default:
                break;
        }
    }//end keyboardEvents
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, just in interfacing requirements
    }
    
    
    
    
    
    
    
    
        ////////////////////////////////////////////  INNER CLASSES  //////////
    
    /**
     * list item for combo box so that category labels can be included
     */
    public class ComboBoxItem{
        private Account account;
        private boolean isLabel;
        public ComboBoxItem(Account account, boolean isLabel){
            this.account = account;
            this.isLabel = isLabel;
        }
        public String getName(){
            if(isLabel){
                return account.getName();
            }
            else{
                return account.toString();
            }
        }
        public boolean isLabel(){
            return isLabel;
        }
        public Account toAccount(){
            if(isLabel){
                return null;
            }
            else{
                return account;
            }
        }
        @Override
        public String toString(){
            return account.toString();
        }
    }
    
    
}//end AccountDialog
