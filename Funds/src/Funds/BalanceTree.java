
package Funds;

import java.text.NumberFormat;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;


public class BalanceTree extends StackPane{
    
    private Book book;
    private AccountType type;
    private TreeView tree;
    private AccountDialog accountDialog;
    
    private int clickCounter = 0;
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    
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
    
    
    public TreeView loadAssetTree(){
        Image scale = new Image(getClass().getResourceAsStream("Images/ScaleIcon.png"));
        Image bag = new Image(getClass().getResourceAsStream("Images/MoneyBagIcon.png"));
        ImageView scaleIcon = new ImageView(scale);
        scaleIcon.setFitWidth(32);
        scaleIcon.setFitHeight(32);
        TreeItem root = new TreeItem("Assets", scaleIcon);
        root.setExpanded(true);
        if(!book.getAssets().isEmpty()){
            for(int i = 0; i < book.getAssets().size(); i++){
                ImageView bagIcon = new ImageView(bag);
                bagIcon.setFitWidth(16);
                bagIcon.setFitHeight(16);
                TreeItem<Account> branch = new TreeItem(book.getAssets().get(i), bagIcon);
                root.getChildren().add(branch);
            }
        }
        
        TreeView assetTree = new TreeView(root);
        return assetTree;
    }//end loadAssetTree()
    
    
    public TreeView loadLiabilityTree(){
        Image scale = new Image(getClass().getResourceAsStream("Images/ScaleIcon.png"));
        Image bag = new Image(getClass().getResourceAsStream("Images/MoneyBagIcon.png"));
        ImageView scaleIcon = new ImageView(scale);
        scaleIcon.setFitWidth(32);
        scaleIcon.setFitHeight(32);
        TreeItem root = new TreeItem("Liabilities", scaleIcon);
        root.setExpanded(true);
         if(!book.getLiabilities().isEmpty()){
            for(int i = 0; i < book.getLiabilities().size(); i++){
                ImageView bagIcon = new ImageView(bag);
                bagIcon.setFitWidth(16);
                bagIcon.setFitHeight(16);
                TreeItem<Account> branch = new TreeItem(book.getLiabilities().get(i), bagIcon);
                root.getChildren().add(branch);
            }
        }
        
        TreeView liabilityTree = new TreeView(root);
        return liabilityTree;
    }//end loadLiabilityTree()
    
    
    public TreeView loadEquityTree(){
        Image scale = new Image(getClass().getResourceAsStream("Images/ScaleIcon.png"));
        Image bag = new Image(getClass().getResourceAsStream("Images/MoneyBagIcon.png"));
        ImageView scaleIcon = new ImageView(scale);
        scaleIcon.setFitWidth(32);
        scaleIcon.setFitHeight(32);
        TreeItem root = new TreeItem("Equity", scaleIcon);
        root.setExpanded(true);
        if(!book.getEquities().isEmpty()){
            for(int i = 0; i < book.getEquities().size(); i++){
                ImageView bagIcon = new ImageView(bag);
                bagIcon.setFitWidth(16);
                bagIcon.setFitHeight(16);
                TreeItem<Account> branch = new TreeItem(book.getEquities().get(i), bagIcon);
                root.getChildren().add(branch);
            }
        }
        
        TreeView equityTree = new TreeView(root);
        return equityTree;
    }//end loadEquityTree()
    
    
    
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
            });
            miRemoveAccount.setOnAction(m -> {
                RemoveAccountDialog temp = new RemoveAccountDialog(book, tree.getRoot().getValue().toString());
            });
            this.getItems().addAll(miNewAccount, miRemoveAccount);
        }//end constructor()
        
        
    }//end BalanceTreeContextMenu
    
}//end BalanceTree


