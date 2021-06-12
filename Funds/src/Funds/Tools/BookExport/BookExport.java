
package Funds.Tools.BookExport;

import Funds.DataEnums.AccountType;
import Funds.DataObjects.Account;
import Funds.DataObjects.Book;
import Funds.DataObjects.Transaction;
import java.io.File;
import java.io.PrintWriter;
import javafx.stage.DirectoryChooser;


/**
 * Object to export the data in one of the big four standards: xml, json, csv, sql
 * @author Chris
 */
public class BookExport {
    
        ///////////////////////////////////////////////  DATAFIELDS  /////////
    
    private Book book;
    private ExportType type;
    
    
    
    
    
    
    
    
        /////////////////////////////////////////////  CONSTRUCTORS  /////////
    
    /**
     * To export the data need to know what book holds the data and how to export it
     * @param book the current working book
     * @param type the enumerated type of export
     */
    public BookExport(Book book, ExportType type){
        this.book = book;
        this.type = type;
    }//end constructor

    
    
    
    /**
     * In an attempt to preserve structure this method sets up a directory structure for each of the main types and all their subcategories, and then writes each account to its own file, with the obvious exception of SQL where it is one big initialize_book-name_DB.sql text file which will preserve the structure in keyed tables
     */
    public void write(){
        try{
            DirectoryChooser choose = new DirectoryChooser();
            File location = choose.showDialog(null);
            File folder = new File(location.getPath() + "/" + book.getName());
            folder.mkdir();
            
            if(type == ExportType.XML){
                    //assets
                File assetFolder = new File(folder.getPath() + "/Assets");
                assetFolder.mkdir();
                for(int i = 0; i < book.getAssets().size(); i++){
                    File file  = new File(assetFolder.getPath() + "/" + book.getAssets().get(i).getName() + ".xml");
                    PrintWriter pw = new PrintWriter(file);
                    pw.write(getXMLString(book.getAssets().get(i)));
                    pw.close();
                }
                for(int i = 0; i < book.getSubcategory(AccountType.ASSET).size(); i++){
                    File category = new File(assetFolder.getPath() + "/" + book.getSubcategory(AccountType.ASSET).get(i).getName());
                    category.mkdir();
                    for(int j = 0; j < book.getSubcategory(AccountType.ASSET).get(i).getAccounts().size(); j++){
                        File file = new File(category.getPath() + "/" + book.getSubcategory(AccountType.ASSET).get(i).getAccounts().get(j).getName() + ".xml");
                        PrintWriter pw = new PrintWriter(file);
                        pw.write(getXMLString(book.getSubcategory(AccountType.ASSET).get(i).getAccounts().get(j)));
                        pw.close();
                    }
                }

                    //liabilities
                File liabilityFolder = new File(folder.getPath() + "/Liability");
                liabilityFolder.mkdir();
                for(int i = 0; i < book.getLiabilities().size(); i++){
                    File file = new File(liabilityFolder.getPath() + "/" + book.getLiabilities().get(i).getName() + ".xml");
                    PrintWriter pw = new PrintWriter(file);
                    pw.write(getXMLString(book.getLiabilities().get(i)));
                    pw.close();
                }
                for(int i = 0; i < book.getSubcategory(AccountType.LIABILITY).size(); i++){
                    File category = new File(liabilityFolder.getPath() + "/" + book.getSubcategory(AccountType.LIABILITY).get(i).getName());
                    category.mkdir();
                    for(int j = 0; j < book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().size(); j++){
                        File file = new File(category.getPath() + "/" + book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().get(j).getName() + ".xml");
                        PrintWriter pw = new PrintWriter(file);
                        pw.write(getXMLString(book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().get(j)));
                        pw.close();
                    }
                }


                    //equity
                File equityFolder = new File(folder.getPath() + "/Equity");
                equityFolder.mkdir();
                for(int i = 0; i < book.getEquities().size(); i++){
                    File file = new File(equityFolder.getPath() + "/" + book.getEquities().get(i).getName() + ".xml");
                    PrintWriter pw = new PrintWriter(file);
                    pw.write(getXMLString(book.getEquities().get(i)));
                    pw.close();
                }
                for(int i = 0; i < book.getSubcategory(AccountType.EQUITY).size(); i++){
                    File category = new File(equityFolder.getPath() + "/" + book.getSubcategory(AccountType.EQUITY).get(i).getName());
                    category.mkdir();
                    for(int j = 0; j < book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().size(); j++){
                        File file = new File(category.getPath() + "/" + book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().get(j).getName() + ".xml");
                        PrintWriter pw = new PrintWriter(file);
                        pw.write(getXMLString(book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().get(j)));
                        pw.close();
                    }
                }
            }
            else if(type == ExportType.JSON){
                
                    //assets
                File assetFolder = new File(folder.getPath() + "/Assets");
                assetFolder.mkdir();
                Account[] accounts;
                for(int i = 0; i < book.getAssets().size(); i++){
                    File file  = new File(assetFolder.getPath() + "/Assets.json");
                    accounts  = new Account[book.getAssets().size()];
                    book.getAssets().toArray(accounts);
                    PrintWriter pw = new PrintWriter(file);
                    pw.write(getJSONString(accounts));
                    pw.close();
                }
                for(int i = 0; i < book.getSubcategory(AccountType.ASSET).size(); i++){
                    File category = new File(assetFolder.getPath() + "/" + book.getSubcategory(AccountType.ASSET).get(i).getName());
                    category.mkdir();
                    File file = new File(category.getPath() + "/" + book.getSubcategory(AccountType.ASSET).get(i).getName() + ".json");
                    accounts = new Account[book.getSubcategory(AccountType.ASSET).get(i).getAccounts().size()];
                    book.getSubcategory(AccountType.ASSET).get(i).getAccounts().toArray(accounts);
                    PrintWriter pw = new PrintWriter(file);
                    pw.write(getJSONString(accounts));
                    pw.close();
                }
                
                
                
                    //liability
                File liabilityFolder = new File(folder.getPath() + "/Liability");
                liabilityFolder.mkdir();
                for(int i = 0; i < book.getLiabilities().size(); i++){
                    File file  = new File(liabilityFolder.getPath() + "/Liability.json");
                    accounts  = new Account[book.getLiabilities().size()];
                    book.getLiabilities().toArray(accounts);
                    PrintWriter pw = new PrintWriter(file);
                    pw.write(getJSONString(accounts));
                    pw.close();
                }
                for(int i = 0; i < book.getSubcategory(AccountType.LIABILITY).size(); i++){
                    File category = new File(liabilityFolder.getPath() + "/" + book.getSubcategory(AccountType.LIABILITY).get(i).getName());
                    category.mkdir();
                    File file = new File(category.getPath() + "/" + book.getSubcategory(AccountType.LIABILITY).get(i).getName() + ".json");
                    accounts = new Account[book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().size()];
                    book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().toArray(accounts);
                    PrintWriter pw = new PrintWriter(file);
                    pw.write(getJSONString(accounts));
                    pw.close();
                }
                
                
                
                    //equity
                File equityFolder = new File(folder.getPath() + "/Equity");
                equityFolder.mkdir();
                for(int i = 0; i < book.getEquities().size(); i++){
                    File file  = new File(equityFolder.getPath() + "/Equity.json");
                    accounts  = new Account[book.getEquities().size()];
                    book.getEquities().toArray(accounts);
                    PrintWriter pw = new PrintWriter(file);
                    pw.write(getJSONString(accounts));
                    pw.close();
                }
                for(int i = 0; i < book.getSubcategory(AccountType.EQUITY).size(); i++){
                    File category = new File(equityFolder.getPath() + "/" + book.getSubcategory(AccountType.EQUITY).get(i).getName());
                    category.mkdir();
                    File file = new File(category.getPath() + "/" + book.getSubcategory(AccountType.EQUITY).get(i).getName() + ".json");
                    accounts = new Account[book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().size()];
                    book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().toArray(accounts);
                    PrintWriter pw = new PrintWriter(file);
                    pw.write(getJSONString(accounts));
                    pw.close();
                }
            }
            else if(type == ExportType.CSV){
                
                    //assets
                File assetFolder = new File(folder.getPath() + "/Assets");
                assetFolder.mkdir();
                File file  = new File(assetFolder.getPath() + "/AssetAccounts.csv");
                Account[] accounts = new Account[book.getAssets().size()];
                book.getAssets().toArray(accounts);
                createAccountCSV(file, accounts);
                for(int i = 0; i < book.getAssets().size(); i++){
                    file = new File(assetFolder.getPath() + "/" + book.getAssets().get(i).getName() + ".csv");
                    Transaction[] transactions = new Transaction[book.getAssets().get(i).getTransactions().size()];
                    book.getAssets().get(i).getTransactions().toArray(transactions);
                    createTransactionCSV(file, transactions);
                }
                for(int i = 0; i < book.getSubcategory(AccountType.ASSET).size(); i++){
                    File category = new File(assetFolder.getPath() + "/" + book.getSubcategory(AccountType.ASSET).get(i).getName());
                    category.mkdir();
                    File accountFile = new File(category.getPath() + "/" + book.getSubcategory(AccountType.ASSET).get(i).getName() + ".csv");
                    accounts = new Account[book.getSubcategory(AccountType.ASSET).get(i).getAccounts().size()];
                    book.getSubcategory(AccountType.ASSET).get(i).getAccounts().toArray(accounts);
                    createAccountCSV(accountFile, accounts);
                    for(int j = 0; j < book.getSubcategory(AccountType.ASSET).get(i).getAccounts().size(); j++){
                        file = new File(category.getPath() + "/" + book.getSubcategory(AccountType.ASSET).get(i).getAccounts().get(j).getName() + ".csv");
                        Transaction[] transactions = new Transaction[book.getSubcategory(AccountType.ASSET).get(i).getAccounts().get(j).getTransactions().size()];
                        book.getSubcategory(AccountType.ASSET).get(i).getAccounts().get(j).getTransactions().toArray(transactions);
                        createTransactionCSV(file, transactions);
                    }
                }
                
                
                    //assets
                File liabilityFolder = new File(folder.getPath() + "/Liability");
                liabilityFolder.mkdir();
                file  = new File(liabilityFolder.getPath() + "/LiabilityAccounts.csv");
                accounts = new Account[book.getLiabilities().size()];
                book.getLiabilities().toArray(accounts);
                createAccountCSV(file, accounts);
                for(int i = 0; i < book.getLiabilities().size(); i++){
                    file = new File(liabilityFolder.getPath() + "/" + book.getLiabilities().get(i).getName() + ".csv");
                    Transaction[] transactions = new Transaction[book.getLiabilities().get(i).getTransactions().size()];
                    book.getLiabilities().get(i).getTransactions().toArray(transactions);
                    createTransactionCSV(file, transactions);
                }
                for(int i = 0; i < book.getSubcategory(AccountType.LIABILITY).size(); i++){
                    File category = new File(liabilityFolder.getPath() + "/" + book.getSubcategory(AccountType.LIABILITY).get(i).getName());
                    category.mkdir();
                    File accountFile = new File(category.getPath() + "/" + book.getSubcategory(AccountType.LIABILITY).get(i).getName() + ".csv");
                    accounts = new Account[book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().size()];
                    book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().toArray(accounts);
                    createAccountCSV(accountFile, accounts);
                    for(int j = 0; j < book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().size(); j++){
                        file = new File(category.getPath() + "/" + book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().get(j).getName() + ".csv");
                        Transaction[] transactions = new Transaction[book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().get(j).getTransactions().size()];
                        book.getSubcategory(AccountType.LIABILITY).get(i).getAccounts().get(j).getTransactions().toArray(transactions);
                        createTransactionCSV(file, transactions);
                    }
                }
                
                
                    //equity
                File equityFolder = new File(folder.getPath() + "/Equity");
                equityFolder.mkdir();
                file  = new File(equityFolder.getPath() + "/EquityAccounts.csv");
                accounts = new Account[book.getEquities().size()];
                book.getEquities().toArray(accounts);
                createAccountCSV(file, accounts);
                for(int i = 0; i < book.getEquities().size(); i++){
                    file = new File(equityFolder.getPath() + "/" + book.getEquities().get(i).getName() + ".csv");
                    Transaction[] transactions = new Transaction[book.getEquities().get(i).getTransactions().size()];
                    book.getEquities().get(i).getTransactions().toArray(transactions);
                    createTransactionCSV(file, transactions);
                }
                for(int i = 0; i < book.getSubcategory(AccountType.EQUITY).size(); i++){
                    File category = new File(equityFolder.getPath() + "/" + book.getSubcategory(AccountType.EQUITY).get(i).getName());
                    category.mkdir();
                    File accountFile = new File(category.getPath() + "/" + book.getSubcategory(AccountType.EQUITY).get(i).getName() + ".csv");
                    accounts = new Account[book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().size()];
                    book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().toArray(accounts);
                    createAccountCSV(accountFile, accounts);
                    for(int j = 0; j < book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().size(); j++){
                        file = new File(category.getPath() + "/" + book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().get(j).getName() + ".csv");
                        Transaction[] transactions = new Transaction[book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().get(j).getTransactions().size()];
                        book.getSubcategory(AccountType.EQUITY).get(i).getAccounts().get(j).getTransactions().toArray(transactions);
                        createTransactionCSV(file, transactions);
                    }
                }
            }
            else if(type == ExportType.SQL){
                //write the .sql file to create database and tables (will kind of match directory strucutre above) then fill tables all in one file
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }//end write()
    
    
    
    
    /**
     * Generates a string of xml data representing the current state of an account
     * @param account the account data object to be formatted
     * @return <b>String</b> in pretty-print ready to be written to an xml file
     */
    public String getXMLString(Account account){
        String xml = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
        xml += "<Account>\n";
        xml += "\t<name>" + account.getName() + "</name>\n";
        xml += "\t<number>" + account.getNumber() + "</number>\n";
        xml += "\t<routing>" + account.getRouting() + "</routing>\n";
        xml += "\t<code>" + account.getCode() + "</code>\n";
        xml += "\t<description>" + account.getDescription() + "</description>\n";
        xml += "\t<type>" + account.getType() + "</type>\n";
        xml += "\t<normalDebit>" + account.isNormalDebit() + "</normalDebit>\n";
        xml += "\t<transactions>\n";
        for(int i = 0; i < account.getTransactions().size(); i++){
            xml += "\t\t<transaction>\n";
            xml += "\t\t\t<date>" + account.getTransactions().get(i).getDate() + "</date>\n";
            xml += "\t\t\t<transactionID>" + account.getTransactions().get(i).getTransactionID() + "</transactionID>\n";
            xml += "\t\t\t<description>" + account.getTransactions().get(i).getDescription() + "</description>\n";
            xml += "\t\t\t<transfer>" + account.getTransactions().get(i).getTransfer().getName() + "</transfer>\n";
            xml += "\t\t\t<reconcile>" + account.getTransactions().get(i).isReconcile() + "</reconcile>\n";
            xml += "\t\t\t<debit>" + account.getTransactions().get(i).getDebit() + "</debit>\n";
            xml += "\t\t\t<credit>" + account.getTransactions().get(i).getCredit() + "</credit>\n";
            xml += "\t\t\t<ledgerID>" + account.getTransactions().get(i).getLedgerID() + "</ledgerID>\n";
            xml += "\t\t</transaction>\n";
        }
        xml += "\t</transactions>\n";
        xml += "</Account>\n";
        return xml;
    }//end getXMLString()
    
    
    
    
    /**
     * generates a string of JSON formatted account data
     * @param account The account to convert from .fabk form to json format
     * @return <b>String</b> ready to be written to a json file in pretty-print
     */
    public String getJSONString(Account[] accounts){
        String json = new String();
        for(int i = 0; i < accounts.length; i++){
            json += "{\n";
            json += "\t\"name\": \"" + accounts[i].getName() + "\",\n";
            json += "\t\"number\": \"" + accounts[i].getNumber() + "\",\n";
            json += "\t\"routing\": \"" + accounts[i].getRouting() + "\",\n";
            json += "\t\"code\": \"" + accounts[i].getCode() + "\",\n";
            json += "\t\"description\": \"" + accounts[i].getDescription() + "\",\n";
            json += "\t\"type\": \"" + accounts[i].getType() + "\",\n";
            json += "\t\"normalDebit\": \"" + accounts[i].isNormalDebit() + "\",\n";
            json += "\t\"transactions\": [\n";
            for(int j = 0; j < accounts[i].getTransactions().size(); j++){
                json += "\t\t{\n";
                json += "\t\t\t\"date\": \"" + accounts[i].getTransactions().get(j).getDate() + "\",\n";
                json += "\t\t\t\"transactionID\": \"" + accounts[i].getTransactions().get(j).getTransactionID() + "\",\n";
                json += "\t\t\t\"description\": \"" + accounts[i].getTransactions().get(j).getDescription() + "\",\n";
                json += "\t\t\t\"transfer\": \"" + accounts[i].getTransactions().get(j).getTransfer().getName() + "\",\n";
                json += "\t\t\t\"reconcile\": \"" + accounts[i].getTransactions().get(j).isReconcile() + "\",\n";
                json += "\t\t\t\"debit\": " + accounts[i].getTransactions().get(j).getDebit() + ",\n";
                json += "\t\t\t\"credit\": " + accounts[i].getTransactions().get(j).getCredit() + ",\n";
                json += "\t\t\t\"ledgerID\": " + accounts[i].getTransactions().get(j).getLedgerID() + "\n";
                if(i < accounts[i].getTransactions().size() - 1){
                    json += "\t\t},\n";
                }
                else{
                    json += "\t\t}\n";
                }
            }
            json += "\t]\n";
            json += "}\n\n\n\n";
        }
        return json;
    }//end getJSONString()
    
    
    
    
    /**
     * Writes the csv file of data about a collection of accounts (such as all the accounts in a given directory)
     * @param file the java file object to be written
     * @param accounts an array of Account object to export for data storage elsewhere
     */
    public void createAccountCSV(File file, Account[] accounts){
        String csv = new String("name,number,routing,code,description,type,normalDebit\n");
        for(int i = 0; i < accounts.length; i++){
            csv += accounts[i].getName() + "," + accounts[i].getNumber() + "," + accounts[i].getRouting() + "," + accounts[i].getCode() + "," + accounts[i].getDescription() + "," + accounts[i].getType() + "," + accounts[i].isNormalDebit() + "\n";
        }
        try{
            PrintWriter pw = new PrintWriter(file);
            pw.write(csv);
            pw.close();
        }
        catch(Exception e){
            e.printStackTrace();
            //nevermind then
        }
    }//end createAccountCSV()
    
    
    
    
    /**
     * Writes the csv file of data about a collection of transactions (such as all the transactions in a given account)
     * @param file the java file object to write to
     * @param transactions an array of Transaction objects to export for data storage elsewhere
     */
    public void createTransactionCSV(File file, Transaction[] transactions){
        String csv = new String("date,transactionID,description,transfer,reconcile,debit,credit,ledgerID\n");
        for(int i = 0; i < transactions.length; i++){
            csv += transactions[i].getDate() + "," + transactions[i].getTransactionID() + "," + transactions[i].getDescription() + "," + transactions[i].getTransfer() + "," + transactions[i].isReconcile() + "," + transactions[i].getDebit() + "," + transactions[i].getCredit() + "," + transactions[i].getLedgerID() + "\n";
        }
        try{
            PrintWriter pw = new PrintWriter(file);
            pw.write(csv);
            pw.close();
        }
        catch(Exception e){
            //nevermind then
        }
    }//end createTransactionCSV()
  
    
    
    

    
    

    
    
}//end BookExport
