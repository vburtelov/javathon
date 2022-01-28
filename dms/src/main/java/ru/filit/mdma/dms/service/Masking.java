package ru.filit.mdma.dms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.filit.mdma.dms.exception.ClientException;
import ru.filit.mdma.dms.exception.WrongDataException;
import ru.filit.mdma.dms.model.Role;
import ru.filit.mdma.dms.web.dto.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

@Component
public class Masking {

    private Logger logger = LoggerFactory.getLogger(Masking.class);

    @Autowired
    private DmsService dmsService;

    public <T> T maskObject(T object, Role role) throws WrongDataException, ClientException {

        T newObject= null;
        try {
            newObject = (T) object.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new WrongDataException("Can't mask");
        }
        List<AccessDto> accessList=dmsService.getAccess(role);
        Field[] fields = newObject.getClass().getDeclaredFields();
        String entityName;
        if(object instanceof AccountDto){
            entityName="account";
        }else if(object instanceof ClientDto){
            entityName="client";
        }else if(object instanceof ContactDto) {
            entityName = "contact";
        }else if(object instanceof CurrentBalanceDto){
            entityName="currentBalance";
        }else if(object instanceof ClientLevelDto){
            entityName="clientLevel";
        }else if(object instanceof LoanPaymentDto) {
            entityName = "loanPayment";
        }else if(object instanceof OperationDto) {
            entityName = "operation";
        }else {
            logger.warn("Cannot mask "+object.getClass()+". Wrong entity name ");
            throw new WrongDataException("Wrong entity name");
        }
        for (Field field:fields){
            field.setAccessible(true);
            try {
                if (!accessList.contains(new AccessDto(entityName, field.getName()))) {
                    field.set(newObject, "****");
                }else {
                    field.set(newObject, field.get(object));
                }
            } catch (IllegalAccessException e) {
                throw new WrongDataException(e.getMessage());
            }
            field.setAccessible(false);
        }
        return newObject;
    }

    public <T> List<T> maskListOfObjects(List<T> list, Role role) throws WrongDataException, ClientException {
        List<T> newList=new LinkedList<>();
        for (T o : list) {
            newList.add(maskObject(o, role));
        }
        return newList;
    }
}
