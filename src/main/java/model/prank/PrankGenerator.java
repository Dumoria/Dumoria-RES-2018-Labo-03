package model.prank;

import config.IConfigurationManager;
import model.mail.Group;
import model.mail.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class PrankGenerator {

    private int numberOfGroups;
    private int numberOfVictims;
    private List<String> messages;
    private IConfigurationManager configurationManager;
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    public PrankGenerator(IConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
        numberOfGroups = configurationManager.getNumberOfGroups();
        numberOfVictims = configurationManager.getVictims().size();
        messages = configurationManager.getMessages();
    }

    public List<Prank> generatePranks(){
        List<Prank> pranks = new ArrayList<Prank>();
        int messageIndex = 0;

        if(numberOfVictims / numberOfGroups < 3){
            numberOfGroups = numberOfVictims / 3;       //Floor (int casting)
            LOG.warning("Not enough victims to generate the desired number of groups");
        }

        List<Group> groups = generateGroups(configurationManager.getVictims());
        for(Group group : groups){
            Prank prank = new Prank();
            List<Person> victims = group.getMembers();

            //Pick a random sender
            Collections.shuffle(victims);
            Person sender = victims.remove(0);
            prank.setVictimSender(sender);

            //Set dst/witnesses
            prank.addVictimRecipients(victims);
            prank.addWitnessesRecipients(configurationManager.getWitnessesToCC());

            //Set message
            prank.setMessage(messages.get(messageIndex));

            messageIndex = (messageIndex + 1) % messages.size();
            pranks.add(prank);

        }
        return pranks;
    }

    public List<Group> generateGroups(List<Person> victims){
        List<Person> availableVictims = new ArrayList<Person>(victims);
        List<Group> groups = new ArrayList<Group>();

        for(int i = 0; i < numberOfGroups; ++i){
            Group group = new Group();
            groups.add(group);
        }

        //Pick a random personn for the group count
        Group trgGroup;
        Collections.shuffle(availableVictims);

        int count = 0;
        while(availableVictims.size() > 0){
            trgGroup = groups.get(count);
            count = (count + 1) % groups.size();
            Person victim = availableVictims.remove(0);
            trgGroup.addMember(victim);
        }
        return groups;
    }

}
