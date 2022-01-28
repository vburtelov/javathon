package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.exception.WrongDataException;
import ru.filit.mdma.dm.model.Contact;
import ru.filit.mdma.dm.web.dto.ContactDto;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ContactService {
    @Autowired
    private YamlService yamlService;
    @Autowired
    private ClientService clientService;

    private final URL contactsFileUrl;

    public ContactService(@Value("${datafiles.contacts}") String contactsFileName){
        this.contactsFileUrl = getClass().getClassLoader().getResource(contactsFileName);
    }

    public List<Contact> getClientContacts(String clientId) throws IOException {
        List<Contact> contacts=yamlService.readYaml(contactsFileUrl, Contact.class);
        return contacts.stream().filter((c)-> c.getClientId().equals(clientId)).collect(Collectors.toList());
    }

    public Contact saveContact(ContactDto contactDto) throws WrongDataException, IOException {
        List<Contact> contacts=yamlService.readYaml(contactsFileUrl, Contact.class);
        if(contactDto.getId()!=null){
            Contact updatedContact = contacts.stream().filter((c)-> c.getId().equals(contactDto.getId()) &&
                    c.getClientId().equals(contactDto.getClientId())
            ).findFirst().orElseThrow(()->new WrongDataException("No contact with id: "+contactDto.getId()+" for client: "+contactDto.getClientId()));
            updatedContact.setType(contactDto.getType());
            updatedContact.setValue(contactDto.getValue());
            yamlService.writeYaml(contactsFileUrl, contacts);
            return updatedContact;
        }else{
            clientService.findClientById(contactDto.getClientId());
            Contact newContact=Contact.fromDto(contactDto);
            int id=(Integer.parseInt(newContact.getClientId())*(new Random().nextInt(100)))%100000;
            newContact.setId(String.valueOf(id));
            contacts.add(newContact);
            contacts.sort(Comparator.comparing(Contact::getClientId));
            yamlService.writeYaml(contactsFileUrl, contacts);
            return newContact;
        }
    }


}
