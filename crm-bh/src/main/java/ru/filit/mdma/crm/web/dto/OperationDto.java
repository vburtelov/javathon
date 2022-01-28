package ru.filit.mdma.crm.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;



/**
 * Банковские операции по счету
 */
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationDto {

  private String type;

  private String accountNumber;

  private String operDate;

  private String amount;

  private String description;

}

