package ru.filit.mdma.dm.web.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.filit.mdma.dm.model.ClientLevel;
import ru.filit.mdma.dm.service.MoneySerializer;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * Уровни клиентов
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientLevelDto   {

  @NotNull
  private ClientLevel level;
  @NotNull
  private String accountNumber;
  @NotNull
  @JsonSerialize(using = MoneySerializer.class)
  private BigDecimal avgBalance;

}

