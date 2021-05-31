
package Funds;

import java.time.LocalDate;


public class Entry {
    
    private LocalDate date;
    private int number;
    private String Description;
    private Account transfer;
    private boolean reconcile;
    private double debit;
    private double credit;
    
    private double balance;
    private boolean normalDebit; //how can i get this from parent instead???
    

    public Entry() {
    }//end default constructor

    public Entry(LocalDate date, int number, String Description, Account transfer, double debit, double credit) {
        this.date = date;
        this.number = number;
        this.Description = Description;
        this.transfer = transfer;
        this.debit = debit;
        this.credit = credit;
    }//end full constructor

    
    
    
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public boolean getReconcile() {
        return reconcile;
    }

    public void setR(boolean reconcile) {
        this.reconcile = reconcile;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public boolean isReconcile() {
        return reconcile;
    }

    public void setReconcile(boolean reconcile) {
        this.reconcile = reconcile;
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
        return "Posted on " + this.getDate().toString() + " was " + this.getDescription() + " for " + (normalDebit ? this.getDebit() : this.getCredit()) + (reconcile ?  "has been" : "has not been") + " reconciled";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}//end Entry
