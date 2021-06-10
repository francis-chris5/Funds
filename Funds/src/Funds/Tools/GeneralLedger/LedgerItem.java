
package Funds.Tools.GeneralLedger;

import Funds.DataObjects.Account;
import java.time.LocalDate;


/**
 * Java bean style object for general-ledger entries
 * @author Chris
 */
public class LedgerItem {
    
        ///////////////////////////////////  DATAFIELDS  //////////////////
    
    private LocalDate date;
    private String transactionID;
    private String description;
    private Account account1;
    private Account account2;
    private boolean reconcile;
    private double debit1;
    private double debit2;
    private double credit1;
    private double credit2;
    
    
    
    
    
    
    
    
        ////////////////////////////////  CONSTRUCTORS  ///////////////////

    public LedgerItem() {
    }//end default constructor
    
    
    
    
    
    
    
    
        /////////////////////////////////  GETTERS AND SETTERS  ///////////

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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount1() {
        return account1;
    }

    public void setAccount1(Account account1) {
        this.account1 = account1;
    }

    public Account getAccount2() {
        return account2;
    }

    public void setAccount2(Account account2) {
        this.account2 = account2;
    }

    public boolean isReconcile() {
        return reconcile;
    }

    public void setReconcile(boolean reconcile) {
        this.reconcile = reconcile;
    }

    public double getDebit1() {
        return debit1;
    }

    public void setDebit1(double debit1) {
        this.debit1 = debit1;
    }

    public double getCredit1() {
        return credit1;
    }

    public void setCredit1(double credit1) {
        this.credit1 = credit1;
    }

    public double getDebit2() {
        return debit2;
    }

    public void setDebit2(double debit2) {
        this.debit2 = debit2;
    }

    public double getCredit2() {
        return credit2;
    }

    public void setCredit2(double credit2) {
        this.credit2 = credit2;
    }
    
    
    

    
    
}//end LedgerItem
