
package Funds;


public enum Reconcile {
    
    n, c;

    @Override
    public String toString() {
        switch(this){
            case n:
                return "pending";
            case c:
                return "cleared";
            default:
                return "pending";
        }
    }//end toString()
    
    
    
}//end Reconcile
