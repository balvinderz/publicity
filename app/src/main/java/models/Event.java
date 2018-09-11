package models;

/**
 * Created by Patil on 25-08-2016.
 */
public class Event {
    private String name;
    private int cost;

    int csi_member;
    int no_of_participants;
    int total_cost;
    int no_payment_due;
    int balance;
    public Event() {
    }

    public Event(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public Event(String name, int cost, int csi_member, int no_of_participants, int total_cost, int no_payment_due,int balance) {
        this.name = name;
        this.cost = cost;
        this.csi_member = csi_member;
        this.no_of_participants = no_of_participants;
        this.total_cost = total_cost;
        this.no_payment_due = no_payment_due;
        this.balance=balance;
    }

    public int getCsi_member() {
        return csi_member;
    }

    public void setCsi_member(int csi_member) {
        this.csi_member = csi_member;
    }

    public int getNo_of_participants() {
        return no_of_participants;
    }

    public void setNo_of_participants(int no_of_participants) {
        this.no_of_participants = no_of_participants;
    }
public int getbalance()
{return balance;}
public  void setBalance(int balance)
{
    this.balance=balance;
}

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public int getNo_payment_due() {
        return no_payment_due;
    }

    public void setNo_payment_due(int no_payment_due) {
        this.no_payment_due = no_payment_due;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


}
