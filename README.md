# ![FundsIcon_small](https://user-images.githubusercontent.com/50467171/120141218-12445000-c1aa-11eb-853c-358a5b205e33.png) Funds

<h2>About</h2>

<h4>Summary</h4>

After having a laptop stolen a few years back led to switching over my personal finance tracking from accounting software to a customized spreadsheet, when time came for a new laptop again the decision was made to go back to accounting software, however, the accounting software initially selected doesn't exactly fit my needs: it's designed for a large corporation which means there is way more there than needed, small input issues -am constantly editing transactions by accident so that needs to be less likely to happen without making it difficult to edit, also been thinking for quite a while that the standard accounting statements should be the primary appearance of the GUI not an afterthought to an overly fancy interface then tacked on and hidden a couple menus deep.

So did not want a general purpose spreadsheet modified to fit my needs, there's a desire for specific purpose software set up to automate double entry accounting, but at the same time don't want one of those massive conglomerations of tools, most of which never get used and just leave a person getting lost in the menus. So that's what I tried to develop here: simplicity is the name of the game... Essentially it's just a balance sheet for an overview of the accounts and a ledger to enter and edit transactions. In the background the software handles the standard double-entry method balancing of accounts while the data is stored in nested collections of Linked Lists rather than a Relational Database, and just the small number of tools to meet needs as they arise.




<h4>Installation</h4>
To run from source files hit the green button here on github and download the files as a zip folder, then simply run the appropriate launcher for your system: .bat for Windows 10, .sh for Linux (tested and works with Chrostini Desktop on Chrombooks as well), or .zsh for Mac (use .sh file if you haven't updated your terminal to Zsh yet). The permissions should be set here but if not make sure the launcher is set to 755 or higher for Linux or Mac.
Alternatively the files contian a folder labeled "Installers" which includes a self-contained standard installer for each system: Windows -simply launch the .exe file and walk through the wizard to install in Programs(x86) folder with desktop and start menu shortcuts, Mac -simply launch the .pkg file to install it to your Applications folder, Linux -obviously the source files are here, but I wanted to do something so there's a .deb file that throws the files in /opt and adds in a desktop entry.



<h4>Design Details:</h4>

It's open source software so if you are interested in customizing some accounting software to fit your exact needs the simplistic idea here may make an ideal starting point.

PLATFORM: 

Coded in Java with Graphical User interface in JavaFX so that the same code can readily be used in desktop, mobile, and web scenarios. 

Find the source-code here: https://github.com/francis-chris5/Funds 

Find the javadocs here: https://francis-chris5.github.io/Funds/javadoc

STRUCTURE: 

Books are the working files (new, save, open, etc.)

Books are collections of Accounts

Accounts are collections of Transactions

Transactions are collections of datafields describing a real world monetary exchange

Everything is tied together via a nested assortment of Linked Lists

<br><br><br>

<br><br>

<h2>User Manual</h2>

BALANCE SHEET OPERATIONS:

Book

To create a new book select [File -> New] from the main menu and select the a starting template for the book (more coming soon...)

To open an existing book select [File -> Open] from the main menu and browse to where the .fabk file is stored

To save a book select [File -> Save] or [File -> Save As...] from the main menu and browse to the location where you wish to store the book and give it a meaningful name

To close the current book but keep the Funds Application running select [File -> Close] from the main menu

To exit the Funds Application select [File -> Quit] from the main menu



<br><br>



Accounts (Balance Sheet Access)

The first tab on the Main Graphical User Interface (GUI) appears as a balance sheet listing all available accounts. 

Clicking an account name will open the ledger for that account -see account ledger instructions to proceed from there

To create a new category to organize accounts in right-click in the white space for one of the categories (Assets, Liabilities, or Equity) and select "New <TYPE> Category" from the context menu. Alternatively select [Accounts > New Account Category] from the main menu --see create account categories to continue

To create a new account right-click in the white space for one of the main types (Assets, Liabilities, or Equity) and select "New <TYPE> Account" from the context menu, if categories are available a second "New <TYPE> Account" menu will be available as well. Alternatively select [Accounts > New Account] from the main menu --see create account instructions to proceed

To modify the order the accounts appear in right click in the white space for one of the main types (Assets, Liabilities, or Equity) and select "Modify <TYPE> Category/Account Order" from the context menu. Alternatively select [Accounts > Modify Category/Account Order] from the main menu --see Modify Order of Categories/Accounts to continue

To remove an account from the book right click in the white space for one of the main types (Assets, Liabilities, or Equity) and select "Remove <TYPE> Category/Account" from the context menu, select the account(s) to remove (hold ctrl while clicking to make multiple selections) and click the ok button when ready. NOTE: An account can only be removed if it's balance is $0.00, if not please transfer the balance to another existing account and try again to remove it.



<br><br>



Account Ledger

At the bottom of the table is a yellow row for inputting a new transaction. The only required field is the amount, enter a positive (money in) or negative (money out) and the posting will set it appropriately as a debit or credit based on the account type.

(Optional) enter a date for the transaction (the default is current date)

(Optional) a human readable id for the transaction (the transfer account for balancing will receive the same ID) --the computer keeps a different id in the background for the transactions

(Optional) a short description of the transaction

(Optional) choose another account to transfer the balance to (if no account is selected there will be an imbalance account created to accept the balance). A debit in the posted transaction will automatically apply as a credit in the transfer and vice-versa. The transfer account will also receive all other fields automatically.

(Optional) check the box in the reconcile column if necessary (traditionally this column was marked after the fact once a check had cleared, but I don't think this applies much in our modern instant-banking world, I chose to leave it in case a user has another purpose in mind for a yes/no value: I personally use it checked for work expenses that may be reimbursed and unchecked for regular expenses).

(Required) enter a positive amount for money coming into the account and a negative amount for money leaving the account, the software will handle balancing the books.

click the add button (leftmost button on the right side of the input row) to post the transaction

click the clear button (center button on the right side of the input row) to reset the input fields and place focus back on the date input field

To edit a transaction that has already been posted right click on it and an edit transaction dialog box will open, make the changes and click the okay button, or cancel out of the dialog to keep the transaction as it was.

To delete a transaction select it from the account ledger and click the delete button (rightmost button on the right side of the input row)



<br><br>



Create Account Categories

To create a new category for accounts to be organized in choose a type (Assets, Liabilities, or Equity) from the drop down menu and give it a meaningful name




<br><br>


Create Account

To create a new account choose a type (Assets, Liabilities, or Equity) of account and parent category if applicable for it from the drop down menus

Give the account a useful nickname to identify it in the books: the account nickname and balance will show anywhere it is listed

Use the "Normal" radio buttons to select whether this is to be a normal debit (increases on left column of ledger) or a normal credit (increases on right column of ledger) account

Fill in the text fields for account number, routing number, account code, and account description as needed

Click ok button to create the new account



<br><br>



Modify Order of Categories/Accounts
   
Traditionally accounts are listed by order of liquidity (how fast it can be turned into cash) but the software will initially order categories and accounts in the order created so it may be necessary to adjust the order.

To modify the display order on the balance sheet right click in the white space for one of the main type trees, and select the "Modify <TYPE> Accounts Order" option. This will open the Modify Account Order Dialog which displays lists populated with all available options. 

Select the appropriate radio button for modifying either categories or accounts, then select items from the list one at a time and use the up or down buttons to relocate them to the desired location

NOTE: An account can only be moved up or down among the category it is organized in, and only the selected type of accounts will be shown so the buttons may need clicked more than once to see the change. For example: if you are looking at the list of asset accounts, but internally there are two liability accounts created/listed between the two asset accounts displayed in the list, then the up or down button would need clicked three times before the change will be seen.

   
   
   <br><br>
   
   
Edit Account Details

Select [Accounts > Edit Account Details > (choose account)] in the main menu to edit any details about the account itself



<br><br>


Change Category Account is organized in

I'm still working on a graceful way to do this, so unfortunately it currently involves manually creating a new account, transferring all transactions to the new account via the edit dialog, and then removing the original account 
    


<br><br>


Remove Categories/Accounts

To remove an unwanted account or category from one of the three main type trees right click in the tree's white space to pull up a context menu and select Remove <TYPE> Accounts. This will open the Remove Account Dialog which displays lists populated with all available options. 

Select the appropriate radio button for removing either a category or an account (more than one can be selected at a time by holding the ctrl key) and click the Remove Accounts Button

NOTE: An account cannot be deleted if it has a non-zero balance and a category cannot be deleted if it contains accounts, so zero out or transfer any balances before removing an account and then remove any accounts before removing a category 

<br><br>
   
   
TOOLS:
   
   
General Ledger

A listing of all transactions with search and filter functionality. NOTE: there is no editing on the general ledger only filters to find what needs to be edited, a general journal for making/editing entries is on the plan to be included eventually
   
Select [Tools > General Ledger] to add the general ledger tab to the main content area

The ledger will automatically fill with all available transactions

select the date or date range to see transactions from and click "Apply Filters" button to remove all but the desired transactions

enter characters expected to be in a description in the description filter text field and click the "Apply Filters" button to remove all but the transactions which contain the entered characters in the order they are entered (it will find partial matches so the filter does not require the full description)

Click the "Clear Filters" button to reset the filter inputs and reload the general ledger with all transactions
   
Hover your mouse over the label for the Debit or Credit columns to see some statistics about the currently displayed transactions before or after applying filters.

   
   <br><br>

Revenue Splitter

The Revenue Splitter is for dividing a payment up between multiple accounts based on percentages: such as in someone's personal accounts 2/3 of a paycheck goes to checking 15% to savings and the remainder goes to petty cash

Select [Tools > Revenue Splitter] to add the Revenue Splitter Tab to the main content area

Choose the accounts you want to divide the payment between

In the "Split Amount" text field enter the amount to be split between the selected accounts

Use the available controls to determine percentage for each account, and if the account's amount needs excluded from the displayed total, since most money comes in as direct deposit these days a checkbox to exclude the initial one from the total to see how much transfers out of it (for those times when the direct deposit goes to an account with a limited number of monthly transfers --typical savings-- and money needs moved in bulk to an account with unlimited transfers --typical checking-- to shuffle it all around)

The amount that is to be moved will display in the non-editable "Moving Amount" text field

The amount field for each control item will display the amount to transfer to each of the selected accounts

The non-editable Moving Amount text field displays the total which takes the "Exclude From Total" checkbox into consideration

If the percentage the Split Amount is being divided into exceeds 100% the amount fields will turn red and indicate the percentage is too high

If this will be a regular division of income the revenue split can be saved, simply give it a name, or leave the name blank and it will show the date the split was saved on, and select the "Save Split" button. CRITICAL NOTE: the splitter uses references to accounts it does not create a different/copy-of account to work with so if the same account shows up in more than one saved split changes to one will affect the other as well as new splits using the same account, changing this will destroy the fairly efficient memory management selected for this software so the suggested workaround if this will be a problem is to create a set of dummy/fake accounts just to get the numbers from the tool instead of working with the actual accounts in the book (it does not make the transfer this tool is only a calculator)

To reopen a saved split left-click on it in the Saved Splits List View

To remove a saved split right-click on it in the Saved Splits List View

To remove a split item from the current set of controls right-click on the item to be removed


<br><br>
   
   
Export


Selecting [File > Export > (export format)] will export the book data in several of the standard formats for data transfer and handling: xml, json, csv, and sql. The generated files are written into a directory structure that attempts to preserve the organization of the book, and the top level folder here will get the name assigned in the book details options rather than the filename.

   
   <br><br>



Keyboard Shortcuts

    File Operations
        ctrl+n  :   create new book
        ctrl+o  :   open existing book
        ctrl+s  :   save current book
        ctrl+shift+s    :   save as... current book


    Account Operations
        ctrl+alt+c  :   new accounts category
        ctrl+alt+a  :   new account
        ctrl+alt+m :   modify category/account order
        ctrl+alt+r  :   remove category/account
   
   
   
    Tool Operations
        alt+shift+g :   general ledger
        alt+shift+r :   revenue splitter



    Help Operations
        F1  :   open user manual    



<br><br>


<br><br><br>


<h2>Project Progress</h2>

I've got the stuff I wanted for keeping my own books ready here, however, I have little doubt that over time there will be new tools and features work their way in. Below are some images captured while making the software to get a feel of what it looks like.


   
The main User-Interface is designed to mimick the appearance of a balance sheet so that the summary of accounts is always readily available. Clicking on an account listed on the balance sheet opens the account's ledger where transactions can be entered and edited.


![pic_for_github_main](https://user-images.githubusercontent.com/50467171/121451427-91384600-c96b-11eb-9566-216a5d2530f3.jpg)



![pic_for_github_ledger](https://user-images.githubusercontent.com/50467171/120670694-aa6a5f80-c45e-11eb-83b6-bad7086c3dc9.jpg)

![pic_for_github_ledger_1](https://user-images.githubusercontent.com/50467171/120670693-aa6a5f80-c45e-11eb-91be-0a26e592990c.jpg)

![pic_for_github_ledger_2](https://user-images.githubusercontent.com/50467171/120670689-a9d1c900-c45e-11eb-98b6-003577c32bee.jpg)


![pic_for_github_details](https://user-images.githubusercontent.com/50467171/120672257-23b68200-c460-11eb-92a4-d2097b06e232.jpg)




If the transfer spot on an account is left blank (the books don't balance) then it will automatically create a liability account to handle the imbalance, so the accounts should remain balanced at all times.


![pic_for_github_imbalance](https://user-images.githubusercontent.com/50467171/120943709-a197b900-c6fe-11eb-9047-c8c612bff27c.jpg)



There is a context menu when right clicking the white space on the balance sheet view port that has options to create a new account or to remove an account for that particular category of accounts. Like most things in this software it is handled via dialog boxes (see images below), however, an ACCOUNT CANNOT BE DELETED WITH A NONZERO BALANCE, so all funds must be transferred to another account before removing one, a warning dialog will pop up if you try to remove an account that has a nonzero balance.


![pic_for_github_newAccount](https://user-images.githubusercontent.com/50467171/120576078-3002f680-c3f0-11eb-8cfa-691a1b3fdbbd.jpg)

![pic_for_github_removeAccount](https://user-images.githubusercontent.com/50467171/120943716-aceae480-c6fe-11eb-95dc-d942f86b669e.jpg)



right clicking on a transaction entry in one of the ledgers opens that for editing

![pic_for_github_edit_entry](https://user-images.githubusercontent.com/50467171/120716224-ba506680-c493-11eb-8e84-9204faecde0d.jpg)

   
 The first couple tools seem to be ready and made it through the rudimentary testing, a revenue splitter to divide an amount between an assortment of accounts based on percentages and a general ledger with date and description filters (no editing on the general ledger, only filtering viewing).
   
![pics_for_github_revenueSplit](https://user-images.githubusercontent.com/50467171/121172494-c5095380-c825-11eb-8e3a-e998fceb9437.jpg)
   
![pic_for_github_generalLedger](https://user-images.githubusercontent.com/50467171/121451429-91384600-c96b-11eb-8fd7-01fd522d42d6.jpg)
 
<br><br>

   
   
I've got the basic structure and first couple tools wanted for keeping my own books here, so I'm calling it version 1 now and the first round of installers for windows, mac, and linux machines are ready, after a week or so of thorough testing I'll click the button to actually make it an official release.


   
   


