package smtp;


import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient{

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private String serverAddress;
    private int port = 25;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;


    public SmtpClient(String serverAddress, int port){
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void sendMessage(Message message) throws IOException {
        LOG.info("Sending message");

        connect();

        String line = reader.readLine();
        LOG.info(line);

        writer.write("EHLO localhost\r\n");
        writer.flush();

        line = reader.readLine();
        LOG.info(line);

        if(!line.startsWith("250")){
            throw new IOException("SMTP error: " + line);
        }

        while(line.startsWith("250-")){
            line = reader.readLine();
            LOG.info(line);
        }

        writer.write("MAIL FROM:");
        writer.write(message.getFrom());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        writeRCPT(message.getTo(), line);
        writeRCPT(message.getCc(), line);
        writeRCPT(message.getBcc(), line);

        writer.write("DATA");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
        writer.write("Content-Type: text/plain; charset=\"utf-8\"\r\n");
        writer.write("From: " + message.getFrom() + "\r\n");

        writer.write("To: " + message.getTo()[0]);
        for(int i = 1; i < message.getTo().length; ++i){
            writer.write(", " + message.getTo()[i]);
        }
        writer.write("\r\n");

        writer.write("Cc: " + message.getCc()[0]);
        for(int i = 1; i < message.getCc().length; ++i){
            writer.write(", " + message.getCc()[i]);
        }
        writer.write("\r\n");

        writer.flush();
        LOG.info(message.getBody());

        writeEndOfMessage();

        line = reader.readLine();
        LOG.info(line);

        quitAndDisconnect();

    }

    public void writeRCPT(String[] messages, String line) throws IOException{
        for(String to: messages){
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);
        }
    }

    /*
    public void writeSingleRCPTProcedur(){
        writer.write("MAIL FROM:");
        writer.write(message.getFrom());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);
    }*/

    public void writeEndOfMessage(){
        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");
        writer.flush();
    }

    public void quitAndDisconnect() throws IOException{
        writer.write("QUIT\r\n");
        writer.flush();

        reader.close();
        writer.close();
        socket.close();
    }

    public void connect() throws IOException{
        Socket socket = new Socket(serverAddress, port);
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

    }

    public void writeMultiplePerson(String[] persons, String typeOfVisibility){
        writer.write(typeOfVisibility + persons[0]);
        for(int i = 1; i < persons.length; ++i){
            writer.write(", " + persons[i]);
        }
        writer.write("\r\n");
    }

}
