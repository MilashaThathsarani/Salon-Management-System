package model;

public class Client {
    private String clientId;
    private String clientName;
    private String clientAddress;
    private String clientContact;
    private String serviceType;
    private String clientDate;
    private String time;

    public Client() {
    }

    public Client(String clientId, String clientName, String clientAddress, String clientContact, String serviceType, String clientDate, String time) {
        this.setClientId(clientId);
        this.setClientName(clientName);
        this.setClientAddress(clientAddress);
        this.setClientContact(clientContact);
        this.setServiceType(serviceType);
        this.setClientDate(clientDate);
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

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientContact() {
        return clientContact;
    }

    public void setClientContact(String clientContact) {
        this.clientContact = clientContact;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getClientDate() {
        return clientDate;
    }

    public void setClientDate(String clientDate) {
        this.clientDate = clientDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", clientContact='" + clientContact + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", clientDate='" + clientDate + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}