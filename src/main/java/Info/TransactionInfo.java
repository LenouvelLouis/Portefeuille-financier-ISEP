package Info;

import java.sql.Date;
import java.sql.Timestamp;

public class TransactionInfo {
    private int id_wallet;

    private Float value;
    private Timestamp date;

    private String type;
    private String libelle_type;

    public TransactionInfo(int id_wallet, Float value, Timestamp date, String type, String libelle_type) {
        this.id_wallet = id_wallet;
        this.value = value;
        this.date = date;
        this.type = type;
        this.libelle_type = libelle_type;
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
