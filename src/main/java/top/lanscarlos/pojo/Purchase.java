package top.lanscarlos.pojo;

public class Purchase {
    String pid;
    String uid;
    String gid;
    String time;
    int price;
    int amount;
    int sum;

    public Purchase() {
    }

    public Purchase(String pid, String uid, String gid, String time, int price, int amount, int sum) {
        this.pid = pid;
        this.uid = uid;
        this.gid = gid;
        this.time = time;
        this.price = price;
        this.amount = amount;
        this.sum = sum;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
