import java.util.*;
import java.io.*;

public class StockTradingSystem {

    static ArrayList<Stock> market = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        market.add(new Stock("TCS",
                "Tata Consultancy Services",
                3900));

        market.add(new Stock("INFY",
                "Infosys",
                1600));

        market.add(new Stock("RELI",
                "Reliance",
                2800));

        User user =
                new User("Anusha", 100000);

        while (true) {

            System.out.println("\n===== STOCK TRADING PLATFORM =====");

            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Save Portfolio");
            System.out.println("6. Exit");

            System.out.print("Choose: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    displayMarket();
                    break;

                case 2:
                    buyStock(user, sc);
                    break;

                case 3:
                    sellStock(user, sc);
                    break;

                case 4:
                    viewPortfolio(user);
                    break;

                case 5:
                    savePortfolio(user);
                    break;

                case 6:
                    System.out.println("Thank You!");
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    static void displayMarket() {

        System.out.println("\nMarket Data");

        for (Stock stock : market) {

            System.out.println(
                    stock.getSymbol()
                            + " - "
                            + stock.getCompanyName()
                            + " - ₹"
                            + stock.getPrice());
        }
    }

    static Stock findStock(String symbol) {

        for (Stock stock : market) {

            if (stock.getSymbol()
                    .equalsIgnoreCase(symbol)) {

                return stock;
            }
        }

        return null;
    }

    static void buyStock(User user,
                         Scanner sc) {

        System.out.print("Enter Symbol: ");
        String symbol = sc.next();

        Stock stock = findStock(symbol);

        if (stock == null) {

            System.out.println("Stock Not Found");
            return;
        }

        System.out.print("Quantity: ");
        int qty = sc.nextInt();

        double total =
                qty * stock.getPrice();

        if (user.withdraw(total)) {

            user.getPortfolio()
                    .buyStock(symbol, qty);

            user.getTransactions()
                    .add(new Transaction(
                            symbol,
                            qty,
                            stock.getPrice(),
                            "BUY"));

            System.out.println("Stock Purchased");
        }
        else {

            System.out.println(
                    "Insufficient Balance");
        }
    }

    static void sellStock(User user,
                          Scanner sc) {

        System.out.print("Enter Symbol: ");
        String symbol = sc.next();

        System.out.print("Quantity: ");
        int qty = sc.nextInt();

        if (user.getPortfolio()
                .sellStock(symbol, qty)) {

            Stock stock =
                    findStock(symbol);

            double amount =
                    qty * stock.getPrice();

            user.deposit(amount);

            user.getTransactions()
                    .add(new Transaction(
                            symbol,
                            qty,
                            stock.getPrice(),
                            "SELL"));

            System.out.println(
                    "Stock Sold Successfully");
        }
        else {

            System.out.println(
                    "Not Enough Shares");
        }
    }

    static void viewPortfolio(User user) {

        System.out.println(
                "\nBalance: ₹"
                        + user.getBalance());

        System.out.println(
                "\nHoldings:");
        double totalvalue = 0;

        for (Map.Entry<String, Integer> entry :
                user.getPortfolio()
                        .getHoldings()
                        .entrySet()) {

            System.out.println(
                    entry.getKey()
                            + " -> "
                            + entry.getValue());

                Stock stock =
                        findStock(entry.getKey());
                if (stock != null) {
                    totalvalue +=
                            stock.getPrice() * entry.getValue();
        }
}

        System.out.println(
                "\nTotal Portfolio Value: ₹" + totalvalue);
        System.out.println(
                "Net Worth: ₹" + (user.getBalance() + totalvalue));

        System.out.println(
                "\nTransactions:");

        for (Transaction t :
                user.getTransactions()) {

            System.out.println(t);
        }
    }

    static void savePortfolio(User user) {

        try {

            FileWriter fw =
                    new FileWriter(
                            "portfolio.txt");

            fw.write(
                    "Balance: "
                            + user.getBalance()
                            + "\n");

            for (Map.Entry<String,
                    Integer> entry :
                    user.getPortfolio()
                            .getHoldings()
                            .entrySet()) {

                fw.write(
                        entry.getKey()
                                + " "
                                + entry.getValue()
                                + "\n");
            }

            fw.close();

            System.out.println(
                    "Portfolio Saved");
        }
        catch (Exception e) {

            System.out.println(
                    "Error Saving File");
        }
    }
}
