package model;

public class InvoiceDB {
    private String invoiceId;
    private String cId;
    private String invoiceDate;
    private String time;

    public InvoiceDB() {
    }

    public InvoiceDB(String invoiceId, String cId, String invoiceDate, String time) {
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

    @Override
    public String toString() {
        return "InvoiceDB{" +
                "invoiceId='" + invoiceId + '\'' +
                ", cId='" + cId + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
