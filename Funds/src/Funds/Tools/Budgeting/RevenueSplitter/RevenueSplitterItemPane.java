
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



/**
 * A set of controls for each individual item representing how money is to be distributed among various accounts
 * @author Chris Francis
 */
public class RevenueSplitterItemPane extends HBox implements Initializable {
    
        ////////////////////////////////////////  INTERFACING REQUIREMENTS  //////
    
    /**
     * An inner interface so that methods from the primary control object can be called safely from each independent item's controls
     */
    public interface SplitControl{
        public void findAutomaticPercents();
        public void displayAccounts();
        public void calculateTransfers();
        public void removeSplitterAccount();
    }//end SplitControl

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
    
    private SplitControl splitControl;
    
    
    
    
    
    
        ////////////////////////////////////////  CONSTRUCTORS  ///////////////
    
    /**
     * The constructor needs one of the items kept in the list held in the tool's primary controller
     * @param item 
     */
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
    
    
    
    
    
    
    
    
        //////////////////////////////////////  GETTERS AND SETTERS  ////////////
    
    public SplitControl getSplitControl(){
        return splitControl;
    }
    
    public void setSplitControl(SplitControl splitControl){
        this.splitControl = splitControl;
    }
    
    
    
    
    
    
    
    
        //////////////////////////////////////////  CLASS METHODS  ///////////
    
    /**
     * Basically all the change listeners in the controls do their own special purpose thing and then call this method to recalculate automatic percents, update the items displayed, and recalculate the transfers
     */
    public void updateGUI(){
        try{
            SplitControl sc = (SplitControl)getSplitControl();
            sc.findAutomaticPercents();
            sc.displayAccounts();
            sc.calculateTransfers();
        }
        catch(Exception e){
            //just move on then
        }
    }//end findAutomaticPercents()
    
    
    
    /**
     * calls method by the same name from the interface
     */
    @FXML
    public void removeSplitterAccount(){
         try{
            SplitControl sc = (SplitControl)getSplitControl();
            sc.removeSplitterAccount();
        }
        catch(Exception e){
            //just move on then
        }
    }//end removeSplitterAccount()
    
    
    
    
    /**
     * When the text field input for percent changes this changes the slider right along with it
     */
    @FXML
    public void onPercentTextChanged(){
        try{
            sldPercent.setValue(Double.parseDouble(txtPercent.getText()));
            chkAutomatic.setSelected(false);
            item.setPercent((Double.parseDouble(txtPercent.getText())));
            item.setAutomatic(false);
            updateGUI();
        }
        catch(Exception e){
            txtPercent.setText(Double.toString(sldPercent.getValue()));
        }
    }//end onPercentTextChanged()
    
    
    
    
    /**
     * when the slider control for percents changes this changes the text field input right along with it
     */
    @FXML
    public void onPercentSliderChanged(){
        txtPercent.setText(Double.toString(sldPercent.getValue()));
        chkAutomatic.setSelected(false);
        item.setPercent(sldPercent.getValue());
        item.setAutomatic(false);
        updateGUI();
    }//end onPercentSliderChanged()
    
    
    
    
    /**
     * manually adjust how an item's percent is calculated: manual - user entered, automatic - (100% - manual_entries)/number_automated
     * automatic is even distribution
     */
    @FXML
    public void onAutomaticCheckChanged(){
        item.setAutomatic(!item.isAutomatic());
        updateGUI();
    }//end onAutomaticCheckedChanged()
    
    
    
    
    /**
     * manually set whether or not an item needs to be displayed in the transfer total (if the account everything is coming out of is included on revenue splitter it doesn't need to be in the transfer total)
     */
    @FXML
    public void onExcludeCheckChanged(){
        item.setExcluded(!item.isExcluded());
        updateGUI();
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
