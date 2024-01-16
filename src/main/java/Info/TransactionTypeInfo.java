package Info;

/**
 * Class qui permet de stocker les informations des types de transactions
 */
public class TransactionTypeInfo {
    public String getName() {
        return name;
    }

    public TransactionTypeInfo(String name) {
        this.name = name;
    }
    private String name;
}
