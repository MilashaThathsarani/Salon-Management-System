package model;

public class Product {
    private String productCode;
    private String description;
    private double price;
    private int qtyOnHand;
    private String date;
    private String time;

    public Product() {
    }

    public Product(String productCode, String description, double price, int qtyOnHand, String date, String time) {
        this.setProductCode(productCode);
        this.setDescription(description);
        this.setPrice(price);
        this.setQtyOnHand(qtyOnHand);
        this.setDate(date);
        this.setTime(time);
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productCode='" + productCode + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", qtyOnHand=" + qtyOnHand +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
