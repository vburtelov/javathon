package ru.filit.mdma.dms.web.dto;



import lombok.Getter;
import lombok.Setter;


/**
 * Уровни клиентов
 */
@Getter
@Setter
public class ClientLevelDto implements Cloneable{


  private String level;

  private String accountNumber;

  private String avgBalance;

}

