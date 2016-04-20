/**
 * File : PrankGenerator.java 
 * This file represents generates the prank.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 * @version 1.0
 */
package ch.heigvd.res.mailrobot.model.prank;

import ch.heigvd.res.mailrobot.config.IConfigurationManager;
import ch.heigvd.res.mailrobot.model.mail.Group;
import ch.heigvd.res.mailrobot.model.mail.Person;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 */
public class PrankGenerator {

    private IConfigurationManager configurationManager;

    public PrankGenerator(IConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    /**
     * allow to obtain a list of prank.
     *
     * @author AYOUB Jean
     * @author AGHAMAHDI Mohammad Hossein
     * @return list of prank
     */
    public List<Prank> generatePranks() {
        List<Prank> pranks = new ArrayList<>();

        List<String> messages = configurationManager.getMessages();
        int messageIndex = 0;

        int numberOfGroups = configurationManager.getNumberOfGroups();
        int numberOfVictims = configurationManager.getVictims().size();

        // testing to have at least 2 recipient.
        if (numberOfVictims / numberOfGroups < 3) {
            numberOfGroups = numberOfVictims / 3;
            LOG.warning("There are not enought victims to generate the desired number of groups. "
                    + "We can only generate a max of " + numberOfGroups
                    + "groups to have at least 3 victims per groups");
        }

        // create the groups
        List<Group> groups = generateGroups(configurationManager.getVictims(), numberOfGroups);

        for (Group group : groups) {
            Prank prank = new Prank();

            List<Person> victims = group.getMembers();
            Collections.shuffle(victims);
            Person sender = victims.remove(0);
            prank.setVictimSender(sender);

            prank.addWitnessRecipients(configurationManager.getWitnessesToCc());

            String message = messages.get(messageIndex);
            messageIndex = (messageIndex + 1) % messages.size();
            prank.setMessage(message);
            
            prank.addVictimRecipients(victims);

            pranks.add(prank);
        }
        return pranks;
    }

    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    /**
     * allow to generate a group.
     *
     * @param victims list of victims
     * @param numberOfGroups number of groups total
     * @return list of group
     */
    public List<Group> generateGroups(List<Person> victims, int numberOfGroups) {
        List<Person> availableVictims = new ArrayList(victims);
        
        List<Group> groups = new ArrayList<>();
        Random rand = new Random();
        int generateMaxNumber = availableVictims.size();
        int randomNumber = rand.nextInt(generateMaxNumber); //generate between 0 et generateMaxNumber-1

        for (int i = 0; i < numberOfGroups; i++) {
            Group group = new Group();
            groups.add(group);
        }

        
        int numberOfvictimsGroups = availableVictims.size() / numberOfGroups;
        int restOfVictims = availableVictims.size() % numberOfGroups;
        
        
        int j = 0;
        for (int i = 0; i < numberOfGroups; i++) {
            for (int k = 0 ; k < numberOfvictimsGroups;j++, k++) {
                groups.get(i).addMember(availableVictims.get(j));
            }
        }
        
        for (int i = 0 ; i < restOfVictims ; i++ ,j++){
            groups.get(i).addMember(availableVictims.get(j));
        }
        

        return groups;
    }
}
