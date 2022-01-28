package ru.filit.mdma.dm.web.dto;

import lombok.Data;


/**
 * Запрос прав доступа для роли
 */
@Data
public class AccessRequestDto   {

  private String role;

  private String version;


}

