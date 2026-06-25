import java.util.ArrayList;

public class User {

    private String name;
    private double balance;
    private Portfolio portfolio;
    private ArrayList<Transaction> transactions;

    public User(String name,
                double balance) {

        this.name = name;
        this.balance = balance;

        portfolio = new Portfolio();
        transactions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {

        if (amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }
}
