package model;

public class Staff {
    private String staffId;
    private String staffName;
    private String address;
    private String contact;
    private String education;
    private String position;
    private double salary;
    private String date;
    private String time;

    public Staff() {
    }

    public Staff(String staffId, String staffName, String address, String contact, String education, String position, double salary, String date, String time) {
        this.setStaffId(staffId);
        this.setStaffName(staffName);
        this.setAddress(address);
        this.setContact(contact);
        this.setEducation(education);
        this.setPosition(position);
        this.setSalary(salary);
        this.setDate(date);
        this.setTime(time);
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
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

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
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
        return "Staff{" +
                "staffId='" + staffId + '\'' +
                ", staffName='" + staffName + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", education='" + education + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
