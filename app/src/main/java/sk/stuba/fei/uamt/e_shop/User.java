package sk.stuba.fei.uamt.e_shop;

/**
 * Created by Zendo on 24.11.2017.
 */

public class User {
    private String action;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String city;
    private String houseNumber;
    private String street;
    private String country;

    public User(String action, String firstname, String lastname, String email, String password, String city, String houseNumber, String street, String country) {
        this.action = action;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.city = city;
        this.houseNumber = houseNumber;
        this.street = street;
        this.country = country;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCity() {
        return city;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public String getCountry() {
        return country;
    }
}
