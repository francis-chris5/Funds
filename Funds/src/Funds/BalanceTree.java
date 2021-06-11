
package Funds;

import Funds.DataEnums.AccountType;
import Funds.Dialogs.RemoveAccountDialog;
import Funds.Dialogs.NewAccountDialog;
import Funds.Dialogs.NewAccountCategoryDialog;
import Funds.Dialogs.AccountDialog;
import Funds.DataObjects.AccountCategory;
import Funds.Dialogs.ModifyAccountOrderDialog;
import Funds.DataObjects.Account;
import Funds.DataObjects.Book;
import java.text.NumberFormat;
import java.util.LinkedList;
import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
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
        
            //create the root with an icon
        Image bag = new Image(getClass().getResourceAsStream("Images/MoneyBagIcon.png"));
        ImageView bagIcon = new ImageView(bag);
        bagIcon.setFitWidth(16);
        bagIcon.setFitHeight(16);
        TreeItem root = new TreeItem("Assets", bagIcon);
        root.setExpanded(true);
        
        
            //add the orphan asset accounts
        if(!book.getAssets().isEmpty()){
            for(int i = 0; i < book.getAssets().size(); i++){
                TreeItem<Account> branch = new TreeItem(book.getAssets().get(i));
                root.getChildren().add(branch);
            }
        }

            //add the categorized asset accounts
        LinkedList<AccountCategory> subcategory = book.getSubcategory(AccountType.ASSET);
        for(int i = 0; i < subcategory.size(); i++){
            TreeItem branch = new TreeItem(subcategory.get(i));
            branch.setExpanded(true);
            root.getChildren().add(branch);
            for(int j = 0; j < subcategory.get(i).getAccounts().size(); j++){
                TreeItem twig = new TreeItem(subcategory.get(i).getAccounts().get(j));
                branch.getChildren().add(twig);
            }
        }
        
        
            //assemble and return the tree
        TreeView assetTree = new TreeView(root);
        return assetTree;
    }//end loadAssetTree()
    
    
    
    
    /**
     * creates and fills the liability tree
     * @return <b>TreeView</b> ready to be loaded into the balance sheet
     */
    public TreeView loadLiabilityTree(){
        
            //create the root with an icon
        Image scale = new Image(getClass().getResourceAsStream("Images/ScaleIcon.png"));
        ImageView scaleIcon = new ImageView(scale);
        scaleIcon.setFitWidth(16);
        scaleIcon.setFitHeight(16);
        TreeItem root = new TreeItem("Liabilities", scaleIcon);
        root.setExpanded(true);
        
        
            //add the orphan liability accounts
        if(!book.getLiabilities().isEmpty()){
            for(int i = 0; i < book.getLiabilities().size(); i++){
                TreeItem<Account> branch = new TreeItem(book.getLiabilities().get(i));
                root.getChildren().add(branch);
            }
        }
        
        
            //add the categorized liability accounts
        LinkedList<AccountCategory> subcategory = book.getSubcategory(AccountType.LIABILITY);
        for(int i = 0; i < subcategory.size(); i++){
            TreeItem branch = new TreeItem(subcategory.get(i));
            branch.setExpanded(true);
            root.getChildren().add(branch);
            for(int j = 0; j < subcategory.get(i).getAccounts().size(); j++){
                TreeItem twig = new TreeItem(subcategory.get(i).getAccounts().get(j));
                branch.getChildren().add(twig);
            }
        }
        
            //assemble and return the tree
        TreeView liabilityTree = new TreeView(root);
        return liabilityTree;
    }//end loadLiabilityTree()
    
    
    
    
    /**
     * creates and fills the equity tree
     * @return <b>TreeView</b> ready to be loaded into the balance sheet
     */
    public TreeView loadEquityTree(){
        
            //create the root with an icon
        Image building = new Image(getClass().getResourceAsStream("Images/BuildingIcon.png"));
        ImageView buildingIcon = new ImageView(building);
        buildingIcon.setFitWidth(16);
        buildingIcon.setFitHeight(16);
        TreeItem root = new TreeItem("Equity", buildingIcon);
        root.setExpanded(true);
        
        
            //add in orphan equity accounts
        if(!book.getEquities().isEmpty()){
            for(int i = 0; i < book.getEquities().size(); i++){
                TreeItem<Account> branch = new TreeItem(book.getEquities().get(i));
                root.getChildren().add(branch);
            }
        }
        
        
            //add the categorized equity accounts
        LinkedList<AccountCategory> subcategory = book.getSubcategory(AccountType.EQUITY);
        for(int i = 0; i < subcategory.size(); i++){
            TreeItem branch = new TreeItem(subcategory.get(i));
            branch.setExpanded(true);
            root.getChildren().add(branch);
            for(int j = 0; j < subcategory.get(i).getAccounts().size(); j++){
                TreeItem twig = new TreeItem(subcategory.get(i).getAccounts().get(j));
                branch.getChildren().add(twig);
            }
        }
        
        
            //assemble and return the tree
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
        
        private MenuItem miNewAccountCategory = new MenuItem("New " + type.toString() + " Category");
        private MenuItem miNewAccountDirect = new MenuItem("New " + type.toString() + " Account");
        private Menu mnNewAccountCategory = new Menu("New Categorized " + type.toString() + " Account");
        private MenuItem miModifyAccountOrder = new MenuItem("Modify " + type.toString() + " Categories/Accounts Order");
        private MenuItem miRemoveAccount = new MenuItem("Remove " + type.toString() + " Categories/Accounts");
        
        
        
        
        
            ////////////////////////////////////////////////  CONSTRUCTORS  //////
        /**
         * Since this is a single purpose object the constructor handles all the work: which methods get called when a menu item is clicked on
         */
        public BalanceTreeContextMenu(){
            
                //find available subcategories of this type
            LinkedList<AccountCategory> subcategory = new LinkedList<>();
            for(int i = 0; i < book.getAccountCategories().size(); i++){
                if(book.getAccountCategories().get(i).getType() == type){
                    subcategory.add(book.getAccountCategories().get(i));
                }
            }
            
            
                //add new category button
            miNewAccountCategory.setOnAction(m ->{
                //AccountType localType = findType(tree.getRoot().getValue().toString());
                NewAccountCategoryDialog temp = new NewAccountCategoryDialog(book, findType(tree.getRoot().getValue().toString()));
                //book.setSaved(false);
                book.displayDetails();
            });
            this.getItems().add(miNewAccountCategory);
            
            
                //add new account button
            miNewAccountDirect.setOnAction(m -> {
                //AccountType localType = findType(tree.getRoot().getValue().toString());
                NewAccountDialog temp = new NewAccountDialog(book, findType(tree.getRoot().getValue().toString()));
                //book.setSaved(false);
                book.displayDetails();
            });
            this.getItems().add(miNewAccountDirect);
            
            
                //add new account menu -for subcategories
            if(!subcategory.isEmpty()){
                for(int i = 0; i < subcategory.size(); i++){
                    MenuItem item = new MenuItem("New " + subcategory.get(i).getName() + " Account");
                    AccountCategory category = subcategory.get(i);
                    item.setOnAction(m -> {
                        //AccountType localType = findType(tree.getRoot().getValue().toString());
                        NewAccountDialog temp = new NewAccountDialog(book, findType(tree.getRoot().getValue().toString()), category);
                        //book.setSaved(false);
                        book.displayDetails();
                    });
                    mnNewAccountCategory.getItems().add(item);
                }
                this.getItems().add(mnNewAccountCategory);
            }
            
            
            this.getItems().add(new SeparatorMenuItem());
            
            
                //add modify order button
            miModifyAccountOrder.setOnAction(m -> {
                ModifyAccountOrderDialog temp = new ModifyAccountOrderDialog(book, findType(tree.getRoot().getValue().toString()));
                //book.setSaved(false);
                book.displayDetails();
            });
            this.getItems().add(miModifyAccountOrder);
            
                //add remove account button
            miRemoveAccount.setOnAction(m -> {
                RemoveAccountDialog temp = new RemoveAccountDialog(book, findType(tree.getRoot().getValue().toString()));
                //book.setSaved(false);
                book.displayDetails();
            });
            this.getItems().add(miRemoveAccount);
        }//end constructor()
        
        
        
        /**
         * converts a localString, such as from a button label, to an enumerated AccountType
         * @param localString
         * @return 
         */
        public AccountType findType(String localString){
            AccountType localType = null;
            if(localString.equals("Assets")){
                localType = AccountType.ASSET;
            }
            else if(localString.equals("Liabilities")){
                localType = AccountType.LIABILITY;
            }
            else if(localString.equals("Equity")){
                localType = AccountType.EQUITY;
            }
            return localType;
        }//end findType()
        
    }//end BalanceTreeContextMenu
    
}//end BalanceTree


