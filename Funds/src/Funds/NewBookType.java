
package Funds;


public enum NewBookType {
    
    EMPTY, BASIC_PERSONAL;

    @Override
    public String toString() {
        switch(this){
            case EMPTY:
                return "Asset, Liability, and Equity charts but no accounts";
            case BASIC_PERSONAL:
                return "Asset, Liability, and Equity charts containing Cash, Credit Card, Revenue, and Expense accounts";
            default: 
                return "Asset, Liability, and Equity charts but no accounts";
        }
    }

}//end NewBookType
