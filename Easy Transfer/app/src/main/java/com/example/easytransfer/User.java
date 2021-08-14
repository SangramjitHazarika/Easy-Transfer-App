package com.example.easytransfer;

public class User {
    private String name;
    private String email_id;
    private String account_no;
    private int balance;

    public User(String name, String email_id, String account_no, int balance) {
        this.name = name;
        this.email_id = email_id;
        this.account_no = account_no;
        this.balance = balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getAccount_no() {
        return account_no;
    }

    public int getBalance() {
        return balance;
    }
}
