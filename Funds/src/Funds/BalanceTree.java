
package Funds;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class BalanceTree extends StackPane{
    
    private Book book;
    private TreeView tree;
    private AccountDialog accountDialog;
    
    public BalanceTree(Book book, AccountType type){
        this.book = book;
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
        //StackPane root = new StackPane();
        this.getChildren().add(tree);
    }//end constructor
    
    
    
    public TreeView loadAssetTree(){
        Image pic = new Image(getClass().getResourceAsStream("Images/tempIcon.png"));
        ImageView icon = new ImageView(pic);
        icon.setFitWidth(16);
        icon.setFitHeight(16);
        TreeItem root = new TreeItem("Assets", icon);
        
        if(!book.getAssets().isEmpty()){
            for(int i = 0; i < book.getAssets().size(); i++){
//                Hyperlink leaf = new Hyperlink(book.getAssets().get(i).toString());
//                leaf.setGraphic(new ImageView(pic));
//                leaf.setOnAction(click -> {
//                    //launchAccountDialog(book.getAssets().get(i));
//                });
                TreeItem<Account> branch = new TreeItem(book.getAssets().get(i));
                root.getChildren().add(branch);
            }
        }
        
        TreeView assetTree = new TreeView(root);
        return assetTree;
    }//end loadAssetTree()
    
    
    public TreeView loadLiabilityTree(){
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("Images/tempIcon.png")));
        icon.setFitWidth(16);
        icon.setFitHeight(16);
        TreeItem root = new TreeItem("Liabilities", icon);
        
        //get liability accounts from the book
        
        TreeView liabilityTree = new TreeView(root);
        return liabilityTree;
    }//end loadLiabilityTree()
    
    
    public TreeView loadEquityTree(){
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("Images/tempIcon.png")));
        icon.setFitWidth(16);
        icon.setFitHeight(16);
        TreeItem root = new TreeItem("Equity", icon);
        
        //get equity accounts from the book
        
        TreeView equityTree = new TreeView(root);
        return equityTree;
    }//end loadEquityTree()
    
    
    
    public void launchAccountDialog(Account account){
            //this will be called when an account is double clicked on tree
        accountDialog = new AccountDialog(account);
    }//end launchAccountDialog()
}//end BalanceTree
