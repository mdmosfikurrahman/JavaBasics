package org.epde.oop.polymorphism;

public class SavingsAccount extends Bank{

    // Override method from Bank
    @Override
    public double getInterestRate() {
        return 0.05;
    }
}
