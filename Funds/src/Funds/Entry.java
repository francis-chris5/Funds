
package Funds;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Entry {
    
    private LocalDate date;
    private String transactionID;
    private String Description;
    private Account transfer;
    private BooleanProperty reconcile;
    private double debit;
    private double credit;
    
    private double balance;
    private boolean normalDebit; //how can i get this from parent instead???
    

    public Entry() {
    }//end default constructor

    public Entry(LocalDate date, String number, String Description, Account transfer, boolean reconcile, double debit, double credit) {
        this.date = date;
        this.transactionID = number;
        this.Description = Description;
        this.transfer = transfer;
        this.reconcile = new SimpleBooleanProperty(reconcile);
        this.debit = debit;
        this.credit = credit;
    }//end full constructor

    
    
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Account getTransfer() {
        return transfer;
    }

    public void setTransfer(Account transfer) {
        this.transfer = transfer;
    }

    public boolean isReconcile() {
        return reconcile.getValue();
    }
    
    public BooleanProperty reconcileProperty(){
        return reconcile;
    }
    

    public void setReconcile(boolean reconcile) {
        this.reconcile = new SimpleBooleanProperty(reconcile);
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isNormalDebit() {
        return normalDebit;
    }

    public void setNormalDebit(boolean normalDebit) {
        this.normalDebit = normalDebit;
    }

    
    
    
    
    
    @Override
    public String toString() {
        return "Posted on " + this.getDate().toString() + " was " + this.getDescription() + " for " + (normalDebit ? this.getDebit() : this.getCredit()) + (isReconcile() ?  " has been" : " has not been") + " reconciled";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}//end Entry
