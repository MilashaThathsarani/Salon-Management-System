package model;

public class InvoiceDetail {
    private String serviceId;
    private String serviceName;
    private int invoiceQty;
    private double cost;
    private String invoiceDueDate;

    public InvoiceDetail() {
    }

    public InvoiceDetail(String serviceId, String serviceName, int invoiceQty, double cost, String invoiceDueDate) {
        this.setServiceId(serviceId);
        this.setServiceName(serviceName);
        this.setInvoiceQty(invoiceQty);
        this.setCost(cost);
        this.setInvoiceDueDate(invoiceDueDate);
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

    public int getInvoiceQty() {
        return invoiceQty;
    }

    public void setInvoiceQty(int invoiceQty) {
        this.invoiceQty = invoiceQty;
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
        return "InvoiceDetail{" +
                "serviceId='" + serviceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", invoiceQty=" + invoiceQty +
                ", cost=" + cost +
                ", invoiceDueDate='" + invoiceDueDate + '\'' +
                '}';
    }
}
