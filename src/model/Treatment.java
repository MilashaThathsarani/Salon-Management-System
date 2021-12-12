package model;

public class Treatment {
    private String treatmentCode;
    private String description;
    private String price;
    private String date;
    private String time;

    public Treatment() {
    }

    public Treatment(String treatmentCode, String description, String price, String date, String time) {
        this.setTreatmentCode(treatmentCode);
        this.setDescription(description);
        this.setPrice(price);
        this.setDate(date);
        this.setTime(time);
    }

    public String getTreatmentCode() {
        return treatmentCode;
    }

    public void setTreatmentCode(String treatmentCode) {
        this.treatmentCode = treatmentCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
        return "Treatment{" +
                "treatmentCode='" + treatmentCode + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
