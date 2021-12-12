package model;

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String address;
    private String contact;
    private String productName;
    private int productQty;
    private String date;
    private String time;

    public Supplier() {
    }

    public Supplier(String supplierId, String supplierName, String address, String contact, String productName, int productQty, String date, String time) {
        this.setSupplierId(supplierId);
        this.setSupplierName(supplierName);
        this.setAddress(address);
        this.setContact(contact);
        this.setProductName(productName);
        this.setProductQty(productQty);
        this.setDate(date);
        this.setTime(time);
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
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
        return "Supplier{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", productName='" + productName + '\'' +
                ", productQty=" + productQty +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
