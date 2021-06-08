
package Funds.Tools.Budgeting.RevenueSplitter;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * a java class to save details for the RevenueSplitter tool, it's just a linked list of items for the tool and a string for a name to identify it with
 * @author Chris
 */
public class RevenueSplit implements Serializable {
    
        ////////////////////////////////////////////////  DATAFIELDS  /////////
    
    private String name = new String();
    private LinkedList<RevenueSplitterItem> items = new LinkedList<>();
    
    
    
    
    
    
    
        //////////////////////////////////////////////  CONSTRUCTORS  ///////

    public RevenueSplit() {
    }//end default constructor
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////  GETTERS AND SETTERS  //////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<RevenueSplitterItem> getItems() {
        return items;
    }

    public void setItems(LinkedList<RevenueSplitterItem> items) {
        this.items = items;
    }
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////////  JAVA OBJECT  /////////

    @Override
    public String toString() {
        return getName() + ": with " + getItems().size() + " accounts";
    }//end toString()
    
    
    
}//end RevenueSplit
