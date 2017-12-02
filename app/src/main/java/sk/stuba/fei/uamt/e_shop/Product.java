package sk.stuba.fei.uamt.e_shop;

/**
 * Created by Zendo on 2.12.2017.
 */


public class Product {
    private String ID;
    private String title;
    private String description;
    private String image;
    private String price;
    private String count;

    public Product(String ID, String title, String description, String image, String price, String count) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
        this.count = count;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getCount() {
        return count;
    }
}
