# ![FundsIcon_small](https://user-images.githubusercontent.com/50467171/120141218-12445000-c1aa-11eb-853c-358a5b205e33.png) Funds

<h2>About</h2>

<h4>Summary</h4>

After having a laptop stolen a few years back I switched tracking my personal finances from accounting software to a customized spreadsheet. When time came for a new laptop again I decided to go back to accounting software, however, the accounting software I've been using personally doesn't exactly fit my needs: it's designed for a large corporation which means there is way more there than I need, small input issues -I am constantly editing transactions by accident so trying to make that less likely to happen without making it difficult to edit transactions, I was also thinking that the standard accounting statements should be the primary appearance of the GUI not an afterthought to an overly fancy interface then tacked on and hidden a couple menus deep.

I don't want a general purpose spreadsheet modified to fit my needs, I want to use specific purpose software set up to automate double entry accounting, but I don't want one of those massive conglomerations of tools that I'll never use and find myself getting lost in the menus for. So that's what I tried to develop here: simplicity is the name of the game... Essentially it's just a balance sheet for an overview of the accounts and a ledger to enter and edit transactions. In the background the software handles the standard double-entry method balancing of accounts while the data is stored in nested collections of Linked Lists rather than a Relational Database, and just the small number of tools I find myself needing.



<h4>Design Details:</h4>

It's open source software so if you are interested in customizing some accounting software to fit your exact needs the simplistic idea I was going for here may make an ideal starting point.

PLATFORM: 

Coded in Java with Graphical User interface in JavaFX so that the same code can readily be used in desktop, mobile, and web scenarios. 

Find the source-code here: https://github.com/francis-chris5/Funds 

Find the javadocs here: https://francis-chris5.github.io/Funds/javadoc/Funds/package-summary.html 

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

(Optional) an id for the transaction (the transfer account for balancing will receive the same ID)

(Optional) a short description of the transaction

(Optional) choose another account to transfer the balance to (if no account is selected there will be an imbalance account created to accept the balance). A debit in the posted transaction will automatically apply as a credit in the transfer and vice-versa. The transfer account will also receive all other fields automatically.

(Optional) check the box in the reconcile column if necessary (traditionally this column was marked after the fact once a check had cleared, but I don't think this applies much in our modern instant-banking world, I chose to leave it in case a user has another purpose in mind for a yes/no value: I personally use it checked for work expenses that may be reimbursed and unchecked for regular expenses).

(Required) enter a positive amount for money coming into the account and a negative amount for money leaving the account, the software will handle balancing the books.

click the add button (leftmost button on the right side of the input row) to post the transaction

click the clear button (center button on the right side of the input row) to reset the input fields and place focus back on the date input field

To edit a transaction that has already been posted right click on it and an edit transaction dialog box will open, make the changes and click the okay button, or cancel out of the dialog to keep the transaction as it was.

To delete a transaction select it from the account ledger and click the delete button (rightmost button on the right side of the input row) --eventually this will delete the balance transfer as well



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


Change Category Account is organized in

I'm still working on a graceful way to do this, so unfortunately it currently involves manually creating a new account, transferring all transactions to the new account via the edit dialog, and then removing the original account 
    


<br><br>


Remove Categories/Accounts

To remove an unwanted account or category from one of the three main type trees right click in the tree's white space to pull up a context menu and select Remove <TYPE> Accounts. This will open the Remove Account Dialog which displays lists populated with all available options. 

Select the appropriate radio button for removing either a category or an account (more than one can be selected at a time by holding the ctrl key) and click the Remove Accounts Button

NOTE: An account cannot be deleted if it has a non-zero balance and a category cannot be deleted if it contains accounts, so zero out or transfer any balances before removing an account and then remove any accounts before removing a category 

<br><br>
   
   
TOOLS:

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

If this will be a regular division of income the revenue split can be saved, simply give it a name, or leave the name blank and it will show the date the split was saved on, and select the "Save Split" button

To reopen a saved split left-click on it in the Saved Splits List View

To remove a saved split right-click on it in the Saved Splits List View

** I hope I explained the purpose of this correctly, if not, for example: my salary paycheck goes to my general savings account, 2/3 of it goes to living funds (checking), 11% to vehicle fund (saving), 9% to home fund (savings), 4% to stock brokerage account, and only the remainder stays in the general savings, 4 transfers with each check, happens every other week, which exceeds the monthly limit on transfers in the account the paycheck is deposited to, so it all needs transferred to checking to begin with except the amount left behind which still needs listed out with these numbers...



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



    Help Operations
        F1  :   open user manual    



<br><br>


<br><br><br>


<h2>Project Progress</h2>

I've got the ledger dialogs set up, and a start on the main gui. The first tab in the main content section of the GUI looks like a balance sheet (or it will when I get around to styling it), clicking on an account opens it in a ledger dialog to enter/edit/remove transactions. Total debits and credits are calculated correctly when transactions are entered and when transactions are edited (still an issue with the delete transaction button throwing the balance way off...)

Finally got around to working in a couple menu operations, so can save and reopen files now along with a couple template options for new, and the rough drafts of a user manual and about file are available in a dialog launched from the help menu.

Got subcategories going to organize the accounts better instead of throwing them all right in one of the three main types. Not updating the pictures here yet since there's nothing significant to see: the trees have more entries with arrows beside them... 

In general everything seems to be moving along nicely with this, certianly still have a lot to do just to get the basics that should be required in accounting software... but creating, organizing, and removing accounts; filling in, editing, or removing items on the ledgers for those accounts; and automated double entry to balance out the books is working as intended at this early stage of the project.




![pic_for_github_main](https://user-images.githubusercontent.com/50467171/120943668-5b425a00-c6fe-11eb-920f-65a9517ce138.jpg)



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

   
 The first of the tools is well underway and appears to be working
   
![pics_for_github_revenueSplit](https://user-images.githubusercontent.com/50467171/121110003-931ecf80-c7da-11eb-95a8-b01f90e76e8a.jpg)
 
<br><br>
It's a start, still a long way to go... though I am looking for something rather simplistic here, so it may be ready to call version 1 in just a couple more days...



