
package Funds;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.LinkedList;



/**
 * <h2>Summary</h2>
 * <p>The Account class will be the primary working tool for organizing the transactions in terms of money coming in or money going out using the standard accounting practice of double-entry method. All transactions are recorded in two accounts, once as a debit and once as a credit, and in the end all debits and credits will balance out.</p>
 * <p>Transactions are the small scale and Books are the big picture which leaves Accounts as that nice middle-ground where we get the whole picture without getting bogged down in, but can still see, the finer details.</p>
 * @author Chris Francis
 */
public class Account implements Serializable {
    
        //////////////////////////////////////////////  DATAFIELDS  /////////
    
    private String name;
    private String number;
    private String routing;
    private String code;
    private String description;
    private AccountType type;
    private boolean normalDebit;
    private LinkedList<Transaction> transactions = new LinkedList<>();
    
    
    
    
    
    
    
    
        //////////////////////////////////////////  CONSTRUCTORS  ///////////
    /**
     * the default constructor to be used in conjunction with getters and setters
     */
    public Account() {
    }//end default constructor
    
    
    
    
    /**
     * For general usage when creating a new account the most important things to know are the name (how to recognize it on the charts) and the normal (which column in the ledger represents an increase), the two-arg constructor implements only this information at creation.
     * @param name The nickname for this account
     * @param normalDebit A boolean value of true if the account increases with debit transactions or false if the account increases with credit transactions
     */
    public Account(String name, boolean normalDebit) {
        this.name = name;
        this.normalDebit = normalDebit;
    }//end two-arg constructor
    
    
    
    
    /**
     * The full constructor is for creating an account with all the common details, this constructor is intended to be used with a new account dialog interface
     * @param name The nickname for this account
     * @param number The account number as issued by the bank
     * @param routing The routing number referring to the bank holding the account
     * @param code A alpha-numerical code to be used in local book keeping records
     * @param description A plain language description of the account
     * @param normalDebit A boolean value of true if the account increases with debit transactions or false if the account increases with credit transactions
     */
    public Account(String name, String number, String routing, String code, String description, boolean normalDebit) {
        this.name = name;
        this.number = number;
        this.routing = routing;
        this.code = code;
        this.description = description;
        this.normalDebit = normalDebit;
    }//end full constructor
    
    
    
    
    
    
    
    
        //////////////////////////////////////////  GETTERS AND SETTERS  ////////
    
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
    
    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
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

    public LinkedList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(LinkedList<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    
    
    
    
    
    
    
        ///////////////////////////////////////  ACCOUNT METHODS  ///////////
    
    /**
     * The recalculated balance for every Transaction (transaction) recorded in this account
     */
    public void findRunningBalance(){
        double runningTotal = 0.0;
        for(int i = 0; i < transactions.size(); i++){
            runningTotal += isNormalDebit() ? transactions.get(i).getDebit() - transactions.get(i).getCredit() : transactions.get(i).getCredit() - transactions.get(i).getDebit();
            transactions.get(i).setBalance(runningTotal);
        }
    }//end findRunningBalance()
    
    
    
    
    /**
     * The total balance for the account
     * @return <b>double</b> representing the total balance not formatted
     */
    public double findBalance(){
        double balance = 0.0;
        for(int i = 0; i < transactions.size(); i++){
            balance += isNormalDebit() ? transactions.get(i).getDebit() - transactions.get(i).getCredit() : transactions.get(i).getCredit() - transactions.get(i).getDebit();
        }
        return balance;
    }
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////////  JAVA OBJECT  ////////
    
    /**
     * override of default method
     * @return <b>String</b> containing the account nickname and total balance formatted as currency
     */
    @Override
    public String toString() {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return getName() + ": " + currencyFormat.format(getTransactions().isEmpty()? 0.0 : findBalance());
    }//end toString()
 
}//end Account
