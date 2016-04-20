/**
 * File : Group.java 
 * This file allows to add people in a group and be able to
 * retrieve the list of people who are member of the group.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 * @version 1.0
 */
package ch.heigvd.res.mailrobot.model.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Tis class is a groupe of people. This class allows to add people in a group
 * and be able to retrieve the list of people who are member of the group.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 */
public class Group {

    private final List<Person> members = new ArrayList<>();

    public void addMember(Person person) {
        members.add(person);
    }

    public List<Person> getMembers() {
        return new ArrayList<>(members);
    }

}
