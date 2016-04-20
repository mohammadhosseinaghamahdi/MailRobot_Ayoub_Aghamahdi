/**
 * File : ISmtpClient.java 
 * This interface provides the method needed for send a message.
 *
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 * @version 1.0
 */
package ch.heigvd.res.mailrobot.smtp;

import ch.heigvd.res.mailrobot.model.mail.Message;
import java.io.IOException;

/**
 *
 * @aut@author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 */
public interface ISmtpClient {

    public void sendMessage(Message message) throws IOException;

}
