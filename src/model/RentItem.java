package model;

public class RentItem {
    private String rentItemCode;
    private String description;
    private double price;
    private int qtyOnHand;
    private String date;
    private String time;

    public RentItem() {
    }

    public RentItem(String rentItemCode, String description, double price, int qtyOnHand, String date, String time) {
        this.setRentItemCode(rentItemCode);
        this.setDescription(description);
        this.setPrice(price);
        this.setQtyOnHand(qtyOnHand);
        this.setDate(date);
        this.setTime(time);
    }

    public String getRentItemCode() {
        return rentItemCode;
    }

    public void setRentItemCode(String rentItemCode) {
        this.rentItemCode = rentItemCode;
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
        return "RentItem{" +
                "rentItemCode='" + rentItemCode + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", qtyOnHand=" + qtyOnHand +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
