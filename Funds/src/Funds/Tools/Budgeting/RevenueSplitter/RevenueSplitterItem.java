
package Funds.Tools.Budgeting.RevenueSplitter;

import Funds.DataObjects.Account;
import java.io.Serializable;


/**
 * Java bean style class to represent the accounts being modified, and how they're modified, by revenue splitter
 * @author Chris Francis
 */
public class RevenueSplitterItem implements Serializable {
    
        /////////////////////////////////////////////////  DATAFIELDS  /////////
    
    private Account account;
    private double percent;
    private boolean automatic;
    private double amount;
    private boolean excluded;
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////////  CONSTRUCTORS  /////////

    public RevenueSplitterItem() {
    }//end default constructor

    public RevenueSplitterItem(Account account, double percent, boolean automatic, double amount, boolean exclude) {
        this.account = account;
        this.percent = percent;
        this.automatic = automatic;
        this.amount = amount;
        this.excluded = exclude;
    }//end full constructor
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////  GETTERS AND SETTERS  ///////

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
    
    public boolean isAutomatic(){
        return automatic;
    }
    
    public void setAutomatic(boolean automatic){
        this.automatic = automatic;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }
    
    
    
    
}//end RevenueSplitterItem
