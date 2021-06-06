
package Funds;


/**
 * List of the available types of accounts
 * @author Chris Francis
 */
public enum AccountType {
    ASSET, LIABILITY, EQUITY, ALL;
    
    
    /**
     * The typical normal (which column an increase is recorded in) for each type of account: normal debit is true, normal credit is false.
     * @return <b>boolean</b> for the general normal in that type of account, NOTE: expense accounts should be normal debit though (expense is negative equity by nature)
     */
    public boolean setNormal(){
        switch(this){
            case ASSET:
                return true;
            case LIABILITY:
                return false;
            case EQUITY:
                return false;
            default:
                return true;
        }
    }//end setNormal()

    
    
    /**
     * overrides default method
     * @return <b>String</b> of each account type with the first letter capitalized
     */
    @Override
    public String toString() {
        switch(this){
            case ASSET:
                return "Asset";
            case LIABILITY:
                return "Liability";
            case EQUITY:
                return "Equity";
            default:
                return "";
        }
    }//end toString()
    
    
    
    
}//end AccountType
