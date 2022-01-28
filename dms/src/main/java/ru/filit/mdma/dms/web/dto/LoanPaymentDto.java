package ru.filit.mdma.dms.web.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Начисленные платежи по кредиту
 */
@Setter
@Getter
public class LoanPaymentDto implements Cloneable{

  private BigDecimal amount;

}

