/**
 * File : Message.java 
 * This file represents all the information associated with
 * a person.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 * @version 1.0
 */
package ch.heigvd.res.mailrobot.model.mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents all the information associated with a person.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 */
public class Person {

    private String firstName;
    private String lastName;
    private String address;

    public Person(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Person(String address) {
        this.address = address;

        // model of valid e-mail
        Pattern pattern = Pattern.compile("(.*)\\.(.*)@");
        Matcher matcher = pattern.matcher(address);
        boolean found = matcher.find();

        if (found) {
            this.firstName = matcher.group(1);
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            this.lastName = matcher.group(2);
            lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        }
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
