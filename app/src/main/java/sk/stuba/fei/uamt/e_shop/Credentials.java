package sk.stuba.fei.uamt.e_shop;

/**
 * Created by Zendo on 22.11.2017.
 */

public class Credentials {

    private String action;
    private String name;
    private String surname;
    private String email;

    public Credentials(String action, String email){
        this.action = action;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
