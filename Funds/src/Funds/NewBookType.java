
package Funds;


public enum NewBookType {
    
    EMPTY, BASIC_PERSONAL, DETAILED;

    @Override
    public String toString() {
        switch(this){
            case EMPTY:
                return "Asset, Liability, and Equity charts but no accounts";
            case BASIC_PERSONAL:
                return "Asset, Liability, and Equity charts containing Cash, Credit Card, Revenue, and Expense accounts";
            case DETAILED:
                return "Asset, Liability, and Equity charts containing categories for current and long term assets and liabilities and an earnings categeory in equity,Cash, Credit Card, Revenue, and Expense accounts are placed into these categories";
            default: 
                return "Asset, Liability, and Equity charts but no accounts";
        }
    }

}//end NewBookType
