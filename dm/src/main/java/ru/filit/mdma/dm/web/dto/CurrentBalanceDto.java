package ru.filit.mdma.dm.web.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.filit.mdma.dm.service.MoneySerializer;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * Значение текущего баланса счета
 */
@Getter
@Setter
@AllArgsConstructor
public class CurrentBalanceDto   {

  @NotNull
  @JsonSerialize(using = MoneySerializer.class)
  private BigDecimal balanceAmount;


}

