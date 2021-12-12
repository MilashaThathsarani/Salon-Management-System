package model;

import java.util.Objects;

public class InvoiceTm  {
    private String clientId;
    private String clientName;
    private String invoiceId;
    private String date;
    private String time;

    public InvoiceTm() {
    }

    public InvoiceTm(String clientId, String clientName, String invoiceId, String date, String time) {
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
        return "InvoiceTm{" +
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
        InvoiceTm invoiceTm = (InvoiceTm) o;
        return Objects.equals(clientId, invoiceTm.clientId) &&
                Objects.equals(clientName, invoiceTm.clientName) &&
                Objects.equals(invoiceId, invoiceTm.invoiceId) &&
                Objects.equals(date, invoiceTm.date) &&
                Objects.equals(time, invoiceTm.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientName, invoiceId, date, time);
    }
}
