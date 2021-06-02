
package Funds;

import java.text.NumberFormat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Account {
    
    private String name;
    private String number;
    private String code;
    private String description;
    private AccountType type;
    private boolean normalDebit;
    private ObservableList<Entry> entries = FXCollections.observableArrayList();

    public Account() {
    }//end default constructor

    public Account(String name, boolean normalDebit) {
        this.name = name;
        this.normalDebit = normalDebit;
    }//end two-arg constructor

    public Account(String name, String number, String code, String description, boolean normalDebit) {
        this.name = name;
        this.number = number;
        this.code = code;
        this.description = description;
        this.normalDebit = normalDebit;
    }//end full constructor

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public boolean isNormalDebit() {
        return normalDebit;
    }

    public void setNormalDebit(boolean normalDebit) {
        this.normalDebit = normalDebit;
    }

    public ObservableList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ObservableList<Entry> entries) {
        this.entries = entries;
    }
    
    
    
    
    
    public void findRunningBalance(){
        double runningTotal = 0.0;
        for(int i = 0; i < entries.size(); i++){
            runningTotal += isNormalDebit() ? entries.get(i).getDebit() - entries.get(i).getCredit() : entries.get(i).getCredit() - entries.get(i).getDebit();
            entries.get(i).setBalance(runningTotal);
        }
    }//end findRunningBalance()
    
    
    public double findBalance(){
        double balance = 0.0;
        for(int i = 0; i < entries.size(); i++){
            balance += isNormalDebit() ? entries.get(i).getDebit() - entries.get(i).getCredit() : entries.get(i).getCredit() - entries.get(i).getDebit();
        }
        return balance;
    }

    
    
    
    
    
    @Override
    public String toString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return getName() + ": " + currencyFormat.format(getEntries().isEmpty()? 0.0 : findBalance());
    }//end toString()
 
}//end Account
