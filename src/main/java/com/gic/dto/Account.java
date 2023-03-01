package com.gic.dto;

import com.gic.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Account {
    public static int nextAvailableAccountNumber;
    int accountNumber;
    double availableBalance;
    ArrayList<Transaction> transactions;

    // constructor to create account object
    public Account(int accountNumber, double availableBalance, ArrayList<Transaction> transactions) {
        this.accountNumber = accountNumber;
        this.availableBalance = availableBalance;
        this.transactions = transactions;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account Number: " +
                accountNumber +
                "\nAvailable Balance: " +
                availableBalance;
    }

    // print the account statement
    public void printStatement() {
        System.out.println(this.toString());
        System.out.printf("%n %-25s %5s %20s %5s %20s %n ", "Date", "|", "Amount", "|", "Balance");
        for (Transaction transaction : transactions) {
            System.out.printf("%-25s %5s %20s %5s %20s %n ", new SimpleDateFormat("dd MMM yyyy hh:mm:ss aa").format(new Date(transaction.getDateTime())), "|", transaction.getAmount(), "|", transaction.getBalance());
        }
        System.out.println(Constants.LINE_BREAK_PHRASE);
    }
}
