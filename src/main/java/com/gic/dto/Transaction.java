package com.gic.dto;

public class Transaction {
    private long dateTime;
    private double amount;
    private double balance;

    // constructor to create transaction object
    public Transaction(long dateTime, double amount, double balance) {
        this.dateTime = dateTime;
        this.amount = amount;
        this.balance = balance;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
