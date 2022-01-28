package ru.filit.mdma.dm.web.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


import javax.validation.constraints.*;

/**
 * Запрос по accountNumber
 */
@Getter
@Setter
public class AccountNumberDto   {

  @NotBlank
  private String accountNumber;

}

