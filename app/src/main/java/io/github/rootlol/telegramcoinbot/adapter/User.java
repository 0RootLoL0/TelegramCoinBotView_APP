package io.github.rootlol.telegramcoinbot.adapter;

public class User {
    private String number;
    private Double balance;

    public User(String number, Double balance) {
        this.number = number;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public String getBalance() {
        return Double.toString(balance);
    }
}
