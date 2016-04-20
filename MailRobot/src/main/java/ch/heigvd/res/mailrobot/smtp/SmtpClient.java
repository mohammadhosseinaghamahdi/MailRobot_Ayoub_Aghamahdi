/**
 * File : SmtpClient.java 
 * This file represents a smtp protocol
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 * @version 1.0
 */
package ch.heigvd.res.mailrobot.smtp;

import ch.heigvd.res.mailrobot.model.mail.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * this class implements a smtp client
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 */
public class SmtpClient implements ISmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private String smtpServerAddress;
    private int smtpServerPort;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SmtpClient(String smtpServerAddress, int port) {
        this.smtpServerAddress = smtpServerAddress;
        this.smtpServerPort = port;
    }

    /**
     * this methode implements a smtp message.
     * @param message
     * @throws IOException 
     */
    public void sendMessage(Message message) throws IOException {
        LOG.info("Sending message via SMTP");
        Socket socket = new Socket(smtpServerAddress, smtpServerPort);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        String line = reader.readLine();
        LOG.info(line);
        writer.printf("EHLO localhost\r\n");
        line = reader.readLine();
        LOG.info(line);
        if (!line.startsWith("250")) {
            throw new IOException("SMTP error:" + line);
        }

        while (line.startsWith("250-")) {
            line = reader.readLine();
            LOG.info(line);
        }

        writer.write("MAIL FROM: ");
        writer.write(message.getFrom());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        for (String to : message.getTo()) {
            writer.write("RCPT TO: ");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        for (String to : message.getCc()) {
            writer.write("RCPT TO: ");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        for (String to : message.getBcc()) {
            writer.write("RCPT TO: ");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }

        writer.write("DATA");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
        writer.write("Content-Type: text/plain; charset=\"utf-8\"\r\n+");
        writer.write("FROM: " + message.getFrom() + "\r\n");
        writer.write("To: " + message.getTo()[0]);

        for (int i = 1; i < message.getTo().length; i++) {
            writer.write(", " + message.getTo()[i]);
        }

        writer.write("\r\n");

        writer.write("Cc: " + message.getCc()[0]);
        for (int i = 1; i < message.getCc().length; i++) {
            writer.write(", " + message.getCc()[i]);
        }
        writer.write("\r\n");
        writer.flush();

        LOG.info(message.getBody());
        writer.write(message.getBody());
        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
        writer.write("QUIT\r\n");
        writer.flush();
        reader.close();
        writer.close();
        socket.close();
    }

}
