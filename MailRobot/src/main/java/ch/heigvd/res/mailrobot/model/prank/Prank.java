/**
 * File : Prank.java This file represents all the information and methods
 * related to the prank.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 * @version 1.0
 */
package ch.heigvd.res.mailrobot.model.prank;

import ch.heigvd.res.mailrobot.model.mail.Message;
import ch.heigvd.res.mailrobot.model.mail.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents all the information and methods related to the game.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 */
public class Prank {

    private Person victimSender;
    private final List<Person> victimRecipients = new ArrayList<>();
    private final List<Person> witnessRecipients = new ArrayList<>();
    private String message;

    public Person getVictimSender() {
        return victimSender;
    }

    public void setVictimSender(Person victimSender) {
        this.victimSender = victimSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addVictimRecipients(List<Person> victims) {
        victimRecipients.addAll(victims);
    }

    public void addWitnessRecipients(List<Person> witnesses) {
        witnessRecipients.addAll(witnesses);
    }

    public List<Person> getVictimRecipients() {
        return new ArrayList(victimRecipients);
    }

    public List<Person> getWitnessRecipients() {
        return new ArrayList(witnessRecipients);
    }

    /**
     *
     * @return generate the mail message
     */
    public Message generateMailMessage() {
        Message msg = new Message();

        msg.setBody(this.message + "\r\n" + victimSender.getFirstName());
        /*
         String[] to = victimRecipients
         .stream()
         .map(p -> p.getAddress())
         .collect(Collectors.toList())
         .toArray(new String[]{});
         */

        // !!!!! une autre manière d'obtenir les adresses pour to !!!!!
        String[] to = new String[victimRecipients.size()];

        int i = 0;
        for (Person person : victimRecipients) {
            to[i++] = person.getAddress();

        }
        msg.setTo(to);

        /*
         String[] cc = witnessRecipients
         .stream()
         .map(p -> p.getAddress())
         .collect(Collectors.toList())
         .toArray(new String[]{});
         */
        // !!!!! une autre maanière d'obtenir les adresses pour Cc !!!!
        i = 0;
        String[] cc = new String[witnessRecipients.size()];
        for (Person person : witnessRecipients) {
            cc[i++] = person.getAddress();

        }

        msg.setCc(cc);

        msg.setFrom(victimSender.getAddress());

        return msg;

    }
}
