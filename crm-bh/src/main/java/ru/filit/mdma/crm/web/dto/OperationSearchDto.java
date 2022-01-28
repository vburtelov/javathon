package ru.filit.mdma.crm.web.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;


/**
 * Запрос операций по счету
 */
@Getter
@Setter
public class OperationSearchDto {

  @NotNull
  private String accountNumber;


  @NotNull
  private Integer quantity;

}

