package ru.filit.mdma.dms.web.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * Запрос операций по счету
 */
@Getter
@Setter
public class OperationSearchDto implements Cloneable{

  @NotNull
  private String accountNumber;


  @NotNull
  private Integer quantity;

}

