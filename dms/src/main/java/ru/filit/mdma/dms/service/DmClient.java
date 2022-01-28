package ru.filit.mdma.dms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.filit.mdma.dms.exception.ClientException;
import ru.filit.mdma.dms.web.dto.*;

import java.util.Arrays;
import java.util.List;


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
    private final String getAccessUrl="/access";

    public DmClient(@Value("${system.element.dm.host}") String host, @Value("${system.element.dm.port}") String port){
        dmRequest="http://"+host+":"+port+"/dm";
    }

    private <T> HttpEntity<T> createHttpEntity(T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> entity = new HttpEntity<>(body, headers);
        return entity;
    }

    /**
     * Запрос поиска клиентов по фильтру
     */
    public List<ClientDto> searchClients(ClientSearchDto clientSearchDto) throws ClientException {
        HttpEntity<ClientSearchDto> entity = createHttpEntity(clientSearchDto);
        try {
            ResponseEntity<ClientDto[]> response = restTemplate.postForEntity(dmRequest+searchClientsUrl, entity, ClientDto[].class);
            return Arrays.asList(response.getBody());
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * Запрос контактов клиента
     */
    public List<ContactDto> getContacts(ClientIdDto clientIdDto) throws ClientException {
        HttpEntity<ClientIdDto> entity = createHttpEntity(clientIdDto);
        try {
            ResponseEntity<ContactDto[]> response = restTemplate.postForEntity(dmRequest+getContactsUrl, entity, ContactDto[].class);
            return Arrays.asList(response.getBody());
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * Запрос счетов клиента
     */
    public List<AccountDto> getAccounts(ClientIdDto clientIdDto) throws ClientException {
        HttpEntity<ClientIdDto> entity = createHttpEntity(clientIdDto);
        try {
            ResponseEntity<AccountDto[]> response = restTemplate.postForEntity(dmRequest+getAccountsUrl, entity, AccountDto[].class);
            return Arrays.asList(response.getBody());
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * Запрос баланса по счёту
     */
    public CurrentBalanceDto getBalance(AccountNumberDto accountNumberDto) throws ClientException {
        HttpEntity<AccountNumberDto> entity = createHttpEntity(accountNumberDto);
        try {
            ResponseEntity<CurrentBalanceDto> response = restTemplate.postForEntity(dmRequest+getBalanceUrl, entity, CurrentBalanceDto.class);
            return response.getBody();
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * Запрос операций по счету
     */
    public List<OperationDto> getOperations(OperationSearchDto operationSearchDto) throws ClientException {
        HttpEntity<OperationSearchDto> entity = createHttpEntity(operationSearchDto);
        try {
            ResponseEntity<OperationDto[]> response = restTemplate.postForEntity(dmRequest+getOperationsUrl, entity, OperationDto[].class);
            return Arrays.asList(response.getBody());
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * Сохранение контакта клиента
     */
    public ContactDto saveContact(ContactDto contactDto) throws ClientException {
        HttpEntity<ContactDto> entity = createHttpEntity(contactDto);
        try {
            ResponseEntity<ContactDto> response = restTemplate.postForEntity(dmRequest+saveContactUrl, entity, ContactDto.class);
            return response.getBody();
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * Получение уровня клиента
     */
    public ClientLevelDto getClientLevel(ClientIdDto clientIdDto) throws ClientException {
        HttpEntity<ClientIdDto> entity = createHttpEntity(clientIdDto);
        try {
            ResponseEntity<ClientLevelDto> response = restTemplate.postForEntity(dmRequest+getLevelUrl, entity, ClientLevelDto.class);
            return response.getBody();
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * Получение суммы процентных платежей по счету Овердрафт
     */
    public LoanPaymentDto getOverdraft(AccountNumberDto accountNumberDto) throws ClientException {
        HttpEntity<AccountNumberDto> entity = createHttpEntity(accountNumberDto);
        try {
            ResponseEntity<LoanPaymentDto> response = restTemplate.postForEntity(dmRequest+getOverdraftUrl, entity, LoanPaymentDto.class);
            return response.getBody();
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

    /**
     * Получение прав доступа
     */
    public List<AccessDto> getAccess(AccessRequestDto accessRequestDto) throws ClientException {
        HttpEntity<AccessRequestDto> entity = createHttpEntity(accessRequestDto);
        try {
            ResponseEntity<AccessDto[]> response = restTemplate.postForEntity(dmRequest+getAccessUrl, entity, AccessDto[].class);
            return Arrays.asList(response.getBody());
        } catch(HttpStatusCodeException e) {
            throw new ClientException(e.getStatusCode(), e.getResponseBodyAsString());
        }
    }

}

