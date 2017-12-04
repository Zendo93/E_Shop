package sk.stuba.fei.uamt.e_shop;

import android.os.StrictMode;

/**
 * Created by Zendo on 22.11.2017.
 */

public class Credentials {

    private String action;
    private String name;
    private String surname;
    private String email;
    private String address;
    private String zip;
    private String city;
    private String country;

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
    public String getAddress() {
        return address;
    }
    public String getZip() {
        return zip;
    }
    public String getCity() {
        return city;
    }
    public String getCountry() {
        return country;
    }
}
