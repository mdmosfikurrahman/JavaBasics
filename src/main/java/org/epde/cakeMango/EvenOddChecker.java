package org.epde.cakeMango;

public class EvenOddChecker extends NumberProcessor implements NumberChecker {


    @Override
    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    @Override
    public void processNumber(int number) throws CustomInvalidException {
        if (number < 0) {
            throw new CustomInvalidException("Bhai Thik number daw");
        }

        boolean isEven = isEven(number);

        NumberInfo numberInfo = new NumberInfo(number, isEven);

        int numberOfNumberInfo = numberInfo.getNumber();
        System.out.println("The Number is " + numberOfNumberInfo);

        boolean isEvenNumberInfo = numberInfo.isEven();
        System.out.println("The Number is " + (isEvenNumberInfo ? "Even" : "Odd") + ". Cause isEven is" + isEvenNumberInfo);

        System.out.println("The Number is " + numberInfo.getNumber() + " & Status is " + (numberInfo.isEven() ? "Even" : "Odd"));
    }
}
