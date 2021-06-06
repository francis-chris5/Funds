
package Funds;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * The controller class for the similarly named FXML dialog.
 * @author Chris Francis
 */
public class RemoveAccountDialog extends Dialog implements Initializable{

        ///////////////////////////////////////////  DATAFIELDS  ///////////
    
    @FXML
    ListView lstAccounts;
    @FXML
    ListView lstCategories;
    @FXML
    RadioButton rdAccounts;
    @FXML
    RadioButton rdCategories;
    
    private Book book;
    private AccountType type;
    
    
    
    
    
    
    
    
        ////////////////////////////////////////  CONSTRUCTORS  ///////////
    
    /**
     * The two-arg constructor needs a reference to the current Book and type of account as a string (not the enum value yet) which it gets from context menu on balance sheet tree
     * @param book The current book this account is being added to
     * @param type String (NOT ENUM VALUE this class will set that) representing the type of account ("Assets", "Liabilities", "Equity")
     */
    public RemoveAccountDialog(Book book, AccountType type){
        this.book = book;
        this.type = type;
        this.setTitle("Funds: Remove Account");
        Image icon = new Image(getClass().getResourceAsStream("Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RemoveAccountDialogGUI.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        fillList();
        setActiveList();
        lstAccounts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lstCategories.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ButtonType btnRemove = new ButtonType("Remove Selected Accounts", ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(btnRemove, ButtonType.CANCEL);
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == btnRemove){
            removeSelectedAccounts();
        }
        else{
            //probably don't do anything then
        }
    }//end one-arg constructor
    
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////  CLASS METHODS  //////////
    
    /**
     * internal method to fill the listview with available accounts
     */
    public void fillList(){
        switch(type){
            case ASSET:
                lstAccounts.getItems().clear();
                lstAccounts.getItems().addAll(book.getAssets());
                lstAccounts.getItems().addAll(book.getSubcategoryAccounts(AccountType.ASSET));
                lstCategories.getItems().clear();
                lstCategories.getItems().addAll(book.getSubcategory(AccountType.ASSET));
                break;
            case LIABILITY:
                lstAccounts.getItems().clear();
                lstAccounts.getItems().addAll(book.getLiabilities());
                lstAccounts.getItems().addAll(book.getSubcategoryAccounts(AccountType.LIABILITY));
                lstCategories.getItems().clear();
                lstCategories.getItems().addAll(book.getSubcategory(AccountType.LIABILITY));
                break;
            case EQUITY:
                lstAccounts.getItems().clear();
                lstAccounts.getItems().addAll(book.getEquities());
                lstAccounts.getItems().addAll(book.getSubcategoryAccounts(AccountType.EQUITY));
                lstCategories.getItems().clear();
                lstCategories.getItems().addAll(book.getSubcategory(AccountType.EQUITY));
                break;
            default:
                lstAccounts.getItems().clear();
                lstAccounts.getItems().addAll(book.getAssets());
                lstAccounts.getItems().addAll(book.getSubcategoryAccounts(AccountType.ASSET));
                 lstAccounts.getItems().addAll(book.getLiabilities());
                lstAccounts.getItems().addAll(book.getSubcategoryAccounts(AccountType.LIABILITY));
                lstAccounts.getItems().addAll(book.getEquities());
                lstAccounts.getItems().addAll(book.getSubcategoryAccounts(AccountType.EQUITY));
                lstCategories.getItems().clear();
                lstCategories.getItems().addAll(book.getSubcategory(AccountType.ASSET));
                lstCategories.getItems().addAll(book.getSubcategory(AccountType.LIABILITY));
                lstCategories.getItems().addAll(book.getSubcategory(AccountType.EQUITY));
        }
    }//end fillList()
    
    
    
    /**
     * checks the radio button toggle group to determine which list view to disable
     */
    @FXML
    public void setActiveList(){
        if(rdAccounts.isSelected()){
            lstCategories.setDisable(true);
            lstAccounts.setDisable(false);
        }
        else if(rdCategories.isSelected()){
            lstCategories.setDisable(false);
            lstAccounts.setDisable(true);
        }
    }//end setActiveList()
    
    
    
    /**
     * this removes selected accounts from the book as long as the balance is zero to try and preserve balanced books, shows a warning message asking user to clear balance before removing the account otherwise
     */
    public void removeSelectedAccounts(){
        if(rdAccounts.isSelected()){
            ObservableList<Account> selected = lstAccounts.getSelectionModel().getSelectedItems();
            selected.forEach(a -> {
                if(a.findBalance() == 0.0){
                    switch(type){
                        case ASSET:
                            for(int i = 0; i < book.getAssets().size(); i++){
                                if(book.getAssets().get(i).toString().equals(a.toString())){
                                    book.getAssets().remove(i);
                                }
                            }
                        case LIABILITY:
                            for(int i = 0; i < book.getLiabilities().size(); i++){
                                if(book.getLiabilities().get(i).toString().equals(a.toString())){
                                    book.getLiabilities().remove(i);
                                }
                            }
                        case EQUITY:
                            for(int i = 0; i < book.getEquities().size(); i++){
                                if(book.getEquities().get(i).toString().equals(a.toString())){
                                    book.getEquities().remove(i);
                                }
                            }
                        default:
                            for(int i = 0; i < book.getAssets().size(); i++){
                                if(book.getAssets().get(i).toString().equals(a.toString())){
                                    book.getAssets().remove(i);
                                }
                            }
                            for(int i = 0; i < book.getLiabilities().size(); i++){
                                if(book.getLiabilities().get(i).toString().equals(a.toString())){
                                    book.getLiabilities().remove(i);
                                }
                            }
                            for(int i = 0; i < book.getEquities().size(); i++){
                                if(book.getEquities().get(i).toString().equals(a.toString())){
                                    book.getEquities().remove(i);
                                }
                            }
                    }
                    for(int i = 0; i < book.getAccountCategories().size(); i++){
                        for(int j = 0; j < book.getAccountCategories().get(i).getAccounts().size(); j++){
                            if(book.getAccountCategories().get(i).getAccounts().get(j).toString().equals(a.toString())){
                                book.getAccountCategories().get(i).getAccounts().remove(j);
                            }
                        }
                    }
                    book.setSaved(false);
                }
                else{
                    Alert noRemove = new Alert(AlertType.WARNING);
                    noRemove.setTitle("Funds");
                    noRemove.setContentText("Can not delete an account with a non-zero balance, please clear the balance for " + a.toString() + " and try again.");
                    noRemove.showAndWait();
                }
            });
        }
        else if(rdCategories.isSelected()){
            ObservableList<AccountCategory> selected = lstCategories.getSelectionModel().getSelectedItems();
            selected.forEach(c -> {
                if(c.getAccounts().isEmpty()){
                    for(int i = 0; i < book.getAccountCategories().size(); i++){
                        if(book.getAccountCategories().get(i) == c){
                            book.getAccountCategories().remove(i);
                        }
                    }
                }
                else{
                    Alert noRemove = new Alert(AlertType.WARNING);
                    noRemove.setTitle("Funds");
                    noRemove.setContentText("Can not delete a category that still contains accounts, please move " + c.getAccounts().toString() + " to a different category or delete " + c.getAccounts().toString() + " first and try again to remove " + c.toString() + ".");
                    noRemove.showAndWait();
                }
            });
        }
    }//end removeSelectedAccounts()
    
    
    
    
    
    
        ////////////////////////////////////////////  JAVA OBJECT  //////////
    
    /**
     * I rarely use this but it's in the interfacing requirements
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but it's in the interfacing requirements
    }//end initialize()
    
}//end RemoveAccountDialog
