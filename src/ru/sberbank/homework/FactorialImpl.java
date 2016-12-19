package ru.sberbank.homework;

import java.math.BigInteger;

public class FactorialImpl implements Factorial {
    @Override
    public BigInteger factorial(int num) {
        BigInteger fact = BigInteger.valueOf(1);
        for (int i = 1; i <= num; i++)
            fact = fact.multiply(BigInteger.valueOf(i));
        return fact;
    }
}
