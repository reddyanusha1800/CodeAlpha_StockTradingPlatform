import java.util.HashMap;

public class Portfolio {

    private HashMap<String, Integer> holdings;

    public Portfolio() {
        holdings = new HashMap<>();
    }

    public void buyStock(String symbol, int quantity) {

        holdings.put(
                symbol,
                holdings.getOrDefault(symbol, 0)
                        + quantity);
    }

    public boolean sellStock(String symbol,
                             int quantity) {

        if (!holdings.containsKey(symbol)
                || holdings.get(symbol) < quantity) {

            return false;
        }

        holdings.put(
                symbol,
                holdings.get(symbol) - quantity);

        if (holdings.get(symbol) == 0) {
            holdings.remove(symbol);
        }

        return true;
    }

    public HashMap<String, Integer> getHoldings() {
        return holdings;
    }
}
