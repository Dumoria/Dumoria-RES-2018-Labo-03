package smtp;

import model.mail.Message;
import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient{

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private String serverAddress;
    private int port = 25;          //default port is 25
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;


    public SmtpClient(String serverAddress, int port){
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public SmtpClient(String serverAddress){
        this.serverAddress = serverAddress;
    }

    public void sendMessage(Message message) throws IOException {

        //Connection to the server
        LOG.info("Sending message");
        connect();
        String line = reader.readLine();
        LOG.info(line);

        //Begin smtp protocol
        writer.write("EHLO localhost\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        //Print options
        if(!line.startsWith("250")){
            throw new IOException("SMTP error: should have begin with 250, instead got " + line);
        }

        while(line.startsWith("250-")){
            line = reader.readLine();
            LOG.info(line);
        }

        //Enter source
        writer.write("MAIL FROM:");
        writer.write(message.getFrom());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        //Enter dest
        writeRCPT(message.getTo(), line);
        writeRCPT(message.getCc(), line);
        writeRCPT(message.getBcc(), line);

        //------Enter body------
        writer.write("DATA");
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        //Src, dst and subject
        writer.write("From: " + message.getFrom() + "\r\n");
        writeMultiplePerson(message.getTo(), "To: ");
        writeMultiplePerson(message.getCc(), "Cc: ");
        writer.write("Subject: " + message.getSubject());
        writer.flush();

        //Load body
        writer.write(message.getBody());
        writeEndOfMessage();

        line = reader.readLine();
        LOG.info(line);

        //Quit
        quit();
        disconnect();

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

    public void writeEndOfMessage(){
        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");
        writer.flush();
    }

    public void quit(){
        writer.write("QUIT\r\n");
        writer.flush();
    }

    public void disconnect() throws IOException{
        if(reader != null)
            reader.close();
        if(writer != null)
            writer.close();
        if(socket != null)
            socket.close();
    }

    public void connect() throws IOException{
        socket = new Socket(serverAddress, port);
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
