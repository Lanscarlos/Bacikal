package top.lanscarlos.pojo;

public class User {
    private String uid;
    private String name;
    private String password;
    private String gender;
    private String address;
    private String reg_time;

    public User() {}

    public User(String uid, String name, String password, String gender, String address, String reg_time) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.address = address;
        this.reg_time = reg_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
}
