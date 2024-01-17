package Info;

/**
 * Class qui permet de stocker les informations des types de transactions
 */
public class TransactionTypeInfo {
    private String name;
    private double value;
    public TransactionTypeInfo(String name, double value) {
        this.name = name;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public double getValue() {
        return value;
    }

}
