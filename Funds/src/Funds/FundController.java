package Funds;


import Funds.DataEnums.NewBookType;
import Funds.DataEnums.AccountType;
import Funds.Dialogs.RemoveAccountDialog;
import Funds.Dialogs.NewBookDialog;
import Funds.Dialogs.AccountDetailsDialog;
import Funds.Dialogs.AccountCategoryDetailsDialog;
import Funds.Dialogs.HelpDialog;
import Funds.Dialogs.BookDetailsDialog;
import Funds.DataObjects.AccountCategory;
import Funds.Dialogs.ModifyAccountOrderDialog;
import Funds.DataObjects.Account;
import Funds.DataObjects.Book;
import Funds.Tools.BookExport.BookExport;
import Funds.Tools.BookExport.ExportType;
import Funds.Tools.Budgeting.RevenueSplitter.RevenueSplitter;
import Funds.Tools.GeneralLedger.LedgerPane;
import java.net.URL;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/**
 * The controller class for the software's main GUI
 * @author Chris Francis
 */
public class FundController implements Initializable, Book.AccountController {

        /////////////////////////////////////////////  GUI  /////////////////

        //main menu
    @FXML
    MenuItem miSave;
    @FXML
    Menu mnEditAccountDetails;
    @FXML
    MenuItem miNewAccountCategory;
    
    
        //left pane
    @FXML
    Button btnBookDetails;
    @FXML
    ImageView imgSave;

        //main content pane
    //tabpane
    @FXML
    TabPane tpMain;
    //balance sheet
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
    
    
        //status bar
    @FXML
    Label lblLeftStatus;
    @FXML
    Pane pnCenterStatus;
    @FXML
    Label lblRightStatus;








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
                fillEditAccountDetailsMenu();
                break;
            case DETAILED:
                AccountCategory currentAssets = new AccountCategory("Current Assets", AccountType.ASSET);
                AccountCategory longTermAssets = new AccountCategory("Long Term Assets", AccountType.ASSET);
                AccountCategory currentLiabilities = new AccountCategory("Current Liabilities", AccountType.LIABILITY);
                AccountCategory longTermLiabilities = new AccountCategory("Long Term Liabilities", AccountType.LIABILITY);
                AccountCategory retainedEarnings = new AccountCategory("Retained Earnings", AccountType.EQUITY);
                currentAssets.getAccounts().add(new Account("Cash", true));
                currentAssets.getAccounts().add(new Account("Accounts Receivable", true));
                longTermAssets.getAccounts().add(new Account("Property, Plant, and Equipment", true));
                longTermAssets.getAccounts().add(new Account("Real Estate", true));
                currentLiabilities.getAccounts().add(new Account("Credit Card", false));
                currentLiabilities.getAccounts().add(new Account("Accounts Payable", false));
                longTermLiabilities.getAccounts().add(new Account("Notes Payable", false));
                retainedEarnings.getAccounts().add(new Account("Initial Capital", false));
                retainedEarnings.getAccounts().add(new Account("Revenue", false));
                retainedEarnings.getAccounts().add(new Account("Expense", true));
                book.getAccountCategories().add(currentAssets);
                book.getAccountCategories().add(longTermAssets);
                book.getAccountCategories().add(currentLiabilities);
                book.getAccountCategories().add(longTermLiabilities);
                book.getAccountCategories().add(retainedEarnings);
                showBalanceSheet();
                fillEditAccountDetailsMenu();
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
            fillEditAccountDetailsMenu();
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
            book.setSaved(true);
            book.setAccountController(this);
            vbxAsset.getChildren().clear();
            vbxLiability.getChildren().clear();
            vbxEquity.getChildren().clear();
            txtTotalAssets.setText(Double.toString(0.0));
            txtTotalLiabilities.setText(Double.toString(0.0));
            txtTotalEquity.setText(Double.toString(0.0));
            displayDetails();
            fillEditAccountDetailsMenu();
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
        book.setAccountController(this);
        Stage stgMain = (Stage)btnBookDetails.getScene().getWindow();
        stgMain.setTitle(book.getFilepath() != null? "Funds:\t\t" + book.getFilepath(): "Book: \t\tunsaved book");
        displayDetails();
    }//end saveBook()




    /**
     * creates a new saved file to serialize current working data to
     */
    @FXML
    public void saveAsBook(){
        book.setAccountController(null);
        book.saveAsBook();
        book.setAccountController(this);
        Stage stgMain = (Stage)btnBookDetails.getScene().getWindow();
        stgMain.setTitle(book.getFilepath() != null? "Funds:\t\t" + book.getFilepath(): "Funds: \t\tunsaved book");
        displayDetails();
    }//end saveAsBook()
    
    
    
    
    
    /**
     * exports the book to a collection of xml files positioned in a directory tree to preserve the structure of the book
     */
    @FXML
    public void bookToXML(){
        BookExport btx = new BookExport(book, ExportType.XML);
        btx.write();
    }//end bookToXML()
    
    
    
    
    /**
     * exports the book to a collection of json files positioned in a directory tree to preserve the structure of the book
     */
    @FXML
    public void bookToJSON(){
        BookExport btj = new BookExport(book, ExportType.JSON);
        btj.write();
    }//end bookToJSON()
    
    
    
    
    /**
     * exports the book to a collection of csv files positioned in a directory tree to preserve structure of the book
     */
    @FXML
    public void bookToCSV(){
        BookExport btc = new BookExport(book, ExportType.CSV);
        btc.write();
    }//end bookToCSV()
    
    
    
    
    /**
     * exports the book to a collection of .sql files to create a relational database mimicking the structure of the original book
     * It was set up for and tested on MariaDB
     */
    @FXML
    public void bookToSQL(){
        BookExport bts = new BookExport(book, ExportType.SQL);
        bts.write();
    }
    
    
    
    
    
    
    
    
        /////////////////////////////////////////  ACCOUNT METHODS  ////////
    
    /**
     * method to launch the dialog to create a subcategory to organize accounts by
     */
    @FXML
    public void newCategory(){
        AccountCategoryDetailsDialog temp = new AccountCategoryDetailsDialog(book, AccountType.ALL);
        book.setSaved(false);
        fillEditAccountDetailsMenu();
        displayDetails();
    }//end newCategory()
    
    
    
    
    /**
     * method to launch the dialog to create a new account
     */
    @FXML
    public void newAccount(){
        AccountDetailsDialog temp = new AccountDetailsDialog(book, AccountType.ALL);
        book.setSaved(false);
        fillEditAccountDetailsMenu();
        displayDetails();
    }//end newAccount()
    
    
    
    
    /**
     * method to create the list of menu buttons to edit the details for an account: name, account number, routing number, description, etc.
     */
    public void fillEditAccountDetailsMenu(){
        mnEditAccountDetails.getItems().clear();
        for(int i = 0; i < book.getAssets().size(); i++){
            MenuItem miAsset = new MenuItem(book.getAssets().get(i).getName());
            Account acc = book.getAssets().get(i);
            miAsset.setOnAction(m -> {
                AccountDetailsDialog add = new AccountDetailsDialog(book, acc);
            });
            mnEditAccountDetails.getItems().add(miAsset);
        }
        for(int i = 0; i < book.getSubcategory(AccountType.ASSET).size(); i++){
            mnEditAccountDetails.getItems().add(new SeparatorMenuItem());
            for(int j = 0; j < book.getSubcategory(AccountType.ASSET).get(i).getAccounts().size(); j++){
                MenuItem miAsset = new MenuItem(book.getSubcategory(AccountType.ASSET).get(i).getAccounts().get(j).getName());
                Account acc = book.getSubcategory(AccountType.ASSET).get(i).getAccounts().get(j);
                miAsset.setOnAction(m ->{
                    AccountDetailsDialog add = new AccountDetailsDialog(book, acc);
                });
                mnEditAccountDetails.getItems().add(miAsset);
            }
        }
        
        
            //liability
        mnEditAccountDetails.getItems().add(new SeparatorMenuItem());
        for(int i = 0; i < book.getLiabilities().size(); i++){
            MenuItem miLiability = new MenuItem(book.getLiabilities().get(i).getName());
            Account acc = book.getLiabilities().get(i);
            miLiability.setOnAction(m -> {
                AccountDetailsDialog add = new AccountDetailsDialog(book, acc);
            });
            mnEditAccountDetails.getItems().add(miLiability);
        }
        for(int i = 0; i < book.getSubcategory(AccountType.LIABILITY).size(); i++){
            mnEditAccountDetails.getItems().add(new SeparatorMenuItem());
            for(int j = 0; j < book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().size(); j++){
                MenuItem miLiability = new MenuItem(book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().get(j).getName());
                Account acc = book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().get(j);
                miLiability.setOnAction(m ->{
                    AccountDetailsDialog add = new AccountDetailsDialog(book, acc);
                });
                mnEditAccountDetails.getItems().add(miLiability);
            }
        }
        
            //equity
        mnEditAccountDetails.getItems().add(new SeparatorMenuItem());
        for(int i = 0; i < book.getEquities().size(); i++){
            MenuItem miEquity = new MenuItem(book.getEquities().get(i).getName());
            Account acc = book.getEquities().get(i);
            miEquity.setOnAction(m -> {
                AccountDetailsDialog add = new AccountDetailsDialog(book, acc);
            });
            mnEditAccountDetails.getItems().add(miEquity);
        }
        for(int i = 0; i < book.getSubcategory(AccountType.EQUITY).size(); i++){
            mnEditAccountDetails.getItems().add(new SeparatorMenuItem());
            for(int j = 0; j < book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().size(); j++){
                MenuItem miEquity = new MenuItem(book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().get(j).getName());
                Account acc = book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().get(j);
                miEquity.setOnAction(m ->{
                    AccountDetailsDialog add = new AccountDetailsDialog(book, acc);
                });
                mnEditAccountDetails.getItems().add(miEquity);
            }
        }
    }//end fillEditAccountDetailsMenu()

    
    
    
    /**
     * opens the dialog to alter the order accounts appear on in the balance sheet and other listings
     */
    @FXML
    public void modifyAccountsOrder(){
        ModifyAccountOrderDialog temp = new ModifyAccountOrderDialog(book, AccountType.ALL);
        book.setSaved(false);
        fillEditAccountDetailsMenu();
        displayDetails();
    }//end modifyAccountOrder()
    
    
    
    
    /**
     * opens the dialog to select and remove accounts
     */
    @FXML
    public void removeAccounts(){
        RemoveAccountDialog temp = new RemoveAccountDialog(book, AccountType.ALL);
        book.setSaved(false);
        fillEditAccountDetailsMenu();
        displayDetails();
    }//end removeAccount()
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////  TOOL METHODS  ////////////
    
    /**
     * Starts the General Ledger Filtering Tool in a new tab in the main content area
     */
    @FXML
    public void launchGeneralLedger(){
        ScrollPane scpContent = new ScrollPane(new LedgerPane(book));
        Tab tbGeneralLedger = new Tab("General Ledger", scpContent);
        tpMain.getTabs().add(tbGeneralLedger);
        tpMain.getSelectionModel().select(tbGeneralLedger);
    }//end launchGeneralLedger()
    
    
    
    
    /**
     * Starts the revenue splitter tool in a new tab in the main content area
     */
    @FXML
    public void launchRevenueSplitter(){
        ScrollPane scpContent = new ScrollPane(new RevenueSplitter(book));
        Tab tbRevenueSplitter = new Tab("Revenue Splitter", scpContent);
        tpMain.getTabs().add(tbRevenueSplitter);
        tpMain.getSelectionModel().select(tbRevenueSplitter);
    }//end launchRevenueSplitter()
    
    
    
    
    
    
    
    
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
        toggleSaveImage();
    }//end displayDetails()
    
    
    
    
    /**
     * switches between blue and red image to match saved state of the book
     */
    public void toggleSaveImage(){
        if(book.isSaved()){
            miSave.setDisable(true);
            imgSave.setImage(new Image(getClass().getResourceAsStream("Images/SaveIcon.png")));
        }
        else{
            miSave.setDisable(false);
            imgSave.setImage(new Image(getClass().getResourceAsStream("Images/NeedSaveIcon.png")));
        }
    }//end toggleSaveImage()
    
    
    
    
    /**
     * Sets the left hand status label on the main GUI
     * @param str The string to show in the status bar
     */
    @Override
    public void updateLeftStatus(String str) {
        lblLeftStatus.setText(str);
    }
    
    
    
    
    /**
     * Sets the central pane container on the status bar of main GUI for use with progress bars and meters and such
     * @param pane A pane containing desired widgets to be loaded in, height restriction is 1 font size tall, load widgets into Pane or subclass of Pane and send to this method to display
     */
    @Override
    public void updateCenterStatus(Pane pane) {
        pnCenterStatus.getChildren().clear();
        pnCenterStatus.getChildren().add(pane);
    }
    
    
    
    
    /**
     * Sets the right hand status bar label in the main GUI
     * @param str The string to show in the status bar
     */
    @Override
    public void updateRightStatus(String str) {
        lblRightStatus.setText(str);
    }
    
    
    

    /**
     * Opens the details dialog with the current Book object for editing
     */
    @FXML
    public void launchBookDetails(){
        BookDetailsDialog temp = new BookDetailsDialog(book);
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
            //this line creates an infinite loop????
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
        LinkedList<Account> subcategory = new LinkedList<>();
        total += findNormalDebitSubtotal(book.getAssets());
        total += findNormalDebitSubtotal(book.getSubcategoryAccounts(AccountType.ASSET));
        txtTotalAssets.setText(currencyFormat.format(total));
        
        total = 0.0;
        total += findNormalCreditSubtotal(book.getLiabilities());
        total += findNormalCreditSubtotal(book.getSubcategoryAccounts(AccountType.LIABILITY));
        txtTotalLiabilities.setText(currencyFormat.format(total));
        
        total = 0.0;
        total += findNormalCreditSubtotal(book.getEquities());
        total += findNormalCreditSubtotal(book.getSubcategoryAccounts(AccountType.EQUITY));
        txtTotalEquity.setText(currencyFormat.format(total));
    }//end displayTotals()
    
    
    
    /**
     * method to find balance totals from a list of accounts
     * @param accounts the collection of accounts to find the balance total of
     * @return <b>double</b> of all the debits in the account less all the credits
     */
    public double findNormalDebitSubtotal(LinkedList<Account> accounts){
        double subtotal = 0.0;
        for(int i = 0; i < accounts.size(); i++){
            Account acc = accounts.get(i);
            for(int j = 0; j < acc.getTransactions().size(); j++){
                subtotal += acc.getTransactions().get(j).getDebit() - acc.getTransactions().get(j).getCredit();
            }
        }
        return subtotal;
    }//end findAccountSubtotal()
    
    
    
    
    /**
     * method to find balance totals from a list of accounts
     * @param accounts the collection of accounts to find the balance total of
     * @return <b>double</b> of all the credits in the account less all the debits
     */
    public double findNormalCreditSubtotal(LinkedList<Account> accounts){
        double subtotal = 0.0;
        for(int i = 0; i < accounts.size(); i++){
            Account acc = accounts.get(i);
            for(int j = 0; j < acc.getTransactions().size(); j++){
                subtotal += acc.getTransactions().get(j).getCredit() - acc.getTransactions().get(j).getDebit();
            }
        }
        return subtotal;
    }//end findAccountSubtotal()
    
    
    
    
    
    
    
    
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
    
    
    
    
    /**
     * checks if book has been saved and closes the main GUI if so (asks for confirmation otherwise)
     */
    @FXML
    public void exit(){
        closeBook();
        Platform.exit();
    }//end quit()







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
        book.setSaved(true);
        fillEditAccountDetailsMenu();
        showBalanceSheet(); //holy cow... I finally did someting at startup...
    }//end initialize()

}//end FundController
