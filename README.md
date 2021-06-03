# ![FundsIcon_small](https://user-images.githubusercontent.com/50467171/120141218-12445000-c1aa-11eb-853c-358a5b205e33.png) Funds

An upcoming project I've been thinking about for a while, some accounting software, it will be a while before it's ready though... Uhh, really all I have starting out the repository here on day one is a logo and idea of how I wanted the ledger dialogs to look... and obviously years of rattling around in the back of my mind features i want or don't want included...

Since this is intended to be an open-source project other developers can find the javadoc pages here https://francis-chris5.github.io/Funds/ to help find your way around the code when customizing it to fit your needs.

Much like the project management software I've been working on the past couple weeks verses what I have been using for a while, the accounting software I've been using doesn't exactly fit my needs: only a few features actually get used and it those in conjunction with a spreadsheet as it doesn't have a good place for general calculations, a couple other thoughts i've had for accounting stuff too. Oh yeah, it also seemed a great place to give the project management thing I just finished the prototype version 1 of a thorough test run (that's the file in the design folder)


So the idea here is some accounting software to sort of accompany the Routines application to perhaps build to a management software suite someday...

A small accounting application, more for tracking a project (go with Routines) than for running an entire business (at least to start with)

    double entry method
    
    traditional ledger appearance for entries (per account and general journal)
    
    main interface is the current balance sheet in two column format, account summaries are buttons that launch dialogs

    balance sheet, cash flows, and a few other standard accounting reports

    a number of charts and graphs tracking the flow of a projects budget as time progresses, control charts showing some common accounting ratios as time progresses
        (maybe some projection things to compare to)
        
    a small spreadsheet built right in somehow for calculations


*********************************************
Trying to pick a local relational database for this, so far I've looked at:


sqlite-jdbc 3.34.0 for relational database
get from https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.34.0/sqlite-jdbc-3.34.0.jar 


apache derby 10.14.2.0
get from https://downloads.apache.org//db/derby/db-derby-10.14.2.0/db-derby-10.14.2.0-bin.zip  


The relational dataabase is mostly to make it compatible with other accounting software, and I've been keeping that in mind since the initial mockup of my objects, so no hurry on that part though.





<h2>Project Progress</h2>

I've got the ledger dialogs set up, and a start on the main gui. The first tab in the main content section of the GUI looks like a balance sheet (or it will when I get around to styling it), clicking on an account opens it in a ledger dialog to enter/edit/remove transactions. Total debits and credits are calculated correctly, but I don't have a good spot to call that recalculation yet (I've been opening and closing the Book details dialog to force a refresh --oh yeah, that works too). For testing I set up a cash, credit card, revenue, and expense account, but once I saw them on the screen I thought this should be the default new book accounts -so I'll probably leave those here.

I still haven't figured out how to convince the Scenebuilder software that I really am using javafx version 11, not 16, so those warning messages will keep showing up until I'm finished with the GUI's and remove the version checks it's determined to put in the code.

Also note that these images were not all taken on the same test run, I'll do a image pass to update them together soon.




![pic_for_github_main](https://user-images.githubusercontent.com/50467171/120409284-9881a300-c31e-11eb-9a39-bec475879e54.jpg)


![pic_for_github_ledger](https://user-images.githubusercontent.com/50467171/120425430-2fa92380-c33c-11eb-9738-655d2527ded1.jpg)


![pic_for_github_details](https://user-images.githubusercontent.com/50467171/120410259-a20c0a80-c320-11eb-9f4a-f3b9d08be6f4.jpg)




If the transfer spot on an account is left blank (the books don't balance) then it will automatically create a liability account to handle the imbalance, so the accounts should remain balanced at all times.


![pic_for_github_main_2](https://user-images.githubusercontent.com/50467171/120424363-2f0f8d80-c33a-11eb-80db-d0c760c661dd.jpg)

![pic_for_github_imbalance](https://user-images.githubusercontent.com/50467171/120424362-2f0f8d80-c33a-11eb-898f-c502577bca51.jpg)


There is a context menu when right clicking the white space on the balance sheet view port that has options to create a new account or to remove an account for that particular category of accounts. Like most things in this software it is handled via dialog boxes (see images below), however, an ACCOUNT CANNOT BE DELETED WITH A NONZERO BALANCE, so all funds must be transferred to another account before removing one, a warning dialog will pop up if you try to remove an account that has a nonzero balance.


![pic_for_github_newAccount](https://user-images.githubusercontent.com/50467171/120576078-3002f680-c3f0-11eb-8cfa-691a1b3fdbbd.jpg)

![pic_for_github_removeAccount](https://user-images.githubusercontent.com/50467171/120576589-08f8f480-c3f1-11eb-987b-63308aca9cfe.jpg)


It's a start, still a long way to go...
