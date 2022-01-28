package ru.filit.mdma.crm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.filit.mdma.crm.model.User;
import ru.filit.mdma.crm.web.dto.*;

import java.security.Principal;
import java.util.*;

@Service
public class CrmService {

    @Autowired
    private DmClient dmClient;

    public List<ClientDto> searchClients(ClientSearchDto client, UserDetails user) throws ClientException {
        return dmClient.searchClients(client, user);
    }

    public ClientInfoDto getClientInfo(ClientIdDto clientId, UserDetails user) throws ClientException {
        ClientSearchDto clientSearchDto=new ClientSearchDto();
        clientSearchDto.setId(clientId.getClientId());
        List<ClientDto> client = dmClient.searchClients(clientSearchDto, user);
        if(client.isEmpty()){
            Map<String, String> error =new HashMap<>();
            error.put("timestamp", new Date().toString());
            error.put("status", HttpStatus.NOT_FOUND.toString());
            error.put("message", "Client not found");
            try {
                throw new ClientException(HttpStatus.NOT_FOUND, error );
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        List<ContactDto> contacts =dmClient.getContacts(clientId, user);
        List<AccountDto> accounts =dmClient.getAccounts(clientId, user);
        return new ClientInfoDto(client.get(0),contacts,accounts);

    }

    public List<OperationDto> getLastOperations(AccountNumberDto accountNumber, int n, UserDetails user) throws ClientException {
        OperationSearchDto operationSearchDto =new OperationSearchDto();
        operationSearchDto.setQuantity(n);
        operationSearchDto.setAccountNumber(accountNumber.getAccountNumber());
        return dmClient.getOperations(operationSearchDto, user);
    }

    public ContactDto saveContact(ContactDto contact, UserDetails user) throws ClientException {
        return dmClient.saveContact(contact, user);
    }

    public ClientLevelDto getClientLevel(ClientIdDto clientId, UserDetails user) throws ClientException {
        return dmClient.getClientLevel(clientId, user);
    }

    public LoanPaymentDto getOverdraft(AccountNumberDto accountNumber, UserDetails user) throws ClientException {
        return dmClient.getOverdraft(accountNumber,user);
    }
}
