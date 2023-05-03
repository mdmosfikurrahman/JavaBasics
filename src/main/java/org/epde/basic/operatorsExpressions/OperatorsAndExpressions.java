package org.epde.basic.operatorsExpressions;

public class OperatorsAndExpressions {

    public static void main(String[] args) {

        int x = 5;
        int y = 3;

        // Arithmetic operators
        int sum = x + y;
        int difference = x - y;
        int product = x * y;
        int quotient = x / y;
        int remainder = x % y;
        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);
        System.out.println("Product: " + product);
        System.out.println("Quotient: " + quotient);
        System.out.println("Remainder: " + remainder);

        // Assignment operators
        int z = 10;
        z += 5;
        System.out.println("Z: " + z);

        // Comparison operators
        boolean isEqual = x == y;
        boolean isNotEqual = x != y;
        boolean isLessThan = x < y;
        boolean isGreaterThan = x > y;
        boolean isLessThanOrEqual = x <= y;
        boolean isGreaterThanOrEqual = x >= y;
        System.out.println("Is equal: " + isEqual);
        System.out.println("Is not equal: " + isNotEqual);
        System.out.println("Is less than: " + isLessThan);
        System.out.println("Is greater than: " + isGreaterThan);
        System.out.println("Is less than or equal to: " + isLessThanOrEqual);
        System.out.println("Is greater than or equal to: " + isGreaterThanOrEqual);

        // Logical operators
        boolean andResult = (x > y) && (x < 10);
        boolean orResult = (x < y) || (y > 0);
        boolean notResult = !(x == y);
        System.out.println("AND result: " + andResult);
        System.out.println("OR result: " + orResult);
        System.out.println("NOT result: " + notResult);

        // Bitwise operators
        int a = 60;
        int b = 13;
        int bitwiseAnd = a & b;
        int bitwiseOr = a | b;
        int bitwiseXor = a ^ b;
        int bitwiseComplement = ~a;
        int leftShift = a << 2;
        int rightShift = a >> 2;
        int unsignedRightShift = a >>> 2;
        System.out.println("Bitwise AND: " + bitwiseAnd);
        System.out.println("Bitwise OR: " + bitwiseOr);
        System.out.println("Bitwise XOR: " + bitwiseXor);
        System.out.println("Bitwise complement: " + bitwiseComplement);
        System.out.println("Left shift: " + leftShift);
        System.out.println("Right shift: " + rightShift);
        System.out.println("Unsigned right shift: " + unsignedRightShift);

        // Conditional expressions
        String result = x > y ? "x is greater than y" : "y is greater than x";
        System.out.println("Result: " + result);
    }
}