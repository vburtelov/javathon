package ru.filit.mdma.dms.web.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * Клиент банка, Физ. лицо
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto implements Cloneable{

  @NotNull
  private String id;
  @NotNull
  private String lastname;
  @NotNull
  private String firstname;

  private String patronymic;

  @NotNull
  private String birthDate;
  @NotNull
  private String passportSeries;
  @NotNull
  private String passportNumber;
  @NotNull
  private String inn;

  private String address;


}

