package ru.filit.mdma.dms.web.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


/**
 * Банковские счета клиента
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto  implements Cloneable {

  private String number;

  private String clientId;

  private String type;

  private String currency;

  private String status;

  private String openDate;

  private String closeDate;

  private String deferment;

  private String shortcut;

  private String balanceAmount;

}

