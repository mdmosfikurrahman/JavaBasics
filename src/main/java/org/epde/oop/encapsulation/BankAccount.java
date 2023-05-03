package org.epde.oop.encapsulation;

public class BankAccount {
    // Declare private instance variables for account number and balance
    private final int accountNumber;
    private double balance;

    // Constructor that takes an account number and initial balance
    public BankAccount(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getter method for account number
    public int getAccountNumber() {
        return accountNumber;
    }

    // Getter method for balance
    public double getBalance() {
        return balance;
    }

    // Method for depositing funds into the account
    public double deposit(double amount) {
        return balance += amount;
    }

    // Method for withdrawing funds from the account
    public double withdraw(double amount) {
        return balance -= amount;
    }
}

