package ru.filit.mdma.crm.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * Запрос по clientId
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientIdDto {

  @NotNull
  private String clientId;

}

