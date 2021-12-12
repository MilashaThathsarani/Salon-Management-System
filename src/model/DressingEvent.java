package model;

public class DressingEvent {
    private String dressingEventCode;
    private String description;
    private double price;
    private String date;
    private String time;

    public DressingEvent() {
    }

    public DressingEvent(String dressingEventCode, String description, double price, String date, String time) {
        this.setDressingEventCode(dressingEventCode);
        this.setDescription(description);
        this.setPrice(price);
        this.setDate(date);
        this.setTime(time);
    }

    public String getDressingEventCode() {
        return dressingEventCode;
    }

    public void setDressingEventCode(String dressingEventCode) {
        this.dressingEventCode = dressingEventCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
        return "DressingEvent{" +
                "dressingEventCode='" + dressingEventCode + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
