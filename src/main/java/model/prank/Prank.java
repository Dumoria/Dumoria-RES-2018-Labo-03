package model.prank;

import model.mail.Message;
import model.mail.Person;

import java.util.ArrayList;
import java.util.List;

public class Prank {

    private Person victimSender;
    private final List<Person> victimRecipients = new ArrayList();
    private final List<Person> witnessRecipients = new ArrayList();
    private String message;

    public Person getVictimSender(){
        return victimSender;
    }

    public void setVictimSender(Person victimSender) {
        this.victimSender = victimSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addVictimRecipients(List<Person> victims){
        victimRecipients.addAll(victims);
    }

    public void addWitnessesRecipients(List<Person> witnesses){
        witnessRecipients.addAll(witnesses);
    }

    public List<Person> getVictimRecipients() {
        return new ArrayList(victimRecipients);
    }

    public List<Person> getWitnessRecipients() {
        return new ArrayList(witnessRecipients);
    }

    public Message generateMailMessage(){
        Message msg = new Message();

        //Set subject
        int subjectEnd = 0;
        int messageSize = message.length();
        while(message.charAt(subjectEnd) != '\n' && subjectEnd < messageSize){
            subjectEnd++;
        }
        msg.setSubject(this.message.substring(0, subjectEnd));

        //Set body
        msg.setBody(this.message.substring(subjectEnd + 1) + "\r\n" + victimSender.getFirstName());

        //Set to
        int victimSize = victimRecipients.size();
        String[] to = new String[victimSize];
        for(int i = 0; i < victimSize; ++i){
            to[i] = victimRecipients.get(i).getAddress();
        }
        msg.setTo(to);

        //Set Cc
        int witnessSize = witnessRecipients.size();
        String[] cc = new String[witnessSize];
        for(int i = 0; i < witnessSize; ++i){
            cc[i] = witnessRecipients.get(i).getAddress();
        }
        msg.setCc(cc);

        //Set from
        msg.setFrom(victimSender.getAddress());
        return msg;

    }
}
