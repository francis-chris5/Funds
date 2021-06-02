
package Funds;

import javafx.application.Platform;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class BalanceTree extends StackPane{
    
    private Book book;
    private AccountType type;
    private TreeView tree;
    private AccountDialog accountDialog;
    
    private int clickCounter = 0;
    
    public BalanceTree(Book book, AccountType type){
        this.book = book;
        this.type = type;
        refreshTree();
        tree.getSelectionModel().selectedItemProperty().addListener(cl -> {
            launchAccountDialog();
        });
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
        Image pic = new Image(getClass().getResourceAsStream("Images/tempIcon.png"));
        ImageView icon = new ImageView(pic);
        icon.setFitWidth(16);
        icon.setFitHeight(16);
        TreeItem root = new TreeItem("Assets", icon);
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
    
    
    public TreeView loadLiabilityTree(){
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("Images/tempIcon.png")));
        icon.setFitWidth(16);
        icon.setFitHeight(16);
        TreeItem root = new TreeItem("Liabilities", icon);
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
    
    
    public TreeView loadEquityTree(){
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("Images/tempIcon.png")));
        icon.setFitWidth(16);
        icon.setFitHeight(16);
        TreeItem root = new TreeItem("Equity", icon);
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
    
    
    
    public void launchAccountDialog(){
        TreeItem selected = (TreeItem)tree.getSelectionModel().getSelectedItem();
        try{
            Account acc = (Account)selected.getValue();
            accountDialog = new AccountDialog(acc);
            Platform.runLater(() -> tree.getSelectionModel().clearSelection());
        }
        catch(Exception e){
            //probably clicked a label then
        }
    }//end launchAccountDialog()
}//end BalanceTree
