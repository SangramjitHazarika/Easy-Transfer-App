package com.example.easytransfer;

public class Transaction_Record {

    private String sender_name;
    private String sender_email;
    private String receiver_name;
    private String receiver_email;
    private int amount;
    private String dateTime;
    private String result;

    public Transaction_Record(String sender_name, String sender_email, String receiver_name, String receiver_email, int amount, String dateTime, String result) {
        this.sender_name = sender_name;
        this.sender_email = sender_email;
        this.receiver_name = receiver_name;
        this.receiver_email = receiver_email;
        this.amount = amount;
        this.dateTime = dateTime;
        this.result = result;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public void setReceiver_email(String receiver_email) {
        this.receiver_email = receiver_email;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getSender_email() {
        return sender_email;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_email() {
        return receiver_email;
    }

    public int getAmount() {
        return amount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getResult() {
        return result;
    }
}
