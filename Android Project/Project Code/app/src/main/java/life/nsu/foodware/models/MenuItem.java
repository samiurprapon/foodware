package life.nsu.foodware.models;

public class MenuItem {

    private String imageUrl;
    private String name;
    private String category;
    private String price;
    private String discount;
    private boolean isAvailable;

    public MenuItem(String imageUrl, String name, String category, String price, String discount) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
    }

    public MenuItem(String imageUrl, String name, String category, String price, String discount, boolean isAvailable) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.category = category;
        this.price = price;
        this.discount = discount;
        this.isAvailable = isAvailable;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
