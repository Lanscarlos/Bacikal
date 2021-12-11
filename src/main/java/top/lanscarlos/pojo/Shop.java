package top.lanscarlos.pojo;

public class Shop {
    String sid;
    String name;
    String password;
    String address;
    String reg_time;
    String subscribe;

    public Shop() {
    }

    public Shop(String sod, String name, String password, String address, String reg_time, String subscribe) {
        this.sid = sid;
        this.name = name;
        this.password = password;
        this.address = address;
        this.reg_time = reg_time;
        this.subscribe = subscribe;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }
}
