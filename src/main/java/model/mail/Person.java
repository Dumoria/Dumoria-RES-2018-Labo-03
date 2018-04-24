package model.mail;

import java.util.InvalidPropertiesFormatException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {

    private String firstName;
    private String lastName;
    private final String address;

    public Person(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Person(String address) throws InvalidPropertiesFormatException{
        this.address = address;

        if(!(address.contains("@") && address.contains(".")))
            throw new InvalidPropertiesFormatException("The address should str.str@str.str");

        int endFirstName = 0;
        while(address.charAt(endFirstName) != '.'){
            endFirstName++;
        }

        int endLastName = endFirstName;
        while(address.charAt(endLastName) != '@' && endLastName < address.length()){
            endLastName++;
        }

        this.firstName = address.substring(0, endFirstName);
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
        this.lastName = address.substring(endFirstName, endLastName);
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }
}
