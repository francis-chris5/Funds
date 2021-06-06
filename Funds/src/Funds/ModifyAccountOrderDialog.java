
package Funds;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * The controller class for the similarly named FXML dialog. The standard practice in accounting is that accounts are listed by order of liquidity (how quickly it can be turned into cash).
 * @author Chris Francis
 */
public class ModifyAccountOrderDialog extends Dialog implements Initializable {

        /////////////////////////////////////////////  DATAFIELDS  /////////
    
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
    
    
    
    
    
    
    
        ////////////////////////////////////////////  CONSTRUCTORS  //////////
    
    /**
     * In order to move accounts around this object will need to know what book the accounts are stored in and what type of accounts need moved
     * @param book The currently open book
     * @param type The enumerated account type
     */
    public ModifyAccountOrderDialog(Book book, AccountType type){
        this.book = book;
        this.type = type;
        this.setTitle("Funds: Modify Account Order");
        Image icon = new Image(getClass().getResourceAsStream("Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyAccountOrderDialogGUI.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        fillList();
        setActiveList();
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == ButtonType.OK){
            //should be finished at this point
        }
        else{
            //probably don't do anything then
        }
    }//end two-argconstructor
    
    
    
    
    
    
    
    
        ////////////////////////////////////////////  CLASS METHODS  /////////
    
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
     * moves the selected account up the list (more liquid)
     */
    @FXML
    public void moveAccountUp(){
        if(rdAccounts.isSelected()){
            Account selected = (Account)lstAccounts.getSelectionModel().getSelectedItem();
            switch(type){                
                case ASSET:
                    for(int i = 0; i < book.getAssets().size(); i++){
                        if(selected.toString().equals(book.getAssets().get(i).toString())){
                            if(i > 0){
                                Collections.swap(book.getAssets(), i, i - 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getAccountCategories().size(); i++){
                        for(int j = 0; j < book.getAccountCategories().get(i).getAccounts().size(); j++){
                            if(selected.toString().equals(book.getAccountCategories().get(i).getAccounts().get(j).toString())){
                                if(j > 0){
                                    Collections.swap(book.getAccountCategories().get(i).getAccounts(), j, j - 1);
                                }
                            }
                        }
                    }
                    fillList();
                    break;
                case LIABILITY:
                    for(int i = 0; i < book.getLiabilities().size(); i++){
                        if(selected.toString().equals(book.getLiabilities().get(i).toString())){
                            if(i > 0){
                                Collections.swap(book.getLiabilities(), i, i - 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getAccountCategories().size(); i++){
                        for(int j = 0; j < book.getAccountCategories().get(i).getAccounts().size(); j++){
                            if(selected.toString().equals(book.getAccountCategories().get(i).getAccounts().get(j).toString())){
                                if(j > 0){
                                    Collections.swap(book.getAccountCategories().get(i).getAccounts(), j, j - 1);
                                }
                            }
                        }
                    }
                    fillList();
                    break;
                case EQUITY:
                    for(int i = 0; i < book.getEquities().size(); i++){
                        if(selected.toString().equals(book.getEquities().get(i).toString())){
                            if(i > 0){
                                Collections.swap(book.getEquities(), i, i - 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getAccountCategories().size(); i++){
                        for(int j = 0; j < book.getAccountCategories().get(i).getAccounts().size(); j++){
                            if(selected.toString().equals(book.getAccountCategories().get(i).getAccounts().get(j).toString())){
                                if(j > 0){
                                    Collections.swap(book.getAccountCategories().get(i).getAccounts(), j, j - 1);
                                }
                            }
                        }
                    }
                    fillList();
                    break;
                default:
                    for(int i = 0; i < book.getAssets().size(); i++){
                        if(selected.toString().equals(book.getAssets().get(i).toString())){
                            if(i > 0){
                                Collections.swap(book.getAssets(), i, i - 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getLiabilities().size(); i++){
                        if(selected.toString().equals(book.getLiabilities().get(i).toString())){
                            if(i > 0){
                                Collections.swap(book.getLiabilities(), i, i - 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getEquities().size(); i++){
                        if(selected.toString().equals(book.getEquities().get(i).toString())){
                            if(i > 0){
                                Collections.swap(book.getEquities(), i, i - 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getAccountCategories().size(); i++){
                        for(int j = 0; j < book.getAccountCategories().get(i).getAccounts().size(); j++){
                            if(selected.toString().equals(book.getAccountCategories().get(i).getAccounts().get(j).toString())){
                                if(j > 0){
                                    Collections.swap(book.getAccountCategories().get(i).getAccounts(), j, j - 1);
                                }
                            }
                        }
                    }
                    fillList();
                    break;
            }
        }
        else if(rdCategories.isSelected()){
            AccountCategory selected = (AccountCategory)lstCategories.getSelectionModel().getSelectedItem();
            for(int i = 0; i < book.getAccountCategories().size(); i++){
                if(selected.toString().equals(book.getAccountCategories().get(i).toString())){
                    if(i > 0){
                        Collections.swap(book.getAccountCategories(), i,  i - 1);
                    }
                }
            }
            fillList();
        }
    }//end moveAccountUp()
    
    
    
    /**
     * moves the selected account down the list (less liquid)
     */
    @FXML
    public void moveAccountDown(){
        if(rdAccounts.isSelected()){
            Account selected = (Account)lstAccounts.getSelectionModel().getSelectedItem();
            switch(type){                
                case ASSET:
                    for(int i = 0; i < book.getAssets().size(); i++){
                        if(selected.toString().equals(book.getAssets().get(i).toString())){
                            if(i < book.getAssets().size() - 1){
                                Collections.swap(book.getAssets(), i, i + 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getAccountCategories().size(); i++){
                        for(int j = 0; j < book.getAccountCategories().get(i).getAccounts().size(); j++){
                            if(selected.toString().equals(book.getAccountCategories().get(i).getAccounts().get(j).toString())){
                                if(j < book.getAccountCategories().get(i).getAccounts().size() - 1){
                                    Collections.swap(book.getAccountCategories().get(i).getAccounts(), j, j + 1);
                                }
                            }
                        }
                    }
                    fillList();
                    break;
                case LIABILITY:
                    for(int i = 0; i < book.getLiabilities().size(); i++){
                        if(selected.toString().equals(book.getLiabilities().get(i).toString())){
                            if(i < book.getLiabilities().size() - 1){
                                Collections.swap(book.getLiabilities(), i, i + 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getAccountCategories().size(); i++){
                        for(int j = 0; j < book.getAccountCategories().get(i).getAccounts().size(); j++){
                            if(selected.toString().equals(book.getAccountCategories().get(i).getAccounts().get(j).toString())){
                                if(j < book.getAccountCategories().get(i).getAccounts().size() - 1){
                                    Collections.swap(book.getAccountCategories().get(i).getAccounts(), j, j + 1);
                                }
                            }
                        }
                    }
                    fillList();
                    break;
                case EQUITY:
                    for(int i = 0; i < book.getEquities().size(); i++){
                        if(selected.toString().equals(book.getEquities().get(i).toString())){
                            if(i < book.getEquities().size() - 1){
                                Collections.swap(book.getEquities(), i, i + 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getAccountCategories().size(); i++){
                        for(int j = 0; j < book.getAccountCategories().get(i).getAccounts().size(); j++){
                            if(selected.toString().equals(book.getAccountCategories().get(i).getAccounts().get(j).toString())){
                                if(j < book.getAccountCategories().get(i).getAccounts().size() - 1){
                                    Collections.swap(book.getAccountCategories().get(i).getAccounts(), j, j + 1);
                                }
                            }
                        }
                    }
                    fillList();
                    break;
                default:
                    for(int i = 0; i < book.getAssets().size(); i++){
                        if(selected.toString().equals(book.getAssets().get(i).toString())){
                            if(i < book.getAssets().size() - 1){
                                Collections.swap(book.getAssets(), i, i + 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getLiabilities().size(); i++){
                        if(selected.toString().equals(book.getLiabilities().get(i).toString())){
                            if(i < book.getLiabilities().size() - 1){
                                Collections.swap(book.getLiabilities(), i, i + 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getEquities().size(); i++){
                        if(selected.toString().equals(book.getEquities().get(i).toString())){
                            if(i < book.getEquities().size() - 1){
                                Collections.swap(book.getEquities(), i, i + 1);
                            }
                        }
                    }
                    for(int i = 0; i < book.getAccountCategories().size(); i++){
                        for(int j = 0; j < book.getAccountCategories().get(i).getAccounts().size(); j++){
                            if(selected.toString().equals(book.getAccountCategories().get(i).getAccounts().get(j).toString())){
                                if(j < book.getAccountCategories().get(i).getAccounts().size() - 1){
                                    Collections.swap(book.getAccountCategories().get(i).getAccounts(), j, j + 1);
                                }
                            }
                        }
                    }
                    fillList();
                    break;
            }
        }
        else if(rdCategories.isSelected()){
            AccountCategory selected = (AccountCategory)lstCategories.getSelectionModel().getSelectedItem();
            for(int i = 0; i < book.getAccountCategories().size(); i++){
                if(selected.toString().equals(book.getAccountCategories().get(i).toString())){
                    if(i < book.getAccountCategories().size() - 1){
                        Collections.swap(book.getAccountCategories(), i,  i+ 1);
                    }
                }
            }
            fillList();
        }
    }//end moveAccountDown()
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////  JAVA OBJECT  /////////
    
    /**
     * I rarely use this but it's in the interfacing requirements
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but it's in the interfacing requirements
    }//end initialize()
    
}//end ModifyAccountOrderDialog
