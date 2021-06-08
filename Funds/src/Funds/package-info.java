/**
 * The .java and .fxml files relating to the primary Graphical User Interface for this application.
 * 
 * <h3>Description</h3>
 * <p>Finally getting around to making the accounting software that's been rattling around in the back of my mind for years. Sort of a small scale thing for personal use or single projects rather than a company-wide conglomeration it's easy to get lost in. Creating it to coincide with an open source project management thing that just hit the first release to perhaps be part of a suite of management software someday.</p>
 * <p>The application is being constructed with Java and JavaFX to ease the replication into web or mobile versions of the app.</p>
 * 
 * <h4>STRUCTURE:</h4>
 * <ul>
 * <li>a Book is what users will open/save/work-with...</li>
 * <li>a Book is a collection of Accounts</li>
 * <li>an Account is a collection of Transactions</li>
 * <li>a Transaction is a collection of datafields describing a real world exchange of currency/equity/other-items-of-value</li>
 * </ul>
 * 
 * <p>entering a transaction will automatically enter it in the corresponding transfer account</p>
 * <p>if the transfer is left blank an liability account called "imbalance" will be created (if necessary) to fill the double entry role</p>
 * 
 * 
 * 
 * 
 * 
 * <h4>USER-INTERFACE:</h4>
 * <ul>
 * <li>The main GUI will consist of a tab-pane showing a number of standard accounting forms (balance sheet, cash-flows, general ledger, etc.) and clicking an account from the statement will open the ledger dialog for the account</li>
 * <li>all transactions must be entered or removed via the ledger</li>
 * <li>editing a transaction will be handled from a dialog launched from a right-click on the transaction (or other non-accidental method as I find myself accidently editing transactions in GNUCash all the time)</li>
 * </ul>
 * 
 * 
 * 
 * 
 * 
 * 
 * <h4>NOTES:</h4>
 * <ul>
 * <li>since debit/credit is a boolean I chose debit == true, credit == false for normals (i.e. which side of ledger positive/increase goes on)</li>
 * <li>a book will have the save/open features, .fabk --"Funds Accounting Book"</li>
 * </ul>
 * 
 * 
 * 
 * @author Chris Francis
 * @version 0.0.1
 */
package Funds;
