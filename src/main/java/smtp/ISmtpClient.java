package smtp;

import java.io.IOException;

public interface ISmtpClient {
    public void sendMessage(Message message) throws IOException;
}
