
package Funds.Tools.GeneralLedger;

import Funds.DataObjects.Transaction;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class LedgerItemPane extends Pane implements Initializable {

        ////////////////////////////////////////  DATAFIELDS  /////////////////
    
    @FXML
    TextField txtDate;
    @FXML
    TextField txtTransactionID;
    @FXML
    TextField txtDescription;
    @FXML
    TextField txtAccount1;
    @FXML
    TextField txtAccount2;
    @FXML
    CheckBox chkReconcile;
    @FXML
    TextField txtDebit1;
    @FXML
    TextField txtDebit2;
    @FXML
    TextField txtCredit1;
    @FXML
    TextField txtCredit2;
    
    
    private LedgerItem item = new LedgerItem();
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    
    
    
    
    
    
    
    
        //////////////////////////////////////  CONSTRUCTORS  //////////////
    
    /**
     * after the general ledger pulls the list of all transactions it will loop through and convert the transactions to ledger items by taking in the two balancing records of the transaction
     * @param t1
     * @param t2 
     */
    public LedgerItemPane(Transaction t1, Transaction t2){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LedgerItemPaneGUI.fxml"));
            loader.setController(this);
            this.getChildren().add(loader.load());
        }
        catch(Exception e){
            //just move on then
        }
        item.setDate(t1.getDate());
        item.setTransactionID(t1.getTransactionID());
        item.setDescription(t1.getDescription());
        if(t1.getDebit() == 0.0 && t2.getDebit() != 0.0){
            item.setAccount1(t1.getTransfer());
            item.setAccount2(t2.getTransfer());
            item.setDebit1(t2.getDebit());
            item.setDebit2(t1.getDebit());
            item.setCredit1(t2.getCredit());
            item.setCredit2(t1.getCredit());
        }
        else{
            item.setAccount1(t2.getTransfer());
            item.setAccount2(t1.getTransfer());
            item.setDebit1(t1.getDebit());
            item.setDebit2(t2.getDebit());
            item.setCredit1(t1.getCredit());
            item.setCredit2(t2.getCredit());
        }
        item.setReconcile(t1.isReconcile());
        fillForm();
    }//end two-arg constructor
    
    
    
    
    
    
    
    
        /////////////////////////////////////////  CLASS METHODS  //////////
    
    /**
     * This loads the ledger item into the ledger item gui pane
     */
    public void fillForm(){
        txtDate.setText(item.getDate().toString());
        txtTransactionID.setText(item.getTransactionID());
        txtDescription.setText(item.getDescription());
        chkReconcile.setSelected(item.isReconcile());
        txtAccount1.setText(item.getAccount1().toString());
        txtAccount2.setText(item.getAccount2().toString());
        txtDebit1.setText(item.getDebit1() == 0.0 ? null : currencyFormat.format(item.getDebit1()));
        txtDebit2.setText(item.getDebit2() == 0.0 ? null : currencyFormat.format(item.getDebit2()));
        txtCredit1.setText(item.getCredit1() == 0.0 ? null : currencyFormat.format(item.getCredit1()));
        txtCredit2.setText(item.getCredit2() == 0.0 ? null : currencyFormat.format(item.getCredit2()));
    }//end fillForm()
    
    
    
        /////////////////////////////////////////  JAVA OBJECT  ////////////
    
    /**
     * I rarely use this but it's in the interfacing requirements for fxml
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but it's in the interfacing requirements for fxml
    }//end initialize
    
}//end LedgerItemPane
