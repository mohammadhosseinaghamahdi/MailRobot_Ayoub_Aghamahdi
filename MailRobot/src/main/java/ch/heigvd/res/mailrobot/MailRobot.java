/**
 * Lab03 smtp
 * File : MailRobot.java
 * This is a game by sending the email forged to different groups of people
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 * @version 1.0
 */

package ch.heigvd.res.mailrobot;
import ch.heigvd.res.mailrobot.config.ConfigurationManager;
import ch.heigvd.res.mailrobot.model.prank.Prank;
import ch.heigvd.res.mailrobot.model.prank.PrankGenerator;
import ch.heigvd.res.mailrobot.smtp.SmtpClient;
import java.io.IOException;
import java.util.List;




/**
 * this class will start the game. it is sending a forged e-mail 
 * to several people groups.
 * @author AYOUB Jean
 * @author AGHAMAHDI Mohammad Hossein
 */
public class MailRobot {
    
    /**
     * this is the method "main" to start the game.
     * @param args
     * @throws IOException 
     */
    public static void main (String[] args) throws IOException{
        
        
        ConfigurationManager cm = new ConfigurationManager();
        
        
        // Create a smtp client
        SmtpClient smtpClient = new SmtpClient(cm.getSmtpServerAddress(), cm.getSmtpServerPort());
        
        // generate the prank               
        PrankGenerator prankGenerator = new PrankGenerator(cm);
        
        // create list of prank
        List<Prank> list  = prankGenerator.generatePranks();
        
        // for each game, send forged emails
        for (Prank prank : list)
            smtpClient.sendMessage(prank.generateMailMessage());
    } 
}
