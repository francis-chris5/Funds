
package Funds.DataObjects;

import Funds.DataEnums.AccountType;
import Funds.Tools.Budgeting.RevenueSplitter.RevenueSplit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;


/**
 * A Book is the main organizer object for this whole software application, transactions will be collected into Accounts, and the Book is a collection of accounts with measures taken to assure it is balanced, and the Book object is what will be saved/opened/worked-with by users for ongoing concerns in accounting.
 * @author Chris Francis
 */
public class Book implements Serializable {
    
        ////////////////////////////////////  INTERFACING REQUIREMENTS  ///////
    
    /**
     * All of the dialogs keep a reference to the current Book object, but not the primary GUI controller, so this interface assures there is a way to call the methods on the primary GUI from any dialog 
     */
    public interface AccountController{
        public void displayDetails();
        public void updateLeftStatus(String str);
        public void updateCenterStatus(Pane pane);
        public void updateRightStatus(String str);
    }//end interfacing requirements
    
    
        //////////////////////////////////////////////////  DATAFIELDS  //////
    
            //data
    private String filename;
    private String filepath;
    private boolean saved;
    
    private String name;
    private LocalDate initialized;
    private String description;
    private String notes;
    
    private int ledgerID = 0;
    
    private LinkedList<Account> assets = new LinkedList<>();
    private LinkedList<Account> liabilities = new LinkedList<>();
    private LinkedList<Account> equities = new LinkedList<>();
    private LinkedList<AccountCategory> accountCategories = new LinkedList<>();
    
            //GUI
    private AccountController accountController;
    
    
            //tools
    private LinkedList<RevenueSplit> savedSplits = new LinkedList<>();

    
    
    
    
    
    
    
        /////////////////////////////////////////////  CONSTRUCTORS  ///////
    
    /**
     * default constructor to be used in conjunction with getters and setters
     */
    public Book() {
    }//end default constructor
    
    
    
    
    /**
     * Since a book will be the primary object for organizing and saving the collections of data one-arg constructor will be the one used most often. By default most places in the code that use this constructor pass in the name "untitled"
     * @param name The name for this chart of accounts, it is different than the filename to allowing versioning of the saved books.
     */
    public Book(String name) {
        this.name = name;
        this.initialized = LocalDate.now();
    }//end one-arg constructor
    
    
    
    
    
    
    
    
        /////////////////////////////////////  GETTERS AND SETTERS  ///////////
    
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getInitialized() {
        return initialized;
    }

    public void setInitialized(LocalDate initialized) {
        this.initialized = initialized;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getLedgerID(){
        return ledgerID;
    }
    
    public void incrementLedgerID(){
        ledgerID++;
    }
    
    public LinkedList<Account> getAssets() {
        return assets;
    }

    public void setAssets(LinkedList<Account> assets) {
        this.assets = assets;
    }

    public LinkedList<Account> getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(LinkedList<Account> liabilities) {
        this.liabilities = liabilities;
    }

    public LinkedList<Account> getEquities() {
        return equities;
    }

    public void setEquities(LinkedList<Account> equities) {
        this.equities = equities;
    }
    
    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public AccountController getAccountController(){
        return accountController;
    }
    
    public void setAccountController(AccountController accountController){
        this.accountController = accountController;
    }
    
    public LinkedList<AccountCategory> getAccountCategories(){
        return accountCategories;
    }
    
    public void setAccountCategories(LinkedList<AccountCategory> accountCategories){
        this.accountCategories = accountCategories;
    }

    public LinkedList<RevenueSplit> getSavedSplits() {
        return savedSplits;
    }

    public void setSavedSplits(LinkedList<RevenueSplit> savedSplits) {
        this.savedSplits = savedSplits;
    }
    
    
 
    
    
    
    
    
    
    
    
        //////////////////////////////////////  CLASS METHODS  /////////////
    
    /**
     * Method to update the balance sheet displayed to the user with every change to a ledger, requires the Book.AccountController interface
     */
    public void displayDetails(){
        try{
            AccountController ac = (AccountController)getAccountController();
            ac.displayDetails();
        }
        catch(Exception e){
            //just move on then
        }
    }
    
    
    
    
    /**
     * method to update the left label on the status bar of main GUI
     * @param str The string to display in the status bar
     */
    public void updateLeftStatus(String str){
        try{
            AccountController ac = (AccountController)getAccountController();
            ac.updateLeftStatus(str);
        }
        catch(Exception e){
            //just move on then
        }
    }//end updateLeftStatus()
    
    
    
    
    /**
     * method to update the central pane of status bar on main GUI with things like progress bars and meters and the like
     * @param pane The container holding desired widgets to load into the pane, height restriction is 1 font size tall
     */
    public void updateCenterStatus(Pane pane){
        try{
            ProgressBar pb = new ProgressBar(0.2);
            Pane pn = new Pane(pb);
            AccountController ac = (AccountController)getAccountController();
            ac.updateCenterStatus(pn);
        }
        catch(Exception e){
            //just move on then
        }
    }//end updateCenterStatus()
    
    
    
    
    /**
     * method to update the right hand label on the status bar of main GUI
     * @param str the string to show on the label
     */
    public void updateRightStatus(String str){
        try{
            AccountController ac = (AccountController)getAccountController();
            ac.updateRightStatus(str);
        }
        catch(Exception e){
            //just move on then
        }
    }//end updateRightStatus()
    
    
    
    
    /**
     * finds all categorized accounts of a given type and puts them in a non-categorized list
     * @param type the type of accounts to find
     * @return <b>LinkedList</b> of Accounts
     */
    public LinkedList<AccountCategory> getSubcategory(AccountType type){
        LinkedList<AccountCategory> subcategory = new LinkedList<>();
        if(!getAccountCategories().isEmpty()){
            for(int i = 0; i < getAccountCategories().size(); i++){
                if(getAccountCategories().get(i).getType() == type){
                    subcategory.add(getAccountCategories().get(i));
                }
            }
        }
        return subcategory;
    }//end getSubcategory()
    
    
    
    
    
    /**
     * finds all categorized accounts of a given type and puts them in a non-categorized list
     * @param type the type of accounts to find
     * @return <b>LinkedList</b> of Accounts
     */
    public LinkedList<Account> getSubcategoryAccounts(AccountType type){
        LinkedList<Account> accounts = new LinkedList<>();
        if(!getAccountCategories().isEmpty()){
            for(int i = 0; i < getAccountCategories().size(); i++){
                if(getAccountCategories().get(i).getType() == type){
                    for(int j = 0; j < getAccountCategories().get(i).getAccounts().size(); j++){
                        accounts.add(getAccountCategories().get(i).getAccounts().get(j));
                    }
                }
            }
        }
        return accounts;
    }//end getSubcategoryAccounts()
    

    
    /**
     * Serializes the current Book to a binary file
     * @return <b>boolean</b> indicating whether or not the Book was successfully saved
     */
    public boolean saveBook() {
        try{
            File file = new File(this.filepath);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            this.saved = true;
            return this.saved;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    } //end saveBook()
    
    
    
    
    /**
     * Serializes the current Book to a binary file
     * @return <b>boolean</b> indicating whether or not the Book was successfully saved
     */
    public boolean saveAsBook(){
        try{
            FileChooser choose = new FileChooser();
            FileChooser.ExtensionFilter bookFilter = new FileChooser.ExtensionFilter("Book, .fabk", "*.fabk");
            choose.getExtensionFilters().add(bookFilter);
            File file = choose.showSaveDialog(null);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            this.filepath = file.getPath();
            this.filename = file.getName();
            this.saved = true;
            return this.saved;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }//end saveAsBook()
    
    
    
    
    /**
     * Retrieves a serialized Book object from a binary file
     * @return <b>Book</b> requested from the file chooser
     */
    public Book openBook(){
        try{
            FileChooser choose = new FileChooser();
            FileChooser.ExtensionFilter bookFilter = new FileChooser.ExtensionFilter("Book, .fabk", "*.fabk");
            choose.getExtensionFilters().add(bookFilter);
            File file = choose.showOpenDialog(null);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Book book = (Book)ois.readObject();
            book.filename = file.getName();
            book.filepath = file.getPath();
            book.saved = true;
            ois.close();
            return book;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }//end openBook()
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////  JAVA OBJECT  //////////
    
    /**
     * default override method
     * @return <b>String</b> detailing the name and when it was opened along with a brief description in sentence form
     */
    @Override
    public String toString() {
        return "The " + getName() + " book was opened on " + getInitialized() + " and is described as " + getDescription();
    }//end toString()

}//end Book
