
package Funds;


public enum AccountType {
    ASSET, LIABILITY, EQUITY;
    
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
    
}//end AccountType
