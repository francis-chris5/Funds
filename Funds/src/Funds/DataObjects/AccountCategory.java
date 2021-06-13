
package Funds.DataObjects;

import Funds.DataEnums.AccountType;
import Funds.DataObjects.Account;
import java.io.Serializable;
import java.util.LinkedList;


/**
 * A identifiable list of accounts of the same primary type so that the Book object has a way to grab only the accounts in a certain category as organized on the balance sheet appearance of the main GUI
 * @author Chris Francis
 */
public class AccountCategory implements Serializable {
    
        ///////////////////////////////////////  DATAFIELDS  ///////////////
    
    private AccountType type = AccountType.NOT_NULL;
    private String name = new String();
    private LinkedList<Account> accounts = new LinkedList<>();
    
    
    
    
    
    
    
        ////////////////////////////////////////////  CONSTRUCTORS  ////////
    
    /**
     * The list needs to be identifiable and filled with accounts of the same type
     * @param name a string to identify this list by, probably the category name that shows on the balance sheet is best
     * @param type the type of accounts held in this list (asset, liabiliity, or equity)
     */
    public AccountCategory(String name, AccountType type){
        this.name = name;
        this.type = type;
    }//end one-arg constructor
    
    
    
    
    
    
    
    
        ////////////////////////////////////////////  GETTERS AND SETTERS  ////////
    
    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(LinkedList<Account> accounts) {
        this.accounts = accounts;
    }
    
    
    
    
    
    
    
    
        ///////////////////////////////////////////  JAVA OBJECT  ///////////
    
    /**
     * overrides default method
     * @return 
     */
    @Override
    public String toString() {
        return getName();
    }

}//end AccountCategory
