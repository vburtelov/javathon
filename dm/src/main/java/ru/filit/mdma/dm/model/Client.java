package ru.filit.mdma.dm.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * Клиент банка, Физ. лицо
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client implements Serializable {

  @NotNull
  private String id;

  @NotNull
  private String lastname;

  @NotNull
  private String firstname;

  private String patronymic;

  @NotNull
  private Long birthDate;

  @NotNull
  private String passportSeries;

  @NotNull
  private String passportNumber;

  @NotNull
  private String inn;

  private String address;

}

