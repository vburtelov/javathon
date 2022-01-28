package ru.filit.mdma.dms.web.dto;


import lombok.Getter;
import lombok.Setter;


/**
 * Значение текущего баланса счета
 */
@Getter
@Setter
public class CurrentBalanceDto   implements Cloneable{

  private String balanceAmount;


}

