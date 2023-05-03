package org.epde.oop.polymorphism;

public class CurrentAccount extends Bank {

    // Override method from Bank
    @Override
    public double getInterestRate() {
        return 0.0;
    }
}
