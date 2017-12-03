package sk.stuba.fei.uamt.e_shop;

/**
 * Created by Zendo on 3.12.2017.
 */

public class Order {
    String action;
    String email;
    String product_id;

    public Order(String action, String email, String product_id) {
        this.action = action;
        this.email = email;
        this.product_id = product_id;
    }

    public String getAction() {
        return action;
    }

    public String getEmail() {
        return email;
    }

    public String getProduct_id() {
        return product_id;
    }
}
