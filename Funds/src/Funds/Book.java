
package Funds;

import java.time.LocalDate;
import java.util.LinkedList;


public class Book {
    
    
    private String filename;
    private String filepath;
    
    private String name;
    private LocalDate initialized;
    private String description;
    private String notes;
    
    private LinkedList<Account> assets = new LinkedList<>();
    private LinkedList<Account> liabilities = new LinkedList<>();
    private LinkedList<Account> equities = new LinkedList<>();

    public Book() {
    }//end default constructor

    public Book(String name) {
        this.name = name;
        this.initialized = LocalDate.now();
    }//end one-arg constructor

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

    @Override
    public String toString() {
        return "The " + getName() + " book was opened on " + getInitialized() + " and is described as " + getDescription();
    }//end toString()
    
    
    
    
    
    
    
}//end Book
