package Info;

import java.sql.Timestamp;

/**
 * Class qui permet de stocker les informations d'une transaction
 */
public class TransactionInfo {
    private int id_wallet;

    private Float value;
    private Timestamp date;

    private String type;
    private String libelle_type;

    private Float realvalue;

    private double value_cours;

    public TransactionInfo(int id_wallet, Float value, Timestamp date, String type, String libelle_type, Float realvalue, double value_cours) {
        this.id_wallet = id_wallet;
        this.value = value;
        this.date = date;
        this.type = type;
        this.libelle_type = libelle_type;
        this.realvalue = realvalue;
        this.value_cours = value_cours;
    }

    public double getValue_cours() {
        return value_cours;
    }
    public Float getRealvalue() {
        return realvalue;
    }

    public int getId_wallet() {
        return id_wallet;
    }

    public Float getValue() {
        return value;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getLibelle_type() {
        return libelle_type;
    }
}
