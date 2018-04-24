package config;

import model.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigurationManager implements IConfigurationManager{

    private String smtpServerAddress;
    private int smtpServerPort;
    private final List<Person> victims;
    private final List<String> messages;
    private int numberOfGroups;
    private List<Person> witnessesToCC;

    public ConfigurationManager() throws IOException{
        victims = loadAddressesFromFile("./src/main/config/victims.utf8");
        messages = loadMessagesFromFile("./src/main/config/messages.utf8");
        loadProperties("./src/main/config/config.properties");
    }

    private void loadProperties(String filename) throws IOException{

        FileInputStream fis = new FileInputStream(filename);
        Properties properties = new Properties();
        properties.load(fis);

        this.smtpServerAddress = properties.getProperty("smtpServerAddress");
        this.smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
        this.numberOfGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));
        this.witnessesToCC = new ArrayList();

        String witnesses = properties.getProperty("witnessesToCC");
        String[] witnessesAddresses = witnesses.split(",");
        for(String address : witnessesAddresses){
            this.witnessesToCC.add(new Person(address));
        }

    }

    private List<Person> loadAddressesFromFile(String filename) throws IOException{
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader reader = new BufferedReader(isr);
        List<Person> addresses = new ArrayList<Person>();

        String address = reader.readLine();
        while(address != null){
            addresses.add(new Person(address));
            address = reader.readLine();
        }

        return addresses;
    }


    private List<String> loadMessagesFromFile(String filename) throws IOException{
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader reader = new BufferedReader(isr);
        List<String> messages = new ArrayList<String>();

        String line = reader.readLine();
        while(line != null){
            String body = new String();
            while((line != null) && (!line.equals("=="))){
                body += (line + "\r\n");
                line = reader.readLine();
            }
            messages.add(body);
            line = reader.readLine();
        }
        return messages;
    }

    public List<Person> getWitnessesToCC() {
        return witnessesToCC;
    }

    public void setWitnessesToCC(List<Person> witnessesToCC) {
        this.witnessesToCC = witnessesToCC;
    }

    public List<Person> getVictims(){
        return victims;
    }

    public List<String> getMessages(){
        return messages;
    }

    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    public void setSmtpServerAddress(String smtpServerAddress) {
        this.smtpServerAddress = smtpServerAddress;
    }

    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    public void setSmtpServerPort(int smtpServerPort) {
        this.smtpServerPort = smtpServerPort;
    }

    public int getNumberOfGroups() {
        return numberOfGroups;
    }

    public void setNumberOfGroups(int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }
}
