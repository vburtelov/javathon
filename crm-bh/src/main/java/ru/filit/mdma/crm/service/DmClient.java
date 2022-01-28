package ru.filit.mdma.crm.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.filit.mdma.crm.web.dto.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Component
public class DmClient {

    private final RestTemplate restTemplate=new RestTemplate();

    private final String dmRequest;

    private final String searchClientsUrl="/client";
    private final String getContactsUrl="/client/contact";
    private final String getAccountsUrl="/client/account";
    private final String getBalanceUrl="/client/account/balance";
    private final String getOperationsUrl="/client/account/operation";
    private final String saveContactUrl="/client/contact/save";
    private final String getLevelUrl="/client/level";
    private final String getOverdraftUrl="/client/account/loan-payment";

    public DmClient(@Value("${system.element.dm.host}") String host, @Value("${system.element.dm.port}") String port){
        dmRequest="http://"+host+":"+port+"/dm";
    }

    private <T> HttpEntity<T> createHttpEntity(T body, UserDetails user) throws ClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("CRM-User-Role", user.getAuthorities().stream().findFirst().
                orElseThrow(()-> new ClientException(HttpStatus.UNAUTHORIZED, "Client hasn't got authorities")).toString());
        headers.add("CRM-User-Name", user.getUsername());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> entity = new HttpEntity<>(body, headers);
        return entity;
    }

    /**
     * /client"
     */
    public List<ClientDto> searchClients(ClientSearchDto clientSearchDto, UserDetails user) throws ClientException {
        HttpEntity<ClientSearchDto> entity = createHttpEntity(clientSearchDto, user);
        try {
            ResponseEntity<ClientDto[]> response = restTemplate.postForEntity(dmRequest+searchClientsUrl, entity, ClientDto[].class);
            return Arrays.asList(response.getBody());
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * /client/contact
     */
    public List<ContactDto> getContacts(ClientIdDto clientIdDto, UserDetails user) throws ClientException {
        HttpEntity<ClientIdDto> entity = createHttpEntity(clientIdDto, user);
        try {
            ResponseEntity<ContactDto[]> response = restTemplate.postForEntity(dmRequest+getContactsUrl, entity, ContactDto[].class);
            return Arrays.asList(response.getBody());
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * /client/account
     */
    public List<AccountDto> getAccounts(ClientIdDto clientIdDto, UserDetails user) throws ClientException {
        HttpEntity<ClientIdDto> entity = createHttpEntity(clientIdDto, user);
        try {
            ResponseEntity<AccountDto[]> response = restTemplate.postForEntity(dmRequest+getAccountsUrl, entity, AccountDto[].class);
            return Arrays.asList(response.getBody());
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * /client/account/balance
     */
    public CurrentBalanceDto getBalance(AccountNumberDto accountNumberDto, UserDetails user) throws ClientException {
        HttpEntity<AccountNumberDto> entity = createHttpEntity(accountNumberDto, user);
        try {
            ResponseEntity<CurrentBalanceDto> response = restTemplate.postForEntity(dmRequest+getBalanceUrl, entity, CurrentBalanceDto.class);
            return response.getBody();
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * /client/account/operation"
     */
    public List<OperationDto> getOperations(OperationSearchDto operationSearchDto, UserDetails user) throws ClientException {
        HttpEntity<OperationSearchDto> entity = createHttpEntity(operationSearchDto, user);
        try {
            ResponseEntity<OperationDto[]> response = restTemplate.postForEntity(dmRequest+getOperationsUrl, entity, OperationDto[].class);
            return Arrays.asList(response.getBody());
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * /client/contact/save
     */
    public ContactDto saveContact(ContactDto contactDto, UserDetails user) throws ClientException {
        HttpEntity<ContactDto> entity = createHttpEntity(contactDto, user);
        try {
            ResponseEntity<ContactDto> response = restTemplate.postForEntity(dmRequest+saveContactUrl, entity, ContactDto.class);
            return response.getBody();
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * /client/level
     */
    public ClientLevelDto getClientLevel(ClientIdDto clientIdDto,UserDetails user) throws ClientException {
        HttpEntity<ClientIdDto> entity = createHttpEntity(clientIdDto, user);
        try {
            ResponseEntity<ClientLevelDto> response = restTemplate.postForEntity(dmRequest+getLevelUrl, entity, ClientLevelDto.class);
            return response.getBody();
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * /client/account/loan-payment
     */
    public LoanPaymentDto getOverdraft(AccountNumberDto accountNumberDto, UserDetails user) throws ClientException {
        HttpEntity<AccountNumberDto> entity = createHttpEntity(accountNumberDto, user);
        try {
            ResponseEntity<LoanPaymentDto> response = restTemplate.postForEntity(dmRequest+getOverdraftUrl, entity, LoanPaymentDto.class);
            return response.getBody();
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

}

