package org.epde.oop.polymorphism;

public class Main {

    public static void main(String[] args) {
        Calculation calc = new Calculation();
        int sum1 = calc.add(5, 10); // calls add(int, int)
        int sum2 = calc.add(5, 10, 15); // calls add(int, int, int)
        double sum3 = calc.add(3.14, 2.71); // calls add(double, double)

        System.out.println("sum1 = " + sum1);
        System.out.println("sum2 = " + sum2);
        System.out.println("sum3 = " + sum3);

        Bank savingsAccount = new SavingsAccount();
        Bank currentAccount = new CurrentAccount();
        double rate1 = savingsAccount.getInterestRate(); // calls getInterestRate() in SavingsAccount
        double rate2 = currentAccount.getInterestRate(); // calls getInterestRate() in CurrentAccount

        System.out.println("Interest rate for savings account: " + rate1);
        System.out.println("Interest rate for current account: " + rate2);
    }

}
