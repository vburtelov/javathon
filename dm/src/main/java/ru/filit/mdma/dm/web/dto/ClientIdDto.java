package ru.filit.mdma.dm.web.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * Запрос по clientId
 */
@Getter
@Setter
public class ClientIdDto   {

  @NotNull
  private String clientId;

}

