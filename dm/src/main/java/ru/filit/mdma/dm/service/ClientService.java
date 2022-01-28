package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.exception.WrongDataException;
import ru.filit.mdma.dm.model.Account;
import ru.filit.mdma.dm.model.Client;
import ru.filit.mdma.dm.model.ClientLevel;
import ru.filit.mdma.dm.web.dto.ClientLevelDto;
import ru.filit.mdma.dm.web.dto.ClientSearchDto;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private YamlService yamlService;

    @Autowired
    private AccountService accountService;

    private final URL clientsFileUrl;

    public ClientService(@Value("${datafiles.clients}") String clientsFileName){
        this.clientsFileUrl = getClass().getClassLoader().getResource(clientsFileName);
    }


    public Client findClientById(String id) throws WrongDataException, IOException {
        List<Client> clients =yamlService.readYaml(clientsFileUrl, Client.class);
        return clients.stream().filter((c)-> Objects.equals(c.getId(), id)).findFirst().orElseThrow(()->new WrongDataException("No client with id:"+id));
    }

    public List<Client> findClients(ClientSearchDto client) throws IOException {
        List<Client> clients =yamlService.readYaml(clientsFileUrl, Client.class);
        return clients.stream().filter((c)->
                (client.getId()==null || c.getId().equals(client.getId())) &&
                        (client.getLastname()==null || c.getLastname().equals(client.getLastname())) &&
                        (client.getFirstname()==null || c.getFirstname().equals(client.getFirstname()) ) &&
                        (client.getPatronymic()==null || c.getPatronymic().equals(client.getPatronymic())) &&
                        (client.getBirthDate()==null || c.getBirthDate()*1000L==client.getBirthDate().getTime()) &&
                        (client.getPassport()==null || client.getPassport().equals(c.getPassportSeries()+" "+c.getPassportNumber())) &&
                        ( client.getInn()==null || c.getInn().equals(client.getInn()))
        ).collect(Collectors.toList());
    }

    public ClientLevelDto calculateClientLevel(String id) throws IOException, WrongDataException, NullPointerException {
        List<Account> clientAccounts = accountService.getAccounts(id);
        Map<String,BigDecimal> svos=new HashMap<>();
        for (Account a: clientAccounts) {
            svos.put(a.getNumber(), accountService.getSVO(a.getNumber()));
        }
        BigDecimal maxSvo= Collections.max(svos.entrySet(), Map.Entry.comparingByValue()).getValue();
        String bestAccountNumber= Collections.max(svos.entrySet(), Map.Entry.comparingByValue()).getKey();
        ClientLevel cl=ClientLevel.Low;
        if(maxSvo.doubleValue()<30000){
            cl=ClientLevel.Low;
        }else if(maxSvo.doubleValue() >=30000 && maxSvo.doubleValue() <300000){
            cl=ClientLevel.Middle;
        }else if (maxSvo.doubleValue() >=300000 && maxSvo.doubleValue() <1000000){
            cl=ClientLevel.Silver;
        }else if (maxSvo.doubleValue() >=1000000 ){
            cl=ClientLevel.Gold;
        }
        ClientLevelDto clientLevelDto=new ClientLevelDto();
        clientLevelDto.setLevel(cl);
        clientLevelDto.setAccountNumber(bestAccountNumber);
        clientLevelDto.setAvgBalance(maxSvo);
        return clientLevelDto;
    }
    
}
