package ru.filit.mdma.crm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.filit.mdma.crm.service.ClientException;
import ru.filit.mdma.crm.service.CrmService;
import ru.filit.mdma.crm.web.dto.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping
public class CrmController {

    @Autowired
    private CrmService crmService;

    /**
     * Поиск клиентов по фильтру
     */
    @PostMapping("client/find")
    public List<ClientDto> searchClients(@RequestBody ClientSearchDto client, Authentication authentication) throws ClientException {
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return crmService.searchClients(client, userDetails);
    }

    /**
     * Поиск информации о клиенте по id
     */
    @PostMapping("/client")
    public ClientInfoDto getClientInfo(@RequestBody ClientIdDto id,Authentication authentication) throws ClientException {
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return crmService.getClientInfo(id, userDetails);
    }

    /**
     * Получение последних 3 операций по счёту
     */
    @PostMapping("/client/account/last-operations")
    public List<OperationDto> getOperations(@RequestBody AccountNumberDto accountNumber,Authentication authentication) throws ClientException {
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return crmService.getLastOperations(accountNumber ,3, userDetails);
    }

    /**
     * Сохранение контакта клиента
     */
    @PostMapping("/client/contact/save")
    public ContactDto saveContact(@RequestBody ContactDto contact,Authentication authentication) throws ClientException {
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return crmService.saveContact(contact, userDetails);
    }

    /**
     * Получение уровня клиента
     */
    @PostMapping("/client/level")
    public ClientLevelDto getClientLevel(@RequestBody ClientIdDto id,Authentication authentication) throws ClientException {
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return crmService.getClientLevel(id, userDetails);
    }

    /**
     * Получение суммы процентных платежей по счету Овердрафт
     */
    @PostMapping("/client/account/loan-payment")
    public LoanPaymentDto getOverdraft(@RequestBody AccountNumberDto accountNumber,Authentication authentication) throws ClientException {
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        return crmService.getOverdraft(accountNumber, userDetails);
    }


}
