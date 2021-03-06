package top.lanscarlos.pojo;

import java.util.Arrays;

public class Cart {
    private String uid;
    private String gid;
    private int amount;
    private String add_time;

    public Cart() {
    }

    public Cart(String uid, String gid, int amount, String add_time) {
        this.uid = uid;
        this.gid = gid;
        this.amount = amount;
        this.add_time = add_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "uid='" + uid + '\'' +
                ", gid='" + gid + '\'' +
                ", amount=" + amount +
                ", add_time='" + add_time + '\'' +
                '}';
    }
}
