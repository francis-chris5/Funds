# ![FundsIcon_small](https://user-images.githubusercontent.com/50467171/120141218-12445000-c1aa-11eb-853c-358a5b205e33.png) Funds

An upcoming project I've been thinking about for a while, some accounting software, it will be a while before it's ready though... Uhh, really all I have starting out the repository here on day one is a logo and idea of how I wanted the ledger dialogs to look... and obviously years of rattling around in the back of my mind features i want or don't want included...


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

I've got the ledger dialogs set up, and a start on the main gui. The first tab in the main content section of the GUI looks like a balance sheet, clicking on an account opens it in a ledger dialog to enter/edit/remove transactions. Total debits and credits are calculated correctly, but I don't have a good spot to call that recalculation yet (I've been opening and closing the Book details dialog (oh yeah, that works too). For testing I set up a cash, credit card, revenue, and expense account, but once I saw them on the screen I thought this should be the default new book accounts -so I'll probably leave those here.

I still haven't figured out how to convince the Scenebuilder software that I really am using javafx version 11, not 16, so those warning messages will keep showing up until I'm finished with the GUI's and remove the version checks it's determined to put in the code.




![pic_for_github_main](https://user-images.githubusercontent.com/50467171/120409284-9881a300-c31e-11eb-9a39-bec475879e54.jpg)


![pic_for_github_ledger](https://user-images.githubusercontent.com/50467171/120409295-9d465700-c31e-11eb-9870-ef808b5b277e.jpg)


![pic_for_github_details](https://user-images.githubusercontent.com/50467171/120410259-a20c0a80-c320-11eb-9f4a-f3b9d08be6f4.jpg)




It's a start, still a long way to go... The next step is to tie all the accounts together so that each double entry only needs made once with a reference of where it transfers the balance to...
