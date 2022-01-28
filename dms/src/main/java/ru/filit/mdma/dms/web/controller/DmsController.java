package ru.filit.mdma.dms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.filit.mdma.dms.auditor.Auditor;
import ru.filit.mdma.dms.auditor.model.AuditMessage;
import ru.filit.mdma.dms.exception.ClientException;
import ru.filit.mdma.dms.exception.WrongDataException;
import ru.filit.mdma.dms.model.Role;
import ru.filit.mdma.dms.service.DmsService;
import ru.filit.mdma.dms.service.Masking;
import ru.filit.mdma.dms.web.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping()
public class DmsController {
    @Autowired
    private DmsService dmsService;
    @Autowired
    private Auditor auditor;
    @Autowired
    private Masking masking;

    public Map<String, String> createError(Exception e, HttpStatus httpStatus){
        Map<String, String> error =new HashMap<>();
        error.put("timestamp", new Date().toString());
        error.put("status", httpStatus.toString());
        error.put("message", e.getMessage());
        return error;
    }

    private final Role auditRole =Role.AUDITOR;

    private Role getRole(HttpHeaders headers) throws WrongDataException {
        try {
            Role role = Role.valueOf(headers.getFirst("CRM-User-Role"));
            return role;
        }catch (IllegalArgumentException | NullPointerException e){
            throw new WrongDataException("Wrong user role");
        }
    }

    private String getUserName(HttpHeaders headers){
        return headers.getFirst("CRM-User-Name");
    }


    //Поиск клиента по параметрам
    @PostMapping("/client")
    public List<ClientDto> searchClients(@RequestBody ClientSearchDto clientSearchDto, @RequestHeader HttpHeaders headers, HttpServletRequest httpServletRequest) throws WrongDataException, ClientException {
        String key =  Auditor.createRequestKey();
        auditor.createAndSend(clientSearchDto, key, AuditMessage.TypeEnum.REQUEST, getUserName(headers),httpServletRequest.getRequestURI());
        try {
            List<ClientDto> clients = dmsService.findClients(clientSearchDto);
            auditor.createAndSend(masking.maskListOfObjects(clients, auditRole), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            return masking.maskListOfObjects(clients, getRole(headers));
        } catch (WrongDataException e) {
            auditor.createAndSend(createError(e,HttpStatus.NOT_FOUND), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new WrongDataException(e.getMessage());
        } catch (ClientException e) {
            auditor.createAndSend(e.getBody(), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new ClientException(e.getStatus(),e.getBody());
        }

    }

    //Запрос контактов клиента
    @PostMapping("/client/contact")
    public List<ContactDto> searchContacts(@RequestBody ClientIdDto id, @RequestHeader HttpHeaders headers, HttpServletRequest httpServletRequest) throws ClientException, WrongDataException {
        String key =  Auditor.createRequestKey();
        auditor.createAndSend(id, key, AuditMessage.TypeEnum.REQUEST, getUserName(headers),httpServletRequest.getRequestURI());

        try {
            List<ContactDto> contacts = dmsService.getClientContacts(id);
            auditor.createAndSend(masking.maskListOfObjects(contacts, auditRole), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers),httpServletRequest.getRequestURI());
            return masking.maskListOfObjects(contacts, getRole(headers));
        } catch (ClientException e) {
            auditor.createAndSend(e.getBody(), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new ClientException(e.getStatus(),e.getBody());
        } catch (WrongDataException e) {
            auditor.createAndSend(createError(e,HttpStatus.NOT_FOUND), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new WrongDataException(e.getMessage());
        }
    }

    //Запрос счетов коиента
    @PostMapping("/client/account")
    public List<AccountDto> searchAccounts(@RequestBody ClientIdDto id, @RequestHeader HttpHeaders headers, HttpServletRequest httpServletRequest) throws ClientException, WrongDataException {
        String key =  Auditor.createRequestKey();
        auditor.createAndSend(id, key, AuditMessage.TypeEnum.REQUEST, getUserName(headers),httpServletRequest.getRequestURI());

        try {
            List<AccountDto> accounts = dmsService.getAccounts(id);
            auditor.createAndSend(masking.maskListOfObjects(accounts, auditRole), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            return masking.maskListOfObjects(accounts, getRole(headers));
        } catch (ClientException e) {
            auditor.createAndSend(e.getBody(), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new ClientException(e.getStatus(),e.getBody());
        } catch (WrongDataException e) {
            auditor.createAndSend(createError(e,HttpStatus.NOT_FOUND), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new WrongDataException(e.getMessage());
        }
    }

    //Запрос баланса по счёту
    @PostMapping("/client/account/balance")
    public CurrentBalanceDto getCurrentBalance(@RequestBody AccountNumberDto accountNumber, @RequestHeader HttpHeaders headers, HttpServletRequest httpServletRequest) throws WrongDataException, ClientException {
        String key =  Auditor.createRequestKey();
        auditor.createAndSend(accountNumber, key, AuditMessage.TypeEnum.REQUEST, getUserName(headers),httpServletRequest.getRequestURI());

        try {
            CurrentBalanceDto currentBalanceDto = dmsService.getCurrentBalance(accountNumber);
            auditor.createAndSend(masking.maskObject(currentBalanceDto, auditRole), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            return masking.maskObject(currentBalanceDto, getRole(headers));
        } catch (ClientException e) {
            auditor.createAndSend(e.getBody(), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new ClientException(e.getStatus(),e.getBody());
        } catch (WrongDataException e) {
            auditor.createAndSend(createError(e,HttpStatus.NOT_FOUND), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new WrongDataException(e.getMessage());
        }
    }

    //Запрос операций по счёту
    @PostMapping("/client/account/operation")
    public List<OperationDto> getOperations(@RequestBody OperationSearchDto operationSearchDto, @RequestHeader HttpHeaders headers, HttpServletRequest httpServletRequest) throws WrongDataException, ClientException {
        String key =  Auditor.createRequestKey();
        auditor.createAndSend(operationSearchDto, key, AuditMessage.TypeEnum.REQUEST, getUserName(headers),httpServletRequest.getRequestURI());

        try {
            List<OperationDto> operations = dmsService.getLastOperations(operationSearchDto);
            auditor.createAndSend(masking.maskListOfObjects(operations, auditRole), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            return masking.maskListOfObjects(operations, getRole(headers));
        } catch (ClientException e) {
            auditor.createAndSend(e.getBody(), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new ClientException(e.getStatus(),e.getBody());
        } catch (WrongDataException e) {
            auditor.createAndSend(createError(e,HttpStatus.NOT_FOUND), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new WrongDataException(e.getMessage());
        }
    }

    //Сохранение Контактов клиента
    @PostMapping("/client/contact/save")
    public ContactDto saveContact(@RequestBody ContactDto contactDto, @RequestHeader HttpHeaders headers,HttpServletRequest httpServletRequest) throws WrongDataException, ClientException {
        String key =  Auditor.createRequestKey();
        auditor.createAndSend(contactDto, key, AuditMessage.TypeEnum.REQUEST, getUserName(headers),httpServletRequest.getRequestURI());

        try {
            ContactDto contact = dmsService.saveContact(contactDto);
            auditor.createAndSend(masking.maskObject(contact, auditRole), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            return masking.maskObject(contact, getRole(headers));
        } catch (ClientException e) {
            auditor.createAndSend(e.getBody(), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new ClientException(e.getStatus(),e.getBody());
        } catch (WrongDataException e) {
            auditor.createAndSend(createError(e,HttpStatus.NOT_FOUND), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new WrongDataException(e.getMessage());
        }
    }

    //Получение уровня клиента
    @PostMapping("/client/level")
    public ClientLevelDto clientLevel(@RequestBody ClientIdDto clientId, @RequestHeader HttpHeaders headers, HttpServletRequest httpServletRequest) throws WrongDataException, ClientException {
        String key =  Auditor.createRequestKey();
        auditor.createAndSend(clientId, key, AuditMessage.TypeEnum.REQUEST, getUserName(headers),httpServletRequest.getRequestURI());

        try{
            ClientLevelDto clientLevel = dmsService.calculateClientLevel(clientId);
            auditor.createAndSend(masking.maskObject(clientLevel, auditRole), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers),httpServletRequest.getRequestURI());
            return  masking.maskObject(clientLevel, getRole(headers));
        } catch (ClientException e) {
            auditor.createAndSend(e.getBody(), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new ClientException(e.getStatus(),e.getBody());
        } catch (WrongDataException e) {
            auditor.createAndSend(createError(e,HttpStatus.NOT_FOUND), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new WrongDataException(e.getMessage());
        }
    }

    //Получение процентов по кредиту
    @PostMapping("/client/account/loan-payment")
    public LoanPaymentDto clientLevel(@RequestBody AccountNumberDto accountNumber, @RequestHeader HttpHeaders headers, HttpServletRequest httpServletRequest) throws WrongDataException, ClientException {
        String key =  Auditor.createRequestKey();
        auditor.createAndSend(accountNumber, key, AuditMessage.TypeEnum.REQUEST, getUserName(headers),httpServletRequest.getRequestURI());

        try {
            LoanPaymentDto loanPayment = dmsService.getOverdraft(accountNumber);
            auditor.createAndSend(masking.maskObject(loanPayment, auditRole), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            return masking.maskObject(loanPayment, getRole(headers));
        } catch (ClientException e) {
            auditor.createAndSend(e.getBody(), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new ClientException(e.getStatus(),e.getBody());
        } catch (WrongDataException e) {
            auditor.createAndSend(createError(e,HttpStatus.NOT_FOUND), key, AuditMessage.TypeEnum.RESPONSE, getUserName(headers), httpServletRequest.getRequestURI());
            throw new WrongDataException(e.getMessage());
        }
    }


}
