package ru.filit.mdma.crm.web.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Запрос по accountNumber
 */
@Getter
@Setter
public class AccountNumberDto {

  @NotNull
  private String accountNumber;

}

