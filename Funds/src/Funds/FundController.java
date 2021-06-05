package Funds;


import java.net.URL;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 * The controller class for the software's main GUI
 * @author Chris Francis
 */
public class FundController implements Initializable, Book.AccountController {

        /////////////////////////////////////////////  GUI  /////////////////

        //right pane
    @FXML
    Button btnBookDetails;

        //center pane
    @FXML
    VBox vbxAsset;
    @FXML
    VBox vbxLiability;
    @FXML
    VBox vbxEquity;
    @FXML
    TextField txtTotalAssets;
    @FXML
    TextField txtTotalLiabilities;
    @FXML
    TextField txtTotalEquity;









        ////////////////////////////////////////////  DATAFIELDS  ///////////

    private Book book = new Book("untitled");

    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();



        //////////////////////////////////////////////  BOOK METHODS  /////////
    
    /**
     * brings up a dialog for the user to select a starting point for the new file (a.k.a. Book)
     */
    @FXML
    public void newBook(){
        closeBook();
        NewBookDialog temp = new NewBookDialog();
        NewBookType start = temp.getBookStart();
        switch(start){
            case EMPTY:
                break; //close method sets this up
            case BASIC_PERSONAL:
                Account cash = new Account("Cash", true);
                Account card = new Account("Credit Card", false);
                Account rev = new Account("Revenue", false);
                Account exp = new Account("Expense", true);
                book.getAssets().add(cash);
                book.getLiabilities().add(card);
                book.getEquities().add(rev);
                book.getEquities().add(exp);
                showBalanceSheet();
            default:
                break; //close method sets this up
        }
    }//end newBook()
    
    
    
    
    /**
     * opens a previously saved book of accounts to work with
     */
    @FXML
    public void openBook(){
        closeBook();
        Book another = new Book().openBook();
        if(another != null){
            book = another;
            book.setAccountController(this);
            displayDetails();
            book.setSaved(true);
        }
        Stage stgMain = (Stage)btnBookDetails.getScene().getWindow();
        stgMain.setTitle(book.getFilepath() != null? "Funds:\t\t" + book.getFilepath(): "Funds: \t\tunsaved book");
    }//end openBook()




    /**
     * Checks if it has been saved, closes the current book, and resets everything on the GUI
     */
    public void closeBook(){
        boolean close = true;
        if(!book.isSaved()){
            Alert wantSave = new Alert(Alert.AlertType.CONFIRMATION);
            Image icon = new Image(getClass().getResourceAsStream("Images/FundsIcon.png"));
            Stage stage = (Stage)wantSave.getDialogPane().getScene().getWindow();
            stage.getIcons().add(icon);
            wantSave.setTitle("Funds");
            wantSave.setHeaderText("Current book has not been saved");
            wantSave.setContentText("Do you want to save the changes to this book before closing?");
            wantSave.getDialogPane().getStylesheets().add(getClass().getResource("Stylesheets/FundStyle.css").toExternalForm());
            wantSave.getDialogPane().getStyleClass().add("FundStyle");

            ButtonType save = new ButtonType("Save");
            ButtonType noSave = new ButtonType("Close Without Saving");
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            wantSave.getButtonTypes().setAll(save, noSave, cancel);
            Optional<ButtonType> result = wantSave.showAndWait();
            if(result.get() == save){
                if(book.getFilename() == null){
                    book.saveAsBook();
                }
                else{
                    close = book.saveBook();
                }
                if(!close){
                    Alert fail = new Alert(Alert.AlertType.ERROR);
                    fail.setTitle("Book");
                    fail.setHeaderText("Unable to save file");
                    fail.setContentText("Please try again to save the book.");
                }
            }
            else if(result.get() == noSave){
                book.setSaved(true);

            }
            else{
                close = false;
            }
        }
        if(close){
            book = new Book();
            book.setAccountController(this);
            vbxAsset.getChildren().clear();
            vbxLiability.getChildren().clear();
            vbxEquity.getChildren().clear();
            txtTotalAssets.setText(Double.toString(0.0));
            txtTotalLiabilities.setText(Double.toString(0.0));
            txtTotalEquity.setText(Double.toString(0.0));
            displayDetails();
            btnBookDetails.setText("Book Details");
            Stage stgMain = (Stage)btnBookDetails.getScene().getWindow();
            stgMain.setTitle("Funds");
        }
    }//end closeRoutine();





    /**
     * serializes current working data to a file: calls save as if it hasn't been saved yet, otherwise overwrites the saved version
     */
    @FXML
    public void saveBook(){
        book.setAccountController(null);
        if(book.getFilename() == null){
            book.saveAsBook();
        }
        else{
            book.saveBook();
        }
        Stage stgMain = (Stage)btnBookDetails.getScene().getWindow();
        stgMain.setTitle(book.getFilepath() != null? "Funds:\t\t" + book.getFilepath(): "Book: \t\tunsaved book");
    }//end saveBook()




    /**
     * creates a new saved file to serialize current working data to
     */
    @FXML
    public void saveAsBook(){
        book.setAccountController(null);
        book.saveAsBook();
        Stage stgMain = (Stage)btnBookDetails.getScene().getWindow();
        stgMain.setTitle(book.getFilepath() != null? "Funds:\t\t" + book.getFilepath(): "Funds: \t\tunsaved book");
    }//end saveAsBook()
    
    
    
    
    /**
     * checks if book has been saved and closes the main GUI if so (asks for confirmation otherwise)
     */
    @FXML
    public void exit(){
        closeBook();
        Platform.exit();
    }//end quit()
     








        ///////////////////////////////////////////  GUI OPERATIONS  //////////

    /**
     * This is basically the refresh method for what's showing on the GUI
     * loads book details in button text, refreshes the balance sheet trees, updates the account totals in the balancing equation listing
     */
    @Override
    public void displayDetails(){
        btnBookDetails.setText(book.toString());
        showBalanceSheet();
        displayTotals();
    }//end displayDetails()




    /**
     * Opens the details dialog with the current Book object for editing
     */
    @FXML
    public void launchBookDetails(){
        DetailsDialog temp = new DetailsDialog(book);
        temp.editDetails();
        displayDetails();
    }//end launch details




    /**
     * clears the balance sheet, creates a new one, and loads it in
     */
    @FXML
    public void showBalanceSheet(){
        vbxAsset.getChildren().clear();
        vbxLiability.getChildren().clear();
        vbxEquity.getChildren().clear();
        BalanceTree asset = new BalanceTree(book, AccountType.ASSET);
        BalanceTree liability = new BalanceTree(book, AccountType.LIABILITY);
        BalanceTree equity = new BalanceTree(book, AccountType.EQUITY);
        vbxAsset.getChildren().add(asset);
        vbxLiability.getChildren().add(liability);
        vbxEquity.getChildren().add(equity);
        displayTotals();
    }//end showBalanceSheet()




    /**
     * calculates and displays the total values from all of the current Book's accounts into the balancing equation listing
     */
    public void displayTotals(){
        double total = 0.0;
        for(int i = 0; i < book.getAssets().size(); i++){
            Account acc = book.getAssets().get(i);
            for(int j = 0; j < acc.getTransactions().size(); j++){
                total += acc.getTransactions().get(j).getDebit() - acc.getTransactions().get(j).getCredit();
            }
        }
        txtTotalAssets.setText(Double.toString(total));
        total = 0.0;
        for(int i = 0; i < book.getLiabilities().size(); i++){
            Account acc = book.getLiabilities().get(i);
            for(int j = 0; j < acc.getTransactions().size(); j++){
                total += acc.getTransactions().get(j).getCredit() - acc.getTransactions().get(j).getDebit();
            }
        }
        txtTotalLiabilities.setText(Double.toString(total));
        total = 0.0;
        for(int i = 0; i < book.getEquities().size(); i++){
            Account acc = book.getEquities().get(i);
            for(int j = 0; j < acc.getTransactions().size(); j++){
                total += acc.getTransactions().get(j).getCredit() - acc.getTransactions().get(j).getDebit();
            }
        }
        txtTotalEquity.setText(Double.toString(total));
        total = 0.0;
    }//end displayTotals()
    
    
    
    
    /**
     * launches a HelpDialog with the filepath to the user manual as a text file
     */
    public void getHelp(){
        new HelpDialog("Funds/References/UserManual.txt");
    }//end getHelp()
    
    
    
    
    /**
     * launches a HelpDialog with the filepath to the about info as a text file
     */
    public void getAbout(){
        new HelpDialog("Funds/References/about.txt");
    }//end getAbout()








        ///////////////////////////////////////////  JAVA OBJECT  ////////////////


    /**
     * holy cow... I finally did something at startup... I created some default accounts (cash, credit card, revenue, and expense), this may not last past early development stages
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //I rarely use this, just need it here
        book.setAccountController(this);
        Account cash = new Account("Cash", true);
        Account card = new Account("Credit Card", false);
        Account rev = new Account("Revenue", false);
        Account exp = new Account("Expense", true);
        book.getAssets().add(cash);
        book.getLiabilities().add(card);
        book.getEquities().add(rev);
        book.getEquities().add(exp);
        showBalanceSheet(); //holy cow... I finally did someting at startup...
    }//end initialize()

}//end FundController
