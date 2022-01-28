package ru.filit.mdma.dm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.mdma.dm.exception.WrongDataException;
import ru.filit.mdma.dm.service.*;
import ru.filit.mdma.dm.web.dto.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class DmController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OperationService operationService;

    @Autowired
    private AccessService accessService;

    //Поиск клиента по параметрам
    @PostMapping("/client")
    public List<ClientDto> searchClients(@RequestBody @Valid ClientSearchDto clientSearchDto) throws IOException {
        return clientService.findClients(clientSearchDto).stream().map(ClientDto::fromEntity).collect(Collectors.toList());
    }

    //Запрос контактов клиента
    @PostMapping("/client/contact")
    public List<ContactDto> searchContacts(@RequestBody @Valid ClientIdDto id) throws IOException {
        return contactService.getClientContacts(id.getClientId()).stream().map(ContactDto::fromEntity).collect(Collectors.toList());
    }

    //Запрос счетов коиента
    @PostMapping("/client/account")
    public List<AccountDto> searchAccounts(@RequestBody @Valid ClientIdDto id) throws IOException {
        return accountService.getAccounts(id.getClientId()).stream().map(AccountDto::fromEntity).collect(Collectors.toList());
    }

    //Запрос баланса по счёту
    @PostMapping("/client/account/balance")
    public CurrentBalanceDto getCurrentBalance(@RequestBody @Valid AccountNumberDto accountNumber) throws WrongDataException, IOException {
        return new CurrentBalanceDto(accountService.getCurrentBalance(accountNumber.getAccountNumber()));
    }

    //Запрос операций по счёту
    @PostMapping("/client/account/operation")
    public List<OperationDto> getOperations(@RequestBody @Valid OperationSearchDto operationSearchDto) throws IOException {
        return operationService.getLastOperations(operationSearchDto.getAccountNumber(), operationSearchDto.getQuantity()).
                stream().map(OperationDto::fromEntity).collect(Collectors.toList());
    }

    //Сохранение Контактов клиента
    @PostMapping("/client/contact/save")
    public ContactDto saveContact(@RequestBody @Valid ContactDto contactDto) throws IOException, WrongDataException {
        return ContactDto.fromEntity(contactService.saveContact(contactDto));
    }

    //Получение уровня клиента
    @PostMapping("/client/level")
    public ClientLevelDto clientLevel(@RequestBody @Valid ClientIdDto clientId) throws WrongDataException, IOException {
        return clientService.calculateClientLevel(clientId.getClientId());
    }

    //Получение процентов по кредиту
    @PostMapping("/client/account/loan-payment")
    public LoanPaymentDto clientLevel(@Valid @RequestBody AccountNumberDto accountNumber) throws WrongDataException, IOException {
        return new LoanPaymentDto(accountService.getOverdraft(accountNumber.getAccountNumber()));
    }

    //Получение прав доступа для роли
    @PostMapping("/access")
    public List<AccessDto> getAccess(@RequestBody AccessRequestDto accessRequestDto) throws WrongDataException, IOException {
        return accessService.getAccess(accessRequestDto);
    }

}
