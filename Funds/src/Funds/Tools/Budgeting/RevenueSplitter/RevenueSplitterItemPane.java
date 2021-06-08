
package Funds.Tools.Budgeting.RevenueSplitter;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class RevenueSplitterItemPane extends HBox implements Initializable {

        /////////////////////////////////////////////  DATAFIELDS  ////////////
    
    @FXML
    TextField txtAccount;
    @FXML
    CheckBox chkAutomatic;
    @FXML
    TextField txtPercent;
    @FXML
    Slider sldPercent;
    @FXML
    TextField txtAmount;
    @FXML
    CheckBox chkExclude;
    
    private RevenueSplitterItem item;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    
    
    
    
    
    
    
        ////////////////////////////////////////  CONSTRUCTORS  ///////////////
    
    public RevenueSplitterItemPane(RevenueSplitterItem item){
        this.item = item;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RevenueSplitterItemGUI.fxml"));
            loader.setController(this);
            this.getChildren().add(loader.load());
        }
        catch(Exception e){
            e.printStackTrace();
            //just move on then
        }
        txtAccount.setText(item.getAccount().toString());
        chkAutomatic.setSelected(item.isAutomatic());
        txtPercent.setText(Double.toString(item.getPercent()));
        sldPercent.setValue(item.getPercent());
        txtAmount.setText(currencyFormat.format(item.getAmount()));
        chkExclude.setSelected(item.isExcluded());
    }//end constructor
    
    
    
    
    
    
    
    
        //////////////////////////////////////////  CLASS METHODS  ///////////
    
    @FXML
    public void onPercentTextChanged(){
        try{
            sldPercent.setValue(Double.parseDouble(txtPercent.getText()));
            item.setPercent((Double.parseDouble(txtPercent.getText())));
        }
        catch(Exception e){
            txtPercent.setText(Double.toString(sldPercent.getValue()));
        }
    }//end onPercentTextChanged()
    
    
    
    @FXML
    public void onPercentSliderChanged(){
        txtPercent.setText(Double.toString(sldPercent.getValue()));
        item.setPercent(sldPercent.getValue());
    }//end onPercentSliderChanged()
    
    
    
    
    @FXML
    public void onAutomaticCheckChanged(){
        item.setAutomatic(!item.isAutomatic());
    }//end onAutomaticCheckedChanged()
    
    
    
    
    @FXML
    public void onExcludeCheckChanged(){
        item.setExcluded(!item.isExcluded());
    }//end onExcludedCheckChanged()
    
    
        ////////////////////////////////////////  JAVA OBJECT //////////
    
    /**
     * I rarely use this but it's in the interfacing requirements for fxml
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this but it's in the interfacing requirements for fxml
    }
    
}//end RevenueSplitterItemPane
