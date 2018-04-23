package smtp;

import model.mail.Message;

import java.io.IOException;

public interface ISmtpClient {

    public void sendMessage(Message message) throws IOException;
}
