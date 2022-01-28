package ru.filit.mdma.dms.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


/**
 * Права доступа к полям сущностей
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessDto  implements Cloneable{

  private String entity;

  private String property;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AccessDto accessDto = (AccessDto) o;
    return Objects.equals(entity, accessDto.entity) && Objects.equals(property, accessDto.property);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entity, property);
  }

}

