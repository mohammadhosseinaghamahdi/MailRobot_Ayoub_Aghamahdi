/**
 * File : ConfigurationManager.java 
 * This file makes the necessary configurations
 * for SMTP protocol and implements the methods needed for the prank, ie,
 * messages, group number, list of victims and the witness.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 * @version 1.0
 */
package ch.heigvd.res.mailrobot.config;

import ch.heigvd.res.mailrobot.model.mail.Person;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * this class retrieves the values configured in different files.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 */
public class ConfigurationManager implements IConfigurationManager {

    // informations to connect the smtp protocol
    private String smtpServerAddress;
    private int smtpServerPort;

    //the people who will receive the forged message.
    private final List<Person> victims;

    // the message will be sent to the victims.
    private final List<String> messages;

    // number of group of people who will receive the forged email
    private int numberOfGroups;

    // witness the person who will receive the mail.
    private List<Person> witnessesToCc;

    /**
     * retrieves the information necessary to start the game. This information
     * is for victims, messages, port number, the witness, the address of the
     * SMTP protocol and group number.
     *
     * @throws IOException
     */
    public ConfigurationManager() throws IOException {

        victims = loadAddressesFromFile("src/main/java/ch/heigvd/res/mailrobot/config/victims.utf8");
        messages = loadMessagesFromFile("src/main/java/ch/heigvd/res/mailrobot/config/messages.utf8");
        loadProperties("src/main/java/ch/heigvd/res/mailrobot/config/config.properties");
    }

    /**
     * retrieves the information about properties of connection.
     *
     * @param fileName the file to retrieve information
     * @throws IOException
     */
    private void loadProperties(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(fileName);
        Properties properties = new Properties();
        properties.load(fis);
        this.smtpServerAddress = properties.getProperty("smtpServerAddress");
        this.smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
        this.numberOfGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));
        this.witnessesToCc = new ArrayList<>();
        String witnesses = properties.getProperty("witnessesToCc");
        String[] witnessesAdresses = witnesses.split(",");
        for (String address : witnessesAdresses) {
            this.witnessesToCc.add(new Person(address));
        }
    }

    /**
     * recover list of victims in the given file.
     *
     * @param fileName the file to retrieve information
     * @return list of victims(theirs e-mails)
     * @throws IOException
     */
    private List<Person> loadAddressesFromFile(String fileName) throws IOException {
        List<Person> result;

        try (FileInputStream fis = new FileInputStream(fileName)) {
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

            try (BufferedReader reader = new BufferedReader(isr)) {
                result = new ArrayList<>();
                String address = reader.readLine();

                while (address != null) {
                    result.add(new Person(address));
                    address = reader.readLine();
                }
            }
        }
        return result;
    }

    /**
     * retrieve messages list in the given file.
     *
     * @param fileName the file to retrieve information
     * @return list of messages to send
     * @throws IOException
     */
    private List<String> loadMessagesFromFile(String fileName) throws IOException {
        List<String> result;

        try (FileInputStream fis = new FileInputStream(fileName)) {
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

            try (BufferedReader reader = new BufferedReader(isr)) {
                result = new ArrayList<>();
                String line = reader.readLine();

                while (line != null) {
                    StringBuilder body = new StringBuilder();

                    // we assume that each message is separated by the sign "=="
                    while ((line != null) && (!line.equals("=="))) {
                        body.append(line);
                        body.append("\r\n");
                        line = reader.readLine();
                    }
                    result.add(body.toString());
                    line = reader.readLine();
                }
            }
        }
        return result;
    }

    @Override
    public List<Person> getVictims() {
        return victims;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    @Override
    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    @Override
    public List<Person> getWitnessesToCc() {
        return witnessesToCc;
    }

}
