package ru.sberbank.homework;

public class Main {

    public static void main(String[] args) throws NoSuchMethodException {
        Factorial factorial = new FactorialImpl();
        Factorial proxyFactorial = CacheProxy.proxying(factorial, Factorial.class);
        System.out.println(proxyFactorial.factorial(500));
        System.out.println(proxyFactorial.factorial(500));

    }
}
