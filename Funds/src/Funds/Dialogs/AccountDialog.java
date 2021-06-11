
package Funds.Dialogs;

import Funds.DataEnums.AccountType;
import Funds.DataObjects.Account;
import Funds.DataObjects.Book;
import Funds.DataObjects.Transaction;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
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


/**
 * This could just be called the "ledger" for each account as that's the old school (paper) model I had in mind for book keeping
 * @author Chris Francis
 */
public class AccountDialog extends Dialog implements Initializable {

        ///////////////////////////////////////////  DATAFIELDS  ////////////
    
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
    private TableView<Transaction> ledger = new TableView<>();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    
    
    
    
    
    
    
        //////////////////////////////////////////////////  CONSTRUCTORS  //////////
    
    /**
     * In order to interact with the account two fields are necessary:
     * @param book The book this account is recorded in
     * @param account  The account to do stuff with
     */
    public AccountDialog(Book book, Account account){
        this.book = book;
        this.account = account;
        this.setTitle("Funds: " + account.getName());
        Image icon = new Image(getClass().getResourceAsStream("../Images/FundsIcon.png"));
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
    
    
    
    
    
    
    
    
        ////////////////////////////////////////////  CLASS METHODS  ///////////
    
    /**
     * Internal method that uses cell factories to create columns for the ledger based off Transaction objects
     */
    public void loadAccountLedger(){
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        ledger.getColumns().clear();
        TableColumn<Transaction, LocalDate> clmDate = new TableColumn<>("Date");
        clmDate.setPrefWidth(75);
        clmDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        TableColumn<Transaction, String> clmNumber = new TableColumn("ID");
        clmNumber.setPrefWidth(40);
        clmNumber.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        
        TableColumn<Transaction, String> clmDescription = new TableColumn<>("Description");
        clmDescription.setPrefWidth(200);
        clmDescription.setCellValueFactory(new PropertyValueFactory("description"));
        
        TableColumn<Transaction, Account> clmTransfer = new TableColumn("Transfer");
        clmTransfer.setPrefWidth(200);
        clmTransfer.setCellValueFactory(new PropertyValueFactory("transfer"));
        
        TableColumn<Transaction, Boolean> clmReconcile = new TableColumn<>("R");
        clmReconcile.setPrefWidth(25);
        clmReconcile.setCellValueFactory(new PropertyValueFactory<>("reconcile"));
        clmReconcile.setCellFactory(chk -> new CheckBoxTableCell());
        
        TableColumn<Transaction, Double> clmDebit = new TableColumn<>("Debit");
        clmDebit.setPrefWidth(75);
        clmDebit.setCellValueFactory(new PropertyValueFactory<>("debit"));
        clmDebit.setCellFactory(cell -> new TableCell<Transaction, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == 0.0 ? "" : currencyFormat.format(item));
            }
        });
        
        TableColumn<Transaction, Double> clmCredit = new TableColumn<>("Credit");
        clmCredit.setPrefWidth(75);
        clmCredit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        clmCredit.setCellFactory(cell -> new TableCell<Transaction, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == 0.0 ? "" : currencyFormat.format(item));
            }
        });
        
        TableColumn<Transaction, Double> clmBalance = new TableColumn<>("Balance");
        clmBalance.setPrefWidth(95);
        clmBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        clmBalance.setCellFactory(cell -> new TableCell<Transaction, Double>(){
            @Override
            protected void updateItem(Double item, boolean empty){
                super.updateItem(item, empty);
                setText(empty || item == 0.0 ? "" : currencyFormat.format(item));
            }
        });
        
        transactions.clear();
        transactions.addAll(account.getTransactions());
        ledger.setItems(transactions);
        ledger.getColumns().addAll(clmDate, clmNumber, clmDescription, clmTransfer, clmReconcile, clmDebit, clmCredit, clmBalance);

        
    }//end loadAccountLedger()
    
    
    
    
    /**
     * internal method to set up the options in the ComboBox on the GUI
     */
    public void setAccountChoices(){
        cmbTransfer.getItems().clear();
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  ASSET  -----", true), true));
        LinkedList<Account> assetAccounts = new LinkedList<>();
        assetAccounts.addAll(book.getAssets());
        assetAccounts.addAll(book.getSubcategoryAccounts(AccountType.ASSET));
        for(int i = 0; i < assetAccounts.size(); i++){
            if(!assetAccounts.get(i).toString().equals(account.toString())){
                cmbTransfer.getItems().add(new ComboBoxItem(assetAccounts.get(i), false));
            }
        }
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  LIABILITY  -----", false), true));
        LinkedList<Account> liabilityAccounts = new LinkedList<>();
        liabilityAccounts.addAll(book.getLiabilities());
        liabilityAccounts.addAll(book.getSubcategoryAccounts(AccountType.LIABILITY));
        for(int i = 0; i < liabilityAccounts.size(); i++){
            if(!liabilityAccounts.get(i).toString().equals(account.toString())){
                cmbTransfer.getItems().add(new ComboBoxItem(liabilityAccounts.get(i), false));
            }
        }
        cmbTransfer.getItems().add(new ComboBoxItem(new Account("-----  EQUITY  -----", false), true));
        LinkedList<Account> equityAccounts = new LinkedList<>();
        equityAccounts.addAll(book.getEquities());
        equityAccounts.addAll(book.getSubcategoryAccounts(AccountType.EQUITY));
        for(int i = 0; i < equityAccounts.size(); i++){
            if(!equityAccounts.get(i).toString().equals(account.toString())){
                cmbTransfer.getItems().add(new ComboBoxItem(equityAccounts.get(i), false));
            }
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
    
    
    
    
    /**
     * Clears the input fields for a new transaction
     */
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
    
    
    
    
    /**
     * uses the values selected/entered for the input fields to create a new Transaction object to represent the details of a real world transaction
     */
    @FXML
    public void addTransaction(){
        try{
            Transaction transaction = new Transaction();
            transaction.setDate(dtDate.getValue());
            transaction.setTransactionID(txtTransactionID.getText());
            transaction.setDescription(txtDescription.getText());
            transaction.setTransfer(cmbTransfer.getValue() != null ? ((ComboBoxItem)cmbTransfer.getValue()).toAccount() : null);
            transaction.setReconcile(chkReconcile.isSelected());
            if(account.isNormalDebit()){
                if(Double.parseDouble(txtAmount.getText()) < 0){
                    transaction.setCredit(-Double.parseDouble(txtAmount.getText()));
                }
                else{
                    transaction.setDebit(Double.parseDouble(txtAmount.getText()));
                }
            }
            else{
                if(Double.parseDouble(txtAmount.getText()) < 0){
                    transaction.setDebit(-Double.parseDouble(txtAmount.getText()));
                }
                else{
                    transaction.setCredit(Double.parseDouble(txtAmount.getText()));
                }
            }
            transaction.setLedgerID(book.getLedgerID());
            account.getTransactions().add(transaction);
            account.findRunningBalance();
            if(transaction.getTransfer() != null){
                addTransfer(transaction.getTransfer(), account.isNormalDebit());
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
                transaction.setTransfer(imbalance);
                addTransfer(imbalance, account.isNormalDebit());
            }
            loadAccountLedger();
            clearTransaction();
            book.incrementLedgerID();
            book.setSaved(false);
            book.displayDetails();
        }
        catch(Exception e){
            //probbaly nothing to add: blank amount, rest are fine
        }
    }//end addTransaction()
    
    
    
    
    /**
     * Internal method to implement double transaction method accounting
     * @param transfer The account to counter balance the changes from the new transaction transaction
     * @param notNormal The debit/credit status of the transfer account is automatically the opposite from the initial transaction
     */
    public void addTransfer(Account transfer, boolean notNormal){
        try{
            Transaction transaction = new Transaction();
            transaction.setDate(dtDate.getValue());
            transaction.setTransactionID(txtTransactionID.getText());
            transaction.setDescription(txtDescription.getText());
            transaction.setTransfer(account);
            transaction.setReconcile(chkReconcile.isSelected());
            if(!notNormal){
                if(Double.parseDouble(txtAmount.getText()) < 0){
                    transaction.setCredit(-Double.parseDouble(txtAmount.getText()));
                }
                else{
                    transaction.setDebit(Double.parseDouble(txtAmount.getText()));
                }
            }
            else{
                if(Double.parseDouble(txtAmount.getText()) < 0){
                    transaction.setDebit(-Double.parseDouble(txtAmount.getText()));
                }
                else{
                    transaction.setCredit(Double.parseDouble(txtAmount.getText()));
                }
            }
            transaction.setLedgerID(book.getLedgerID());
            transfer.getTransactions().add(transaction);
        }
        catch(Exception e){
            //probbaly nothing to add: blank amount, rest are fine
        }
    }//end addTransfer()
    
    
    
    
    /**
     * Method to remove selected transaction entries from this account's ledger
     */
    @FXML
    public void removeTransactions(){
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Funds");
        confirm.setContentText("Are you sure you want to remove these transactions from the ledger?");
        Optional<ButtonType> clicker = confirm.showAndWait();
        if(clicker.get() == ButtonType.OK){
            ObservableList<Transaction> remove = ledger.getSelectionModel().getSelectedItems();
            remove.forEach(transaction -> {
                for(int i = 0; i < transaction.getTransfer().getTransactions().size(); i++){
                    if(transaction.getTransfer().getTransactions().get(i).getLedgerID() == transaction.getLedgerID()){
                        transaction.getTransfer().getTransactions().remove(i);
                    }
                }
                account.getTransactions().remove(transaction);
            });
        }
        else{
            //just cancel out then without doing anything
        }
        ledger.getSelectionModel().clearSelection();
        loadAccountLedger();
        book.setSaved(false);
        book.displayDetails();
    }//end removeTransactions()
    
    
    
    
    /**
     * opens the EditTransactionDialog dialog with the transaction selected from the ledger
     */
    @FXML
    public void editTransaction(){
        ObservableList<Transaction> choice = ledger.getSelectionModel().getSelectedItems();
        try{
            choice.forEach(c -> {
                EditTransactionDialog editor = new EditTransactionDialog(book, account, c);
                Transaction revised = editor.edit();
                if(revised != null){
                    for(int i = 0; i < account.getTransactions().size(); i++){
                        if(account.getTransactions().get(i) == c){
                            account.getTransactions().set(i, revised);
                            account.findRunningBalance();
                            break;
                        }
                    }
                }
            });
            loadAccountLedger();
            book.setSaved(false);
            book.displayDetails();
        }
        catch(Exception e){
            //above changes the selected value but then throws error in javafx objects --my part seems to work fine like this
        }
    }//end editTransaction()
    
    
    
    
    /**
     * Method to listen for and react to keyboard events, currently the only one is to try and add the new transaction when the enter key is pressed
     * @param event The keyboard event detected
     */
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
    
    
    
    
    
    
    
    
        ////////////////////////////////////////////  JAVA OBJECT  //////////
    
    /**
     * I rarely use this, just in interfacing requirements
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, just in interfacing requirements
    }
    
    
    
    
    
    
    
    
        ////////////////////////////////////////////  INNER CLASSES  //////////
    
    /**
     * list item for combo box so that category labels can be included which requires a factory
     */
    public class ComboBoxItem{
        
            /////////////////////////////  DATAFIELDS  /////////
        
        private Account account;
        private boolean isLabel;
        
        
            ///////////////////////////  CONSTRUCTORS  /////////
        public ComboBoxItem(Account account, boolean isLabel){
            this.account = account;
            this.isLabel = isLabel;
        }//end constructor
        
        
            ///////////////////////  CLASS METHODS  //////////
        public String getName(){
            if(isLabel){
                return account.getName();
            }
            else{
                return account.toString();
            }
        }//end getName()
        
        
        public boolean isLabel(){
            return isLabel;
        }//end isLabel()
        
        
        public Account toAccount(){
            if(isLabel){
                return null;
            }
            else{
                return account;
            }
        }//end toAccount()
        
        
        
            ///////////////////////////  JAVA OBJECT  //////////////
        @Override
        public String toString(){
            return account.toString();
        }//end toString()
        
    }//end ComboBoxItem
    
    
}//end AccountDialog
