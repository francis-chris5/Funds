CREATE DATABASE IF NOT EXISTS testing123_DB;
USE testing123_DB;



CREATE TABLE IF NOT EXISTS Accounts_Table(accountID INT PRIMARY KEY AUTO_INCREMENT, category VARCHAR(255), name VARCHAR(255), number VARCHAR(16), routing VARCHAR(16), code VARCHAR(255), description VARCHAR(255), type VARCHAR(16), normalDebit BOOLEAN);

INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Assets", "Cash", "null", "null", "null", "null", "null", true);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Assets", "Receivables", "", "", "", "", "Asset", true);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Liability", "Credit Card", "null", "null", "null", "null", "null", false);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Equity", "Revenue", "null", "null", "null", "null", "null", false);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Equity", "Expense", "null", "null", "null", "null", "null", true);



CREATE TABLE IF NOT EXISTS Cash_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Cash_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(1, "2021-06-01", "", "open", "Revenue", false, 123.0, 0.0, 0);
INSERT INTO Cash_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(1, "2021-06-03", "", "buy sandals", "Expense", false, 0.0, 19.0, 2);



CREATE TABLE IF NOT EXISTS Receivables_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Receivables_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(2, "2021-06-09", "", "sally", "Revenue", false, 15.0, 0.0, 3);



CREATE TABLE IF NOT EXISTS Credit_Card_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Credit_Card_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(3, "2021-06-05", "", "mcdonalds", "Expense", false, 0.0, 8.0, 1);



CREATE TABLE IF NOT EXISTS Revenue_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Revenue_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(4, "2021-06-01", "", "open", "Cash", false, 0.0, 123.0, 0);
INSERT INTO Revenue_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(4, "2021-06-09", "", "sally", "Receivables", false, 0.0, 15.0, 3);



CREATE TABLE IF NOT EXISTS Expense_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Expense_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(5, "2021-06-05", "", "mcdonalds", "Credit Card", false, 8.0, 0.0, 1);
INSERT INTO Expense_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(5, "2021-06-03", "", "buy sandals", "Cash", false, 19.0, 0.0, 2);
