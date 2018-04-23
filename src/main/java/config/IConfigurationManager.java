package config;

import model.mail.Person;
import java.util.List;

public interface IConfigurationManager {


    List<Person> getVictims();

    List<String> getMessages();

    String getSmtpServerAddress();

    void setSmtpServerAddress(String smtpServerAddress);

    int getSmtpServerPort();

    void setSmtpServerPort(int smtpServerPort);

    int getNumberOfGroups();

    void setNumberOfGroups(int numberOfGroups);

    List<Person> getWitnessesToCC();

    void setWitnessesToCC(List<Person> witnessesToCC);
}
