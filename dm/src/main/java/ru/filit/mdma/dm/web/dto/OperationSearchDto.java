package ru.filit.mdma.dm.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * Запрос операций по счету
 */
@Setter
@Getter
public class OperationSearchDto   {

  @NotNull
  private String accountNumber;


  @NotNull
  private Integer quantity;

}

