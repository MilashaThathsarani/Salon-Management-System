package model;

import java.util.Objects;

public class InvoiceDetail1 {
    private String serviceCode;
    private String serviceName;
    private int qty;
    private double cost;
    private String invoiceDueDate;

    public InvoiceDetail1() {
    }

    public InvoiceDetail1(String serviceCode, String serviceName, int qty, double cost, String invoiceDueDate) {
        this.setServiceCode(serviceCode);
        this.setServiceName(serviceName);
        this.setQty(qty);
        this.setCost(cost);
        this.setInvoiceDueDate(invoiceDueDate);
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public void setInvoiceDueDate(String invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    @Override
    public String toString() {
        return "InvoiceDetail1{" +
                "serviceCode='" + serviceCode + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", qty=" + qty +
                ", cost=" + cost +
                ", invoiceDueDate='" + invoiceDueDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDetail1 that = (InvoiceDetail1) o;
        return qty == that.qty &&
                Double.compare(that.cost, cost) == 0 &&
                Objects.equals(serviceCode, that.serviceCode) &&
                Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(invoiceDueDate, that.invoiceDueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceCode, serviceName, qty, cost, invoiceDueDate);
    }
}
