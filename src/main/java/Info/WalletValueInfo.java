package Info;

import java.math.BigDecimal;
import java.sql.Date;

public class WalletValueInfo {

    private Date date;

    @Override
    public String toString() {
        return "WalletValueInfo{" +
                ", date=" + date +
                ", value=" + value +
                '}';
    }

    private double value;

    public Date getDate() {
        return date;
    }


    public double getValue() {
        return value;
    }

    public WalletValueInfo(Date d, double v){
        this.date=d;
        this.value=v;
    }
}
