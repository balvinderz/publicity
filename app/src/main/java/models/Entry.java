package models;

/**
 * Created by Patil on 25-08-2016.
 */
public class Entry {

    String receiptId;
    String name;
    String mobile;
    String email;
    String college;
    String year;
    String eventName;
    int payment;
    String status;
    boolean csimember;
    boolean paid;
    int balance;
    String payed_at;
    String balance_payed_at;
    String balance_paid_by;

    public Entry() {
    }

    public Entry(String receiptId, String name, String mobile, String email, String college,
                 String year, String eventName, int payment, String status, boolean csimember,
                 int balance, String payed_at, String balance_payed_at, String balance_paid_by,
                 boolean paid) {
        this.receiptId = receiptId;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.college = college;
        this.year = year;
        this.eventName = eventName;
        this.payment = payment;
        this.status = status;
        this.csimember = csimember;
        this.balance = balance;
        this.payed_at = payed_at;
        this.balance_payed_at = balance_payed_at;
        this.balance_paid_by = balance_paid_by;
        this.paid=paid;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCsimember() {
        return csimember;
    }

    public void setCsimember(boolean csimember) {
        this.csimember = csimember;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPayed_at() {
        return payed_at;
    }

    public void setPayed_at(String payed_at) {
        this.payed_at = payed_at;
    }

    public String getBalance_payed_at() {
        return balance_payed_at;
    }

    public void setBalance_payed_at(String balance_payed_at) {
        this.balance_payed_at = balance_payed_at;
    }

    public String getBalance_paid_by() {
        return balance_paid_by;
    }

    public void setBalance_paid_by(String balance_paid_by) {
        this.balance_paid_by = balance_paid_by;
    }
}
