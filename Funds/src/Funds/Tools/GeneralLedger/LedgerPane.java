
package Funds.Tools.GeneralLedger;

import Funds.DataEnums.AccountType;
import Funds.DataObjects.Book;
import Funds.DataObjects.Transaction;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;



/**
 * The general ledger listing out all transactions with filters to sort through and track down the records of a transaction or group of transactions
 * @author Chris Francis
 */
public class LedgerPane extends Pane implements Initializable {
    
        ////////////////////////////////////////////  DATAFIELDS  ///////////
    
    @FXML
    RadioButton rdOn;
    @FXML
    RadioButton rdBefore;
    @FXML
    RadioButton rdAfter;
    @FXML
    RadioButton rdBetween;
    @FXML
    DatePicker dtDate1;
    @FXML
    DatePicker dtDate2;
    @FXML
    TextField txtDescription;
    @FXML
    TextField txtTransactionID;
    @FXML
    RadioButton rdChecked;
    @FXML
    RadioButton rdUnchecked;
    @FXML
    ComboBox cmbTransfer;
    @FXML
    RadioButton rdLess;
    @FXML
    RadioButton rdEqual;
    @FXML
    RadioButton rdMore;
    @FXML
    TextField txtAmount;
    
    @FXML
    Tooltip ttDebit;
    @FXML
    Tooltip ttCredit;
    
    @FXML
    ListView lstTransactions;
    
    private Book book;
    private TableView<Transaction> tblLedger = new TableView<>();
    private LinkedList<Transaction> transactions = new LinkedList<>();
    private ObservableList<LedgerItemPane> observableTransactions = FXCollections.observableArrayList();
    
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////  CONSTRUCTORS  //////////
    
    /**
     * In order to list out all recorded transactions the only thing necessary is the book where those transactions are recorded
     * @param book the current working book
     */
    public LedgerPane(Book book){
        this.book = book;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LedgerPaneGUI.fxml"));
            loader.setController(this);
            this.getChildren().add(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        fillList();
        loadAccountList();
        lstTransactions.getItems().addAll(observableTransactions);
        displayFilteredStats();
    }//end one-arg constructor
    
    
    
    
    
    
    
    
        ///////////////////////////////////////  CLASS METHODS  /////////////
    
    /**
     * makes a single linked list of all transactions in the order they were recorded
     */
    public void fillList(){
        for(int i = 0; i < book.getAssets().size(); i++){
            transactions.addAll(book.getAssets().get(i).getTransactions());
        }
        for(int i = 0; i < book.getSubcategoryAccounts(AccountType.ASSET).size(); i++){
            transactions.addAll(book.getSubcategoryAccounts(AccountType.ASSET).get(i).getTransactions());
        }
        for(int i = 0; i < book.getLiabilities().size(); i++){
            transactions.addAll(book.getLiabilities().get(i).getTransactions());
        }
        for(int i = 0; i < book.getSubcategoryAccounts(AccountType.LIABILITY).size(); i++){
            transactions.addAll(book.getSubcategoryAccounts(AccountType.LIABILITY).get(i).getTransactions());
        }
        for(int i = 0; i < book.getEquities().size(); i++){
            transactions.addAll(book.getEquities().get(i).getTransactions());
        }
        for(int i = 0; i < book.getSubcategoryAccounts(AccountType.EQUITY).size(); i++){
            transactions.addAll(book.getSubcategoryAccounts(AccountType.EQUITY).get(i).getTransactions());
        }
        for(int i = 0; i < book.getLedgerID(); i++){
            Transaction t1 = null;
            Transaction t2 = null;
            for(int j = 0; j < transactions.size(); j++){
                if(transactions.get(j).getLedgerID() == i){
                    if(t1 == null){
                        t1 = transactions.get(j);
                    }
                    else{
                        t2 = transactions.get(j);
                    }
                }
            }
            if(t1 != null && t2 != null){
                observableTransactions.add(new LedgerItemPane(t1, t2));
            }
        }
    }//end fillLists()
    
    
    
    /**
     * internal method to disable/enable the second date picker when it's needed
     */
    @FXML
    public void activateDate2(){
        if(rdBetween.isSelected()){
            dtDate2.setDisable(false);
        }
        else{
            dtDate2.setDisable(true);
        }
    }//end activateDate2()
    
    
    
    
    /**
     * fills the options for the account filter
     */
    public void loadAccountList(){
        cmbTransfer.getItems().clear();
        cmbTransfer.setValue(null);
        cmbTransfer.getItems().addAll(book.getAssets());
        cmbTransfer.getItems().addAll(book.getSubcategoryAccounts(AccountType.ASSET));
        cmbTransfer.getItems().addAll(book.getLiabilities());
        cmbTransfer.getItems().addAll(book.getSubcategoryAccounts(AccountType.LIABILITY));
        cmbTransfer.getItems().addAll(book.getEquities());
        cmbTransfer.getItems().addAll(book.getSubcategoryAccounts(AccountType.EQUITY));
    }//end loadAccountList()
    
    
    
    
    /**
     * uses the selected filters to remove items from the list of transactions being shown
     */
    @FXML
    public void applyFilters(){
        
            // date filters
        if(rdOn.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(dtDate1.getValue() != null && !transactions.get(i).getDate().equals(dtDate1.getValue())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        else if(rdBefore.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(dtDate1.getValue() != null && !transactions.get(i).getDate().isBefore(dtDate1.getValue())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        else if(rdAfter.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(dtDate1.getValue() != null && !transactions.get(i).getDate().isAfter(dtDate1.getValue())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        else if(rdBetween.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(dtDate1.getValue() != null && (transactions.get(i).getDate().isBefore(dtDate1.getValue()) || transactions.get(i).getDate().isAfter(dtDate2.getValue()))){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
            //description filter
        if(txtDescription.getText().length() > 0){
            for(int i = 0; i < transactions.size(); i++){
                if(!transactions.get(i).getDescription().contains(txtDescription.getText())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
        
            //filter by human readable transaction id
        if(txtTransactionID.getText().length() > 0){
            for(int i = 0; i < transactions.size(); i++){
                if(!transactions.get(i).getTransactionID().contains(txtTransactionID.getText())){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
            //reconciled filter
        if(rdChecked.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(!transactions.get(i).isReconcile()){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
        
            //unreconciled filter
        if(rdUnchecked.isSelected()){
            for(int i = 0; i < transactions.size(); i++){
                if(transactions.get(i).isReconcile()){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
        
            //account filter
        if(cmbTransfer.getValue() != null){
            LinkedList<Integer> keepIDs = new LinkedList<>();
            for(int i = 0; i < transactions.size(); i++){
                if(transactions.get(i).getTransfer() == cmbTransfer.getValue()){
                    keepIDs.add(transactions.get(i).getLedgerID());
                }
            }
            for(int i = 0; i < transactions.size(); i++){
                boolean keep = false;
                for(int j = 0; j < keepIDs.size(); j++){
                    if(transactions.get(i).getLedgerID() == keepIDs.get(j)){
                        keep = true;
                        break;
                    }
                }
                if(!keep){
                    transactions.remove(i);
                    i--;
                }
            }
        }
        
        
        
        if(txtAmount.getText().length() > 0){
            double amount = 0.0;
            try{
                amount = Double.parseDouble(txtAmount.getText());
            }
            catch(Exception e){
                try{
                    amount = Double.parseDouble(txtAmount.getText().substring(1));
                }
                catch(NumberFormatException nf){
                    //not a number or number with dollar sign, just ignoring then
                }
            }
            if(amount != 0.0 && rdLess.isSelected()){
                for(int i = 0; i < transactions.size(); i++){
                    if(transactions.get(i).getDebit() >= amount || transactions.get(i).getCredit() >= amount){
                        transactions.remove(i);
                        i--;
                    }
                }
            }
            if(amount != 0.0 && rdEqual.isSelected()){
                for(int i = 0; i < transactions.size(); i++){
                    if(transactions.get(i).getDebit() != amount && transactions.get(i).getCredit() != amount){
                        transactions.remove(i);
                        i--;
                    }
                }
            }
            if(amount != 0.0 && rdMore.isSelected()){
                for(int i = 0; i < transactions.size(); i++){
                    if(transactions.get(i).getDebit() <= amount && transactions.get(i).getCredit() <= amount){
                        transactions.remove(i);
                        i--;
                    }
                }
            }
        }
        
        
        
        
        
            //reset the list with selected filters applied
        observableTransactions.clear();
        for(int i = 0; i < book.getLedgerID(); i++){
            Transaction t1 = null;
            Transaction t2 = null;
            for(int j = 0; j < transactions.size(); j++){
                if(transactions.get(j).getLedgerID() == i){
                    if(t1 == null){
                        t1 = transactions.get(j);
                    }
                    else{
                        t2 = transactions.get(j);
                    }
                }
            }
            if(t1 != null && t2 != null){
                observableTransactions.add(new LedgerItemPane(t1, t2));
            }
        }
        lstTransactions.getItems().clear();
        lstTransactions.getItems().addAll(observableTransactions);
        displayFilteredStats();
    }//end applyFilters()

    
    
    
    
    /**
     * resets the filters and reloads the full list of transactions
     */
    @FXML
    public void clearFilters(){
        rdOn.setSelected(true);
        dtDate1.setValue(null);
        dtDate2.setValue(null);
        dtDate2.setDisable(true);
        txtDescription.clear();
        txtTransactionID.clear();
        rdChecked.setSelected(false);
        rdUnchecked.setSelected(false);
        loadAccountList();
        rdEqual.setSelected(true);
        txtAmount.clear();
        transactions.clear();
        observableTransactions.clear();
        fillList();
        lstTransactions.getItems().clear();
        lstTransactions.getItems().addAll(observableTransactions);
        displayFilteredStats();
    }//end clearFilters()
    
    
    
    
    public void displayFilteredStats(){
        if(transactions.size() > 0){
                //totals
            double debitTotal = 0.0, creditTotal = 0.0;
            //int debits = 0, credits = 0;
            LinkedList<Double> debits = new LinkedList<>();
            LinkedList<Double> credits = new LinkedList<>();
            double debitMin = -1.1, debitMax = 0.0, creditMin = -1.1, creditMax = 0.0;
            double debitMedian = 0.0, creditMedian = 0.0;
            for(int i = 0; i < transactions.size(); i++){
                if(transactions.get(i).getDebit() != 0.0){
                    debitTotal += transactions.get(i).getDebit();
                    debits.add(transactions.get(i).getDebit());
                    if(debitMin > transactions.get(i).getDebit() || debitMin == -1.1){
                        debitMin = transactions.get(i).getDebit();
                    }
                    if(debitMax < transactions.get(i).getDebit()){
                        debitMax = transactions.get(i).getDebit();
                    }
                }
                else if(transactions.get(i).getCredit() != 0.0){
                    creditTotal += transactions.get(i).getCredit();
                    credits.add(transactions.get(i).getCredit());
                    if(creditMin > transactions.get(i).getCredit() || creditMin == -1.1){
                        creditMin = transactions.get(i).getCredit();
                    }
                    if(creditMax < transactions.get(i).getCredit()){
                        creditMax = transactions.get(i).getCredit();
                    }
                }
            }
            Collections.sort(debits);
            Collections.sort(credits);
            
                //average
            double debitAverage = debitTotal/debits.size();
            double creditAverage = creditTotal/credits.size();
            
                //get median and percentiles
            if(debits.size() % 2 == 0){
                int middle = debits.size() / 2 - 1;
                debitMedian = (debits.get(middle) + debits.get(middle + 1)) / 2;
            }
            else{
                int middle = debits.size() / 2;
                debitMedian = debits.get(middle);
            }
            if(credits.size() % 2 == 0){
                int middle = credits.size() / 2 - 1;
                creditMedian = (credits.get(middle) + credits.get(middle + 1)) / 2;
            }
            else{
                int middle = credits.size() / 2;
                creditMedian = credits.get(middle);
            }
            
                //standard deviation
            double variance = 0.0;
            for(int i = 0; i < debits.size(); i++){
                variance += Math.pow((debits.get(i) - debitAverage), 2);
            }
            double debitStdDev = Math.sqrt(variance/debits.size());
            variance = 0.0;
            for(int i = 0; i < credits.size(); i++){
                variance += Math.pow((credits.get(i) - creditAverage), 2);
            }
            double creditStdDev = Math.sqrt(variance/credits.size());
            
            
                //debits
            String debitStats = "Total:\t\t" + currencyFormat.format(debitTotal)  + "\n";
            debitStats += "Average:\t\t" + currencyFormat.format(debitAverage) + "\n";
            debitStats += "Median:\t\t" + currencyFormat.format(debitMedian) + "\n";
            debitStats += "Std.Dev.:\t\t" + currencyFormat.format(debitStdDev) + "\n";
            debitStats += "Minimum:\t" + currencyFormat.format(debitMin) + "\n";
            debitStats += "Maximum:\t" + currencyFormat.format(debitMax) + "\n";
            
                //credits
            String creditStats = "Total:\t\t" + currencyFormat.format(creditTotal) + "\n";
            creditStats += "Average:\t\t" + currencyFormat.format(creditAverage) + "\n";
            creditStats += "Median:\t\t" + currencyFormat.format(creditMedian) + "\n";
            creditStats += "Std. Dev.:\t\t" + currencyFormat.format(creditStdDev) + "\n";
            creditStats += "Minimum:\t" + currencyFormat.format(creditMin) + "\n";
            creditStats += "Maximum:\t" + currencyFormat.format(creditMax) + "\n";

                //display
            ttDebit.setText(debitStats);
            ttCredit.setText(creditStats);
        }
    }//end displayFilteredStats()
    
    
    
    
    
    /**
     * Method to listen for and react to keyboard events, currently the only one is to try apply filters when the enter key is pressed
     * @param event The keyboard event detected
     */
    @FXML
    public void checkKeyboardEvents(KeyEvent event){
        switch(event.getCode()){
            case ENTER:
                applyFilters();
                break;
            default:
                break;
        }
    }//end keyboardEvents
    
    
    
    
    
    
        /////////////////////////////////////////  JAVA OBJECT  /////////////
    
    /**
     * I rarely use this but it's in the interfacing requirements for fxml
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but it's in the interfacing requirements for fxml
    }

}//end LedgerPane
