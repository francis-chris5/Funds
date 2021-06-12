CREATE DATABASE IF NOT EXISTS testing987_DB;
USE testing987_DB;



CREATE TABLE IF NOT EXISTS Accounts_Table(accountID INT PRIMARY KEY AUTO_INCREMENT, category VARCHAR(255), name VARCHAR(255), number VARCHAR(16), routing VARCHAR(16), code VARCHAR(255), description VARCHAR(255), type VARCHAR(16), normalDebit BOOLEAN);

INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Liability", "IMBALANCE", "null", "null", "null", "null", "null", false);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Current Assets", "Cash", "null", "null", "null", "null", "null", true);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Current Assets", "Accounts Receivable", "null", "null", "null", "null", "null", true);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Long Term Assets", "Property, Plant, and Equipment", "", "", "", "", "Asset", true);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Long Term Assets", "Real Estate", "null", "null", "null", "null", "null", true);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Current Liabilities", "Credit Card", "null", "null", "null", "null", "null", false);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Current Liabilities", "Accounts Payable", "null", "null", "null", "null", "null", false);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Long Term Liabilities", "Notes Payable", "null", "null", "null", "null", "null", false);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Retained Earnings", "Initial Capital", "null", "null", "null", "null", "null", false);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Retained Earnings", "Revenue", "null", "null", "null", "null", "null", false);
INSERT INTO Accounts_TABLE(category, name, number, routing, code, description, type, normalDebit) VALUES("Retained Earnings", "Expense", "null", "null", "null", "null", "null", true);



CREATE TABLE IF NOT EXISTS IMBALANCE_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO IMBALANCE_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(1, "2021-06-11", "", "sally", "Accounts Receivable", false, 0.0, 15.0, 3);
INSERT INTO IMBALANCE_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(1, "2021-06-11", "", "bcky", "Accounts Payable", false, 7.0, 0.0, 4);



CREATE TABLE IF NOT EXISTS Cash_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Cash_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(2, "2021-06-11", "", "open", "Initial Capital", false, 123.0, 0.0, 0);
INSERT INTO Cash_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(2, "2021-06-11", "", "buy sandals", "Expense", false, 0.0, 19.0, 2);



CREATE TABLE IF NOT EXISTS Accounts_Receivable_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Accounts_Receivable_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(3, "2021-06-11", "", "sally", "IMBALANCE", false, 15.0, 0.0, 3);



CREATE TABLE IF NOT EXISTS Property_Plant_and_Equipment_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);




CREATE TABLE IF NOT EXISTS Real_Estate_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);




CREATE TABLE IF NOT EXISTS Credit_Card_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Credit_Card_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(6, "2021-06-11", "", "mcdonalds", "Expense", false, 0.0, 8.0, 1);



CREATE TABLE IF NOT EXISTS Accounts_Payable_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Accounts_Payable_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(7, "2021-06-11", "", "bcky", "IMBALANCE", false, 0.0, 7.0, 4);



CREATE TABLE IF NOT EXISTS Notes_Payable_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);




CREATE TABLE IF NOT EXISTS Initial_Capital_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Initial_Capital_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(9, "2021-06-11", "", "open", "Cash", false, 0.0, 123.0, 0);



CREATE TABLE IF NOT EXISTS Revenue_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);




CREATE TABLE IF NOT EXISTS Expense_Table(accountTransactionID INT PRIMARY KEY AUTO_INCREMENT, accountID INT, date DATE, transactionID VARCHAR(16), description VARCHAR(255), transfer VARCHAR(255), reconcile BOOLEAN, debit FLOAT, credit FLOAT, ledgerID int);

INSERT INTO Expense_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(11, "2021-06-11", "", "mcdonalds", "Credit Card", false, 8.0, 0.0, 1);
INSERT INTO Expense_Table(accountID, date, transactionID, description, transfer, reconcile, debit, credit, ledgerID) VALUES(11, "2021-06-11", "", "buy sandals", "Cash", false, 19.0, 0.0, 2);
