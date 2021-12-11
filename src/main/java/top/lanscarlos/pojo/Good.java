package top.lanscarlos.pojo;

/**
 * @author 10511
 */
public class Good {
    private String sid;
    private String gid;
    private String cid;
    private String name;
    private String description;
    private String image;
    private int stock;
    private double price;
    private String putaway_time;

    // 附加属性
    private String shop;
    private String category;

    public Good(){

    }

    public Good(String sid, String gid, String cid, String name, String description, String image, int stock, double price, String putaway_time) {
        this.sid = sid;
        this.gid = gid;
        this.cid = cid;
        this.name = name;
        this.description = description;
        this.image = image;
        this.stock = stock;
        this.price = price;
        this.putaway_time = putaway_time;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPutaway_time() {
        return putaway_time;
    }

    public void setPutaway_time(String putaway_time) {
        this.putaway_time = putaway_time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Good{" +
                "sid='" + sid + '\'' +
                ", gid='" + gid + '\'' +
                ", cid='" + cid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", putaway_time='" + putaway_time + '\'' +
                '}';
    }
}
