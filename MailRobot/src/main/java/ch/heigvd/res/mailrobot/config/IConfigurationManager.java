/**
 * File : IConfigurationManager.java 
 * This interface provides the methods needed
 * for the prank, ie, messages, group number, list of victims and the witness.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 * @version 1.0
 */
package ch.heigvd.res.mailrobot.config;

import ch.heigvd.res.mailrobot.model.mail.Person;
import java.util.List;

/**
 * This interface provides the methods needed
 * for the prank, ie, messages, group number, list of victims and the witness.
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 */
public interface IConfigurationManager {

    public List<String> getMessages();

    public int getNumberOfGroups();

    public List<Person> getVictims();

    public List<Person> getWitnessesToCc();
}
