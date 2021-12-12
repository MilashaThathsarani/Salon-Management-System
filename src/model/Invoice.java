package model;

import java.util.ArrayList;

public class Invoice {
    private String invoiceId;
    private String cId;
    private String invoiceDate;
    private String time;
    private ArrayList<InvoiceDetail> invoices;

    public Invoice() {
    }

    public Invoice(String invoiceId, String cId, String invoiceDate, String time, ArrayList<InvoiceDetail> invoices) {
        this.setInvoiceId(invoiceId);
        this.setcId(cId);
        this.setInvoiceDate(invoiceDate);
        this.setTime(time);
        this.setInvoices(invoices);
    }

    public Invoice(String invoiceId, String cId, String invoiceDate, String time) {
        this.setInvoiceId(invoiceId);
        this.setcId(cId);
        this.setInvoiceDate(invoiceDate);
        this.setTime(time);
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<InvoiceDetail> getInvoices() {
        return invoices;
    }

    public void setInvoices(ArrayList<InvoiceDetail> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", cId='" + cId + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", time='" + time + '\'' +
                ", invoices=" + invoices +
                '}';
    }
}