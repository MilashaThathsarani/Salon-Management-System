package controller.Controller;

public class CartTm {
    private String serviceId;
    private String serviceName;
    private int qtyOnHand;
    private double price;
    private int qty;
    private String dueDate;
    private double cost;

    public CartTm(String serviceId, String serviceName, int qtyOnHand, double price, int qty, String dueDate, double cost) {
        this.setServiceId(serviceId);
        this.setServiceName(serviceName);
        this.setQtyOnHand(qtyOnHand);
        this.setPrice(price);
        this.setQty(qty);
        this.setDueDate(dueDate);
        this.setCost(cost);
    }

    public CartTm(String value, String serviceName, Integer integer, double price, Object o, Object dueDate, double cost) {
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "CartTm{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", price=" + price +
                ", qty=" + qty +
                ", dueDate='" + dueDate + '\'' +
                ", cost=" + cost +
                '}';
    }

}
