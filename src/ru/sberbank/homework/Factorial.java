package ru.sberbank.homework;

import java.math.BigInteger;

public interface Factorial {
    @Cache
    BigInteger factorial(int num);
}
