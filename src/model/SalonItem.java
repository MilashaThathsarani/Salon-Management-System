package model;

public class SalonItem {
    private String itemCode;
    private String description;
    private Double unitPrice;
    private int qtyOnHand;
    private String date;
    private String time;

    public SalonItem() {
    }

    public SalonItem(String itemCode, String description, Double unitPrice, int qtyOnHand, String date, String time) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setUnitPrice(unitPrice);
        this.setQtyOnHand(qtyOnHand);
        this.setDate(date);
        this.setTime(time);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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
        return "SalonItem{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
