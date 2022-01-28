package ru.filit.mdma.dms.web.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * Контактные данные клиента
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactDto implements Cloneable{

  private String id;

  @NotNull
  private String clientId;

  @NotNull
  private String type;

  @NotNull
  private String value;

  private String shortcut;

}

