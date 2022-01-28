package ru.filit.mdma.crm.web.dto;



import lombok.Getter;
import lombok.Setter;



/**
 * Уровни клиентов
 */
@Getter
@Setter
public class ClientLevelDto {


  private String level;

  private String accountNumber;

  private String avgBalance;

}

