package de.travelbuddy;

public class ContactDetails {

    private String phone;
    private String email;
    private String town;
    private String street;
    private int streetNumber;
    private String ZIP;
    private String country;

    /**
     * combines the contact details
     * @param phone phone number, it could a mobile number or a home phone number
     * @param email a simple mail address
     * @param town were a person live or you can find the place (sight)
     * @param street a street
     * @param streetNumber a street number without letters
     * @param ZIP the zip from the town, its more accurate then town only
     * @param country it's important, if the journey takes place in several countries
     */
    public ContactDetails(String phone, String email,String town, String street, int streetNumber, String ZIP, String country) {
        this.phone = phone;
        this.email = email;
        this.town = town;
        this.street = street;
        this.streetNumber = streetNumber;
        this.ZIP = ZIP;
        this.country = country;
    }

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getTown() {return town;}

    public void setTown(String town) {this.town = town;}

    public String getStreet() {return street;}

    public void setStreet(String street) {this.street = street;}

    public int getStreetNumber() {return streetNumber;}

    public void setStreetNumber(int streetNumber) {this.streetNumber = streetNumber;}

    public String getZIP() {return ZIP;}

    public void setZIP(String ZIP) {this.ZIP = ZIP;}

    public String getCountry() {return country;}

    public void setCountry(String country) {this.country = country;}
}
