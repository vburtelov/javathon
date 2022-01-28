package ru.filit.mdma.dms.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientInfoDto implements Cloneable{

    private String id;

    private String lastname;

    private String firstname;

    private String patronymic;

    private String birthDate;

    private String passportSeries;

    private String passportNumber;

    private String inn;

    private String address;

    private List<ContactDto> contacts;

    private List<AccountDto> accounts;

    public ClientInfoDto(ClientDto client, List<ContactDto> contacts, List<AccountDto> accounts){
        this.contacts=contacts;
        this.accounts=accounts;
        this.id=client.getId();
        this.lastname=client.getLastname();
        this.firstname=client.getFirstname();
        this.patronymic=client.getPatronymic();
        this.birthDate=client.getBirthDate();
        this.passportSeries=client.getPassportSeries();
        this.passportNumber=client.getPassportNumber();
        this.inn=client.getInn();
        this.address=client.getAddress();
    }
}
