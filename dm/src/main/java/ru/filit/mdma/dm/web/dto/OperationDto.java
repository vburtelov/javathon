package ru.filit.mdma.dm.web.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import ru.filit.mdma.dm.model.Operation;
import ru.filit.mdma.dm.service.MoneySerializer;
import javax.validation.constraints.*;

/**
 * Банковские операции по счету
 */
@Setter
@Getter
public class OperationDto   {
  @NotNull
  private Operation.TypeEnum type;
  @NotNull
  private String accountNumber;
  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
  private Date operDate;
  @NotNull
  @JsonSerialize(using = MoneySerializer.class)
  private BigDecimal amount;

  private String description;

  public static OperationDto fromEntity(Operation operation){
    OperationDto operationDto=new OperationDto();
    operationDto.setType(operation.getType());
    operationDto.setAccountNumber(operation.getAccountNumber());
    operationDto.setOperDate(new Date(operation.getOperDate()*1000L));
    operationDto.setAmount(operation.getAmount());
    operationDto.setDescription(operation.getDescription());
    return operationDto;
  }


}

