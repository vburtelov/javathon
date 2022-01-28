package ru.filit.mdma.dms.web.dto;


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
public class ClientIdDto implements Cloneable{

  @NotNull
  private String clientId;

}

