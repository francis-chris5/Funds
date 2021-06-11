
package Funds.Tools.Budgeting.RevenueSplitter;

import Funds.DataEnums.AccountType;
import Funds.DataObjects.Account;
import Funds.DataObjects.Book;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


/**
 * The primary control class for all of the user and machine interface components making up this tool
 * 
 * @author Chris
 */
public class RevenueSplitter extends Pane implements Initializable, RevenueSplitterItemPane.SplitControl {
    
    
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
    TextField txtMoveAmount;
    
    @FXML
    ListView lstSplitItems;
    
    private Book book;
    private LinkedList<RevenueSplitterItem> currentItems = new LinkedList<>();
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    
    
    
    
    
    
    
    
        //////////////////////////////////////  CONSTRUCTORS  ///////////
    
    /**
     * The one-arg constructor only requires a reference to the book containing the accounts the money will be split among
     * @param book The currently open book
     */
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
        clearControls();
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
    
    
    
    
    /**
     * Resets/Clears the GUI components for a split
     */
    @FXML
    public void clearControls(){
        txtSplitName.clear();
        txtSplitAmount.clear();
        txtMoveAmount.clear();
        lstSplitItems.getItems().clear();
        currentItems.clear();
    }//end clearControls()
    
    
    
    
    /**
     * Creates a revenue splitter item for each selected account and fills the list of items to have controls shown on the interface
     */
    @FXML
    public void selectAccounts(){
        ObservableList<Account> selected = lstAccounts.getSelectionModel().getSelectedItems();
        selected.forEach(acc -> {
            RevenueSplitterItem it = new RevenueSplitterItem();
            it.setAccount((Account)acc);
            it.setAutomatic(true);
            currentItems.add(it);
        });
        findAutomaticPercents();
        displayAccounts();
        lstAccounts.getSelectionModel().clearSelection();
    }//end selectAccounts()
    
    
    
    /**
     * This method actually walks through the list of revenue splitter items and loads in the GUI controls for each one
     */
    @FXML
    public void displayAccounts(){
        lstSplitItems.getItems().clear();
        ObservableList<RevenueSplitterItemPane> splitterPanes = FXCollections.observableArrayList();
        for(int i = 0; i < currentItems.size(); i++){
            RevenueSplitterItemPane rsip = new RevenueSplitterItemPane(currentItems.get(i));
            rsip.setSplitControl(this);
            splitterPanes.add(rsip);
        }
        lstSplitItems.getItems().addAll(splitterPanes);
    }//end displayAccounts()
    
    
    
    
    /**
     * Removes the selected revenue splitter item pane from the list of controls
     */
    @FXML
    public void removeSplitterAccount(){
        int item= lstSplitItems.getSelectionModel().getSelectedIndex();
        currentItems.remove(item);
        displayAccounts();
    }//end removeSplitterAccount()
    
    
    
    
    /**
     * Copies the list of revenue splitter items to the current book and clears the controls to prepare for the next split
     */
    @FXML
    public void saveSplit(){
        RevenueSplit split = new RevenueSplit();
        split.setName(txtSplitName.getText().length() == 0 ? "untitled split from " + LocalDate.now() : txtSplitName.getText() + " from " + LocalDate.now());
        for(int i  = 0; i < currentItems.size(); i++){
            split.getItems().add(currentItems.get(i));
        }
        currentItems.clear();
        book.getSavedSplits().add(split);
        book.setSaved(false);
        book.displayDetails();
        txtSplitName.clear();
        lstSplitItems.getItems().clear();
        fillLists();
    }//end saveSplits()
    
    
    
    
    /**
     * loads in a previously saved revenue split
     */
    @FXML
    public void openSplit(){
        RevenueSplit split = (RevenueSplit)lstSplits.getSelectionModel().getSelectedItem();
        //currentItems = split.getItems();
        currentItems.clear();
        for(int i = 0; i < split.getItems().size(); i++){
            currentItems.add(split.getItems().get(i));
        }
        findAutomaticPercents();
        displayAccounts();
    }//end openSplit()
    
    
    
    /**
     * removes a stored list of revenue split items from the current book
     */
    @FXML
    public void removeSavedSplit(){
        RevenueSplit split = (RevenueSplit)lstSplits.getSelectionModel().getSelectedItem();
        for(int i = 0; i < book.getSavedSplits().size(); i++){
            if(book.getSavedSplits().get(i) == split){
                book.getSavedSplits().remove(i);
            }
        }
        lstSplitItems.getItems().clear();
        book.setSaved(false);
        book.displayDetails();
        fillLists();
    }//end removedSavedSplit()
    
    
    
    
    /**
     * subtracts any non-automatic percents from one-hundred and then divides the remaining percentage evenly among the automatic items
     */
    @FXML
    public void findAutomaticPercents(){
        double available = 100;
        int autos = 0;
        for(int i = 0; i < currentItems.size(); i++){
            if(!currentItems.get(i).isAutomatic()){
                available -= currentItems.get(i).getPercent();
            }
            else{
                autos++;
            }
        }
        for(int i = 0; i < currentItems.size(); i++){
            if(currentItems.get(i).isAutomatic()){
                currentItems.get(i).setPercent(available/autos);
            }
        }
    }//end findAutomaticPercents()
    
    
    
    
    /**
     * Method that walks through the settings for the split to calculate and display amounts
     */
    @FXML
    public void calculateTransfers(){
        double totalPercent = 0.0;
        findAutomaticPercents();
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
            double splitAmount = 0.0;
            try{
                splitAmount = Double.parseDouble(txtSplitAmount.getText());
            }
            catch(NumberFormatException e0){
                try{
                        //check if it's a dollar sign
                    splitAmount = Double.parseDouble(txtSplitAmount.getText().substring(1));
                }
                catch(Exception e1){
                    //bad input just move on with it at zero then
                }
            }
            for(int i = 0; i < currentItems.size(); i++){
                currentItems.get(i).setAmount(splitAmount * 0.01 * currentItems.get(i).getPercent());
                moving += !currentItems.get(i).isExcluded() ? currentItems.get(i).getAmount() : 0.0;
            }
            txtMoveAmount.setText(currencyFormat.format(moving));
            displayAccounts();
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
