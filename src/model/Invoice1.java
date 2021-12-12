package model;

import java.util.Objects;

public class Invoice1 {
    private String clientId;
    private String clientName;
    private String invoiceId;
    private String date;
    private String time;

    public Invoice1() {
    }

    public Invoice1(String clientId, String clientName, String invoiceId, String date, String time) {
        this.setClientId(clientId);
        this.setClientName(clientName);
        this.setInvoiceId(invoiceId);
        this.setDate(date);
        this.setTime(time);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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
        return "Invoice1{" +
                "clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", invoiceId='" + invoiceId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice1 invoice1 = (Invoice1) o;
        return Objects.equals(clientId, invoice1.clientId) &&
                Objects.equals(clientName, invoice1.clientName) &&
                Objects.equals(invoiceId, invoice1.invoiceId) &&
                Objects.equals(date, invoice1.date) &&
                Objects.equals(time, invoice1.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientName, invoiceId, date, time);
    }
}
