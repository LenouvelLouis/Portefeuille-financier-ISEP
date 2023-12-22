package Info;

import java.util.List;

public class WalletInfo {
    private int id;
    List<WalletValueInfo> walletValueInfoList;

    String name;


    @Override
    public String toString() {
        return "WalletInfo{" +
                "id=" + id +
                ", walletValueInfoList=" + walletValueInfoList +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public List<WalletValueInfo> getWalletValueInfoList() {
        return walletValueInfoList;
    }

    public String getName() {
        return name;
    }

    public WalletInfo(int id, String nom, List<WalletValueInfo> list){
        this.id=id;
        this.name=nom;
        this.walletValueInfoList=list;
    }
}
