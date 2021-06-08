
package Funds.Tools.Budgeting.RevenueSplitter;

import Funds.DataEnums.AccountType;
import Funds.DataObjects.Account;
import Funds.DataObjects.Book;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * One thing I always kept set up in my spreadsheet that seemed to be lacking in the accounting software was a way to divy up the paychecks (or other income) between various savings/checking accounts based off percentages.
 * This tool brings in that functionality: select several accounts from the list of available accounts, then use sliders to determine what percentage goes to each account, and since most money comes in as direct deposit these days a checkbox to exclude the initial one from the total to see how much transfers out of it (for those times when the direct deposit goes to an account with a limited number of monthly transfers --typical savings-- and money needs moved in bulk to an account with unlimited transfers --typical checking-- to shuffle it all around)
 * @author Chris
 */
public class RevenueSplitter extends Pane implements Initializable {
    
    
        /////////////////////////////////////////  DATAFIELDS  ////////////
    
    @FXML
    ListView lstAccounts;
    @FXML
    ListView lstSplits;
    @FXML
    TextField txtSplitAmount;
    @FXML
    TextField txtSplitName;
    @FXML
    VBox vbxControls;
    @FXML
    TextField txtMoveAmount;
    
    private Book book;
    private LinkedList<RevenueSplitterItem> currentItems = new LinkedList<>();
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    
    
    
    
    
    
    
    
        //////////////////////////////////////  CONSTRUCTORS  ///////////
    
    public RevenueSplitter(Book book){
        this.book = book;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RevenueSplitterGUI.fxml"));
            loader.setController(this);
            this.getChildren().add(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        fillLists();
        prepControls();
        lstAccounts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
    }//end constructor
    
    
    
    
    
    
    
    
        //////////////////////////////////////////////  CLASS METHODS  /////////
    
    /**
     * internal method for filling up list of available accounts, has to be public to use with fxml
     */
    public void fillLists(){
        lstAccounts.getItems().clear();
        lstAccounts.getItems().addAll(book.getAssets());
        lstAccounts.getItems().addAll(book.getSubcategoryAccounts(AccountType.ASSET));
        lstAccounts.getItems().addAll(book.getLiabilities());
        lstAccounts.getItems().addAll(book.getSubcategoryAccounts(AccountType.LIABILITY));
        lstAccounts.getItems().addAll(book.getEquities());
        lstAccounts.getItems().addAll(book.getSubcategoryAccounts(AccountType.EQUITY));
        lstSplits.getItems().clear();
        lstSplits.getItems().addAll(book.getSavedSplits());
    }//end fillList()
    
    
    
    
    public void prepControls(){
        txtSplitName.setPromptText("untitled split");
        vbxControls.getChildren().clear();
    }//end prepControls()
    
    
    
    
    @FXML
    public void selectAccounts(){
        ObservableList<Account> selected = lstAccounts.getSelectionModel().getSelectedItems();
        selected.forEach(acc -> {
            RevenueSplitterItem it = new RevenueSplitterItem();
            it.setAccount((Account)acc);
            currentItems.add(it);
        });
        displayAccounts();
        lstAccounts.getSelectionModel().clearSelection();
    }//end selectAccounts()
    
    
    
    @FXML
    public void displayAccounts(){
        vbxControls.getChildren().clear();
        for(int i = 0; i < currentItems.size(); i++){
            vbxControls.getChildren().add(new RevenueSplitterItemPane(currentItems.get(i)));
        }
    }//end displayAccounts()
    
    
    
    
    @FXML
    public void saveSplit(){
        RevenueSplit split = new RevenueSplit();
        split.setName(txtSplitName.getText().length() == 0 ? "untitled split " + LocalDate.now() : txtSplitName.getText());
        for(int i  = 0; i < currentItems.size(); i++){
            split.getItems().add(currentItems.get(i));
        }
        currentItems.clear();
        book.getSavedSplits().add(split);
        book.setSaved(false);
        txtSplitName.clear();
        vbxControls.getChildren().clear();
        fillLists();
    }//end saveSplits()
    
    
    
    
    @FXML
    public void openSplit(){
        RevenueSplit split = (RevenueSplit)lstSplits.getSelectionModel().getSelectedItem();
        currentItems = split.getItems();
        displayAccounts();
    }//end openSplit()
    
    
    
    
    @FXML
    public void removeSavedSplit(){
        RevenueSplit split = (RevenueSplit)lstSplits.getSelectionModel().getSelectedItem();
        for(int i = 0; i < book.getSavedSplits().size(); i++){
            if(book.getSavedSplits().get(i) == split){
                book.getSavedSplits().remove(i);
            }
        }
        vbxControls.getChildren().clear();
        fillLists();
    }//end removedSavedSplit()
    
    
    
    
    @FXML
    public void calculateTransfers(){
        double totalPercent = 0.0;
        for(int i = 0; i < currentItems.size(); i++){
            totalPercent += currentItems.get(i).getPercent();
        }
        if(totalPercent > 100){
            txtSplitAmount.setStyle("-fx-background-color: red;");
            txtMoveAmount.setStyle("-fx-background-color: red;");
            txtMoveAmount.setText("> 100%");
        }
        else{
            txtSplitAmount.setStyle("-fx-background-color: white;");
            txtMoveAmount.setStyle("-fx-background-color: white;");
            double moving = 0.0;
            try{
                double splitAmount = Double.parseDouble(txtSplitAmount.getText());
                for(int i = 0; i < currentItems.size(); i++){
                    currentItems.get(i).setAmount(splitAmount * 0.01 * currentItems.get(i).getPercent());
                    moving += !currentItems.get(i).isExcluded() ? currentItems.get(i).getAmount() : 0.0;
                }
                txtMoveAmount.setText(currencyFormat.format(moving));
                displayAccounts();
            }
            catch(Exception e){
                //it's probably not a number then
            }
        }
        
    }//end calculateTransfers()

    
    
    
    
    
    
    
        ////////////////////////////////////  JAVA OBJECT  /////////////
    
    /**
     * I rarely use this but it's in the interfacing requirements for fxml
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but it's in the interfacing requirements for fxml
    }//end initialize()
    
}//end RevenueSplitter
