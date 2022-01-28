package ru.filit.mdma.crm.web.dto;



import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * Начисленные платежи по кредиту
 */
@Setter
@Getter
public class LoanPaymentDto {

  private BigDecimal amount;

}

