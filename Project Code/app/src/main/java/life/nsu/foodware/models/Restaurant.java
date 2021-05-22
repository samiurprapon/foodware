package life.nsu.foodware.models;

public class Restaurant {
    private String logo;
    private String name;
    private String ownerName;
    private String phone;
    private String bkash;
    private String status;
    private Location location;

    private String openingAt;
    private String closingAt;

    public Restaurant() {
        // empty constructor for firebase
    }

    public Restaurant(String name, String ownerName, String phone, String bkash, String status, Location location, String openingAt, String closingAt) {
        this.name = name;
        this.ownerName = ownerName;
        this.phone = phone;
        this.bkash = bkash;
        this.status = status;
        this.location = location;
        this.openingAt = openingAt;
        this.closingAt = closingAt;
    }

    public Location getLocation() {
        return location;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBkash() {
        return bkash;
    }

    public void setBkash(String bkash) {
        this.bkash = bkash;
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
