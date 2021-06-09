
package Funds.DataEnums;


/**
 * The available options for creating new Books, or we could just call them templates if you prefer
 * @author Chris
 */
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
                return "Asset, Liability, and Equity charts containing categories for Current and Long Term Assets and Liabilities and a Retained Earnings categeory under Equity, numerous accounts have been placed in these categories for a starting point";
            default: 
                return "Asset, Liability, and Equity charts but no accounts";
        }
    }

}//end NewBookType
