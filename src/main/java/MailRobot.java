import config.ConfigurationManager;
import config.IConfigurationManager;
import model.prank.Prank;
import model.prank.PrankGenerator;
import smtp.SmtpClient;

import java.io.IOException;
import java.util.ArrayList;

public class MailRobot {
    public static void main(String[] args){
        try{
            IConfigurationManager conf = new ConfigurationManager();
            ArrayList<Prank> pranks = (ArrayList<Prank>) new PrankGenerator(conf).generatePranks();
            SmtpClient client = new SmtpClient(conf.getSmtpServerAddress(), conf.getSmtpServerPort());

            for(Prank p : pranks){
                client.sendMessage(p.generateMailMessage());
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
