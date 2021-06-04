
package Funds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import javafx.stage.FileChooser;


/**
 * <h2>Summary</h2>
 * <p>A Book is the main organizer object for this whole software application. Entries will be collected into Accounts, and the Book is a collection of accounts with measures taken to assure it is balanced.</p>
 * <p>The Book object is what will be saved/opened/worked-with by users for ongoing concerns in accounting.</p>
 * @author Chris Francis
 */
public class Book implements Serializable {
    
    
        //////////////////////////////////////////////////  DATAFIELDS  //////
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

    
    
    
    
    
    
    
    //
    
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
