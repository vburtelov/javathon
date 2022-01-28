package ru.filit.mdma.dm.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * Права доступа к полям сущностей
 */
@Getter
@Setter
@AllArgsConstructor
public class AccessDto  {

  private String entity;

  private String property;

}

