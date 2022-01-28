package ru.filit.mdma.dms.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Запрос прав доступа для роли
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessRequestDto implements Cloneable  {

  private String role;

  private String version;


}

