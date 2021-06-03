
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
