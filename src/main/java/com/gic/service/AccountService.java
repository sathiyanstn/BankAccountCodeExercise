package com.gic.service;

import com.gic.dto.Account;
import com.gic.dto.Transaction;
import com.gic.utils.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountService {
    public Scanner in = new Scanner(System.in);
    private AccountDataService accountDataService = new AccountDataService();

    public Account loginSignUpAccount() {
        System.out.println(
                Constants.INITIAL_QUESTION_PHRASE +
                        "\n\t[L]ogin\n\t" +
                        "[S]ign Up\n\t" +
                        "[Q]uit"
        );
        Account account = null;
        try {
            // take user input to proceed login / sign up actions
            String loginSignUpMethod = in.next();

            accountDataService.readAccountData();
            ArrayList<Account> accountsArray = AccountDataService.accountsArray;

            if (loginSignUpMethod.equalsIgnoreCase("Q")) {
                // Q : Quit

                // Quit and exit the application
                accountDataService.quit();
            } else if (loginSignUpMethod.equalsIgnoreCase("Stop")) {
                // Stop : Stop the application with out exit used for test purpose only

                accountDataService.quitWithoutSystemExit();
            } else if (loginSignUpMethod.equalsIgnoreCase("L")) {
                // L : Login

                System.out.print("Enter your account number: ");

                // take user input to login into that account
                int accountNumber = in.nextInt();
                accountDataService.readAccountData();
                accountsArray = AccountDataService.accountsArray;

                // validate the account number and if not exist throw IllegalArgumentException
                account = getAccount(accountNumber, accountsArray);
            } else if (loginSignUpMethod.equalsIgnoreCase("S")) {
                // S : Sign Up

                // Create new account using Account static next available account number
                account = createAccount(accountsArray);
                System.out.println(
                        "Your account has been created successfully!\n\t" +
                                "Your Account Number: " +
                                account.getAccountNumber());
            } else {
                // If user entered invalid  inputs

                System.out.println(Constants.INVALID_ENTRY_PHRASE);

                // Recursive
                return loginSignUpAccount();
            }
        } catch (InputMismatchException e) {
            // If user entered invalid  inputs
            System.out.println(Constants.INVALID_ENTRY_PHRASE);

            // Recursive
            return loginSignUpAccount();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

            // Recursive
            return loginSignUpAccount();
        }

        // Always return account number
        return account;
    }

    public synchronized Account createAccount(ArrayList<Account> accountsArray) {
        // take initial transaction as amount 0 and balance 0
        Transaction initialTransaction = new Transaction(new Date().getTime(), 0, 0);
        ArrayList<Transaction> initialTransactionsArray = new ArrayList<Transaction>();
        initialTransactionsArray.add(initialTransaction);

        // Create new account with the Account static next available account number and the initial transaction array
        Account newAccount = new Account(Account.nextAvailableAccountNumber, 0, initialTransactionsArray);
        accountsArray.add(newAccount);

        // write the data into the json file
        accountDataService.updateAccountData();

        // return created account and take next available account number and set to the Account static variable
        Account.nextAvailableAccountNumber = Account.nextAvailableAccountNumber + 1;
        return newAccount;
    }

    public void makeTransaction(Account account) {
        accountDataService.readAccountData();
        account = getAccount(account.getAccountNumber(), AccountDataService.accountsArray);
        System.out.println(
                "\t[D]eposit\n\t" +
                        "[W]ithdraw\n\t" +
                        "[P]rint statement\n\t" +
                        "[Q]uit"
        );
        double amount;
        try {
            // take user input to proceed the transactions
            String transactionMethod = in.next();

            if (transactionMethod.equalsIgnoreCase("Q")) {
                // Q : Quit

                // Quit and exit the application
                accountDataService.quit();
            } else if (transactionMethod.equalsIgnoreCase("Stop")) {
                // Stop : Stop the application with out exit used for test purpose only

                accountDataService.quitWithoutSystemExit();
            } else if (transactionMethod.equalsIgnoreCase("D")) {
                // D : Deposit

                System.out.print("Please enter the amount to deposit: ");

                // take user amount
                amount = in.nextDouble();

                // validate the value
                if (amount > 0) {
                    // do the action
                    depositWithdraw(account, amount);
                } else {
                    System.out.println(Constants.INVALID_ENTRY_PHRASE);
                }
                System.out.println(Constants.SECONDARY_QUESTION_PHRASE);

                // Recursive
                makeTransaction(account);
            } else if (transactionMethod.equalsIgnoreCase("W")) {
                // W : Withdraw

                System.out.print("Please enter the amount to withdraw: ");

                // take user amount
                amount = in.nextDouble();

                // validate the value
                if (amount > 0) {
                    // do the action
                    depositWithdraw(account, amount * -1);
                } else {
                    System.out.println(Constants.INVALID_ENTRY_PHRASE);
                }
                System.out.println(Constants.SECONDARY_QUESTION_PHRASE);

                // Recursive
                makeTransaction(account);
            } else if (transactionMethod.equalsIgnoreCase("P")) {
                // P : Print Statement

                // print Account transaction data using account number, if account not exist throw IllegalArgumentException and exit
                account.printStatement();
                System.out.println(Constants.SECONDARY_QUESTION_PHRASE);

                // Recursive
                makeTransaction(account);
            } else {
                // If user entered invalid  inputs
                System.out.println(Constants.INVALID_ENTRY_PHRASE + "\n" + Constants.SECONDARY_QUESTION_PHRASE);

                // Recursive
                makeTransaction(account);
            }
        } catch (InputMismatchException e) {
            // If user entered invalid  inputs
            System.out.println(Constants.INVALID_ENTRY_PHRASE + "\n" + Constants.SECONDARY_QUESTION_PHRASE);

            // Recursive
            makeTransaction(account);
        } catch (IllegalArgumentException e) {
            // If account not exist when we try to print the statement
            System.out.println(e.getMessage());

            // quit and exit the application
            accountDataService.quit();
        }
    }

    public Account getAccount(int accountNumber, ArrayList<Account> accountsArray) {
        // Loop in accounts array
        for (Account acc : accountsArray) {
            if (acc.getAccountNumber() == accountNumber) {
                return acc;
            }
        }

        // If no account matched with the account number throw IllegalArgumentException
        throw new IllegalArgumentException("Account Not Found");
    }

    public synchronized void depositWithdraw(Account account, double amount) {
        // take account balance
        double balance = account.getAvailableBalance() + amount;
        if (balance < 0) {
            // Only If user tried to withdraw more than balance, throw IllegalArgumentException
            throw new InputMismatchException("Insufficient Balance");
        }

        // create new transaction with current date time to maintain history
        Transaction transaction = new Transaction(new Date().getTime(), amount, balance);
        account.setAvailableBalance(balance);
        account.getTransactions().add(transaction);

        // write the updated accounts array into json
        accountDataService.updateAccountData();

        System.out.println(
                "Thank you. $" +
                        (amount > 0 ?
                                new BigDecimal(String.valueOf(amount)).setScale(2, BigDecimal.ROUND_FLOOR) :
                                new BigDecimal(String.valueOf(amount * -1)).setScale(2, BigDecimal.ROUND_FLOOR)) +
                        " has been " +
                        (amount > 0 ?
                                "deposited to your account." :
                                "withdrawn."));
        System.out.println(Constants.LINE_BREAK_PHRASE);
    }
}
