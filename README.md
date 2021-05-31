# ![FundsIcon_small](https://user-images.githubusercontent.com/50467171/120141218-12445000-c1aa-11eb-853c-358a5b205e33.png) Funds
An upcoming project I've been thinking about for a while, some accounting software, it will be a while before it's ready though... Uhh, really all I have in mind starting out here is a logo and idea of how I wanted the ledger dialogs to look... and obviously years of rattling around in the back of my mind features i want or don't want included...

![pic_for_github_ledger](https://user-images.githubusercontent.com/50467171/120141050-bbd71180-c1a9-11eb-8e6b-043e49299d98.jpg)


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



