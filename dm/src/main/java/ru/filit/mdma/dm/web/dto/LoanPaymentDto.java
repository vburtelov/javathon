package ru.filit.mdma.dm.web.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.filit.mdma.dm.service.MoneySerializer;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Начисленные платежи по кредиту
 */
@Setter
@Getter
@AllArgsConstructor
public class LoanPaymentDto   {

  @NotNull
  @JsonSerialize(using = MoneySerializer.class)
  private BigDecimal amount;

}

