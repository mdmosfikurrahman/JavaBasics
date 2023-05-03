package org.epde.oop.encapsulation;

public class Main {

    public static void main(String[] args) {
        // Create a new BankAccount object with an account number of 123456 and a balance of 1000.0
        BankAccount account = new BankAccount(123456, 1000.0);

        // Print out the account number and balance
        System.out.println("Account number: " + account.getAccountNumber());
        System.out.println("Balance: " + account.getBalance());

        // Deposit 500.0 into the account
        double newAmount = account.deposit(500.0);

        // Print out the balance after the deposit
        System.out.println("Balance after deposit: " + newAmount);

        // Withdraw 200.0 from the account
        newAmount = account.withdraw(200.0);

        // Print out the balance after the withdrawal
        System.out.println("Balance after withdrawal: " + newAmount);
    }
}
