package life.nsu.foodware.models;

public class Restaurant {
    private String photo;
    private String name;
    private String ownerName;
    private String phone;
    private String bkash;
    private String status;

    private String openingAt;
    private String closingAt;

    public Restaurant(String name, String ownerName, String phone, String bkash, String status, String openingAt, String closingAt) {
        this.name = name;
        this.ownerName = ownerName;
        this.phone = phone;
        this.bkash = bkash;
        this.status = status;
        this.openingAt = openingAt;
        this.closingAt = closingAt;
    }

    public Restaurant(String photo, String name, String ownerName, String phone, String bkash, String status, String openingAt, String closingAt) {
        this.photo = photo;
        this.name = name;
        this.ownerName = ownerName;
        this.phone = phone;
        this.bkash = bkash;
        this.status = status;
        this.openingAt = openingAt;
        this.closingAt = closingAt;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpeningAt() {
        return openingAt;
    }

    public void setOpeningAt(String openingAt) {
        this.openingAt = openingAt;
    }

    public String getClosingAt() {
        return closingAt;
    }

    public void setClosingAt(String closingAt) {
        this.closingAt = closingAt;
    }
}
