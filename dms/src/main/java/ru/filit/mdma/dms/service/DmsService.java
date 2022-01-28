package ru.filit.mdma.dms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dms.exception.ClientException;
import ru.filit.mdma.dms.exception.WrongDataException;
import ru.filit.mdma.dms.model.Role;
import ru.filit.mdma.dms.web.dto.*;

import java.util.List;

@Service
public class DmsService {

    @Autowired
    private DmClient dmClient;

    private final String accessVersion = "2";

    public List<AccessDto> getAccess(Role role) throws ClientException {

        return dmClient.getAccess(new AccessRequestDto(role.getValue(), accessVersion));
    }

    public List<ClientDto> findClients(ClientSearchDto clientSearchDto) throws ClientException {
        List<ClientDto> clients = dmClient.searchClients(clientSearchDto);
        return clients;
    }

    public List<ContactDto> getClientContacts(ClientIdDto id) throws ClientException {
        List<ContactDto> contacts = dmClient.getContacts(id);
        return contacts;
    }

    public List<AccountDto> getAccounts(ClientIdDto id) throws ClientException {
        List<AccountDto> accounts = dmClient.getAccounts(id);
        return accounts;
    }

    public CurrentBalanceDto getCurrentBalance(AccountNumberDto accountNumber) throws ClientException {
        CurrentBalanceDto currentBalance =  dmClient.getBalance(accountNumber);
        return currentBalance;
    }

    public List<OperationDto> getLastOperations(OperationSearchDto operationSearchDto) throws ClientException {
        List<OperationDto> operations =  dmClient.getOperations(operationSearchDto);
        return operations;
    }

    public ContactDto saveContact(ContactDto contactDto) throws ClientException {
        ContactDto contact = dmClient.saveContact(contactDto);
        return contact;
    }

    public ClientLevelDto calculateClientLevel(ClientIdDto clientId) throws ClientException {
        ClientLevelDto clientLevel =  dmClient.getClientLevel(clientId);
        return clientLevel;
    }

    public LoanPaymentDto getOverdraft(AccountNumberDto accountNumber) throws ClientException {
        LoanPaymentDto loanPayment =  dmClient.getOverdraft(accountNumber);
        return  loanPayment;
    }
}
