package ru.filit.mdma.crm.web.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;


/**
 * Контактные данные клиента
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactDto {

  private String id;

  @NotNull
  private String clientId;

  @NotNull
  private String type;

  @NotNull
  private String value;

  private String shortcut;

}

