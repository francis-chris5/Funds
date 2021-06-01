
package Funds;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class EditTransaction extends Dialog implements Initializable {

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
    
    private Entry revised = new Entry();
    
    public EditTransaction(Entry original){
        this.setTitle("Funds: " + original.getDescription());
        Image icon = new Image(getClass().getResourceAsStream("Images/FundsIcon.png"));
        Stage stage = (Stage)this.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTransactionGUI.fxml"));
            loader.setController(this);
            this.setDialogPane(loader.load());
        }
        catch(Exception e){
            e.printStackTrace();
            //just move on then
        }
        dtDate.setValue(original.getDate());
        txtTransactionID.setText(original.getTransactionID());
        txtDescription.setText(original.getDescription());
        cmbTransfer.setValue(original.getTransfer());
        chkReconcile.setSelected(original.isReconcile());
        if(original.getDebit() == 0.0){
            txtAmount.setText(Double.toString(original.getCredit()));
            revised.setNormalDebit(false);
        }
        else{
            txtAmount.setText(Double.toString(original.getDebit()));
            revised.setNormalDebit(true);
        }
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> clicked = this.showAndWait();
        if(clicked.get() == ButtonType.OK){
            edit();
        }
        else{
            //just cancel it then
        }
    }//end constructor
    
    
    
    public Entry edit(){
        revised.setDate(dtDate.getValue());
        revised.setTransactionID(txtTransactionID.getText());
        revised.setDescription(txtDescription.getText());
        revised.setTransfer((Account)cmbTransfer.getValue());
        revised.setReconcile(chkReconcile.isSelected());
        if(revised.isNormalDebit()){
            if(Double.parseDouble(txtAmount.getText()) < 0){
                revised.setCredit(-Double.parseDouble(txtAmount.getText()));
            }
            else{
                revised.setDebit(Double.parseDouble(txtAmount.getText()));
            }
        }
        else{
            if(Double.parseDouble(txtAmount.getText()) < 0){
                revised.setDebit(-Double.parseDouble(txtAmount.getText()));
            }
            else{
                revised.setCredit(Double.parseDouble(txtAmount.getText()));
            }
        }
        return revised;
    }//end edit()
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, but required in interfacing requirements
    }
    
}//end EditTransaction
