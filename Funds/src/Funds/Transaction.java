
package Funds;

import java.io.Serializable;
import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * <h2>Summary</h2>
 * <p>An Transaction is the basic building block of the whole thing. It is a collection of datafields representing a single real-world monetary transaction.</p>
 * <p>Every Transaction made will have a corresponding Transfer -another entry with debits and credits reversed for the GAAP standard double-entry method of "balancing the books."</p>
 * <p>The early stages of the plan have the Transaction object corresponding to a single record in a relational database for compatibility with other accounting software concerns.</p>
 * @author Chris
 */
public class Transaction implements Serializable {
    
        ////////////////////////////////////////////////  DATAFIELDS  /////////
    private LocalDate date;
    private String transactionID;
    private String Description;
    private Account transfer;
    //private BooleanProperty reconcile;
    private boolean reconcile;
    private double debit;
    private double credit;
    
    private double balance;
    private boolean normalDebit; //how can i get this from parent instead???
    
    private int ledgerID;
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////  CONSTRUCTORS  //////////
    
    /**
     * The default constructor to be used in conjunction with getter and setter methods
     */
    public Transaction() {
    }//end default constructor
    
    
    
    
    /**
     * The full constructor to be used when all fields for a transaction are known (such as pulling from a relational database table)
     * @param date The date on which the transaction was recognized
     * @param transactionID A human usable id for the transactions, may be different than database id
     * @param Description A brief description of the transaction
     * @param transfer The account into which the transaction will also be recorded for double-entry method of accounting
     * @param reconcile Traditionally this column was marked after the fact once a check had cleared the bank, I don't think this applies much in the modern world, but there's still plenty of ways a particular user could use a boolean value to mark or not in the transaction (personally I check this for expenses that may be reimbursed and unchecked for regular expenses, among other things)
     * @param debit The left column of the ledger: assets, dividends, and expenses are normal (increase with) debit accounts
     * @param credit The right column of the ledger: liabilities, revenue, and most equity are normal (increase with) credit accounts
     */
    public Transaction(LocalDate date, String transactionID, String Description, Account transfer, boolean reconcile, double debit, double credit) {
        this.date = date;
        this.transactionID = transactionID;
        this.Description = Description;
        this.transfer = transfer;
        this.reconcile = reconcile;
        this.debit = debit;
        this.credit = credit;
    }//end full constructor
    
    
    
    
    
    
    
    
        //////////////////////////////////////  GETTERS AND SETTERS  /////////
    
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
        //return reconcile.getValue();
        return reconcile;
    }
    
    public BooleanProperty reconcileProperty(){
        return  new SimpleBooleanProperty(reconcile);
    }
    

    public void setReconcile(boolean reconcile) {
        this.reconcile = reconcile;
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
    
    public int getLedgerID(){
        return ledgerID;
    }
    
    public void setLedgerID(int ledgerID){
        this.ledgerID = ledgerID;
    }
    
    
    
    
    
    
    
        //////////////////////////////////////////////  JAVA OBJECT  //////////
    
    /**
     * default override method
     * @return <b>String</b> representing the transaction in sentence form.
     */
    @Override
    public String toString() {
        return "Posted on " + this.getDate().toString() + " was " + this.getDescription() + ", debited for " + this.getDebit() + ", and credited for " + this.getCredit() + (isReconcile() ?  " has been" : " has not been") + " reconciled, and is at " + getLedgerID();
    }

}//end Transaction
