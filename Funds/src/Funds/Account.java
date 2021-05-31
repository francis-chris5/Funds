
package Funds;

import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Account {
    
    private String name;
    private String number;
    private String code;
    private String description;
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
            if(i == 0){
                entries.get(i).setBalance(isNormalDebit() ? entries.get(i).getDebit() : entries.get(i).getCredit());
                runningTotal += isNormalDebit() ? entries.get(i).getDebit() : entries.get(i).getCredit();
            }
            else{
                entries.get(i).setBalance(isNormalDebit() ? entries.get(i).getDebit() + runningTotal : entries.get(i).getCredit() + runningTotal);
                runningTotal += isNormalDebit() ? entries.get(i).getDebit() : entries.get(i).getCredit();
            }
        }
    }//end findRunningBalance()

    
    
    
    
    
    @Override
    public String toString() {
        return getName();
    }//end toString()

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}//end Account
