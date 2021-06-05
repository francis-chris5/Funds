
package Funds;

import java.text.NumberFormat;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


/**
 * Class to create and fill the TreeView objects for the balance sheet
 * @author Chris Francis
 */
public class BalanceTree extends StackPane{
    
        ///////////////////////////////////////////  DATAFIELDS  /////////////
    
    private Book book;
    private AccountType type;
    private TreeView tree;
    private AccountDialog accountDialog;
    
    private int clickCounter = 0;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    
    
    
    
    
    
    
    
        ////////////////////////////////////////////////  CONSTRUCTORS  ////////
    
    /**
     * The two-arg constructor needs the book holding the records and the enumerated type of account
     * @param book book where the account is recorded
     * @param type enumerated available account types (ASSET, LIABILITY, EQUITY)
     */
    public BalanceTree(Book book, AccountType type){
        this.book = book;
        this.type = type;
        refreshTree();
        tree.setEditable(true);
        tree.getSelectionModel().selectedItemProperty().addListener(cl -> {
            launchAccountDialog();
        });
        tree.setContextMenu(new BalanceTreeContextMenu());
        this.getChildren().add(tree);
        
    }//end constructor
    
    
    
    
    
    
    
    
        /////////////////////////////////////////  CLASS METHODS  /////////
    
    /**
     * deletes the tree and loads in a new one based off the enumerated account type
     */
    public void refreshTree(){
        tree = null;
        switch(type){
            case ASSET:
                tree = loadAssetTree();
                break;
            case LIABILITY:
                tree = loadLiabilityTree();
                break;
            case EQUITY:
                tree = loadEquityTree();
                break;
        }
    }//end refreshTree()
    
    
    
    
    /**
     * creates and fills the asset tree
     * @return <b>TreeView</b> ready to be loaded into the balance sheet
     */
    public TreeView loadAssetTree(){
        Image bag = new Image(getClass().getResourceAsStream("Images/MoneyBagIcon.png"));
        ImageView bagIcon = new ImageView(bag);
        bagIcon.setFitWidth(16);
        bagIcon.setFitHeight(16);
        TreeItem root = new TreeItem("Assets", bagIcon);
        root.setExpanded(true);
        if(!book.getAssets().isEmpty()){
            for(int i = 0; i < book.getAssets().size(); i++){
                TreeItem<Account> branch = new TreeItem(book.getAssets().get(i));
                root.getChildren().add(branch);
            }
        }
        
        TreeView assetTree = new TreeView(root);
        return assetTree;
    }//end loadAssetTree()
    
    
    
    
    /**
     * creates and fills the liability tree
     * @return <b>TreeView</b> ready to be loaded into the balance sheet
     */
    public TreeView loadLiabilityTree(){
        Image scale = new Image(getClass().getResourceAsStream("Images/ScaleIcon.png"));
        ImageView scaleIcon = new ImageView(scale);
        scaleIcon.setFitWidth(16);
        scaleIcon.setFitHeight(16);
        TreeItem root = new TreeItem("Liabilities", scaleIcon);
        root.setExpanded(true);
         if(!book.getLiabilities().isEmpty()){
            for(int i = 0; i < book.getLiabilities().size(); i++){
                TreeItem<Account> branch = new TreeItem(book.getLiabilities().get(i));
                root.getChildren().add(branch);
            }
        }
        
        TreeView liabilityTree = new TreeView(root);
        return liabilityTree;
    }//end loadLiabilityTree()
    
    
    
    
    /**
     * creates and fills the equity tree
     * @return <b>TreeView</b> ready to be loaded into the balance sheet
     */
    public TreeView loadEquityTree(){
        Image building = new Image(getClass().getResourceAsStream("Images/BuildingIcon.png"));
        ImageView buildingIcon = new ImageView(building);
        buildingIcon.setFitWidth(16);
        buildingIcon.setFitHeight(16);
        TreeItem root = new TreeItem("Equity", buildingIcon);
        root.setExpanded(true);
        if(!book.getEquities().isEmpty()){
            for(int i = 0; i < book.getEquities().size(); i++){
                TreeItem<Account> branch = new TreeItem(book.getEquities().get(i));
                root.getChildren().add(branch);
            }
        }
        
        TreeView equityTree = new TreeView(root);
        return equityTree;
    }//end loadEquityTree()
    
    
    
    /**
     * method to open the selected account's ledger dialog
     */
    public void launchAccountDialog(){
        TreeItem selected = (TreeItem)tree.getSelectionModel().getSelectedItem();
        try{
            Account acc = (Account)selected.getValue();
            accountDialog = new AccountDialog(book, acc);
            Platform.runLater(() -> tree.getSelectionModel().clearSelection());
        }
        catch(Exception e){
            //probably clicked a label then
        }
    }//end launchAccountDialog()
    
    
    
    
    
    
    
    
        ////////////////////////////////////////////////  INNER CLASSES  //////////
    
    /**
     * Context menu for functions related to each particular type of account on the balance sheet
     */
    public class BalanceTreeContextMenu extends ContextMenu{
        
            /////////////////////////////////////////////////  DATAFIELDS  ///////
        
        private MenuItem miNewAccount = new MenuItem("New " + type.toString() + " Account");
        private MenuItem miRemoveAccount = new MenuItem("Remove " + type.toString() + " Account");
        
        
        
        
        
        
        
            ////////////////////////////////////////////////  CONSTRUCTORS  //////
        /**
         * Since this is a single purpose object the constructor handles all the work: which methods get called when a menu item is clicked on
         */
        public BalanceTreeContextMenu(){
            miNewAccount.setOnAction(m -> {
                NewAccountDialog temp = new NewAccountDialog(book, tree.getRoot().getValue().toString());
                book.displayDetails();
            });
            miRemoveAccount.setOnAction(m -> {
                RemoveAccountDialog temp = new RemoveAccountDialog(book, tree.getRoot().getValue().toString());
                book.displayDetails();
            });
            this.getItems().addAll(miNewAccount, miRemoveAccount);
        }//end constructor()
        
        
    }//end BalanceTreeContextMenu
    
}//end BalanceTree


