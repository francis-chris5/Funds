
package Funds.DataObjects;

import Funds.DataEnums.AccountType;
import Funds.DataObjects.Account;
import java.io.Serializable;
import java.util.LinkedList;


public class AccountCategory implements Serializable {
    
        ///////////////////////////////////////  DATAFIELDS  ///////////////
    
    private AccountType type;
    private String name;
    private LinkedList<Account> accounts = new LinkedList<>();
    
    
    
    
    
    
    
        ////////////////////////////////////////////  CONSTRUCTORS  ////////
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
